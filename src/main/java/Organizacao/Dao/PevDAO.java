package Organizacao.Dao;

import Organizacao.Model.PevModel;
import Organizacao.Model.EnderecoModel;
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
        try (Connection conexao = Conexao_Banco.conectar()) {
            inserirPev(pev, conexao);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void inserirPev(PevModel pev, Connection conexao) {
        // define a consulta sql de insercao para ponto de entrega voluntaria
        String sql = "INSERT INTO \"PEV\" (\"ID_endereco\", \"ID_usuario\", status, \"DT_aprovacao\") VALUES (?, ?, CAST(? AS public.status_pev), CURRENT_DATE)";

        // abre a conexao e prepara a insercao de ponto de entrega voluntaria com fechamento automatico
        try (PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_PEV"})) {

            // associa os campos de ponto de entrega voluntaria aos parametros da insercao
            stmt.setObject(1, pev.getId_endereco(), java.sql.Types.INTEGER);
            stmt.setObject(2, pev.getId_usuario(), java.sql.Types.INTEGER);
            stmt.setString(3, pev.getStatus());

            // executa a gravacao dos dados de ponto de entrega voluntaria
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pev.setId_pev(rs.getInt(1));
                }
            }
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
        String sql = "SELECT p.\"ID_PEV\" AS id_pev, p.\"ID_usuario\" AS id_usuario, p.\"ID_endereco\" AS id_endereco, p.status, p.\"DT_aprovacao\" AS dt_aprovacao, e.rua, e.numero, e.bairro, e.cidade, e.estado, e.cep, e.complemento FROM \"PEV\" p LEFT JOIN \"ENDERECO\" e ON e.\"ID_endereco\" = p.\"ID_endereco\" ORDER BY p.\"ID_PEV\"";

        // abre a conexao e executa a consulta de ponto de entrega voluntaria com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em ponto de entrega voluntaria
            while (rs.next()) {
                int id_pev = rs.getInt("id_pev");
                Integer id_endereco = rs.getObject("id_endereco", Integer.class);
                Integer id_usuario_b2b = null;
                Integer id_usuario_b2c = null;
                String qr_code = null;
                String status = rs.getString("status");
                LocalDate dt_aprovacao = rs.getObject("dt_aprovacao", LocalDate.class);

                PevModel pev = new PevModel(
                        id_endereco, id_usuario_b2b, id_usuario_b2c, qr_code, status, dt_aprovacao
                );
                pev.setId_pev(id_pev);
                pev.setId_usuario(rs.getObject("id_usuario", Integer.class));
                if (id_endereco != null) {
                    pev.setEndereco(new EnderecoModel(rs.getString("cep"), rs.getString("rua"),
                            rs.getString("numero"), rs.getString("complemento"), rs.getString("bairro"),
                            rs.getString("cidade"), rs.getString("estado"), "Brasil"));
                }

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
        try (Connection conexao = Conexao_Banco.conectar()) {
            atualizarPev(pev, conexao);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco: " + e.getMessage(), e);
        }
    }

    public void atualizarPev(PevModel pev, Connection conexao) {
        // define a consulta sql de atualizacao para ponto de entrega voluntaria
        String sql = "UPDATE \"PEV\" SET \"ID_endereco\" = ?, status = CAST(? AS public.status_pev), \"ID_usuario\" = ? WHERE \"ID_PEV\" = ?";

        // abre a conexao e prepara a atualizacao de ponto de entrega voluntaria com fechamento automatico
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de ponto de entrega voluntaria aos parametros da atualizacao
            stmt.setObject(1, pev.getId_endereco(), java.sql.Types.INTEGER);
            stmt.setString(2, pev.getStatus());
            stmt.setObject(3, pev.getId_usuario(), java.sql.Types.INTEGER);
            stmt.setInt(4, pev.getId_pev());

            // executa a atualizacao dos dados de ponto de entrega voluntaria
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de ponto de entrega voluntaria foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("PEV atualizado com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // propaga a falha ao atualizar ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar PEV: " + e.getMessage(), e);
        }
    }

    // remove o registro de ponto de entrega voluntaria do banco de dados
    public void deletarPev(int id_pev) {
        // define a consulta sql de exclusao para ponto de entrega voluntaria
        String sql = "DELETE FROM \"PEV\" WHERE \"ID_PEV\" = ?";

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
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

        // propaga a falha ao remover ponto de entrega voluntaria para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar PEV: " + e.getMessage(), e);
        }
    }
    // grava o cadastro e seu endereco juntos, sem deixar gravacoes parciais
    public void salvarComEndereco(PevModel pev, EnderecoModel endereco, boolean atualizar) {
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                Integer id_endereco = null;
                if (atualizar) {
                    String sql = "SELECT \"ID_endereco\" AS id_endereco FROM \"PEV\" WHERE \"ID_PEV\" = ? FOR UPDATE";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setInt(1, pev.getId_pev());
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (!rs.next()) {
                                throw new java.util.NoSuchElementException("Registro não encontrado.");
                            }
                            id_endereco = rs.getObject("id_endereco", Integer.class);
                        }
                    }
                }
                new EnderecoDAO().salvarEndereco(endereco, id_endereco, conexao);
                pev.setId_endereco(endereco.getId_endereco());
                if (atualizar) {
                    atualizarPev(pev, conexao);
                } else {
                    inserirPev(pev, conexao);
                }
                conexao.commit();
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cadastro e endereço: " + e.getMessage(), e);
        }
    }
}
