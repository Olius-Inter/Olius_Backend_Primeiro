package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.B2cModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class B2cDAO {

    // Cadastra o CPF de um usuário B2C
    public void inserirB2c(B2cModel b2c) {
        String sql = "INSERT INTO b2c (cpf, id_usuario) VALUES (?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2c.getCpf());
            stmt.setInt(2, b2c.getIdUsuario());

            stmt.executeUpdate();
            System.out.println("Cliente B2C cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2C: " + e.getMessage(), e);
        }
    }

    // Lista todos os clientes B2C
    public List<B2cModel> listarB2c() {
        List<B2cModel> lista = new ArrayList<>();
        String sql = "SELECT id_b2c, cpf, id_usuario FROM b2c ORDER BY id_b2c";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new B2cModel(rs.getInt("id_b2c"), rs.getInt("id_usuario"), rs.getString("cpf")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2C: " + e.getMessage(), e);
        }

        return lista;
    }

    // Atualiza o CPF, localizando o cliente pelo usuário
    public void atualizarB2c(B2cModel b2c) {
        String sql = "UPDATE b2c SET cpf = ? WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2c.getCpf());
            stmt.setInt(2, b2c.getIdUsuario());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Cliente B2C atualizado com sucesso!"
                    : "Nenhum cliente B2C encontrado para esse usuário.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2C: " + e.getMessage(), e);
        }
    }

    // Remove o cliente B2C pelo usuário
    public void deletarB2c(int idUsuario) {
        String sql = "DELETE FROM b2c WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Cliente B2C deletado com sucesso!"
                    : "Nenhum cliente B2C encontrado para esse usuário.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2C: " + e.getMessage(), e);
        }
    }
}
