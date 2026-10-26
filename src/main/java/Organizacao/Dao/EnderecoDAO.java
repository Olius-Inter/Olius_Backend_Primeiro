package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.EnderecoModel;
import Organizacao.Model.Enum.Estados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {

    // Método para cadastrar um novo endereço
    public void inserirEndereco(EnderecoModel endereco) {
        String sql = "INSERT INTO ENDERECO (rua, cidade, bairro, cep, numero, complemento, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCep());
            stmt.setString(5, endereco.getNumero());
            stmt.setString(6, endereco.getComplemento());
            stmt.setString(7, endereco.getEstado().name());

            stmt.executeUpdate();
            System.out.println("Endereço cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
        }
    }

    // Método para buscar todos os endereços do banco
    public List<EnderecoModel> listarEndereco() {
        List<EnderecoModel> listaEndereco = new ArrayList<>();
        String sql = "SELECT * FROM ENDERECO ORDER BY ID_endereco";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_endereco = rs.getInt("ID_endereco");
                String rua = rs.getString("rua");
                String cidade = rs.getString("cidade");
                String bairro = rs.getString("bairro");
                String cep = rs.getString("cep");
                String numero = rs.getString("numero");
                String complemento = rs.getString("complemento");
                Estados estado = Estados.valueOf(rs.getString("estado"));

                EnderecoModel novoEndereco = new EnderecoModel(
                        id_endereco, rua, cidade, bairro, cep, numero, complemento, estado
                );

                listaEndereco.add(novoEndereco);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage(), e);
        }

        return listaEndereco;
    }

    // Método para atualizar dados de um endereço existente
    public void atualizarEndereco(EnderecoModel endereco) {
        String sql = "UPDATE ENDERECO SET rua = ?, cidade = ?, bairro = ?, cep = ?, numero = ?, complemento = ?, estado = ? WHERE ID_endereco = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCep());
            stmt.setString(5, endereco.getNumero());
            stmt.setString(6, endereco.getComplemento());
            stmt.setString(7, endereco.getEstado().name());
            stmt.setInt(8, endereco.getIdEndereco());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereço atualizado com sucesso!");
            } else {
                System.out.println("Nenhum endereço encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage(), e);
        }
    }

    // Método para deletar um endereço pelo ID
    public void deletarEndereco(int id) {
        String sql = "DELETE FROM ENDERECO WHERE ID_endereco = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereço deletado com sucesso!");
            } else {
                System.out.println("Nenhum endereço encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage(), e);
        }
    }
}