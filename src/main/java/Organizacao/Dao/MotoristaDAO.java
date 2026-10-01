package Organizacao.Dao;

import Organizacao.Model.MotoristaModel;
import Organizacao.Conexao.Conexao_Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Classe de acesso a dados (DAO) da entidade Motorista.

public class MotoristaDAO {

    // Insere um novo registro de Motorista no banco de dados.

    public void inserirMotorista(MotoristaModel motorista) {
        String sql = "INSERT INTO motorista (nome, cpf, telefone, cnh, empresa, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getCnh());
            stmt.setString(5, motorista.getEmpresa());
            stmt.setString(6, motorista.getStatus());

            stmt.executeUpdate();
            System.out.println("Motorista cadastrado com sucesso!");

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir motorista: " + e.getMessage(), e);
        }
    }

    // Consulta e retorna os registros de Motorista cadastrados no banco.

    public List<MotoristaModel> listarMotoristas() {
        List<MotoristaModel> listaMotoristas = new ArrayList<>();
        String sql = "SELECT * FROM motorista ORDER BY id_motorista";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }

        return listaMotoristas;
    }

    // Atualiza os dados de um registro de Motorista já existente.

    public void atualizarMotorista(MotoristaModel motorista) {
        String sql = "UPDATE motorista SET nome = ?, cpf = ?, telefone = ?, cnh = ?, empresa = ?, status = ? WHERE id_motorista = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getCnh());
            stmt.setString(5, motorista.getEmpresa());
            stmt.setString(6, motorista.getStatus());
            stmt.setInt(7, motorista.getId_motorista());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Motorista atualizado com sucesso!");
            } else {
                System.out.println("Motorista não encontrado.");
            }

        // Falha de acesso ao banco de dados
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    // Remove definitivamente um registro de Motorista do banco de dados.

    public void deletarMotorista(int id_motorista) {
        String sql = "DELETE FROM motorista WHERE id_motorista = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id_motorista);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Motorista deletado com sucesso!");
            } else {
                System.out.println("Motorista não encontrado.");
            }

        // Captura qualquer outro erro inesperado durante a operação
        } catch (Exception e) {
            System.out.println("Erro ao deletar motorista: " + e.getMessage());
        }
    }
}
