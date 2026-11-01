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


// acessa e administra os registros de certificado empresarial
public class CertificadoDAO {

    // grava os dados de certificado empresarial no banco de dados
    public void inserirCertificado(CertificadoModel certificado) {
        // define a consulta sql de insercao para certificado empresarial
        String sql = "INSERT INTO \"CERTIFICADO\" (\"ID_B2B\", volume_total, \"DT_emissao\", \"PDF\") VALUES (?, ?, CURRENT_DATE, ?)";

        // abre a conexao e prepara a insercao de certificado empresarial com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_certificado"})) {

            // associa os campos de certificado empresarial aos parametros da insercao
            stmt.setObject(1, certificado.getId_usuario_b2b(), java.sql.Types.INTEGER);
            stmt.setDouble(2, certificado.getVolume_total());
            stmt.setString(3, certificado.getArquivo_pdf());

            // executa a gravacao dos dados de certificado empresarial
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    certificado.setId_certificado(rs.getInt(1));
                }
            }
            System.out.println("Certificado cadastrado com sucesso!");

        // propaga a falha ao gravar certificado empresarial para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir certificado: " + e.getMessage(), e);
        }
    }

    // consulta os registros de certificado empresarial e devolve a lista
    public List<CertificadoModel> listarCertificados() {
        List<CertificadoModel> listaCertificados = new ArrayList<>();
        // define a consulta sql de leitura para certificado empresarial
        String sql = "SELECT \"ID_certificado\" AS id_certificado, \"ID_B2B\" AS id_usuario_b2b, \"ID_coleta\" AS id_coleta, NULL::text AS codigo, NULL::text AS nivel, volume_total, \"DT_emissao\" AS dt_emissao, \"PDF\" AS arquivo_pdf FROM \"CERTIFICADO\" ORDER BY \"ID_certificado\"";

        // abre a conexao e executa a consulta de certificado empresarial com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em certificado empresarial
            while (rs.next()) {
                int id_certificado = rs.getInt("id_certificado");
                Integer id_usuario_b2b = rs.getObject("id_usuario_b2b", Integer.class);
                String codigo = rs.getString("codigo");
                String nivel = rs.getString("nivel");
                double volume_total = rs.getDouble("volume_total");
                LocalDate dt_emissao = rs.getObject("dt_emissao", LocalDate.class);
                String arquivo_pdf = rs.getString("arquivo_pdf");

                CertificadoModel certificado = new CertificadoModel(
                        id_usuario_b2b, codigo, nivel, volume_total, dt_emissao, arquivo_pdf
                );
                certificado.setId_certificado(id_certificado);
                certificado.setId_coleta(rs.getInt("id_coleta"));

                listaCertificados.add(certificado);
            }

        // propaga a falha ao consultar certificado empresarial para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar certificados: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaCertificados;
    }

    // atualiza os dados de certificado empresarial no banco de dados
    public void atualizarCertificado(CertificadoModel certificado) {
        // define a consulta sql de atualizacao para certificado empresarial
        String sql = "UPDATE \"CERTIFICADO\" SET \"ID_B2B\" = ?, volume_total = ?, \"PDF\" = ? WHERE \"ID_certificado\" = ?";

        // abre a conexao e prepara a atualizacao de certificado empresarial com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de certificado empresarial aos parametros da atualizacao
            stmt.setObject(1, certificado.getId_usuario_b2b(), java.sql.Types.INTEGER);
            stmt.setDouble(2, certificado.getVolume_total());
            stmt.setString(3, certificado.getArquivo_pdf());
            stmt.setInt(4, certificado.getId_certificado());

            // executa a atualizacao dos dados de certificado empresarial
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de certificado empresarial foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Certificado atualizado com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // propaga a falha ao atualizar certificado empresarial para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar certificado: " + e.getMessage(), e);
        }
    }

    // remove o registro de certificado empresarial do banco de dados
    public void deletarCertificado(int id_certificado) {
        // define a consulta sql de exclusao para certificado empresarial
        String sql = "DELETE FROM \"CERTIFICADO\" WHERE \"ID_certificado\" = ?";

        // abre a conexao e prepara a exclusao de certificado empresarial com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_certificado);

            // executa a exclusao do registro de certificado empresarial
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de certificado empresarial foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Certificado deletado com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // registra a falha ao remover certificado empresarial e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar certificado: " + e.getMessage(), e);
        }
    }
}
