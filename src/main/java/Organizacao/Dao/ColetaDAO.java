package Organizacao.Dao;

import Organizacao.Model.ColetaModel;
import Organizacao.Conexo.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColetaDAO {

    // CREATE
    public void inserirColeta(ColetaModel coleta) {
        String sql = "INSERT INTO coleta (id_solicitacao, id_motorista, dt_coleta, volume, observacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, coleta.getId_solicitacao());
            stmt.setInt(2, coleta.getId_motorista());
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setString(5, coleta.getObservacao());

            stmt.executeUpdate();
            System.out.println("Coleta cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir coleta: " + e.getMessage(), e);
        }
    }

    // READ
    public List<ColetaModel> listarColetas() {
        List<ColetaModel> listaColetas = new ArrayList<>();
        String sql = "SELECT * FROM coleta ORDER BY id_coleta";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_coleta = rs.getInt("id_coleta");
                int id_solicitacao = rs.getInt("id_solicitacao");
                int id_motorista = rs.getInt("id_motorista");
                LocalDate dt_coleta = rs.getObject("dt_coleta", LocalDate.class);
                double volume = rs.getDouble("volume");
                String observacao = rs.getString("observacao");

                ColetaModel coleta = new ColetaModel(id_solicitacao, id_motorista, dt_coleta, volume, observacao);
                coleta.setId_coleta(id_coleta);

                listaColetas.add(coleta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar coletas: " + e.getMessage(), e);
        }

        return listaColetas;
    }

    // UPDATE
    public void atualizarColeta(ColetaModel coleta) {
        String sql = "UPDATE coleta SET id_solicitacao = ?, id_motorista = ?, dt_coleta = ?, volume = ?, observacao = ? WHERE id_coleta = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, coleta.getId_solicitacao());
            stmt.setInt(2, coleta.getId_motorista());
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setString(5, coleta.getObservacao());
            stmt.setInt(6, coleta.getId_coleta());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Coleta atualizada com sucesso!");
            } else {
                System.out.println("Coleta não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar coleta: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarColeta(int id_coleta) {
        String sql = "DELETE FROM coleta WHERE id_coleta = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_coleta);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Coleta deletada com sucesso!");
            } else {
                System.out.println("Coleta não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar coleta: " + e.getMessage());
        }
    }
}
