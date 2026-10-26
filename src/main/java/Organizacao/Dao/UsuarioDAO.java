package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.Enum.TipoUsuario;
import Organizacao.Model.UsuarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para cadastrar um novo usuário
    public void inserirUsuario(UsuarioModel usuario) {
        String sql = "INSERT INTO USUARIO (email, telefone, nome, primeiro_registro, senha, tipo_usuario, ID_endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getNome());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getPrimeiroRegistro()));
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getTipoUsuario().name());
            stmt.setInt(7, usuario.getIdEndereco());

            stmt.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    // Método para listar todos os usuários
    public List<UsuarioModel> listarUsuario() {
        List<UsuarioModel> listaUsuario = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO ORDER BY ID_usuario";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_usuario = rs.getInt("ID_usuario");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String nome = rs.getString("nome");
                LocalDate primeiro_registro = rs.getDate("primeiro_registro").toLocalDate();
                String senha = rs.getString("senha");
                TipoUsuario tipo_usuario = TipoUsuario.valueOf(rs.getString("tipo_usuario"));
                int id_endereco = rs.getInt("ID_endereco");

                UsuarioModel novoUsuario = new UsuarioModel(
                        id_usuario, email, telefone, nome, primeiro_registro, senha, tipo_usuario, id_endereco
                );

                listaUsuario.add(novoUsuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
        }

        return listaUsuario;
    }

    // Método para atualizar informações de um usuário
    public void atualizarUsuario(UsuarioModel usuario) {
        String sql = "UPDATE USUARIO SET email = ?, telefone = ?, nome = ?, senha = ?, tipo_usuario = ?, ID_endereco = ? WHERE ID_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getTelefone());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipoUsuario().name());
            stmt.setInt(6, usuario.getIdEndereco());
            stmt.setInt(7, usuario.getIdUsuario());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    // Método para deletar usuário pelo ID
    public void deletarUsuario(int id) {
        String sql = "DELETE FROM USUARIO WHERE ID_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }
}