package Organizacao.Dao;

import Organizacao.Model.ColetaModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de coleta
public class ColetaDAO {

    // grava os dados de coleta no banco de dados
    public void inserirColeta(ColetaModel coleta) {
        // define a consulta sql de insercao para coleta
        String sql = "INSERT INTO \"COLETA\" (\"ID_solicitacao\", \"ID_motorista\", \"DT_coleta\", volume_coletado, avaliacao_coleta, \"ID_B2B\") VALUES (?, ?, ?, ?, ?, (SELECT \"ID_B2B\" FROM \"SOLICITACAO_COLETA\" WHERE \"ID_solicitacao\" = ?))";

        // abre a conexao e prepara a insercao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_coleta"})) {

            // associa os campos de coleta aos parametros da insercao
            stmt.setObject(1, coleta.getId_solicitacao(), java.sql.Types.INTEGER);
            stmt.setObject(2, coleta.getId_motorista(), java.sql.Types.INTEGER);
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setInt(5, coleta.getAvaliacao_coleta());
            stmt.setObject(6, coleta.getId_solicitacao(), java.sql.Types.INTEGER);

            // executa a gravacao dos dados de coleta
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    coleta.setId_coleta(rs.getInt(1));
                }
            }
            System.out.println("Coleta cadastrada com sucesso!");

        // propaga a falha ao gravar coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir coleta: " + e.getMessage(), e);
        }
    }

    // consulta os registros de coleta e devolve a lista
    public List<ColetaModel> listarColetas() {
        List<ColetaModel> listaColetas = new ArrayList<>();
        // define a consulta sql de leitura para coleta
        String sql = "SELECT \"ID_coleta\" AS id_coleta, \"ID_solicitacao\" AS id_solicitacao, \"ID_motorista\" AS id_motorista, \"DT_coleta\" AS dt_coleta, volume_coletado AS volume, avaliacao_coleta FROM \"COLETA\" ORDER BY \"ID_coleta\"";

        // abre a conexao e executa a consulta de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em coleta
            while (rs.next()) {
                int id_coleta = rs.getInt("id_coleta");
                Integer id_solicitacao = rs.getObject("id_solicitacao", Integer.class);
                Integer id_motorista = rs.getObject("id_motorista", Integer.class);
                LocalDate dt_coleta = rs.getObject("dt_coleta", LocalDate.class);
                double volume = rs.getDouble("volume");
                String observacao = null;

                ColetaModel coleta = new ColetaModel(id_solicitacao, id_motorista, dt_coleta, volume, observacao);
                coleta.setId_coleta(id_coleta);
                coleta.setAvaliacao_coleta(rs.getInt("avaliacao_coleta"));

                listaColetas.add(coleta);
            }

        // propaga a falha ao consultar coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar coletas: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaColetas;
    }

    // atualiza os dados de coleta no banco de dados
    public void atualizarColeta(ColetaModel coleta) {
        // define a consulta sql de atualizacao para coleta
        String sql = "UPDATE \"COLETA\" SET \"ID_solicitacao\" = ?, \"ID_motorista\" = ?, \"DT_coleta\" = ?, volume_coletado = ?, avaliacao_coleta = ?, \"ID_B2B\" = (SELECT \"ID_B2B\" FROM \"SOLICITACAO_COLETA\" WHERE \"ID_solicitacao\" = ?) WHERE \"ID_coleta\" = ?";

        // abre a conexao e prepara a atualizacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de coleta aos parametros da atualizacao
            stmt.setObject(1, coleta.getId_solicitacao(), java.sql.Types.INTEGER);
            stmt.setObject(2, coleta.getId_motorista(), java.sql.Types.INTEGER);
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setInt(5, coleta.getAvaliacao_coleta());
            stmt.setObject(6, coleta.getId_solicitacao(), java.sql.Types.INTEGER);
            stmt.setInt(7, coleta.getId_coleta());

            // executa a atualizacao dos dados de coleta
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de coleta foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Coleta atualizada com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // propaga a falha ao atualizar coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar coleta: " + e.getMessage(), e);
        }
    }

    // remove o registro de coleta do banco de dados
    public void deletarColeta(int id_coleta) {
        // define a consulta sql de exclusao para coleta
        String sql = "DELETE FROM \"COLETA\" WHERE \"ID_coleta\" = ?";

        // abre a conexao e prepara a exclusao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_coleta);

            // executa a exclusao do registro de coleta
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de coleta foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Coleta deletada com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // registra a falha ao remover coleta e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar coleta: " + e.getMessage(), e);
        }
    }
}
