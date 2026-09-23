package Organizacao.Dao;

import Organizacao.Model.SolicitacaoColetaModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoColetaDAO {

    // CREATE
    public void inserirSolicitacao(SolicitacaoColetaModel solicitacao) {
        String sql = "INSERT INTO solicitacao_coleta (litros_estimados, dt_solicitacao, status, id_b2b, id_pev) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, solicitacao.getLitros_estimados());
            stmt.setString(2, solicitacao.getDt_solicitacao());
            stmt.setString(3, solicitacao.getStatus());
            stmt.setInt(4, solicitacao.getId_b2b());
            stmt.setInt(5, solicitacao.getId_pev());

            stmt.executeUpdate();
            System.out.println("Solicitação de coleta cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir solicitação de coleta: " + e.getMessage(), e);
        }
    }

    // READ
    public List<SolicitacaoColetaModel> listarSolicitacoes() {
        List<SolicitacaoColetaModel> listaSolicitacoes = new ArrayList<>();
        String sql = "SELECT * FROM solicitacao_coleta ORDER BY id_solicitacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_solicitacao = rs.getInt("id_solicitacao");
                double litros_estimados = rs.getDouble("litros_estimados");
                String dt_solicitacao = rs.getString("dt_solicitacao");
                String status = rs.getString("status");
                int id_b2b = rs.getInt("id_b2b");
                int id_pev = rs.getInt("id_pev");

                SolicitacaoColetaModel solicitacao = new SolicitacaoColetaModel(
                        litros_estimados, dt_solicitacao, status, id_b2b, id_pev
                );
                solicitacao.setId_solicitacao(id_solicitacao);

                listaSolicitacoes.add(solicitacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações de coleta: " + e.getMessage(), e);
        }

        return listaSolicitacoes;
    }

    // UPDATE
    public void atualizarSolicitacao(SolicitacaoColetaModel solicitacao) {
        String sql = "UPDATE solicitacao_coleta SET litros_estimados = ?, dt_solicitacao = ?, status = ?, id_b2b = ?, id_pev = ? WHERE id_solicitacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, solicitacao.getLitros_estimados());
            stmt.setString(2, solicitacao.getDt_solicitacao());
            stmt.setString(3, solicitacao.getStatus());
            stmt.setInt(4, solicitacao.getId_b2b());
            stmt.setInt(5, solicitacao.getId_pev());
            stmt.setInt(6, solicitacao.getId_solicitacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Solicitação atualizada com sucesso!");
            } else {
                System.out.println("Solicitação não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar solicitação: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarSolicitacao(int id_solicitacao) {
        String sql = "DELETE FROM solicitacao_coleta WHERE id_solicitacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_solicitacao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Solicitação deletada com sucesso!");
            } else {
                System.out.println("Solicitação não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar solicitação: " + e.getMessage());
        }
    }
}
