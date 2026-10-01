package Organizacao.Dao;

import Organizacao.Model.EntregaPevModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade EntregaPev.

public class EntregaPevDAO {

    // Insere um novo registro de EntregaPev no banco de dados.
    // @param entrega dado(s) utilizado(s) na operação
    public void inserirEntregaPev(EntregaPevModel entrega) {
        String sql = "INSERT INTO entrega_pev (id_usuario_b2c, id_pev, qtd_litros, pontos_gerados, dt_entrega) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getId_usuario_b2c());
            stmt.setInt(2, entrega.getId_pev());
            stmt.setDouble(3, entrega.getQtd_litros());
            stmt.setInt(4, entrega.getPontos_gerados());
            stmt.setDate(5, java.sql.Date.valueOf(entrega.getDt_entrega()));

            stmt.executeUpdate();
            System.out.println("Entrega PEV cadastrada com sucesso!");

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir entrega PEV: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de EntregaPev cadastrados no banco.

    public List<EntregaPevModel> listarEntregasPev() {
        List<EntregaPevModel> listaEntregas = new ArrayList<>();
        String sql = "SELECT * FROM entrega_pev ORDER BY id_entrega";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_entrega = rs.getInt("id_entrega");
                int id_usuario_b2c = rs.getInt("id_usuario_b2c");
                int id_pev = rs.getInt("id_pev");
                double qtd_litros = rs.getDouble("qtd_litros");
                int pontos_gerados = rs.getInt("pontos_gerados");
                LocalDate dt_entrega = rs.getObject("dt_entrega", LocalDate.class);

                EntregaPevModel entrega = new EntregaPevModel(
                        id_usuario_b2c, id_pev, qtd_litros, pontos_gerados, dt_entrega
                );
                entrega.setId_entrega(id_entrega);

                listaEntregas.add(entrega);
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar entregas PEV: " + e.getMessage(), e);
        }

        return listaEntregas;
    }

    // Atualiza os dados de um registro de EntregaPev já existente.

    public void atualizarEntregaPev(EntregaPevModel entrega) {
        String sql = "UPDATE entrega_pev SET id_usuario_b2c = ?, id_pev = ?, qtd_litros = ?, pontos_gerados = ?, dt_entrega = ? WHERE id_entrega = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getId_usuario_b2c());
            stmt.setInt(2, entrega.getId_pev());
            stmt.setDouble(3, entrega.getQtd_litros());
            stmt.setInt(4, entrega.getPontos_gerados());
            stmt.setDate(5, java.sql.Date.valueOf(entrega.getDt_entrega()));
            stmt.setInt(6, entrega.getId_entrega());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Entrega PEV atualizada com sucesso!");
            } else {
                System.out.println("Entrega PEV não encontrada.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar entrega PEV: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de EntregaPev do banco de dados.

    public void deletarEntregaPev(int id_entrega) {
        String sql = "DELETE FROM entrega_pev WHERE id_entrega = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_entrega);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Entrega PEV deletada com sucesso!");
            } else {
                System.out.println("Entrega PEV não encontrada.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar entrega PEV: " + e.getMessage());
        }
    }
}
