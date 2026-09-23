package Organizacao.Dao;

import Organizacao.Conexo.Conexao_Banco;
import Organizacao.Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // create
    public void salvar(UsuarioModel usuario) throws Exception {
        String sql = "INSERT INTO usuario (id_usuario, nome, email, senha, primeiro_registro, tipo_usuario, telefone) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao_Banco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId_usuario());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, usuario.getPrimeiroRegistro());
            stmt.setString(6, usuario.getTipoUsuario());
            stmt.setString(7, usuario.getTelefone());

            stmt.executeUpdate();
        }
    }

    // read
    public List<UsuarioModel> listar() throws Exception {
        List<UsuarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = Conexao_Banco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UsuarioModel u = new UsuarioModel(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getDate("primeiro_registro"),
                        rs.getString("tipo_usuario"),
                        rs.getString("telefone")
                );
                lista.add(u);
            }
        }
        return lista;
    }

    // UPDATE
    public void atualizarUsuario(UsuarioModel usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, tipo_usuario = ?, telefone = ? WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setString(5, usuario.getTelefone());
            stmt.setInt(6, usuario.getId_usuario());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro de usuário encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // delete
    public void deletarUsuario(int id_Usuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_Usuario);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}