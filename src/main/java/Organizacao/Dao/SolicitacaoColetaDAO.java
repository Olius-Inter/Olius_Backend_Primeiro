package Organizacao.Dao;

import Organizacao.Model.SolicitacaoColetaModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de solicitacao de coleta
public class SolicitacaoColetaDAO {

    // grava os dados de solicitacao de coleta no banco de dados
    public void inserirSolicitacao(SolicitacaoColetaModel solicitacao) {
        // define a consulta sql de insercao para solicitacao de coleta
        String sql = "INSERT INTO solicitacao_coleta (litros_estimados, dt_solicitacao, status, id_b2b, id_pev) VALUES (?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de solicitacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de solicitacao de coleta aos parametros da insercao
            stmt.setDouble(1, solicitacao.getLitros_estimados());
            stmt.setString(2, solicitacao.getDt_solicitacao());
            stmt.setString(3, solicitacao.getStatus());
            stmt.setInt(4, solicitacao.getId_b2b());
            stmt.setInt(5, solicitacao.getId_pev());

            // executa a gravacao dos dados de solicitacao de coleta
            stmt.executeUpdate();
            System.out.println("Solicitação de coleta cadastrada com sucesso!");

        // propaga a falha ao gravar solicitacao de coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir solicitação de coleta: " + e.getMessage(), e);
        }
    }

    // consulta os registros de solicitacao de coleta e devolve a lista
    public List<SolicitacaoColetaModel> listarSolicitacoes() {
        List<SolicitacaoColetaModel> listaSolicitacoes = new ArrayList<>();
        // define a consulta sql de leitura para solicitacao de coleta
        String sql = "SELECT * FROM solicitacao_coleta ORDER BY id_solicitacao";

        // abre a conexao e executa a consulta de solicitacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em solicitacao de coleta
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

        // propaga a falha ao consultar solicitacao de coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações de coleta: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaSolicitacoes;
    }

    // atualiza os dados de solicitacao de coleta no banco de dados
    public void atualizarSolicitacao(SolicitacaoColetaModel solicitacao) {
        // define a consulta sql de atualizacao para solicitacao de coleta
        String sql = "UPDATE solicitacao_coleta SET litros_estimados = ?, dt_solicitacao = ?, status = ?, id_b2b = ?, id_pev = ? WHERE id_solicitacao = ?";

        // abre a conexao e prepara a atualizacao de solicitacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de solicitacao de coleta aos parametros da atualizacao
            stmt.setDouble(1, solicitacao.getLitros_estimados());
            stmt.setString(2, solicitacao.getDt_solicitacao());
            stmt.setString(3, solicitacao.getStatus());
            stmt.setInt(4, solicitacao.getId_b2b());
            stmt.setInt(5, solicitacao.getId_pev());
            stmt.setInt(6, solicitacao.getId_solicitacao());

            // executa a atualizacao dos dados de solicitacao de coleta
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de solicitacao de coleta foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Solicitação atualizada com sucesso!");
            } else {
                System.out.println("Solicitação não encontrada.");
            }

        // propaga a falha ao atualizar solicitacao de coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar solicitação: " + e.getMessage(), e);
        }
    }

    // remove o registro de solicitacao de coleta do banco de dados
    public void deletarSolicitacao(int id_solicitacao) {
        // define a consulta sql de exclusao para solicitacao de coleta
        String sql = "DELETE FROM solicitacao_coleta WHERE id_solicitacao = ?";

        // abre a conexao e prepara a exclusao de solicitacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_solicitacao);

            // executa a exclusao do registro de solicitacao de coleta
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de solicitacao de coleta foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Solicitação deletada com sucesso!");
            } else {
                System.out.println("Solicitação não encontrada.");
            }

        // registra a falha ao remover solicitacao de coleta e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação: " + e.getMessage(), e);
        }
    }
}
