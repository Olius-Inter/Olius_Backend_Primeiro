package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.B2BModel;
import Organizacao.Model.Enum.Plano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class B2BDAO {

    // Método para cadastrar uma empresa B2B
    public void inserirB2B(B2BModel b2b) {
        String sql = "INSERT INTO B2B (CNPJ, Plano, ID_usuario) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getPlano().name());
            stmt.setInt(3, b2b.getIdUsuario());

            stmt.executeUpdate();
            System.out.println("B2B cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2B: " + e.getMessage(), e);
        }
    }

    // Método para listar os cadastros B2B
    public List<B2BModel> listarB2B() {
        List<B2BModel> listaB2B = new ArrayList<>();
        String sql = "SELECT * FROM B2B ORDER BY ID_B2B";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_B2B = rs.getInt("ID_B2B");
                String cnpj = rs.getString("CNPJ");
                Plano plano = Plano.valueOf(rs.getString("Plano"));
                int id_usuario = rs.getInt("ID_usuario");

                B2BModel novoB2B = new B2BModel(
                        id_B2B, cnpj, plano, id_usuario
                );

                listaB2B.add(novoB2B);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2Bs: " + e.getMessage(), e);
        }

        return listaB2B;
    }

    // Método para atualizar dados de uma empresa B2B
    public void atualizarB2B(B2BModel b2b) {
        String sql = "UPDATE B2B SET CNPJ = ?, Plano = ?, ID_usuario = ? WHERE ID_B2B = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getPlano().name());
            stmt.setInt(3, b2b.getIdUsuario());
            stmt.setInt(4, b2b.getIdB2B());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("B2B atualizado com sucesso!");
            } else {
                System.out.println("Nenhum B2B encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2B: " + e.getMessage(), e);
        }
    }

    // Método para remover um registro B2B
    public void deletarB2B(int id) {
        String sql = "DELETE FROM B2B WHERE ID_B2B = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("B2B deletado com sucesso!");
            } else {
                System.out.println("Nenhum B2B encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2B: " + e.getMessage(), e);
        }
    }
}