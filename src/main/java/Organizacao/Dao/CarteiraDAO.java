package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.CarteiraModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDAO {

    // Cadastra uma carteira (id_b2c ou id_b2b pode ser nulo, mas não os dois)
    public void inserirCarteira(CarteiraModel carteira) {
        String sql = "INSERT INTO carteira (pontos_totais, patente, nivel, id_b2c, id_b2b) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, carteira.getPontosTotais());
            stmt.setString(2, carteira.getPatente());
            stmt.setInt(3, carteira.getNivel());
            stmt.setObject(4, carteira.getIdB2c(), Types.INTEGER);
            stmt.setObject(5, carteira.getIdB2b(), Types.INTEGER);

            stmt.executeUpdate();
            System.out.println("Carteira cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir carteira: " + e.getMessage(), e);
        }
    }

    // Lista todas as carteiras
    public List<CarteiraModel> listarCarteiras() {
        List<CarteiraModel> lista = new ArrayList<>();
        String sql = "SELECT id_carteira, pontos_totais, patente, nivel, id_b2c, id_b2b FROM carteira ORDER BY id_carteira";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new CarteiraModel(
                        rs.getInt("id_carteira"),
                        rs.getInt("pontos_totais"),
                        rs.getString("patente"),
                        rs.getInt("nivel"),
                        rs.getObject("id_b2c", Integer.class),
                        rs.getObject("id_b2b", Integer.class)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar carteiras: " + e.getMessage(), e);
        }

        return lista;
    }

    // Atualiza uma carteira pelo ID
    public void atualizarCarteira(CarteiraModel carteira) {
        String sql = "UPDATE carteira SET pontos_totais = ?, patente = ?, nivel = ?, id_b2c = ?, id_b2b = ? WHERE id_carteira = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, carteira.getPontosTotais());
            stmt.setString(2, carteira.getPatente());
            stmt.setInt(3, carteira.getNivel());
            stmt.setObject(4, carteira.getIdB2c(), Types.INTEGER);
            stmt.setObject(5, carteira.getIdB2b(), Types.INTEGER);
            stmt.setInt(6, carteira.getIdCarteira());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Carteira atualizada com sucesso!" : "Carteira não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar carteira: " + e.getMessage(), e);
        }
    }

    // Remove uma carteira pelo ID
    public void deletarCarteira(int idCarteira) {
        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCarteira);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Carteira deletada com sucesso!" : "Carteira não encontrada.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar carteira: " + e.getMessage(), e);
        }
    }
}
