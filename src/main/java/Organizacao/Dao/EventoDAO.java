package Organizacao.Dao;

import Organizacao.Model.EventoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade Evento.

public class EventoDAO {

    // Insere um novo registro de Evento no banco de dados.

    public void inserirEvento(EventoModel evento) {
        String sql = "INSERT INTO evento (nome, descricao, dt_finalizacao, dt_inicio, id_b2b) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDt_finalizacao());
            stmt.setString(4, evento.getDt_inicio());
            stmt.setInt(5, evento.getId_b2b());

            stmt.executeUpdate();
            System.out.println("Evento cadastrado com sucesso!");

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir evento: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de Evento cadastrados no banco.

    public List<EventoModel> listarEventos() {
        List<EventoModel> listaEventos = new ArrayList<>();
        String sql = "SELECT * FROM evento ORDER BY id_evento";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar eventos: " + e.getMessage(), e);
        }

        return listaEventos;
    }

    // Atualiza os dados de um registro de Evento já existente.

    public void atualizarEvento(EventoModel evento) {
        String sql = "UPDATE evento SET nome = ?, descricao = ?, dt_finalizacao = ?, dt_inicio = ?, id_b2b = ? WHERE id_evento = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDt_finalizacao());
            stmt.setString(4, evento.getDt_inicio());
            stmt.setInt(5, evento.getId_b2b());
            stmt.setInt(6, evento.getId_evento());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Evento atualizado com sucesso!");
            } else {
                System.out.println("Evento não encontrado.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar evento: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de Evento do banco de dados.

    public void deletarEvento(int id_evento) {
        String sql = "DELETE FROM evento WHERE id_evento = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_evento);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Evento deletado com sucesso!");
            } else {
                System.out.println("Evento não encontrado.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar evento: " + e.getMessage());
        }
    }
}
