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

// acessa e administra os registros de administrador (tabela "ADM")
// a tabela nao tem coluna de situacao: todo administrador cadastrado pode entrar no painel
public class AdmDAO {

    // cadastra um novo administrador (a senha ja vem em hash no modelo)
    public void inserirAdm(AdmModel adm) {
        String sql = "INSERT INTO \"ADM\" (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            stmt.setString(3, adm.getSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir administrador: " + e.getMessage(), e);
        }
    }

    // lista todos os administradores (sem a senha)
    public List<AdmModel> listarAdm() {
        List<AdmModel> listaAdm = new ArrayList<>();
        String sql = "SELECT \"ID_ADM\" AS id_adm, nome, email FROM \"ADM\" ORDER BY \"ID_ADM\"";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaAdm.add(new AdmModel(
                        rs.getInt("id_adm"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        EmAtivo.Ativo
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores: " + e.getMessage(), e);
        }

        return listaAdm;
    }

    // usado pelo filtro: confere se o administrador da sessao ainda esta cadastrado
    public boolean estaAtivo(int id) {
        String sql = "SELECT 1 FROM \"ADM\" WHERE \"ID_ADM\" = ?";
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conferir administrador: " + e.getMessage(), e);
        }
    }

    // busca um administrador pelo e-mail (usado no login); devolve null se nao existir
    public AdmModel buscarPorEmail(String email) {
        String sql = "SELECT \"ID_ADM\" AS id_adm, nome, email, senha FROM \"ADM\" WHERE LOWER(email) = LOWER(?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AdmModel adm = new AdmModel(
                            rs.getInt("id_adm"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            EmAtivo.Ativo
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

    // atualiza um administrador (a senha so muda se vier uma nova no modelo)
    public void atualizarAdm(AdmModel adm) {
        boolean trocaSenha = adm.getSenha() != null;
        String sql = trocaSenha
                ? "UPDATE \"ADM\" SET nome = ?, email = ?, senha = ? WHERE \"ID_ADM\" = ?"
                : "UPDATE \"ADM\" SET nome = ?, email = ? WHERE \"ID_ADM\" = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getEmail());
            if (trocaSenha) {
                stmt.setString(3, adm.getSenha());
                stmt.setInt(4, adm.getIdAdm());
            } else {
                stmt.setInt(3, adm.getIdAdm());
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar administrador: " + e.getMessage(), e);
        }
    }

    // apaga um administrador pelo id
    public void deletarAdm(int id) {
        String sql = "DELETE FROM \"ADM\" WHERE \"ID_ADM\" = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar administrador: " + e.getMessage(), e);
        }
    }
}
