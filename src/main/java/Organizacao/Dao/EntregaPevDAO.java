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


// acessa e administra os registros de entrega em ponto voluntario
public class EntregaPevDAO {

    // grava os dados de entrega em ponto voluntario no banco de dados
    public void inserirEntregaPev(EntregaPevModel entrega) {
        // define a consulta sql de insercao para entrega em ponto voluntario
        String sql = "INSERT INTO entrega_pev (id_usuario_b2c, id_pev, qtd_litros, pontos_gerados, dt_entrega) VALUES (?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de entrega em ponto voluntario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de entrega em ponto voluntario aos parametros da insercao
            stmt.setInt(1, entrega.getId_usuario_b2c());
            stmt.setInt(2, entrega.getId_pev());
            stmt.setDouble(3, entrega.getQtd_litros());
            stmt.setInt(4, entrega.getPontos_gerados());
            stmt.setDate(5, java.sql.Date.valueOf(entrega.getDt_entrega()));

            // executa a gravacao dos dados de entrega em ponto voluntario
            stmt.executeUpdate();
            System.out.println("Entrega PEV cadastrada com sucesso!");

        // propaga a falha ao gravar entrega em ponto voluntario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir entrega PEV: " + e.getMessage(), e);
        }
    }

    // consulta os registros de entrega em ponto voluntario e devolve a lista
    public List<EntregaPevModel> listarEntregasPev() {
        List<EntregaPevModel> listaEntregas = new ArrayList<>();
        // define a consulta sql de leitura para entrega em ponto voluntario
        String sql = "SELECT * FROM entrega_pev ORDER BY id_entrega";

        // abre a conexao e executa a consulta de entrega em ponto voluntario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em entrega em ponto voluntario
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

        // propaga a falha ao consultar entrega em ponto voluntario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar entregas PEV: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaEntregas;
    }

    // atualiza os dados de entrega em ponto voluntario no banco de dados
    public void atualizarEntregaPev(EntregaPevModel entrega) {
        // define a consulta sql de atualizacao para entrega em ponto voluntario
        String sql = "UPDATE entrega_pev SET id_usuario_b2c = ?, id_pev = ?, qtd_litros = ?, pontos_gerados = ?, dt_entrega = ? WHERE id_entrega = ?";

        // abre a conexao e prepara a atualizacao de entrega em ponto voluntario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de entrega em ponto voluntario aos parametros da atualizacao
            stmt.setInt(1, entrega.getId_usuario_b2c());
            stmt.setInt(2, entrega.getId_pev());
            stmt.setDouble(3, entrega.getQtd_litros());
            stmt.setInt(4, entrega.getPontos_gerados());
            stmt.setDate(5, java.sql.Date.valueOf(entrega.getDt_entrega()));
            stmt.setInt(6, entrega.getId_entrega());

            // executa a atualizacao dos dados de entrega em ponto voluntario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de entrega em ponto voluntario foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Entrega PEV atualizada com sucesso!");
            } else {
                System.out.println("Entrega PEV não encontrada.");
            }

        // propaga a falha ao atualizar entrega em ponto voluntario para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar entrega PEV: " + e.getMessage(), e);
        }
    }

    // remove o registro de entrega em ponto voluntario do banco de dados
    public void deletarEntregaPev(int id_entrega) {
        // define a consulta sql de exclusao para entrega em ponto voluntario
        String sql = "DELETE FROM entrega_pev WHERE id_entrega = ?";

        // abre a conexao e prepara a exclusao de entrega em ponto voluntario com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_entrega);

            // executa a exclusao do registro de entrega em ponto voluntario
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de entrega em ponto voluntario foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Entrega PEV deletada com sucesso!");
            } else {
                System.out.println("Entrega PEV não encontrada.");
            }

        // registra a falha ao remover entrega em ponto voluntario e informa o ocorrido

        } catch (Exception e) {
            System.out.println("Erro ao deletar entrega PEV: " + e.getMessage());
        }
    }
}
