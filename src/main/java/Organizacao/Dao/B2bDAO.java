package Organizacao.Dao;

import Organizacao.Model.B2bModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de empresa b dois b
public class B2bDAO {

    // grava os dados de empresa b dois b no banco de dados
    public void inserirB2b(B2bModel b2b) {
        // define a consulta sql de insercao para empresa b dois b
        String sql = "INSERT INTO B2b (cnpj, razao_social, nome_fantasia, telefone, id_endereco) VALUES (?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de empresa b dois b com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de empresa b dois b aos parametros da insercao
            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getRazao_social());
            stmt.setString(3, b2b.getNome_fantasia());
            stmt.setString(4, b2b.getTelefone());
            stmt.setInt(5, b2b.getId_endereco());

            // executa a gravacao dos dados de empresa b dois b
            stmt.executeUpdate();
            System.out.println("Usuário B2b cadastrado com sucesso!");

        // propaga a falha ao gravar empresa b dois b para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2b: " + e.getMessage(), e);
        }
    }


    // consulta os registros de empresa b dois b e devolve a lista
    public List<B2bModel> listarB2b() {
        List<B2bModel> listaB2b = new ArrayList<>();
        // define a consulta sql de leitura para empresa b dois b
        String sql = "SELECT * FROM B2b ORDER BY id_usuario";

        // abre a conexao e executa a consulta de empresa b dois b com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em empresa b dois b
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String cnpj = rs.getString("cnpj");
                String razaoSocial = rs.getString("razao_social");
                String nome_Fantasia = rs.getString("nome_fantasia");
                String telefone = rs.getString("telefone");
                int id_endereco = rs.getInt("id_endereco");
                B2bModel novoB2b = new B2bModel(id_usuario, cnpj, razaoSocial, nome_Fantasia, telefone, id_endereco);
                listaB2b.add(novoB2b);
            }

        // propaga a falha ao consultar empresa b dois b para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2b: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaB2b;
    }
    // atualiza os dados de empresa b dois b no banco de dados
    public void atualizarB2b(B2bModel b2b) {
        // define a consulta sql de atualizacao para empresa b dois b
        String sql = "UPDATE B2b SET cnpj = ?, razao_social = ?, nome_fantasia = ?, telefone = ?, id_endereco = ? WHERE id_usuario = ?";

        // abre a conexao e prepara a atualizacao de empresa b dois b com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de empresa b dois b aos parametros da atualizacao
            stmt.setString(1, b2b.getCnpj());
            stmt.setString(2, b2b.getRazao_social());
            stmt.setString(3, b2b.getNome_fantasia());
            stmt.setString(4, b2b.getTelefone());
            stmt.setInt(5, b2b.getId_endereco());
            stmt.setInt(6, b2b.getId_usuario());

            // executa a atualizacao dos dados de empresa b dois b
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de empresa b dois b foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Empresa B2b atualizada com sucesso!");
            } else {
                System.out.println("Nenhum registro B2b encontrado com o ID informado.");
            }

        // propaga a falha ao atualizar empresa b dois b para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2b: " + e.getMessage(), e);
        }
    }
    // remove o registro de empresa b dois b do banco de dados
    public void deletarB2b(int id) {
        // define a consulta sql de exclusao para empresa b dois b
        String sql = "DELETE FROM B2b WHERE id_usuario = ?";

        // abre a conexao e prepara a exclusao de empresa b dois b com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id);

            // executa a exclusao do registro de empresa b dois b
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de empresa b dois b foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Usuário B2b deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID para deletar.");
            }

        // propaga a falha ao remover empresa b dois b para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2b: " + e.getMessage(), e);
        }
    }
}