package Organizacao.Dao;

import Organizacao.Model.B2cModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class B2cDAO {

    // CREATE
    public void inserirB2c(B2cModel b2c) {
        String sql = "INSERT INTO B2c ( cpf, telefone, id_usuario) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(3, b2c.getId_usuario());
            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());

            stmt.executeUpdate();
            System.out.println("Usuário B2c cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2c: " + e.getMessage(), e);
        }
    }

    // READ
    public List<B2cModel> listarB2c() {
        List<B2cModel> listaB2c = new ArrayList<>();
        String sql = "SELECT * FROM B2c ORDER BY id_usuario";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                int id_usuario = rs.getInt("id_usuario");

                B2cModel novoB2c = new B2cModel(id_usuario, cpf, telefone);
                listaB2c.add(novoB2c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2c: " + e.getMessage(), e);
        }

        return listaB2c;
    }

    // UPDATE
    public void atualizarB2c(B2cModel b2c) {
        String sql = "UPDATE B2c SET cpf = ?, telefone = ? WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());
            stmt.setInt(3, b2c.getId_usuario());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro B2c encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2c: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarB2c(int id_usuario) {
        String sql = "DELETE FROM B2c WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_usuario);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }
}
