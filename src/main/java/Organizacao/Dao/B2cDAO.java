package Organizacao.Dao;

import Organizacao.Model.B2cModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de clientes B2C
public class B2cDAO {

    // grava os dados de um cliente B2C no banco de dados
    public void inserirB2c(B2cModel b2c) {
        // define a consulta sql de insercao para cliente B2C
        String sql = "INSERT INTO B2c ( cpf, telefone, id_usuario) VALUES (?, ?, ?)";

        // abre a conexao e prepara a insercao de cliente B2C com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos do cliente B2C aos parametros da insercao
            stmt.setInt(3, b2c.getId_usuario());
            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());

            // executa a gravacao dos dados do cliente B2C
            stmt.executeUpdate();
            System.out.println("Usuário B2c cadastrado com sucesso!");

        // propaga a falha ao gravar cliente B2C para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2c: " + e.getMessage(), e);
        }
    }

    // consulta os registros de clientes B2C e devolve a lista
    public List<B2cModel> listarB2c() {
        List<B2cModel> listaB2c = new ArrayList<>();
        // define a consulta sql de leitura para clientes B2C
        String sql = "SELECT * FROM B2c ORDER BY id_usuario";

        // abre a conexao e executa a consulta de clientes B2C com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em um cliente B2C
            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                int id_usuario = rs.getInt("id_usuario");

                B2cModel novoB2c = new B2cModel(id_usuario, cpf, telefone);
                listaB2c.add(novoB2c);
            }

        // propaga a falha ao consultar clientes B2C para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2c: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaB2c;
    }

    // atualiza os dados de um cliente B2C no banco de dados
    public void atualizarB2c(B2cModel b2c) {
        // define a consulta sql de atualizacao para cliente B2C
        String sql = "UPDATE B2c SET cpf = ?, telefone = ? WHERE id_usuario = ?";

        // abre a conexao e prepara a atualizacao de cliente B2C com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos do cliente B2C aos parametros da atualizacao
            stmt.setString(1, b2c.getCpf());
            stmt.setString(2, b2c.getTelefone());
            stmt.setInt(3, b2c.getId_usuario());

            // executa a atualizacao dos dados do cliente B2C
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de cliente B2C foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro B2c encontrado com o ID informado.");
            }

        // propaga a falha ao atualizar cliente B2C para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2c: " + e.getMessage(), e);
        }
    }

    // remove o registro de um cliente B2C do banco de dados
    public void deletarB2c(int id_usuario) {
        // define a consulta sql de exclusao para cliente B2C
        String sql = "DELETE FROM B2c WHERE id_usuario = ?";

        // abre a conexao e prepara a exclusao de cliente B2C com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_usuario);

            // executa a exclusao do registro de cliente B2C
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de cliente B2C foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2c deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        // registra a falha ao remover cliente B2C e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário B2C: " + e.getMessage(), e);
        }
    }
}
