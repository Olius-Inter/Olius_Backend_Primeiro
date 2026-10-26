package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.ColetaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColetaDAO {

    // Método para registrar execução de coleta de óleo
    public void inserirColeta(ColetaModel coleta) {
        String sql = "INSERT INTO COLETA (DT_coleta, volume_coletado, avaliacao_coleta, ID_solicitacao, ID_motorista, ID_B2B) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(coleta.getDtColeta()));
            stmt.setDouble(2, coleta.getVolumeColetado());
            stmt.setInt(3, coleta.getAvaliacaoColeta());
            stmt.setInt(4, coleta.getIdSolicitacao());
            stmt.setInt(5, coleta.getIdMotorista());
            stmt.setInt(6, coleta.getIdB2B());

            stmt.executeUpdate();
            System.out.println("Coleta cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir coleta: " + e.getMessage(), e);
        }
    }

    // Método para listar histórico de coletas executadas
    public List<ColetaModel> listarColeta() {
        List<ColetaModel> listaColeta = new ArrayList<>();
        String sql = "SELECT * FROM COLETA ORDER BY ID_coleta";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_coleta = rs.getInt("ID_coleta");
                LocalDate dt_coleta = rs.getDate("DT_coleta").toLocalDate();
                double volume_coletado = rs.getDouble("volume_coletado");
                int avaliacao_coleta = rs.getInt("avaliacao_coleta");
                int id_solicitacao = rs.getInt("ID_solicitacao");
                int id_motorista = rs.getInt("ID_motorista");
                int id_B2B = rs.getInt("ID_B2B");

                ColetaModel novaColeta = new ColetaModel(
                        id_coleta, dt_coleta, volume_coletado, avaliacao_coleta, id_solicitacao, id_motorista, id_B2B
                );

                listaColeta.add(novaColeta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar coletas: " + e.getMessage(), e);
        }

        return listaColeta;
    }

    // Método para alterar informações da coleta
    public void atualizarColeta(ColetaModel coleta) {
        String sql = "UPDATE COLETA SET DT_coleta = ?, volume_coletado = ?, avaliacao_coleta = ?, ID_solicitacao = ?, ID_motorista = ?, ID_B2B = ? WHERE ID_coleta = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(coleta.getDtColeta()));
            stmt.setDouble(2, coleta.getVolumeColetado());
            stmt.setInt(3, coleta.getAvaliacaoColeta());
            stmt.setInt(4, coleta.getIdSolicitacao());
            stmt.setInt(5, coleta.getIdMotorista());
            stmt.setInt(6, coleta.getIdB2B());
            stmt.setInt(7, coleta.getIdColeta());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Coleta atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma coleta encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar coleta: " + e.getMessage(), e);
        }
    }

    // Método para deletar uma coleta realizada
    public void deletarColeta(int id) {
        String sql = "DELETE FROM COLETA WHERE ID_coleta = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Coleta deletada com sucesso!");
            } else {
                System.out.println("Nenhuma coleta encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar coleta: " + e.getMessage(), e);
        }
    }
}