package Organizacao.Dao;

import Organizacao.Model.PontosMovimentacaoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de movimentacao de pontos
public class PontosMovimentacaoDAO {

    // grava os dados de movimentacao de pontos no banco de dados
    public void inserirPontosMovimentacao(PontosMovimentacaoModel movimentacao) {
        // define a consulta sql de insercao para movimentacao de pontos
        String sql = "INSERT INTO pontos_movimentacao (pontos_ganhos, tipo_movimentacao, dt_movimentacao, id_entrega, id_carteira, id_participacao) VALUES (?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de movimentacao de pontos com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de movimentacao de pontos aos parametros da insercao
            stmt.setInt(1, movimentacao.getPontos_ganhos());
            stmt.setString(2, movimentacao.getTipo_movimentacao());
            stmt.setString(3, movimentacao.getDt_movimentacao());
            stmt.setInt(4, movimentacao.getId_entrega());
            stmt.setInt(5, movimentacao.getId_carteira());
            stmt.setInt(6, movimentacao.getId_participacao());

            // executa a gravacao dos dados de movimentacao de pontos
            stmt.executeUpdate();
            System.out.println("Movimentação de pontos cadastrada com sucesso!");

        // propaga a falha ao gravar movimentacao de pontos para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // consulta os registros de movimentacao de pontos e devolve a lista
    public List<PontosMovimentacaoModel> listarPontosMovimentacao() {
        List<PontosMovimentacaoModel> listaMovimentacoes = new ArrayList<>();
        // define a consulta sql de leitura para movimentacao de pontos
        String sql = "SELECT * FROM pontos_movimentacao ORDER BY id_movimentacao";

        // abre a conexao e executa a consulta de movimentacao de pontos com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em movimentacao de pontos
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

        // propaga a falha ao consultar movimentacao de pontos para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentações de pontos: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaMovimentacoes;
    }

    // atualiza os dados de movimentacao de pontos no banco de dados
    public void atualizarPontosMovimentacao(PontosMovimentacaoModel movimentacao) {
        // define a consulta sql de atualizacao para movimentacao de pontos
        String sql = "UPDATE pontos_movimentacao SET pontos_ganhos = ?, tipo_movimentacao = ?, dt_movimentacao = ?, id_entrega = ?, id_carteira = ?, id_participacao = ? WHERE id_movimentacao = ?";

        // abre a conexao e prepara a atualizacao de movimentacao de pontos com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de movimentacao de pontos aos parametros da atualizacao
            stmt.setInt(1, movimentacao.getPontos_ganhos());
            stmt.setString(2, movimentacao.getTipo_movimentacao());
            stmt.setString(3, movimentacao.getDt_movimentacao());
            stmt.setInt(4, movimentacao.getId_entrega());
            stmt.setInt(5, movimentacao.getId_carteira());
            stmt.setInt(6, movimentacao.getId_participacao());
            stmt.setInt(7, movimentacao.getId_movimentacao());

            // executa a atualizacao dos dados de movimentacao de pontos
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de movimentacao de pontos foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Movimentação de pontos atualizada com sucesso!");
            } else {
                System.out.println("Movimentação não encontrada.");
            }

        // propaga a falha ao atualizar movimentacao de pontos para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar movimentação de pontos: " + e.getMessage(), e);
        }
    }

    // remove o registro de movimentacao de pontos do banco de dados
    public void deletarPontosMovimentacao(int id_movimentacao) {
        // define a consulta sql de exclusao para movimentacao de pontos
        String sql = "DELETE FROM pontos_movimentacao WHERE id_movimentacao = ?";

        // abre a conexao e prepara a exclusao de movimentacao de pontos com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_movimentacao);

            // executa a exclusao do registro de movimentacao de pontos
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de movimentacao de pontos foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Movimentação deletada com sucesso!");
            } else {
                System.out.println("Movimentação não encontrada.");
            }

        // registra a falha ao remover movimentacao de pontos e informa o ocorrido

        } catch (Exception e) {
            System.out.println("Erro ao deletar movimentação: " + e.getMessage());
        }
    }
}
