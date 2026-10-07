package Organizacao.Dao;

import Organizacao.Model.B2cModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de usuario b dois c
public class B2cDAO {

    // grava os dados de usuario b dois c no banco de dados
    public void inserirB2c(B2cModel b2c) {
        // define a consulta sql de insercao para usuario b dois c
        String sql = "INSERT INTO B2c ( cpf, telefone, id_usuario) VALUES (?, ?, ?)";

        // abre a conexao e prepara a insercao de usuario b dois c com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de usuario b dois c aos parametros da insercao
            stmt.setInt(3, b2c.getId_usuario());
            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());

            // executa a gravacao dos dados de usuario b dois c
            stmt.executeUpdate();
            System.out.println("Usuário B2c cadastrado com sucesso!");

        // propaga a falha ao gravar usuario b dois c para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2c: " + e.getMessage(), e);
        }
    }

    // consulta os registros de usuario b dois c e devolve a lista
    public List<B2cModel> listarB2c() {
        List<B2cModel> listaB2c = new ArrayList<>();
        // define a consulta sql de leitura para usuario b dois c
        String sql = "SELECT * FROM B2c ORDER BY id_usuario";

        // abre a conexao e executa a consulta de usuario b dois c com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em usuario b dois c
            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                int id_usuario = rs.getInt("id_usuario");

                B2cModel novoB2c = new B2cModel(id_usuario, cpf, telefone);
                listaB2c.add(novoB2c);
            }

        // propaga a falha ao consultar usuario b dois c para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2c: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaB2c;
    }

    // atualiza os dados de usuario b dois c no banco de dados
    public void atualizarB2c(B2cModel b2c) {
        // define a consulta sql de atualizacao para usuario b dois c
        String sql = "UPDATE B2c SET cpf = ?, telefone = ? WHERE id_usuario = ?";

        // abre a conexao e prepara a atualizacao de usuario b dois c com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de usuario b dois c aos parametros da atualizacao
            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());
            stmt.setInt(3, b2c.getId_usuario());

            // executa a atualizacao dos dados de usuario b dois c
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de usuario b dois c foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro B2c encontrado com o ID informado.");
            }

        // propaga a falha ao atualizar usuario b dois c para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2c: " + e.getMessage(), e);
        }
    }

    // remove o registro de usuario b dois c do banco de dados
    public void deletarB2c(int id_usuario) {
        // define a consulta sql de exclusao para usuario b dois c
        String sql = "DELETE FROM B2c WHERE id_usuario = ?";

        // abre a conexao e prepara a exclusao de usuario b dois c com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_usuario);

            // executa a exclusao do registro de usuario b dois c
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de usuario b dois c foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        // registra a falha ao remover usuario b dois c e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário B2C: " + e.getMessage(), e);
        }
    }
}
