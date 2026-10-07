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
        String sql = "INSERT INTO coleta (id_solicitacao, id_motorista, dt_coleta, volume, observacao) VALUES (?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de coleta aos parametros da insercao
            stmt.setInt(1, coleta.getId_solicitacao());
            stmt.setInt(2, coleta.getId_motorista());
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setString(5, coleta.getObservacao());

            // executa a gravacao dos dados de coleta
            stmt.executeUpdate();
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
        String sql = "SELECT * FROM coleta ORDER BY id_coleta";

        // abre a conexao e executa a consulta de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em coleta
            while (rs.next()) {
                int id_coleta = rs.getInt("id_coleta");
                int id_solicitacao = rs.getInt("id_solicitacao");
                int id_motorista = rs.getInt("id_motorista");
                LocalDate dt_coleta = rs.getObject("dt_coleta", LocalDate.class);
                double volume = rs.getDouble("volume");
                String observacao = rs.getString("observacao");

                ColetaModel coleta = new ColetaModel(id_solicitacao, id_motorista, dt_coleta, volume, observacao);
                coleta.setId_coleta(id_coleta);

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
        String sql = "UPDATE coleta SET id_solicitacao = ?, id_motorista = ?, dt_coleta = ?, volume = ?, observacao = ? WHERE id_coleta = ?";

        // abre a conexao e prepara a atualizacao de coleta com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de coleta aos parametros da atualizacao
            stmt.setInt(1, coleta.getId_solicitacao());
            stmt.setInt(2, coleta.getId_motorista());
            stmt.setDate(3, java.sql.Date.valueOf(coleta.getDt_coleta()));
            stmt.setDouble(4, coleta.getVolume());
            stmt.setString(5, coleta.getObservacao());
            stmt.setInt(6, coleta.getId_coleta());

            // executa a atualizacao dos dados de coleta
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de coleta foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Coleta atualizada com sucesso!");
            } else {
                System.out.println("Coleta não encontrada.");
            }

        // propaga a falha ao atualizar coleta para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar coleta: " + e.getMessage(), e);
        }
    }

    // remove o registro de coleta do banco de dados
    public void deletarColeta(int id_coleta) {
        // define a consulta sql de exclusao para coleta
        String sql = "DELETE FROM coleta WHERE id_coleta = ?";

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
                System.out.println("Coleta não encontrada.");
            }

        // registra a falha ao remover coleta e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar coleta: " + e.getMessage(), e);
        }
    }
}
