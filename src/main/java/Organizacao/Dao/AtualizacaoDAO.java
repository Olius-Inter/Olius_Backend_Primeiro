package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.AtualizacaoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtualizacaoDAO {

    // Método para registrar uma atualização no sistema
    public void inserirAtualizacao(AtualizacaoModel atualizacao) {
        String sql = "INSERT INTO ATUALIZACAO (motivo, autorizacao, mudanca) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, atualizacao.getMotivo());
            stmt.setInt(2, atualizacao.getAutorizacao());
            stmt.setString(3, atualizacao.getMudanca());

            stmt.executeUpdate();
            System.out.println("Atualização cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir atualização: " + e.getMessage(), e);
        }
    }

    // Método para buscar todas as atualizações cadastradas
    public List<AtualizacaoModel> listarAtualizacao() {
        List<AtualizacaoModel> listaAtualizacao = new ArrayList<>();
        String sql = "SELECT * FROM ATUALIZACAO ORDER BY ID_atualizacao";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_atualizacao = rs.getInt("ID_atualizacao");
                String motivo = rs.getString("motivo");
                int autorizacao = rs.getInt("autorizacao");
                String mudanca = rs.getString("mudanca");

                AtualizacaoModel novaAtualizacao = new AtualizacaoModel(
                        id_atualizacao, motivo, autorizacao, mudanca
                );

                listaAtualizacao.add(novaAtualizacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar atualizações: " + e.getMessage(), e);
        }

        return listaAtualizacao;
    }

    // Método para modificar um registro de atualização
    public void atualizarAtualizacao(AtualizacaoModel atualizacao) {
        String sql = "UPDATE ATUALIZACAO SET motivo = ?, autorizacao = ?, mudanca = ? WHERE ID_atualizacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, atualizacao.getMotivo());
            stmt.setInt(2, atualizacao.getAutorizacao());
            stmt.setString(3, atualizacao.getMudanca());
            stmt.setInt(4, atualizacao.getIdAtualizacao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Atualização alterada com sucesso!");
            } else {
                System.out.println("Nenhuma atualização encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro: " + e.getMessage(), e);
        }
    }

    // Método para remover um registro de atualização
    public void deletarAtualizacao(int id) {
        String sql = "DELETE FROM ATUALIZACAO WHERE ID_atualizacao = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Atualização deletada com sucesso!");
            } else {
                System.out.println("Nenhuma atualização encontrada com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar atualização: " + e.getMessage(), e);
        }
    }
}