package Organizacao.Dao;

import Organizacao.Model.ParticipacaoEventoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de participacao em evento
public class ParticipacaoEventoDAO {

    // grava os dados de participacao em evento no banco de dados
    public void inserirParticipacao(ParticipacaoEventoModel participacao) {
        // define a consulta sql de insercao para participacao em evento
        String sql = "INSERT INTO participacao_evento (status, id_b2c, id_evento) VALUES (?, ?, ?)";

        // abre a conexao e prepara a insercao de participacao em evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de participacao em evento aos parametros da insercao
            stmt.setString(1, participacao.getStatus());
            stmt.setInt(2, participacao.getId_b2c());
            stmt.setInt(3, participacao.getId_evento());

            // executa a gravacao dos dados de participacao em evento
            stmt.executeUpdate();
            System.out.println("Participação em evento cadastrada com sucesso!");

        // propaga a falha ao gravar participacao em evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir participação: " + e.getMessage(), e);
        }
    }

    // consulta os registros de participacao em evento e devolve a lista
    public List<ParticipacaoEventoModel> listarParticipacoes() {
        List<ParticipacaoEventoModel> listaParticipacoes = new ArrayList<>();
        // define a consulta sql de leitura para participacao em evento
        String sql = "SELECT * FROM participacao_evento ORDER BY id_participacao";

        // abre a conexao e executa a consulta de participacao em evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em participacao em evento
            while (rs.next()) {
                int id_participacao = rs.getInt("id_participacao");
                String status = rs.getString("status");
                int id_b2c = rs.getInt("id_b2c");
                int id_evento = rs.getInt("id_evento");

                ParticipacaoEventoModel participacao = new ParticipacaoEventoModel(status, id_b2c, id_evento);
                participacao.setId_participacao(id_participacao);

                listaParticipacoes.add(participacao);
            }

        // propaga a falha ao consultar participacao em evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar participações: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaParticipacoes;
    }

    // atualiza os dados de participacao em evento no banco de dados
    public void atualizarParticipacao(ParticipacaoEventoModel participacao) {
        // define a consulta sql de atualizacao para participacao em evento
        String sql = "UPDATE participacao_evento SET status = ?, id_b2c = ?, id_evento = ? WHERE id_participacao = ?";

        // abre a conexao e prepara a atualizacao de participacao em evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de participacao em evento aos parametros da atualizacao
            stmt.setString(1, participacao.getStatus());
            stmt.setInt(2, participacao.getId_b2c());
            stmt.setInt(3, participacao.getId_evento());
            stmt.setInt(4, participacao.getId_participacao());

            // executa a atualizacao dos dados de participacao em evento
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de participacao em evento foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Participação atualizada com sucesso!");
            } else {
                System.out.println("Participação não encontrada.");
            }

        // propaga a falha ao atualizar participacao em evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar participação: " + e.getMessage(), e);
        }
    }

    // remove o registro de participacao em evento do banco de dados
    public void deletarParticipacao(int id_participacao) {
        // define a consulta sql de exclusao para participacao em evento
        String sql = "DELETE FROM participacao_evento WHERE id_participacao = ?";

        // abre a conexao e prepara a exclusao de participacao em evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_participacao);

            // executa a exclusao do registro de participacao em evento
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de participacao em evento foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Participação deletada com sucesso!");
            } else {
                System.out.println("Participação não encontrada.");
            }

        // registra a falha ao remover participacao em evento e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar participação: " + e.getMessage(), e);
        }
    }
}
