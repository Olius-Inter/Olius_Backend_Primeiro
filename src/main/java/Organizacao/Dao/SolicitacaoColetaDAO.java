package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.SolicitacaoColetaModel;
import Organizacao.Model.Enum.StatusSolicitacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoColetaDAO {

    // Método para registrar solicitação de coleta
    public void inserirSolicitacao(SolicitacaoColetaModel solicitacao) {
        String sql = "INSERT INTO SOLICITACAO_COLETA (status, volume_estimado, DT_solicitacao, ID_B2B) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, solicitacao.getStatus().name());
            stmt.setDouble(2, solicitacao.getVolumeEstimado());
            stmt.setDate(3, java.sql.Date.valueOf(solicitacao.getDtSolicitacao()));
            stmt.setInt(4, solicitacao.getIdB2B());

            stmt.executeUpdate();
            System.out.println("Solicitação de coleta cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir solicitação de coleta: " + e.getMessage(), e);
        }
    }

    // Método para listar todas as solicitações de coleta
    public List<SolicitacaoColetaModel> listarSolicitacao() {
        List<SolicitacaoColetaModel> listaSolicitacao = new ArrayList<>();
        String sql = "SELECT * FROM SOLICITACAO_COLETA ORDER BY ID_solicitacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_solicitacao = rs.getInt("ID_solicitacao");
                StatusSolicitacao status = StatusSolicitacao.valueOf(rs.getString("status"));
                double volume_estimado = rs.getDouble("volume_estimado");
                LocalDate dt_solicitacao = rs.getDate("DT_solicitacao").toLocalDate();
                int id_B2B = rs.getInt("ID_B2B");

                SolicitacaoColetaModel novaSolicitacao = new SolicitacaoColetaModel(
                        id_solicitacao, status, volume_estimado, dt_solicitacao, id_B2B
                );

                listaSolicitacao.add(novaSolicitacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações de coleta: " + e.getMessage(), e);
        }

        return listaSolicitacao;
    }

    // Método para atualizar status/volume da solicitação de coleta
    public void atualizarSolicitacao(SolicitacaoColetaModel solicitacao) {
        String sql = "UPDATE SOLICITACAO_COLETA SET status = ?, volume_estimado = ?, DT_solicitacao = ?, ID_B2B = ? WHERE ID_solicitacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, solicitacao.getStatus().name());
            stmt.setDouble(2, solicitacao.getVolumeEstimado());
            stmt.setDate(3, java.sql.Date.valueOf(solicitacao.getDtSolicitacao()));
            stmt.setInt(4, solicitacao.getIdB2B());
            stmt.setInt(5, solicitacao.getIdSolicitacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Solicitação de coleta atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma solicitação de coleta encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar solicitação de coleta: " + e.getMessage(), e);
        }
    }

    // Método para cancelar/deletar solicitação de coleta
    public void deletarSolicitacao(int id) {
        String sql = "DELETE FROM SOLICITACAO_COLETA WHERE ID_solicitacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Solicitação de coleta deletada com sucesso!");
            } else {
                System.out.println("Nenhuma solicitação de coleta encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação de coleta: " + e.getMessage(), e);
        }
    }
}