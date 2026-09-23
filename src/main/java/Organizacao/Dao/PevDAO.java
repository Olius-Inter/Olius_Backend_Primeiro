package Organizacao.Dao;

import Organizacao.Model.PevModel;
import Organizacao.Conexo.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PevDAO {

    // CREATE
    public void inserirPev(PevModel pev) {
        String sql = "INSERT INTO pev (id_endereco, id_usuario_b2b, id_usuario_b2c, qr_code, status, dt_aprovacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pev.getId_endereco());
            stmt.setInt(2, pev.getId_usuario_b2b());
            stmt.setInt(3, pev.getId_usuario_b2c());
            stmt.setString(4, pev.getQr_code());
            stmt.setString(5, pev.getStatus());
            stmt.setDate(6, java.sql.Date.valueOf(pev.getDt_aprovacao()));

            stmt.executeUpdate();
            System.out.println("PEV cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir PEV: " + e.getMessage(), e);
        }
    }

    // READ
    public List<PevModel> listarPevs() {
        List<PevModel> listaPevs = new ArrayList<>();
        String sql = "SELECT * FROM pev ORDER BY id_pev";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_pev = rs.getInt("id_pev");
                int id_endereco = rs.getInt("id_endereco");
                int id_usuario_b2b = rs.getInt("id_usuario_b2b");
                int id_usuario_b2c = rs.getInt("id_usuario_b2c");
                String qr_code = rs.getString("qr_code");
                String status = rs.getString("status");
                LocalDate dt_aprovacao = rs.getObject("dt_aprovacao", LocalDate.class);

                PevModel pev = new PevModel(
                        id_endereco, id_usuario_b2b, id_usuario_b2c, qr_code, status, dt_aprovacao
                );
                pev.setId_pev(id_pev);

                listaPevs.add(pev);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar PEVs: " + e.getMessage(), e);
        }

        return listaPevs;
    }

    // UPDATE
    public void atualizarPev(PevModel pev) {
        String sql = "UPDATE pev SET id_endereco = ?, id_usuario_b2b = ?, id_usuario_b2c = ?, qr_code = ?, status = ?, dt_aprovacao = ? WHERE id_pev = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pev.getId_endereco());
            stmt.setInt(2, pev.getId_usuario_b2b());
            stmt.setInt(3, pev.getId_usuario_b2c());
            stmt.setString(4, pev.getQr_code());
            stmt.setString(5, pev.getStatus());
            stmt.setDate(6, java.sql.Date.valueOf(pev.getDt_aprovacao()));
            stmt.setInt(7, pev.getId_pev());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("PEV atualizado com sucesso!");
            } else {
                System.out.println("PEV não encontrado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar PEV: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarPev(int id_pev) {
        String sql = "DELETE FROM pev WHERE id_pev = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_pev);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("PEV deletado com sucesso!");
            } else {
                System.out.println("PEV não encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar PEV: " + e.getMessage());
        }
    }
}
