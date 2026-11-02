package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.UsuarioModel;
import Organizacao.Model.B2cModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Organizacao.Utilitarios.SenhaUtil;
import Organizacao.Utilitarios.CpfUtil;


// acessa e administra os registros de usuario
public class UsuarioDAO {

    // grava os dados de usuario no banco de dados
    public void salvar(UsuarioModel usuario) throws Exception {
        try (Connection conexao = Conexao_Banco.conectar()) {
            salvar(usuario, conexao);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void salvar(UsuarioModel usuario, Connection conexao) {
        // define a consulta sql de insercao para usuario
        String sql = "INSERT INTO \"USUARIO\" (nome, email, senha, primeiro_registro, tipo_usuario, telefone) VALUES (?, ?, ?, COALESCE(?, CURRENT_DATE), CAST(? AS public.tipo_usuario), ?)";

        // abre a conexao e prepara a insercao de usuario com fechamento automatico
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_usuario"})) {

            // associa os campos de usuario aos parametros da insercao
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, senhaParaGravar(usuario.getSenha()));
            stmt.setDate(4, usuario.getPrimeiroRegistro());
            stmt.setString(5, usuario.getTipoUsuario());
            stmt.setString(6, usuario.getTelefone());

            // executa a gravacao dos dados de usuario
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage(), e);
        }
    }

    // consulta os registros de usuario e devolve a lista
    public List<UsuarioModel> listar() throws Exception {
        return listar(false);
    }

    public List<UsuarioModel> listar(boolean referencias) throws Exception {
        List<UsuarioModel> lista = new ArrayList<>();
        // define a consulta sql de leitura para usuario
        String sql = "SELECT u.\"ID_usuario\" AS id_usuario, u.nome, u.email, NULL::text AS senha, u.primeiro_registro, u.tipo_usuario, u.telefone, b.\"CPF\" AS cpf FROM \"USUARIO\" u LEFT JOIN \"B2C\" b ON b.\"ID_usuario\" = u.\"ID_usuario\" ORDER BY u.\"ID_usuario\"";

        // abre a conexao e executa a consulta de usuario com fechamento automatico
        if (!referencias) sql = sql.replace("ORDER BY", "WHERE u.tipo_usuario = 'B2C' ORDER BY");

        try (Connection conn = Conexao_Banco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em usuario
            while (rs.next()) {
                UsuarioModel u = new UsuarioModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getDate("primeiro_registro"),
                        rs.getString("tipo_usuario"),
                        rs.getString("telefone")
                );
                u.setCpf(CpfUtil.descriptografar(rs.getString("cpf")));
                lista.add(u);
            }
        }
        // devolve os registros encontrados pela consulta
        return lista;
    }

    // a senha nunca vai pro banco em texto puro: guarda o hash (pbkdf2); se ja vier em hash, mantem
    private String senhaParaGravar(String senha) {
        if (senha == null || senha.isEmpty()) {
            return null;
        }
        return senha.startsWith("pbkdf2$") ? senha : SenhaUtil.gerarHash(senha);
    }

    // busca um usuario pelo e-mail (usado no login); devolve null se nao existir
    public UsuarioModel buscarPorEmail(String email) throws Exception {
        String sql = "SELECT \"ID_usuario\" AS id_usuario, nome, email, senha, primeiro_registro, tipo_usuario, telefone FROM \"USUARIO\" WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = Conexao_Banco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioModel(
                            rs.getInt("id_usuario"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getDate("primeiro_registro"),
                            rs.getString("tipo_usuario"),
                            rs.getString("telefone")
                    );
                }
            }
        }
        return null;
    }

    // atualiza os dados de usuario no banco de dados
    public void atualizarUsuario(UsuarioModel usuario) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            atualizarUsuario(usuario, conexao);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void atualizarUsuario(UsuarioModel usuario, Connection conexao) {
        // define a consulta sql de atualizacao para usuario
        String sql = "UPDATE \"USUARIO\" SET nome = ?, email = ?, senha = COALESCE(?, senha), tipo_usuario = CAST(? AS public.tipo_usuario), telefone = ? WHERE \"ID_usuario\" = ?";

        // abre a conexao e prepara a atualizacao de usuario com fechamento automatico
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de usuario aos parametros da atualizacao
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, senhaParaGravar(usuario.getSenha()));
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setString(5, usuario.getTelefone());
            stmt.setInt(6, usuario.getId_usuario());

            // executa a atualizacao dos dados de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de usuario foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // propaga a falha ao atualizar usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // remove o registro de usuario do banco de dados
    public void deletarUsuario(int id_Usuario) {
        // define a consulta sql de exclusao para usuario
        String sql = "DELETE FROM \"USUARIO\" WHERE \"ID_usuario\" = ?";

        // abre a conexao e prepara a exclusao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            conexao.setAutoCommit(false);
            try {
                try (PreparedStatement pev = conexao.prepareStatement("DELETE FROM \"PEV\" WHERE \"ID_usuario\" = ?")) {
                    pev.setInt(1, id_Usuario);
                    pev.executeUpdate();
                }
                try (PreparedStatement cliente = conexao.prepareStatement("DELETE FROM \"B2C\" WHERE \"ID_usuario\" = ?")) {
                    cliente.setInt(1, id_Usuario);
                    cliente.executeUpdate();
                }
                // associa o identificador do registro aos parametros da exclusao
                stmt.setInt(1, id_Usuario);

                // executa a exclusao do registro de usuario
                int linhasAfetadas = stmt.executeUpdate();

                // verifica se algum registro de usuario foi removido
                if (linhasAfetadas > 0) {
                    System.out.println("Usuário deletado com sucesso!");
                } else {
                    throw new java.util.NoSuchElementException("Registro não encontrado.");
                }

                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        // propaga a falha ao remover usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
    // salva usuario e cliente B2C juntos, usando a mesma chave e transacao
    public void salvarComB2c(UsuarioModel usuario, String cpf, boolean atualizar) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                if (atualizar) {
                    String sql = "SELECT \"ID_usuario\" FROM \"USUARIO\" WHERE \"ID_usuario\" = ? FOR UPDATE";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, usuario.getId_usuario());
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (!rs.next()) throw new java.util.NoSuchElementException("Registro não encontrado.");
                        }
                    }
                    atualizarUsuario(usuario, conexao);
                } else {
                    salvar(usuario, conexao);
                }
                if ("B2C".equals(usuario.getTipoUsuario())) {
                    boolean existe;
                    String sql = "SELECT \"ID_usuario\" FROM \"B2C\" WHERE \"ID_usuario\" = ?";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, usuario.getId_usuario());
                        try (ResultSet rs = stmt.executeQuery()) {
                            existe = rs.next();
                        }
                    }
                    B2cModel b2c = new B2cModel(usuario.getId_usuario(), cpf, usuario.getTelefone());
                    if (existe) new B2cDAO().atualizarB2c(b2c, conexao);
                    else new B2cDAO().inserirB2c(b2c, conexao);
                } else if (atualizar) {
                    // dependencias existentes impedem retirar o perfil B2C
                    try (PreparedStatement stmt = conexao.prepareStatement("DELETE FROM \"B2C\" WHERE \"ID_usuario\" = ?")) {
                        stmt.setInt(1, usuario.getId_usuario());
                        stmt.executeUpdate();
                    }
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário B2C: " + e.getMessage(), e);
        }
    }
}
