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


// acessa e administra os registros de pontuacao de usuario
public class PontuacaoDAO {

    // grava os dados de pontuacao de usuario no banco de dados
    public void inserirPontuacao(PontuacaoModel pontuacao) {
        // define a consulta sql de insercao para pontuacao de usuario
        String sql = "INSERT INTO pontuacao (id_usuario_b2c, id_entrega, pontos, tp_movimentacao, descricao, dt_movimentacao) VALUES (?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de pontuacao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de pontuacao de usuario aos parametros da insercao
            stmt.setInt(1, pontuacao.getId_usuario_b2c());
            stmt.setInt(2, pontuacao.getId_entrega());
            stmt.setInt(3, pontuacao.getPontos());
            stmt.setString(4, pontuacao.getTp_movimentacao());
            stmt.setString(5, pontuacao.getDescricao());
            stmt.setDate(6, java.sql.Date.valueOf(pontuacao.getDt_movimentacao()));

            // executa a gravacao dos dados de pontuacao de usuario
            stmt.executeUpdate();
            System.out.println("Pontuação cadastrada com sucesso!");

        // propaga a falha ao gravar pontuacao de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pontuação: " + e.getMessage(), e);
        }
    }

    // consulta os registros de pontuacao de usuario e devolve a lista
    public List<PontuacaoModel> listarPontuacoes() {
        List<PontuacaoModel> listaPontuacoes = new ArrayList<>();
        // define a consulta sql de leitura para pontuacao de usuario
        String sql = "SELECT * FROM pontuacao ORDER BY id_pontuacao";

        // abre a conexao e executa a consulta de pontuacao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em pontuacao de usuario
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

        // propaga a falha ao consultar pontuacao de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pontuações: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaPontuacoes;
    }

    // atualiza os dados de pontuacao de usuario no banco de dados
    public void atualizarPontuacao(PontuacaoModel pontuacao) {
        // define a consulta sql de atualizacao para pontuacao de usuario
        String sql = "UPDATE pontuacao SET id_usuario_b2c = ?, id_entrega = ?, pontos = ?, tp_movimentacao = ?, descricao = ?, dt_movimentacao = ? WHERE id_pontuacao = ?";

        // abre a conexao e prepara a atualizacao de pontuacao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de pontuacao de usuario aos parametros da atualizacao
            stmt.setInt(1, pontuacao.getId_usuario_b2c());
            stmt.setInt(2, pontuacao.getId_entrega());
            stmt.setInt(3, pontuacao.getPontos());
            stmt.setString(4, pontuacao.getTp_movimentacao());
            stmt.setString(5, pontuacao.getDescricao());
            stmt.setDate(6, java.sql.Date.valueOf(pontuacao.getDt_movimentacao()));
            stmt.setInt(7, pontuacao.getId_pontuacao());

            // executa a atualizacao dos dados de pontuacao de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de pontuacao de usuario foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Pontuação atualizada com sucesso!");
            } else {
                System.out.println("Pontuação não encontrada.");
            }

        // propaga a falha ao atualizar pontuacao de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pontuação: " + e.getMessage(), e);
        }
    }

    // remove o registro de pontuacao de usuario do banco de dados
    public void deletarPontuacao(int id_pontuacao) {
        // define a consulta sql de exclusao para pontuacao de usuario
        String sql = "DELETE FROM pontuacao WHERE id_pontuacao = ?";

        // abre a conexao e prepara a exclusao de pontuacao de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_pontuacao);

            // executa a exclusao do registro de pontuacao de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de pontuacao de usuario foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Pontuação deletada com sucesso!");
            } else {
                System.out.println("Pontuação não encontrada.");
            }

        // registra a falha ao remover pontuacao de usuario e informa o ocorrido

        } catch (Exception e) {
            System.out.println("Erro ao deletar pontuação: " + e.getMessage());
        }
    }
}
