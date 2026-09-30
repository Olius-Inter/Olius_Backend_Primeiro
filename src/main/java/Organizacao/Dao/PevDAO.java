package Organizacao.Dao;

import Organizacao.Model.PevModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de ponto de entrega voluntaria
public class PevDAO {

    // grava os dados de ponto de entrega voluntaria no banco de dados
    public void inserirPev(PevModel pev) {
        // define a consulta sql de insercao para ponto de entrega voluntaria
        String sql = "INSERT INTO pev (id_endereco, id_usuario_b2b, id_usuario_b2c, qr_code, status, dt_aprovacao) VALUES (?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de ponto de entrega voluntaria com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de ponto de entrega voluntaria aos parametros da insercao
            stmt.setInt(1, pev.getId_endereco());
            stmt.setInt(2, pev.getId_usuario_b2b());
            stmt.setInt(3, pev.getId_usuario_b2c());
            stmt.setString(4, pev.getQr_code());
            stmt.setString(5, pev.getStatus());
            stmt.setDate(6, java.sql.Date.valueOf(pev.getDt_aprovacao()));

            // executa a gravacao dos dados de ponto de entrega voluntaria
            stmt.executeUpdate();
            System.out.println("PEV cadastrado com sucesso!");

        // propaga a falha ao gravar ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir PEV: " + e.getMessage(), e);
        }
    }

    // consulta os registros de ponto de entrega voluntaria e devolve a lista
    public List<PevModel> listarPevs() {
        List<PevModel> listaPevs = new ArrayList<>();
        // define a consulta sql de leitura para ponto de entrega voluntaria
        String sql = "SELECT * FROM pev ORDER BY id_pev";

        // abre a conexao e executa a consulta de ponto de entrega voluntaria com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em ponto de entrega voluntaria
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

        // propaga a falha ao consultar ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar PEVs: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaPevs;
    }

    // atualiza os dados de ponto de entrega voluntaria no banco de dados
    public void atualizarPev(PevModel pev) {
        // define a consulta sql de atualizacao para ponto de entrega voluntaria
        String sql = "UPDATE pev SET id_endereco = ?, id_usuario_b2b = ?, id_usuario_b2c = ?, qr_code = ?, status = ?, dt_aprovacao = ? WHERE id_pev = ?";

        // abre a conexao e prepara a atualizacao de ponto de entrega voluntaria com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de ponto de entrega voluntaria aos parametros da atualizacao
            stmt.setInt(1, pev.getId_endereco());
            stmt.setInt(2, pev.getId_usuario_b2b());
            stmt.setInt(3, pev.getId_usuario_b2c());
            stmt.setString(4, pev.getQr_code());
            stmt.setString(5, pev.getStatus());
            stmt.setDate(6, java.sql.Date.valueOf(pev.getDt_aprovacao()));
            stmt.setInt(7, pev.getId_pev());

            // executa a atualizacao dos dados de ponto de entrega voluntaria
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de ponto de entrega voluntaria foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("PEV atualizado com sucesso!");
            } else {
                System.out.println("PEV não encontrado.");
            }

        // propaga a falha ao atualizar ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar PEV: " + e.getMessage(), e);
        }
    }

    // remove o registro de ponto de entrega voluntaria do banco de dados
    public void deletarPev(int id_pev) {
        // define a consulta sql de exclusao para ponto de entrega voluntaria
        String sql = "DELETE FROM pev WHERE id_pev = ?";

        // abre a conexao e prepara a exclusao de ponto de entrega voluntaria com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_pev);

            // executa a exclusao do registro de ponto de entrega voluntaria
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de ponto de entrega voluntaria foi removido
            if (linhasAfetadas > 0) {
                System.out.println("PEV deletado com sucesso!");
            } else {
                System.out.println("PEV não encontrado.");
            }

        // propaga a falha ao remover ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar PEV: " + e.getMessage(), e);
        }
    }
}
