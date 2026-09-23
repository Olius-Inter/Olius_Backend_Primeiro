package Organizacao.Dao;

import Organizacao.Model.PontuacaoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PontuacaoDAO {

    // CREATE
    public void inserirPontuacao(PontuacaoModel pontuacao) {
        String sql = "INSERT INTO pontuacao (id_usuario_b2c, id_entrega, pontos, tp_movimentacao, descricao, dt_movimentacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pontuacao.getId_usuario_b2c());
            stmt.setInt(2, pontuacao.getId_entrega());
            stmt.setInt(3, pontuacao.getPontos());
            stmt.setString(4, pontuacao.getTp_movimentacao());
            stmt.setString(5, pontuacao.getDescricao());
            stmt.setDate(6, java.sql.Date.valueOf(pontuacao.getDt_movimentacao()));

            stmt.executeUpdate();
            System.out.println("Pontuação cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pontuação: " + e.getMessage(), e);
        }
    }

    // READ
    public List<PontuacaoModel> listarPontuacoes() {
        List<PontuacaoModel> listaPontuacoes = new ArrayList<>();
        String sql = "SELECT * FROM pontuacao ORDER BY id_pontuacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_pontuacao = rs.getInt("id_pontuacao");
                int id_usuario_b2c = rs.getInt("id_usuario_b2c");
                int id_entrega = rs.getInt("id_entrega");
                int pontos = rs.getInt("pontos");
                String tp_movimentacao = rs.getString("tp_movimentacao");
                String descricao = rs.getString("descricao");
                LocalDate dt_movimentacao = rs.getObject("dt_movimentacao", LocalDate.class);

                PontuacaoModel pontuacao = new PontuacaoModel(
                        id_usuario_b2c, id_entrega, pontos, tp_movimentacao, descricao, dt_movimentacao
                );
                pontuacao.setId_pontuacao(id_pontuacao);

                listaPontuacoes.add(pontuacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pontuações: " + e.getMessage(), e);
        }

        return listaPontuacoes;
    }

    // UPDATE
    public void atualizarPontuacao(PontuacaoModel pontuacao) {
        String sql = "UPDATE pontuacao SET id_usuario_b2c = ?, id_entrega = ?, pontos = ?, tp_movimentacao = ?, descricao = ?, dt_movimentacao = ? WHERE id_pontuacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pontuacao.getId_usuario_b2c());
            stmt.setInt(2, pontuacao.getId_entrega());
            stmt.setInt(3, pontuacao.getPontos());
            stmt.setString(4, pontuacao.getTp_movimentacao());
            stmt.setString(5, pontuacao.getDescricao());
            stmt.setDate(6, java.sql.Date.valueOf(pontuacao.getDt_movimentacao()));
            stmt.setInt(7, pontuacao.getId_pontuacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Pontuação atualizada com sucesso!");
            } else {
                System.out.println("Pontuação não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pontuação: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarPontuacao(int id_pontuacao) {
        String sql = "DELETE FROM pontuacao WHERE id_pontuacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_pontuacao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Pontuação deletada com sucesso!");
            } else {
                System.out.println("Pontuação não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar pontuação: " + e.getMessage());
        }
    }
}
