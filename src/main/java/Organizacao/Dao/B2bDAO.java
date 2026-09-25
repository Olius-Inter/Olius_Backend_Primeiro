package Organizacao.Dao;

import Organizacao.Model.B2bModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class B2bDAO {

    // CREATE
    public void inserirB2b(B2bModel b2b) {
        String sql = "INSERT INTO B2b (cnpj, razao_social, nome_fantasia, telefone, id_endereco) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getRazao_social());
            stmt.setString(3, b2b.getNome_fantasia());
            stmt.setString(4, b2b.getTelefone());
            stmt.setInt(5, b2b.getId_endereco());

            stmt.executeUpdate();
            System.out.println("Usuário B2b cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2b: " + e.getMessage(), e);
        }
    }


    // READ
    public List<B2bModel> listarB2b() {
        List<B2bModel> listaB2b = new ArrayList<>();
        String sql = "SELECT * FROM B2b ORDER BY id_usuario";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String cnpj = rs.getString("cnpj");
                String razaoSocial = rs.getString("razao_social");
                String nome_Fantasia = rs.getString("nome_fantasia");
                String telefone = rs.getString("telefone");
                int id_endereco = rs.getInt("id_endereco");
                B2bModel novoB2b = new B2bModel(id_usuario, cnpj, razaoSocial, nome_Fantasia, telefone, id_endereco);
                listaB2b.add(novoB2b);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2b: " + e.getMessage(), e);
        }

        return listaB2b;
    }
    //UPDATE
    public void atualizarB2b(B2bModel b2b) {
        String sql = "UPDATE B2b SET cnpj = ?, razao_social = ?, nome_fantasia = ?, telefone = ?, id_endereco = ? WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getRazao_social());
            stmt.setString(3, b2b.getNome_fantasia());
            stmt.setString(4, b2b.getTelefone());
            stmt.setInt(5, b2b.getId_endereco());
            stmt.setInt(6, b2b.getId_usuario());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Empresa B2b atualizada com sucesso!");
            } else {
                System.out.println("Nenhum registro B2b encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2b: " + e.getMessage(), e);
        }
    }
    public void deletarB2b(int id) {
        String sql = "DELETE FROM B2b WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2b deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2b: " + e.getMessage(), e);
        }
    }
}