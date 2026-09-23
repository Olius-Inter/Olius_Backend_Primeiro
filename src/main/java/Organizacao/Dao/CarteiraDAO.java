package Organizacao.Dao;

import Organizacao.Model.CarteiraModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDAO {

    // CREATE
    public void inserirCarteira(CarteiraModel carteira) {

        String sql = "INSERT INTO carteira (pontuacao, patente, nivel, id_b2c) VALUES (?, ?, ?, ?)";

        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getPatente());
            stmt.setString(3, carteira.getNivel());
            stmt.setInt(4, carteira.getId_b2c());

            stmt.executeUpdate();

            System.out.println("Carteira cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir carteira: " + e.getMessage());
        }
    }

    // READ
    public List<CarteiraModel> listarCarteiras() {

        List<CarteiraModel> listaCarteiras = new ArrayList<>();

        String sql = "SELECT * FROM carteira ORDER BY id_carteira";

        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

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

        } catch (SQLException e) {
            System.out.println("Erro ao listar carteiras: " + e.getMessage());
        }

        return listaCarteiras;
    }

    // UPDATE
    public void atualizarCarteira(CarteiraModel carteira) {

        String sql = "UPDATE carteira SET pontuacao = ?, patente = ?, nivel = ?, id_b2c = ? WHERE id_carteira = ?";

        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, carteira.getPontuacao());
            stmt.setString(2, carteira.getPatente());
            stmt.setString(3, carteira.getNivel());
            stmt.setInt(4, carteira.getId_b2c());
            stmt.setInt(5, carteira.getId_carteira());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Carteira atualizada com sucesso!");
            } else {
                System.out.println("Carteira não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao atualizar carteira: " + e.getMessage());
        }
    }

    // DELETE
    public void deletarCarteira(int id_carteira) {

        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

        try (
                Connection conexao = Conexao_Banco.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id_carteira);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Carteira deletada com sucesso!");
            } else {
                System.out.println("Carteira não encontrada.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao deletar carteira: " + e.getMessage());
        }
    }
}