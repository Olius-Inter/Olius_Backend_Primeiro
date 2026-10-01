package Organizacao.Dao;

import Organizacao.Model.ParticipacaoEventoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade ParticipacaoEvento.

public class ParticipacaoEventoDAO {

    // Insere um novo registro de ParticipacaoEvento no banco de dados.

    public void inserirParticipacao(ParticipacaoEventoModel participacao) {
        String sql = "INSERT INTO participacao_evento (status, id_b2c, id_evento) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, participacao.getStatus());
            stmt.setInt(2, participacao.getId_b2c());
            stmt.setInt(3, participacao.getId_evento());

            stmt.executeUpdate();
            System.out.println("Participação em evento cadastrada com sucesso!");

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir participação: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de ParticipacaoEvento cadastrados no banco.

    public List<ParticipacaoEventoModel> listarParticipacoes() {
        List<ParticipacaoEventoModel> listaParticipacoes = new ArrayList<>();
        String sql = "SELECT * FROM participacao_evento ORDER BY id_participacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_participacao = rs.getInt("id_participacao");
                String status = rs.getString("status");
                int id_b2c = rs.getInt("id_b2c");
                int id_evento = rs.getInt("id_evento");

                ParticipacaoEventoModel participacao = new ParticipacaoEventoModel(status, id_b2c, id_evento);
                participacao.setId_participacao(id_participacao);

                listaParticipacoes.add(participacao);
            }

            // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar participações: " + e.getMessage(), e);
        }

        return listaParticipacoes;
    }

    // Atualiza os dados de um registro de ParticipacaoEvento já existente.

    public void atualizarParticipacao(ParticipacaoEventoModel participacao) {
        String sql = "UPDATE participacao_evento SET status = ?, id_b2c = ?, id_evento = ? WHERE id_participacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, participacao.getStatus());
            stmt.setInt(2, participacao.getId_b2c());
            stmt.setInt(3, participacao.getId_evento());
            stmt.setInt(4, participacao.getId_participacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Participação atualizada com sucesso!");
            } else {
                System.out.println("Participação não encontrada.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar participação: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de ParticipacaoEvento do banco de dados.

    public void deletarParticipacao(int id_participacao) {
        String sql = "DELETE FROM participacao_evento WHERE id_participacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_participacao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Participação deletada com sucesso!");
            } else {
                System.out.println("Participação não encontrada.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar participação: " + e.getMessage());
        }
    }
}
