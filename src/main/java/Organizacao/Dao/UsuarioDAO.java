package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de usuario
public class UsuarioDAO {

    // grava os dados de usuario no banco de dados
    public void salvar(UsuarioModel usuario) throws Exception {
        // define a consulta sql de insercao para usuario
        String sql = "INSERT INTO usuario (id_usuario, nome, email, senha, primeiro_registro, tipo_usuario, telefone) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de usuario com fechamento automatico
        try (Connection conn = Conexao_Banco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // associa os campos de usuario aos parametros da insercao
            stmt.setInt(1, usuario.getId_usuario());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, usuario.getPrimeiroRegistro());
            stmt.setString(6, usuario.getTipoUsuario());
            stmt.setString(7, usuario.getTelefone());

            // executa a gravacao dos dados de usuario
            stmt.executeUpdate();
        }
    }

    // consulta os registros de usuario e devolve a lista
    public List<UsuarioModel> listar() throws Exception {
        List<UsuarioModel> lista = new ArrayList<>();
        // define a consulta sql de leitura para usuario
        String sql = "SELECT * FROM usuario";

        // abre a conexao e executa a consulta de usuario com fechamento automatico
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
                lista.add(u);
            }
        }
        // devolve os registros encontrados pela consulta
        return lista;
    }

    // atualiza os dados de usuario no banco de dados
    public void atualizarUsuario(UsuarioModel usuario) {
        // define a consulta sql de atualizacao para usuario
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, tipo_usuario = ?, telefone = ? WHERE id_usuario = ?";

        // abre a conexao e prepara a atualizacao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de usuario aos parametros da atualizacao
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setString(5, usuario.getTelefone());
            stmt.setInt(6, usuario.getId_usuario());

            // executa a atualizacao dos dados de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de usuario foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro de usuário encontrado com o ID informado.");
            }

        // propaga a falha ao atualizar usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // remove o registro de usuario do banco de dados
    public void deletarUsuario(int id_Usuario) {
        // define a consulta sql de exclusao para usuario
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        // abre a conexao e prepara a exclusao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_Usuario);

            // executa a exclusao do registro de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de usuario foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        // propaga a falha ao remover usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}