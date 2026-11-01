package Organizacao.Dao;

import Organizacao.Model.B2bModel;
import Organizacao.Model.EnderecoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de empresa b dois b
public class B2bDAO {

    // grava os dados de empresa b dois b no banco de dados
    public void inserirB2b(B2bModel b2b) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                inserirB2b(b2b, conexao);
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void inserirB2b(B2bModel b2b, Connection conexao) {
        String sql = "INSERT INTO \"B2B\" (\"CNPJ\", \"Plano\", nome_empresa, telefone, \"ID_endereco\") VALUES (?, CAST(? AS public.plano), ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_B2B"})) {
            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getPlano());
            stmt.setString(3, b2b.getNome_empresa());
            stmt.setString(4, b2b.getTelefone());
            stmt.setObject(5, b2b.getId_endereco(), java.sql.Types.INTEGER);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) b2b.setId_usuario(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2b: " + e.getMessage(), e);
        }
    }

    // consulta os registros de empresa b dois b e devolve a lista
    public List<B2bModel> listarB2b() {
        List<B2bModel> listaB2b = new ArrayList<>();
        // define a consulta sql de leitura para empresa b dois b
        String sql = "SELECT b.\"ID_B2B\" AS id_usuario, b.\"CNPJ\" AS cnpj, b.nome_empresa AS razao_social, b.nome_empresa, b.telefone, b.\"ID_endereco\" AS id_endereco, b.\"Plano\" AS plano FROM \"B2B\" b ORDER BY b.\"ID_B2B\"";

        // abre a conexao e executa a consulta de empresa b dois b com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em empresa b dois b
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String cnpj = rs.getString("cnpj");
                String razaoSocial = rs.getString("razao_social");
                String nome_empresa = rs.getString("nome_empresa");
                String telefone = rs.getString("telefone");
                Integer id_endereco = rs.getObject("id_endereco", Integer.class);
                B2bModel novoB2b = new B2bModel(id_usuario, cnpj, razaoSocial, nome_empresa, telefone, id_endereco);
                novoB2b.setPlano(rs.getString("plano"));
                listaB2b.add(novoB2b);
            }

        // propaga a falha ao consultar empresa b dois b para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2b: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaB2b;
    }
    // atualiza os dados de empresa b dois b no banco de dados
    public void atualizarB2b(B2bModel b2b) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                atualizarB2b(b2b, conexao);
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void atualizarB2b(B2bModel b2b, Connection conexao) {
        String sql = "UPDATE \"B2B\" SET \"CNPJ\" = ?, \"Plano\" = CAST(? AS public.plano), \"ID_endereco\" = ?, nome_empresa = ?, telefone = ? WHERE \"ID_B2B\" = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getPlano());
            stmt.setObject(3, b2b.getId_endereco(), java.sql.Types.INTEGER);
            stmt.setString(4, b2b.getNome_empresa());
            stmt.setString(5, b2b.getTelefone());
            stmt.setInt(6, b2b.getId_usuario());
            if (stmt.executeUpdate() == 0) throw new java.util.NoSuchElementException("Empresa não encontrada.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2b: " + e.getMessage(), e);
        }
    }
    // remove o registro de empresa b dois b do banco de dados
    public void deletarB2b(int id) {
        String sql = "DELETE FROM \"B2B\" WHERE \"ID_B2B\" = ?";
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == 0) throw new java.util.NoSuchElementException("Empresa não encontrada.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2b: " + e.getMessage(), e);
        }
    }
    // grava o cadastro e seu endereco juntos, sem deixar gravacoes parciais
    public void salvarComEndereco(B2bModel b2b, EnderecoModel endereco, boolean atualizar) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                Integer id_endereco = null;
                if (atualizar) {
                    String sql = "SELECT \"ID_endereco\" AS id_endereco FROM \"B2B\" WHERE \"ID_B2B\" = ? FOR UPDATE";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, b2b.getId_usuario());
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (!rs.next()) {
                                throw new java.util.NoSuchElementException("Registro não encontrado.");
                            }
                            id_endereco = rs.getObject("id_endereco", Integer.class);
                        }
                    }
                }
                new EnderecoDAO().salvarEndereco(endereco, id_endereco, conexao);
                b2b.setId_endereco(endereco.getId_endereco());
                if (atualizar) {
                    atualizarB2b(b2b, conexao);
                } else {
                    inserirB2b(b2b, conexao);
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cadastro e endereço: " + e.getMessage(), e);
        }
    }
}
