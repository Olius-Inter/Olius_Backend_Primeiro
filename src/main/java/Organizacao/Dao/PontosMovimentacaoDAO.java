package Organizacao.Dao;

import Organizacao.Model.PontosMovimentacaoModel;
import Organizacao.Conexo.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PontosMovimentacaoDAO {

    // CREATE
    public void inserirPontosMovimentacao(PontosMovimentacaoModel movimentacao) {
        String sql = "INSERT INTO pontos_movimentacao (pontos_ganhos, tipo_movimentacao, dt_movimentacao, id_entrega, id_carteira, id_participacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, movimentacao.getPontos_ganhos());
            stmt.setString(2, movimentacao.getTipo_movimentacao());
            stmt.setString(3, movimentacao.getDt_movimentacao());
            stmt.setInt(4, movimentacao.getId_entrega());
            stmt.setInt(5, movimentacao.getId_carteira());
            stmt.setInt(6, movimentacao.getId_participacao());

            stmt.executeUpdate();
            System.out.println("Movimentação de pontos cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // READ
    public List<PontosMovimentacaoModel> listarPontosMovimentacao() {
        List<PontosMovimentacaoModel> listaMovimentacoes = new ArrayList<>();
        String sql = "SELECT * FROM pontos_movimentacao ORDER BY id_movimentacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_movimentacao = rs.getInt("id_movimentacao");
                int pontos_ganhos = rs.getInt("pontos_ganhos");
                String tipo_movimentacao = rs.getString("tipo_movimentacao");
                String dt_movimentacao = rs.getString("dt_movimentacao");
                int id_entrega = rs.getInt("id_entrega");
                int id_carteira = rs.getInt("id_carteira");
                int id_participacao = rs.getInt("id_participacao");

                PontosMovimentacaoModel movimentacao = new PontosMovimentacaoModel(
                        pontos_ganhos, tipo_movimentacao, dt_movimentacao, id_entrega, id_carteira, id_participacao
                );
                movimentacao.setId_movimentacao(id_movimentacao);

                listaMovimentacoes.add(movimentacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentações de pontos: " + e.getMessage(), e);
        }

        return listaMovimentacoes;
    }

    // UPDATE
    public void atualizarPontosMovimentacao(PontosMovimentacaoModel movimentacao) {
        String sql = "UPDATE pontos_movimentacao SET pontos_ganhos = ?, tipo_movimentacao = ?, dt_movimentacao = ?, id_entrega = ?, id_carteira = ?, id_participacao = ? WHERE id_movimentacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, movimentacao.getPontos_ganhos());
            stmt.setString(2, movimentacao.getTipo_movimentacao());
            stmt.setString(3, movimentacao.getDt_movimentacao());
            stmt.setInt(4, movimentacao.getId_entrega());
            stmt.setInt(5, movimentacao.getId_carteira());
            stmt.setInt(6, movimentacao.getId_participacao());
            stmt.setInt(7, movimentacao.getId_movimentacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Movimentação de pontos atualizada com sucesso!");
            } else {
                System.out.println("Movimentação não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarPontosMovimentacao(int id_movimentacao) {
        String sql = "DELETE FROM pontos_movimentacao WHERE id_movimentacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_movimentacao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Movimentação deletada com sucesso!");
            } else {
                System.out.println("Movimentação não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar movimentação: " + e.getMessage());
        }
    }
}
