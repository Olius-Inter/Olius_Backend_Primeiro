package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.PevModel;
import Organizacao.Model.Enum.StatusPev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PevDAO {

    // Método para cadastrar um novo PEV (Ponto de Entrega)
    public void inserirPev(PevModel pev) {
        String sql = "INSERT INTO PEV (status, DT_aprovacao, ID_usuario) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pev.getStatus().name());
            stmt.setDate(2, java.sql.Date.valueOf(pev.getDtAprovacao()));
            stmt.setInt(3, pev.getIdUsuario());

            stmt.executeUpdate();
            System.out.println("PEV cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir PEV: " + e.getMessage(), e);
        }
    }

    // Método para listar pontos PEV
    public List<PevModel> listarPev() {
        List<PevModel> listaPev = new ArrayList<>();
        String sql = "SELECT * FROM PEV ORDER BY ID_PEV";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_PEV = rs.getInt("ID_PEV");
                StatusPev status = StatusPev.valueOf(rs.getString("status"));
                LocalDate dt_aprovacao = rs.getDate("DT_aprovacao").toLocalDate();
                int id_usuario = rs.getInt("ID_usuario");

                PevModel novoPev = new PevModel(
                        id_PEV, status, dt_aprovacao, id_usuario
                );

                listaPev.add(novoPev);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar PEVs: " + e.getMessage(), e);
        }

        return listaPev;
    }

    // Método para atualizar status/dados de um PEV
    public void atualizarPev(PevModel pev) {
        String sql = "UPDATE PEV SET status = ?, DT_aprovacao = ?, ID_usuario = ? WHERE ID_PEV = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pev.getStatus().name());
            stmt.setDate(2, java.sql.Date.valueOf(pev.getDtAprovacao()));
            stmt.setInt(3, pev.getIdUsuario());
            stmt.setInt(4, pev.getIdPev());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("PEV atualizado com sucesso!");
            } else {
                System.out.println("Nenhum PEV encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar PEV: " + e.getMessage(), e);
        }
    }

    // Método para deletar um PEV
    public void deletarPev(int id) {
        String sql = "DELETE FROM PEV WHERE ID_PEV = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("PEV deletado com sucesso!");
            } else {
                System.out.println("Nenhum PEV encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar PEV: " + e.getMessage(), e);
        }
    }
}