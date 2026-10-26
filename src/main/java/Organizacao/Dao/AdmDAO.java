package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.AdmModel;
import Organizacao.Model.Enum.EmAtivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdmDAO {

    // Método para cadastrar um novo administrador
    public void inserirAdm(AdmModel adm) {
        String sql = "INSERT INTO ADM (nome, email, em_ativo) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            stmt.setString(3, adm.getEmAtivo().name());

            stmt.executeUpdate();
            System.out.println("Administrador cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir administrador: " + e.getMessage(), e);
        }
    }

    // Método para buscar todos os administradores cadastrados
    public List<AdmModel> listarAdm() {
        List<AdmModel> listaAdm = new ArrayList<>();
        String sql = "SELECT * FROM ADM ORDER BY ID_ADM";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_adm = rs.getInt("ID_ADM");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                EmAtivo em_ativo = EmAtivo.valueOf(rs.getString("em_ativo"));

                AdmModel novoAdm = new AdmModel(
                        id_adm, nome, email, em_ativo
                );

                listaAdm.add(novoAdm);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage(), e);
        }

        return listaAdm;
    }

    // Método para atualizar informações de um administrador
    public void atualizarAdm(AdmModel adm) {
        String sql = "UPDATE ADM SET nome = ?, email = ?, em_ativo = ? WHERE ID_ADM = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            stmt.setString(3, adm.getEmAtivo().name());
            stmt.setInt(4, adm.getIdAdm());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Administrador atualizado com sucesso!");
            } else {
                System.out.println("Nenhum administrador encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar administrador: " + e.getMessage(), e);
        }
    }

    // Método para deletar um administrador pelo ID
    public void deletarAdm(int id) {
        String sql = "DELETE FROM ADM WHERE ID_ADM = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Administrador deletado com sucesso!");
            } else {
                System.out.println("Nenhum administrador encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar administrador: " + e.getMessage(), e);
        }
    }
}