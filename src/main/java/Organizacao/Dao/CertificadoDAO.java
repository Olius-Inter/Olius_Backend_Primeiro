package Organizacao.Dao;

import Organizacao.Model.CertificadoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CertificadoDAO {

    // CREATE
    public void inserirCertificado(CertificadoModel certificado) {
        String sql = "INSERT INTO certificado (id_usuario_b2b, codigo, nivel, volume_total, dt_emissao, arquivo_pdf) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, certificado.getId_usuario_b2b());
            stmt.setString(2, certificado.getCodigo());
            stmt.setString(3, certificado.getNivel());
            stmt.setDouble(4, certificado.getVolume_total());
            stmt.setDate(5, java.sql.Date.valueOf(certificado.getDt_emissao()));
            stmt.setString(6, certificado.getArquivo_pdf());

            stmt.executeUpdate();
            System.out.println("Certificado cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir certificado: " + e.getMessage(), e);
        }
    }

    // READ
    public List<CertificadoModel> listarCertificados() {
        List<CertificadoModel> listaCertificados = new ArrayList<>();
        String sql = "SELECT * FROM certificado ORDER BY id_certificado";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_certificado = rs.getInt("id_certificado");
                int id_usuario_b2b = rs.getInt("id_usuario_b2b");
                String codigo = rs.getString("codigo");
                String nivel = rs.getString("nivel");
                double volume_total = rs.getDouble("volume_total");
                LocalDate dt_emissao = rs.getObject("dt_emissao", LocalDate.class);
                String arquivo_pdf = rs.getString("arquivo_pdf");

                CertificadoModel certificado = new CertificadoModel(
                        id_usuario_b2b, codigo, nivel, volume_total, dt_emissao, arquivo_pdf
                );
                certificado.setId_certificado(id_certificado);

                listaCertificados.add(certificado);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar certificados: " + e.getMessage(), e);
        }

        return listaCertificados;
    }

    // UPDATE
    public void atualizarCertificado(CertificadoModel certificado) {
        String sql = "UPDATE certificado SET id_usuario_b2b = ?, codigo = ?, nivel = ?, volume_total = ?, dt_emissao = ?, arquivo_pdf = ? WHERE id_certificado = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, certificado.getId_usuario_b2b());
            stmt.setString(2, certificado.getCodigo());
            stmt.setString(3, certificado.getNivel());
            stmt.setDouble(4, certificado.getVolume_total());
            stmt.setDate(5, java.sql.Date.valueOf(certificado.getDt_emissao()));
            stmt.setString(6, certificado.getArquivo_pdf());
            stmt.setInt(7, certificado.getId_certificado());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Certificado atualizado com sucesso!");
            } else {
                System.out.println("Certificado não encontrado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar certificado: " + e.getMessage(), e);
        }
    }

    // DELETE
    public void deletarCertificado(int id_certificado) {
        String sql = "DELETE FROM certificado WHERE id_certificado = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_certificado);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Certificado deletado com sucesso!");
            } else {
                System.out.println("Certificado não encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar certificado: " + e.getMessage());
        }
    }
}
