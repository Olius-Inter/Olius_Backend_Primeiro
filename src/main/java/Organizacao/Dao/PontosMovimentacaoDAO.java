package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.PontosMovimentacaoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PontosMovimentacaoDAO {

    // Registra uma movimentação de pontos (id_coleta ou id_entrega, nunca os dois)
    public void inserirMovimentacao(PontosMovimentacaoModel mov) {
        String sql = "INSERT INTO movimentacao_pontos (quantidade_pontos, id_carteira, id_coleta, id_entrega) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, mov.getQuantidadePontos());
            stmt.setInt(2, mov.getIdCarteira());
            stmt.setObject(3, mov.getIdColeta(), Types.INTEGER);
            stmt.setObject(4, mov.getIdEntrega(), Types.INTEGER);

            stmt.executeUpdate();
            System.out.println("Movimentação de pontos cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // Lista todas as movimentações
    public List<PontosMovimentacaoModel> listarMovimentacoes() {
        List<PontosMovimentacaoModel> lista = new ArrayList<>();
        String sql = "SELECT id_movimentacao, quantidade_pontos, id_carteira, id_coleta, id_entrega "
                + "FROM movimentacao_pontos ORDER BY id_movimentacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new PontosMovimentacaoModel(
                        rs.getInt("id_movimentacao"),
                        rs.getInt("quantidade_pontos"),
                        rs.getInt("id_carteira"),
                        rs.getObject("id_coleta", Integer.class),
                        rs.getObject("id_entrega", Integer.class)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentações de pontos: " + e.getMessage(), e);
        }

        return lista;
    }

    // Atualiza uma movimentação pelo ID
    public void atualizarMovimentacao(PontosMovimentacaoModel mov) {
        String sql = "UPDATE movimentacao_pontos SET quantidade_pontos = ?, id_carteira = ?, id_coleta = ?, id_entrega = ? "
                + "WHERE id_movimentacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, mov.getQuantidadePontos());
            stmt.setInt(2, mov.getIdCarteira());
            stmt.setObject(3, mov.getIdColeta(), Types.INTEGER);
            stmt.setObject(4, mov.getIdEntrega(), Types.INTEGER);
            stmt.setInt(5, mov.getIdMovimentacao());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Movimentação atualizada com sucesso!" : "Movimentação não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // Remove uma movimentação pelo ID
    public void deletarMovimentacao(int idMovimentacao) {
        String sql = "DELETE FROM movimentacao_pontos WHERE id_movimentacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idMovimentacao);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Movimentação deletada com sucesso!" : "Movimentação não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar movimentação de pontos: " + e.getMessage(), e);
        }
    }
}
