package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.CertificadoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CertificadoDAO {

    // Metodo para gerar/cadastrar certificado de descarte
    public void inserirCertificado(CertificadoModel certificado) {
        String sql = "INSERT INTO CERTIFICADO (volume_total, DT_emissao, PDF, ID_B2B, ID_coleta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, certificado.getVolumeTotal());
            stmt.setDate(2, java.sql.Date.valueOf(certificado.getDtEmissao()));
            stmt.setString(3, certificado.getPdf());
            stmt.setInt(4, certificado.getIdB2B());
            stmt.setInt(5, certificado.getIdColeta());

            stmt.executeUpdate();
            System.out.println("Certificado cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir certificado: " + e.getMessage(), e);
        }
    }

    // Metodo para consultar certificados emitidos
    public List<CertificadoModel> listarCertificado() {
        List<CertificadoModel> listaCertificado = new ArrayList<>();
        String sql = "SELECT * FROM CERTIFICADO ORDER BY ID_certificado";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_certificado = rs.getInt("ID_certificado");
                double volume_total = rs.getDouble("volume_total");
                LocalDate dt_emissao = rs.getDate("DT_emissao").toLocalDate();
                String pdf = rs.getString("PDF");
                int id_B2B = rs.getInt("ID_B2B");
                int id_coleta = rs.getInt("ID_coleta");

                CertificadoModel novoCertificado = new CertificadoModel(
                        id_certificado, volume_total, dt_emissao, pdf, id_B2B, id_coleta
                );

                listaCertificado.add(novoCertificado);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar certificados: " + e.getMessage(), e);
        }

        return listaCertificado;
    }

    // Metodo para atualizar informações do certificado
    public void atualizarCertificado(CertificadoModel certificado) {
        String sql = "UPDATE CERTIFICADO SET volume_total = ?, DT_emissao = ?, PDF = ?, ID_B2B = ?, ID_coleta = ? WHERE ID_certificado = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, certificado.getVolumeTotal());
            stmt.setDate(2, java.sql.Date.valueOf(certificado.getDtEmissao()));
            stmt.setString(3, certificado.getPdf());
            stmt.setInt(4, certificado.getIdB2B());
            stmt.setInt(5, certificado.getIdColeta());
            stmt.setInt(6, certificado.getIdCertificado());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Certificado atualizado com sucesso!");
            } else {
                System.out.println("Nenhum certificado encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar certificado: " + e.getMessage(), e);
        }
    }

    // Metodo para remover um certificado
    public void deletarCertificado(int id) {
        String sql = "DELETE FROM CERTIFICADO WHERE ID_certificado = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Certificado deletado com sucesso!");
            } else {
                System.out.println("Nenhum certificado encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar certificado: " + e.getMessage(), e);
        }
    }
}