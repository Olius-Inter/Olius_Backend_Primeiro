package Organizacao.Dao;

import Organizacao.Model.CarteiraModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de carteira de pontos
public class CarteiraDAO {

    // grava os dados de carteira de pontos no banco de dados
    public void inserirCarteira(CarteiraModel carteira) {

        // define a consulta sql de insercao para carteira de pontos
        String sql = "INSERT INTO \"CARTEIRA\" (pontos_totais, nivel, \"ID_B2C\") VALUES (?, CAST(? AS integer), ?)";

        // abre a conexao e prepara a insercao de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"ID_carteira"})
        ) {

            // associa os campos de carteira de pontos aos parametros da insercao
            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getNivel());
            stmt.setObject(3, carteira.getId_b2c(), java.sql.Types.INTEGER);

            // executa a gravacao dos dados de carteira de pontos
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    carteira.setId_carteira(rs.getInt(1));
                }
            }

            System.out.println("Carteira cadastrada com sucesso!");

            // registra a falha ao gravar carteira de pontos e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir carteira: " + e.getMessage(), e);
        }
    }

    // consulta os registros de carteira de pontos e devolve a lista
    public List<CarteiraModel> listarCarteiras() {

        List<CarteiraModel> listaCarteiras = new ArrayList<>();

        // define a consulta sql de leitura para carteira de pontos
        String sql = "SELECT \"ID_carteira\" AS id_carteira, pontos_totais AS pontuacao, NULL::text AS patente, nivel, \"ID_B2C\" AS id_b2c FROM \"CARTEIRA\" ORDER BY \"ID_carteira\"";

        // abre a conexao e executa a consulta de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            // converte os campos de cada linha consultada em carteira de pontos
            while (rs.next()) {

                int id_carteira = rs.getInt("id_carteira");
                int pontuacao = rs.getInt("pontuacao");
                String patente = rs.getString("patente");
                String nivel = rs.getString("nivel");
                Integer id_b2c = rs.getObject("id_b2c", Integer.class);

                CarteiraModel carteira = new CarteiraModel(
                        pontuacao,
                        patente,
                        nivel,
                        id_b2c
                );

                carteira.setId_carteira(id_carteira);

                listaCarteiras.add(carteira);
            }

            // propaga a falha ao consultar carteira de pontos para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar carteiras: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaCarteiras;
    }

    // atualiza os dados de carteira de pontos no banco de dados
    public void atualizarCarteira(CarteiraModel carteira) {

        // define a consulta sql de atualizacao para carteira de pontos
        String sql = "UPDATE \"CARTEIRA\" SET pontos_totais = ?, nivel = CAST(? AS integer), \"ID_B2C\" = ? WHERE \"ID_carteira\" = ?";

        // abre a conexao e prepara a atualizacao de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            // associa os campos de carteira de pontos aos parametros da atualizacao
            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getNivel());
            stmt.setObject(3, carteira.getId_b2c(), java.sql.Types.INTEGER);
            stmt.setInt(4, carteira.getId_carteira());

            // executa a atualizacao dos dados de carteira de pontos
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de carteira de pontos foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Carteira atualizada com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

            // registra a falha ao atualizar carteira de pontos e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar carteira: " + e.getMessage(), e);
        }
    }

    // remove o registro de carteira de pontos do banco de dados
    public void deletarCarteira(int id_carteira) {

        // define a consulta sql de exclusao para carteira de pontos
        String sql = "DELETE FROM \"CARTEIRA\" WHERE \"ID_carteira\" = ?";

        // abre a conexao e prepara a exclusao de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_carteira);

            // executa a exclusao do registro de carteira de pontos
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de carteira de pontos foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Carteira deletada com sucesso!");
            } else {
                throw new java.util.NoSuchElementException("Registro não encontrado.");
            }

            // registra a falha ao remover carteira de pontos e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar carteira: " + e.getMessage(), e);
        }
    }
}