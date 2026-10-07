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


// acessa e administra os registros de historico de usuario
public class HistoricoDAO {

    // grava os dados de historico de usuario no banco de dados
    public void inserirHistorico(HistoricoModel historico) {
        // define a consulta sql de insercao para historico de usuario
        String sql = "INSERT INTO historico (id_usuario, tp_evento, descricao, dt_evento) VALUES (?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de historico de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de historico de usuario aos parametros da insercao
            stmt.setInt(1, historico.getId_usuario());
            stmt.setString(2, historico.getTp_evento());
            stmt.setString(3, historico.getDescricao());
            stmt.setDate(4, java.sql.Date.valueOf(historico.getDt_evento()));

            // executa a gravacao dos dados de historico de usuario
            stmt.executeUpdate();
            System.out.println("Histórico cadastrado com sucesso!");

        // propaga a falha ao gravar historico de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir histórico: " + e.getMessage(), e);
        }
    }

    // consulta os registros de historico de usuario e devolve a lista
    public List<HistoricoModel> listarHistoricos() {
        List<HistoricoModel> listaHistoricos = new ArrayList<>();
        // define a consulta sql de leitura para historico de usuario
        String sql = "SELECT * FROM historico ORDER BY id_historico";

        // abre a conexao e executa a consulta de historico de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em historico de usuario
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

        // propaga a falha ao consultar historico de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar históricos: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaHistoricos;
    }

    // atualiza os dados de historico de usuario no banco de dados
    public void atualizarHistorico(HistoricoModel historico) {
        // define a consulta sql de atualizacao para historico de usuario
        String sql = "UPDATE historico SET id_usuario = ?, tp_evento = ?, descricao = ?, dt_evento = ? WHERE id_historico = ?";

        // abre a conexao e prepara a atualizacao de historico de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de historico de usuario aos parametros da atualizacao
            stmt.setInt(1, historico.getId_usuario());
            stmt.setString(2, historico.getTp_evento());
            stmt.setString(3, historico.getDescricao());
            stmt.setDate(4, java.sql.Date.valueOf(historico.getDt_evento()));
            stmt.setInt(5, historico.getId_historico());

            // executa a atualizacao dos dados de historico de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de historico de usuario foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Histórico atualizado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }

        // propaga a falha ao atualizar historico de usuario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar histórico: " + e.getMessage(), e);
        }
    }

    // remove o registro de historico de usuario do banco de dados
    public void deletarHistorico(int id_historico) {
        // define a consulta sql de exclusao para historico de usuario
        String sql = "DELETE FROM historico WHERE id_historico = ?";

        // abre a conexao e prepara a exclusao de historico de usuario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_historico);

            // executa a exclusao do registro de historico de usuario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de historico de usuario foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Histórico deletado com sucesso!");
            } else {
                System.out.println("Histórico não encontrado.");
            }

        // registra a falha ao remover historico de usuario e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar histórico: " + e.getMessage(), e);
        }
    }
}
