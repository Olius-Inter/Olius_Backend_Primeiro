package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.MotoristaModel;
import Organizacao.Model.Enum.StatusMotorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotoristaDAO {

    // Método para inserir um novo motorista
    public void inserirMotorista(MotoristaModel motorista) {
        String sql = "INSERT INTO MOTORISTA (status, empresa_resp, telefone, nome, licenca) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getStatus().name());
            stmt.setString(2, motorista.getEmpresaResp());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getNome());
            stmt.setString(5, motorista.getLicenca());

            stmt.executeUpdate();
            System.out.println("Motorista cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir motorista: " + e.getMessage(), e);
        }
    }

    // Método para listar motoristas cadastrados
    public List<MotoristaModel> listarMotorista() {
        List<MotoristaModel> listaMotorista = new ArrayList<>();
        String sql = "SELECT * FROM MOTORISTA ORDER BY ID_motorista";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_motorista = rs.getInt("ID_motorista");
                StatusMotorista status = StatusMotorista.valueOf(rs.getString("status"));
                String empresa_resp = rs.getString("empresa_resp");
                String telefone = rs.getString("telefone");
                String nome = rs.getString("nome");
                String licenca = rs.getString("licenca");

                MotoristaModel novoMotorista = new MotoristaModel(
                        id_motorista, status, empresa_resp, telefone, nome, licenca
                );

                listaMotorista.add(novoMotorista);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar motoristas: " + e.getMessage(), e);
        }

        return listaMotorista;
    }

    // Método para atualizar informações do motorista
    public void atualizarMotorista(MotoristaModel motorista) {
        String sql = "UPDATE MOTORISTA SET status = ?, empresa_resp = ?, telefone = ?, nome = ?, licenca = ? WHERE ID_motorista = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, motorista.getStatus().name());
            stmt.setString(2, motorista.getEmpresaResp());
            stmt.setString(3, motorista.getTelefone());
            stmt.setString(4, motorista.getNome());
            stmt.setString(5, motorista.getLicenca());
            stmt.setInt(6, motorista.getIdMotorista());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Motorista atualizado com sucesso!");
            } else {
                System.out.println("Nenhum motorista encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar motorista: " + e.getMessage(), e);
        }
    }

    // Método para deletar motorista por ID
    public void deletarMotorista(int id) {
        String sql = "DELETE FROM MOTORISTA WHERE ID_motorista = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Motorista deletado com sucesso!");
            } else {
                System.out.println("Nenhum motorista encontrado com esse ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar motorista: " + e.getMessage(), e);
        }
    }
}