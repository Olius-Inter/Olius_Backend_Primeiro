package Organizacao.Dao;

import Organizacao.Model.HistoricoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade Historico.

public class HistoricoDAO {

    // Insere um novo registro de Historico no banco de dados.

    public void inserirHistorico(HistoricoModel historico) {
        String sql = "INSERT INTO historico (id_usuario, tp_evento, descricao, dt_evento) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, historico.getId_usuario());
            stmt.setString(2, historico.getTp_evento());
            stmt.setString(3, historico.getDescricao());
            stmt.setDate(4, java.sql.Date.valueOf(historico.getDt_evento()));

            stmt.executeUpdate();
            System.out.println("Histórico cadastrado com sucesso!");

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir histórico: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de Historico cadastrados no banco.

    public List<HistoricoModel> listarHistoricos() {
        List<HistoricoModel> listaHistoricos = new ArrayList<>();
        String sql = "SELECT * FROM historico ORDER BY id_historico";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_historico = rs.getInt("id_historico");
                int id_usuario = rs.getInt("id_usuario");
                String tp_evento = rs.getString("tp_evento");
                String descricao = rs.getString("descricao");
                LocalDate dt_evento = rs.getObject("dt_evento", LocalDate.class);

                HistoricoModel historico = new HistoricoModel(id_usuario, tp_evento, descricao, dt_evento);
                historico.setId_historico(id_historico);

                listaHistoricos.add(historico);
            }

        // Falha de acesso ao banco de dados (conexão, SQL inválido, violação de constraint etc.)
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar históricos: " + e.getMessage(), e);
        }

        return listaHistoricos;
    }

    // Atualiza os dados de um registro de Historico já existente.

    public void atualizarHistorico(HistoricoModel historico) {
        String sql = "UPDATE historico SET id_usuario = ?, tp_evento = ?, descricao = ?, dt_evento = ? WHERE id_historico = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, historico.getId_usuario());
            stmt.setString(2, historico.getTp_evento());
            stmt.setString(3, historico.getDescricao());
            stmt.setDate(4, java.sql.Date.valueOf(historico.getDt_evento()));
            stmt.setInt(5, historico.getId_historico());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Histórico atualizado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar histórico: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de Historico do banco de dados.

    public void deletarHistorico(int id_historico) {
        String sql = "DELETE FROM historico WHERE id_historico = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_historico);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Histórico deletado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar histórico: " + e.getMessage());
        }
    }
}
