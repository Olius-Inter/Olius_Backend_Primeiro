package Organizacao.Dao;

import Organizacao.Model.EventoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de evento
public class EventoDAO {

    // grava os dados de evento no banco de dados
    public void inserirEvento(EventoModel evento) {
        // define a consulta sql de insercao para evento
        String sql = "INSERT INTO evento (nome, descricao, dt_finalizacao, dt_inicio, id_b2b) VALUES (?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de evento aos parametros da insercao
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDt_finalizacao());
            stmt.setString(4, evento.getDt_inicio());
            stmt.setInt(5, evento.getId_b2b());

            // executa a gravacao dos dados de evento
            stmt.executeUpdate();
            System.out.println("Evento cadastrado com sucesso!");

        // propaga a falha ao gravar evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir evento: " + e.getMessage(), e);
        }
    }

    // consulta os registros de evento e devolve a lista
    public List<EventoModel> listarEventos() {
        List<EventoModel> listaEventos = new ArrayList<>();
        // define a consulta sql de leitura para evento
        String sql = "SELECT * FROM evento ORDER BY id_evento";

        // abre a conexao e executa a consulta de evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em evento
            while (rs.next()) {
                int id_evento = rs.getInt("id_evento");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                String dt_finalizacao = rs.getString("dt_finalizacao");
                String dt_inicio = rs.getString("dt_inicio");
                int id_b2b = rs.getInt("id_b2b");

                EventoModel evento = new EventoModel(nome, descricao, dt_finalizacao, dt_inicio, id_b2b);
                evento.setId_evento(id_evento);

                listaEventos.add(evento);
            }

        // propaga a falha ao consultar evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar eventos: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaEventos;
    }

    // atualiza os dados de evento no banco de dados
    public void atualizarEvento(EventoModel evento) {
        // define a consulta sql de atualizacao para evento
        String sql = "UPDATE evento SET nome = ?, descricao = ?, dt_finalizacao = ?, dt_inicio = ?, id_b2b = ? WHERE id_evento = ?";

        // abre a conexao e prepara a atualizacao de evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de evento aos parametros da atualizacao
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDt_finalizacao());
            stmt.setString(4, evento.getDt_inicio());
            stmt.setInt(5, evento.getId_b2b());
            stmt.setInt(6, evento.getId_evento());

            // executa a atualizacao dos dados de evento
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de evento foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Evento atualizado com sucesso!");
            } else {
                System.out.println("Evento não encontrado.");
            }

        // propaga a falha ao atualizar evento para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar evento: " + e.getMessage(), e);
        }
    }

    // remove o registro de evento do banco de dados
    public void deletarEvento(int id_evento) {
        // define a consulta sql de exclusao para evento
        String sql = "DELETE FROM evento WHERE id_evento = ?";

        // abre a conexao e prepara a exclusao de evento com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_evento);

            // executa a exclusao do registro de evento
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de evento foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Evento deletado com sucesso!");
            } else {
                System.out.println("Evento não encontrado.");
            }

        // registra a falha ao remover evento e informa o ocorrido

        } catch (Exception e) {
            System.out.println("Erro ao deletar evento: " + e.getMessage());
        }
    }
}
