package Organizacao.Dao;

import Organizacao.Model.MotoristaModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


// acessa e administra os registros de motorista
public class MotoristaDAO {

    // grava os dados de motorista no banco de dados
    public void inserirMotorista(MotoristaModel motorista) {
        // define a consulta sql de insercao para motorista
        String sql = "INSERT INTO motorista (nome, cpf, telefone, cnh, empresa, status) VALUES (?, ?, ?, ?, ?, ?)";

        // abre a conexao e prepara a insercao de motorista com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de motorista aos parametros da insercao
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getCnh());
            stmt.setString(5, motorista.getEmpresa());
            stmt.setString(6, motorista.getStatus());

            // executa a gravacao dos dados de motorista
            stmt.executeUpdate();
            System.out.println("Motorista cadastrado com sucesso!");

        // propaga a falha ao gravar motorista para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir motorista: " + e.getMessage(), e);
        }
    }

    // consulta os registros de motorista e devolve a lista
    public List<MotoristaModel> listarMotoristas() {
        List<MotoristaModel> listaMotoristas = new ArrayList<>();
        // define a consulta sql de leitura para motorista
        String sql = "SELECT * FROM motorista ORDER BY id_motorista";

        // abre a conexao e executa a consulta de motorista com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // converte os campos de cada linha consultada em motorista
            while (rs.next()) {
                int id_motorista = rs.getInt("id_motorista");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String cnh = rs.getString("cnh");
                String empresa = rs.getString("empresa");
                String status = rs.getString("status");

                MotoristaModel motorista = new MotoristaModel(nome, cpf, telefone, cnh, empresa, status);
                motorista.setId_motorista(id_motorista);

                listaMotoristas.add(motorista);
            }

        // propaga a falha ao consultar motorista para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }

        // devolve os registros encontrados pela consulta
        return listaMotoristas;
    }

    // atualiza os dados de motorista no banco de dados
    public void atualizarMotorista(MotoristaModel motorista) {
        // define a consulta sql de atualizacao para motorista
        String sql = "UPDATE motorista SET nome = ?, cpf = ?, telefone = ?, cnh = ?, empresa = ?, status = ? WHERE id_motorista = ?";

        // abre a conexao e prepara a atualizacao de motorista com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa os campos de motorista aos parametros da atualizacao
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getCnh());
            stmt.setString(5, motorista.getEmpresa());
            stmt.setString(6, motorista.getStatus());
            stmt.setInt(7, motorista.getId_motorista());

            // executa a atualizacao dos dados de motorista
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de motorista foi atualizado
            if (linhasAfetadas > 0) {
                System.out.println("Motorista atualizado com sucesso!");
            } else {
                System.out.println("Motorista não encontrado.");
            }

        // propaga a falha ao atualizar motorista para informar o erro ao chamador
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    // remove o registro de motorista do banco de dados
    public void deletarMotorista(int id_motorista) {
        // define a consulta sql de exclusao para motorista
        String sql = "DELETE FROM motorista WHERE id_motorista = ?";

        // abre a conexao e prepara a exclusao de motorista com fechamento automatico
        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // associa o identificador do registro aos parametros da exclusao
            stmt.setInt(1, id_motorista);

            // executa a exclusao do registro de motorista
            int linhasAfetadas = stmt.executeUpdate();

            // verifica se algum registro de motorista foi removido
            if (linhasAfetadas > 0) {
                System.out.println("Motorista deletado com sucesso!");
            } else {
                System.out.println("Motorista não encontrado.");
            }

        // registra a falha ao remover motorista e informa o ocorrido

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar motorista: " + e.getMessage(), e);
        }
    }
}
