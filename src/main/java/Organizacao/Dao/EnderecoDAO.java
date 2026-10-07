package Organizacao.Dao;

import Organizacao.Model.EnderecoModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de endereco
public class EnderecoDAO {

    // grava os dados de endereco no banco de dados
    public void inserirEndereco(EnderecoModel endereco) {
        // define a consulta sql de insercao para endereco
        String sql = "INSERT INTO endereco (cep, logradouro, numero, complemento, bairro, cidade, estado, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de endereco com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de endereco aos parametros da insercao
            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getPais());

            // executa a gravacao dos dados de endereco
            stmt.executeUpdate();
            System.out.println("Endereço cadastrado com sucesso!");

        // propaga a falha ao gravar endereco para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
        }
    }

    // consulta os registros de endereco e devolve a lista
    public List<EnderecoModel> listarEnderecos() {
        List<EnderecoModel> listaEnderecos = new ArrayList<>();
        // define a consulta sql de leitura para endereco
        String sql = "SELECT * FROM endereco ORDER BY id_endereco";

        // abre a conexao e executa a consulta de endereco com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em endereco
            while (rs.next()) {
                int id_endereco = rs.getInt("id_endereco");
                String cep = rs.getString("cep");
                String logradouro = rs.getString("logradouro");
                String numero = rs.getString("numero");
                String complemento = rs.getString("complemento");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String pais = rs.getString("pais");

                EnderecoModel endereco = new EnderecoModel(
                        cep, logradouro, numero, complemento, bairro, cidade, estado, pais
                );
                endereco.setId_endereco(id_endereco);

                listaEnderecos.add(endereco);
            }

        // propaga a falha ao consultar endereco para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaEnderecos;
    }

    // atualiza os dados de endereco no banco de dados
    public void atualizarEndereco(EnderecoModel endereco) {
        // define a consulta sql de atualizacao para endereco
        String sql = "UPDATE endereco SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, pais = ? WHERE id_endereco = ?";

        // abre a conexao e prepara a atualizacao de endereco com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de endereco aos parametros da atualizacao
            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getEstado());
            stmt.setString(8, endereco.getPais());
            stmt.setInt(9, endereco.getId_endereco());

            // executa a atualizacao dos dados de endereco
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de endereco foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Endereço atualizado com sucesso!");
            } else {
                System.out.println("Endereço não encontrado.");
            }

        // propaga a falha ao atualizar endereco para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage(), e);
        }
    }

    // remove o registro de endereco do banco de dados
    public void deletarEndereco(int id_endereco) {
        // define a consulta sql de exclusao para endereco
        String sql = "DELETE FROM endereco WHERE id_endereco = ?";

        // abre a conexao e prepara a exclusao de endereco com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_endereco);

            // executa a exclusao do registro de endereco
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de endereco foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Endereço deletado com sucesso!");
            } else {
                System.out.println("Endereço não encontrado.");
            }

        // registra a falha ao remover endereco e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage(), e);
        }
    }
}
