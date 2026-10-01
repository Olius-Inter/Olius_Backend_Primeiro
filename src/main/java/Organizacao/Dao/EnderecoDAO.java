package Organizacao.Dao;

import Organizacao.Model.EnderecoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade Endereco.

public class EnderecoDAO {

    // Insere um novo registro de Endereco no banco de dados.

    public void inserirEndereco(EnderecoModel endereco) {
        String sql = "INSERT INTO endereco (cep, logradouro, numero, complemento, bairro, cidade, estado, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getPais());

            stmt.executeUpdate();
            System.out.println("Endereço cadastrado com sucesso!");

        // Falha de acesso ao banco de dados (conexão, SQL inválido, violação de constraint etc.)
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de Endereco cadastrados no banco.
    // @return List<EnderecoModel> resultante da operação
    public List<EnderecoModel> listarEnderecos() {
        List<EnderecoModel> listaEnderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco ORDER BY id_endereco";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_endereco = rs.getInt("id_endereco");
                String cep = rs.getString("cep");
                String logradouro = rs.getString("logradouro");
                String numero = rs.getString("numero");
                String complemento = rs.getString("complemento");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String pais = rs.getString("pais");

                EnderecoModel endereco = new EnderecoModel(
                        cep, logradouro, numero, complemento, bairro, cidade, estado, pais
                );
                endereco.setId_endereco(id_endereco);

                listaEnderecos.add(endereco);
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage(), e);
        }

        return listaEnderecos;
    }

    // Atualiza os dados de um registro de Endereco já existente.

    public void atualizarEndereco(EnderecoModel endereco) {
        String sql = "UPDATE endereco SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, pais = ? WHERE id_endereco = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getPais());
            stmt.setInt(9, endereco.getId_endereco());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereço atualizado com sucesso!");
            } else {
                System.out.println("Endereço não encontrado.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de Endereco do banco de dados.

    public void deletarEndereco(int id_endereco) {
        String sql = "DELETE FROM endereco WHERE id_endereco = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_endereco);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereço deletado com sucesso!");
            } else {
                System.out.println("Endereço não encontrado.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar endereço: " + e.getMessage());
        }
    }
}
