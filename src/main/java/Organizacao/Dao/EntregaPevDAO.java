package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.EntregaPevModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntregaPevDAO {

    // Registra uma entrega de óleo em um PEV
    public void inserirEntrega(EntregaPevModel entrega) {
        String sql = "INSERT INTO entrega_pev (dt_entrega, volume_entrega, id_pev, id_b2c) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(entrega.getDtEntrega()));
            stmt.setBigDecimal(2, entrega.getVolumeEntrega());
            stmt.setInt(3, entrega.getIdPev());
            stmt.setInt(4, entrega.getIdB2c());

            stmt.executeUpdate();
            System.out.println("Entrega cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir entrega: " + e.getMessage(), e);
        }
    }

    // Lista todas as entregas
    public List<EntregaPevModel> listarEntregas() {
        List<EntregaPevModel> lista = new ArrayList<>();
        String sql = "SELECT id_entrega, dt_entrega, volume_entrega, id_pev, id_b2c FROM entrega_pev ORDER BY id_entrega";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new EntregaPevModel(
                        rs.getInt("id_entrega"),
                        rs.getDate("dt_entrega").toLocalDate(),
                        rs.getBigDecimal("volume_entrega"),
                        rs.getInt("id_pev"),
                        rs.getInt("id_b2c")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar entregas: " + e.getMessage(), e);
        }

        return lista;
    }

    // Atualiza uma entrega pelo ID
    public void atualizarEntrega(EntregaPevModel entrega) {
        String sql = "UPDATE entrega_pev SET dt_entrega = ?, volume_entrega = ?, id_pev = ?, id_b2c = ? WHERE id_entrega = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(entrega.getDtEntrega()));
            stmt.setBigDecimal(2, entrega.getVolumeEntrega());
            stmt.setInt(3, entrega.getIdPev());
            stmt.setInt(4, entrega.getIdB2c());
            stmt.setInt(5, entrega.getIdEntrega());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Entrega atualizada com sucesso!" : "Entrega não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar entrega: " + e.getMessage(), e);
        }
    }

    // Remove uma entrega pelo ID
    public void deletarEntrega(int idEntrega) {
        String sql = "DELETE FROM entrega_pev WHERE id_entrega = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idEntrega);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Entrega deletada com sucesso!" : "Entrega não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar entrega: " + e.getMessage(), e);
        }
    }
}
