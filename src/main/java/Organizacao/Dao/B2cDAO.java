package Organizacao.Dao;

import Organizacao.Conexao.Conexao_Banco;
import Organizacao.Model.B2cModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class B2cDAO {

    // cadastra um usuario b2c com o cpf protegido
    public void inserirB2c(B2cModel b2c) {
        String sql = "INSERT INTO b2c (cpf, id_usuario) VALUES (?, ?)";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, hashCpf(b2c.getCpf()));
            stmt.setInt(2, b2c.getIdUsuario());

            stmt.executeUpdate();
            System.out.println("Cliente B2C cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir B2C: " + e.getMessage(), e);
        }
    }

    // lista clientes e retorna apenas os hashes dos cpfs
    public List<B2cModel> listarB2c() {
        List<B2cModel> lista = new ArrayList<>();
        String sql = "SELECT id_b2c, cpf, id_usuario FROM b2c ORDER BY id_b2c";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String cpfArmazenado = rs.getString("cpf");
                String cpf;
                if (cpfArmazenado != null && cpfArmazenado.startsWith("hmac:v1:")) {
                    cpf = cpfArmazenado;
                } else {
                    cpf = cpfArmazenado == null ? null : hashCpf(cpfArmazenado);
                }
                lista.add(new B2cModel(rs.getInt("id_b2c"), rs.getInt("id_usuario"), cpf));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar B2C: " + e.getMessage(), e);
        }

        return lista;
    }

    // atualiza o cpf protegido pelo identificador do usuario
    public void atualizarB2c(B2cModel b2c) {
        String sql = "UPDATE b2c SET cpf = ? WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, hashCpf(b2c.getCpf()));
            stmt.setInt(2, b2c.getIdUsuario());

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Cliente B2C atualizado com sucesso!"
                    : "Nenhum cliente B2C encontrado para esse usuário.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar B2C: " + e.getMessage(), e);
        }
    }

    // remove o cliente pelo identificador do usuario
    public void deletarB2c(int idUsuario) {
        String sql = "DELETE FROM b2c WHERE id_usuario = ?";

        try (Connection conexao = Conexao_Banco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println(linhasAfetadas > 0 ? "Cliente B2C deletado com sucesso!"
                    : "Nenhum cliente B2C encontrado para esse usuário.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar B2C: " + e.getMessage(), e);
        }
    }

    // cria uma identificacao irreversivel do cpf com chave secreta
    private String hashCpf(String cpf) {
        String chaveConfigurada = System.getenv("CPF_HASH_KEY");
        if (chaveConfigurada == null || chaveConfigurada.trim().isEmpty()) {
            throw new IllegalStateException("A variável CPF_HASH_KEY não está configurada.");
        }
        try {
            byte[] chave = Base64.getDecoder().decode(chaveConfigurada.trim());
            if (chave.length != 32) {
                throw new IllegalStateException("A chave CPF_HASH_KEY deve ter 32 bytes em base64.");
            }
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(chave, "HmacSHA256"));
            byte[] digest = mac.doFinal(cpf.getBytes(StandardCharsets.UTF_8));
            return "hmac:v1:" + Base64.getEncoder().withoutPadding().encodeToString(digest);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalStateException("Não foi possível gerar a identificação protegida do CPF.", e);
        }
    }
}
