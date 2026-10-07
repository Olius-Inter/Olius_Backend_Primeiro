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
        String sql = "INSERT INTO carteira (pontuacao, patente, nivel, id_b2c) VALUES (?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            // associa os campos de carteira de pontos aos parametros da insercao
            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getPatente());
            stmt.setString(3, carteira.getNivel());
            stmt.setInt(4, carteira.getId_b2c());

            // executa a gravacao dos dados de carteira de pontos
            stmt.executeUpdate();

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
        String sql = "SELECT * FROM carteira ORDER BY id_carteira";

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
                int id_b2c = rs.getInt("id_b2c");

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
            System.out.println("Erro ao listar carteiras: " + e.getMessage());
        }

        // devolve os registros encontrados pela consulta
        return listaCarteiras;
    }

    // atualiza os dados de carteira de pontos no banco de dados
    public void atualizarCarteira(CarteiraModel carteira) {

        // define a consulta sql de atualizacao para carteira de pontos
        String sql = "UPDATE carteira SET pontuacao = ?, patente = ?, nivel = ?, id_b2c = ? WHERE id_carteira = ?";

        // abre a conexao e prepara a atualizacao de carteira de pontos com fechamento automatico
        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            // associa os campos de carteira de pontos aos parametros da atualizacao
            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getPatente());
            stmt.setString(3, carteira.getNivel());
            stmt.setInt(4, carteira.getId_b2c());
            stmt.setInt(5, carteira.getId_carteira());

            // executa a atualizacao dos dados de carteira de pontos
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de carteira de pontos foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Carteira atualizada com sucesso!");
            } else {
                System.out.println("Carteira não encontrada.");
            }

        // registra a falha ao atualizar carteira de pontos e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar carteira: " + e.getMessage(), e);
        }
    }

    // remove o registro de carteira de pontos do banco de dados
    public void deletarCarteira(int id_carteira) {

        // define a consulta sql de exclusao para carteira de pontos
        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

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
                System.out.println("Carteira não encontrada.");
            }

        // registra a falha ao remover carteira de pontos e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar carteira: " + e.getMessage(), e);
        }
    }
}