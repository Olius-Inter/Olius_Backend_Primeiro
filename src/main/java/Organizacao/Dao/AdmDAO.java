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

    // Método para cadastrar um novo administrador (a senha já vem em hash no modelo)
    public void inserirAdm(AdmModel adm) {
        String sql = "INSERT INTO adm (nome, email, senha, em_ativo) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            stmt.setString(3, adm.getSenha());
            stmt.setString(4, adm.getEmAtivo().name());

            stmt.executeUpdate();
            System.out.println("Administrador cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir administrador: " + e.getMessage(), e);
        }
    }

    // Método para buscar todos os administradores cadastrados (sem a senha)
    public List<AdmModel> listarAdm() {
        List<AdmModel> listaAdm = new ArrayList<>();
        String sql = "SELECT id_adm, nome, email, em_ativo FROM adm ORDER BY id_adm";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaAdm.add(new AdmModel(
                        rs.getInt("id_adm"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        EmAtivo.valueOf(rs.getString("em_ativo"))
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage(), e);
        }

        return listaAdm;
    }

    // Busca um administrador pelo e-mail (usado no login). Retorna null se não existir.
    public AdmModel buscarPorEmail(String email) {
        String sql = "SELECT id_adm, nome, email, senha, em_ativo FROM adm WHERE LOWER(email) = LOWER(?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AdmModel adm = new AdmModel(
                            rs.getInt("id_adm"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            EmAtivo.valueOf(rs.getString("em_ativo"))
                    );
                    adm.setSenha(rs.getString("senha"));
                    return adm;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar administrador por e-mail: " + e.getMessage(), e);
        }

        return null;
    }

    // Método para atualizar um administrador (a senha só muda se vier uma nova no modelo)
    public void atualizarAdm(AdmModel adm) {
        boolean trocaSenha = adm.getSenha() != null;
        String sql = trocaSenha
                ? "UPDATE adm SET nome = ?, email = ?, em_ativo = ?, senha = ? WHERE id_adm = ?"
                : "UPDATE adm SET nome = ?, email = ?, em_ativo = ? WHERE id_adm = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            stmt.setString(3, adm.getEmAtivo().name());
            if (trocaSenha) {
                stmt.setString(4, adm.getSenha());
                stmt.setInt(5, adm.getIdAdm());
            } else {
                stmt.setInt(4, adm.getIdAdm());
            }

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
        String sql = "DELETE FROM adm WHERE id_adm = ?";

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
