package Organizacao.Utilitarios;

import Organizacao.Conexao.Conexao_Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// executar uma vez, com backup, para proteger os dados antigos sem apagar registros
public class AtualizarProtecaoDados {

    public static void main(String[] args) throws Exception {
        // confere a chave antes de fazer qualquer alteracao no banco
        CpfUtil.criptografar("00000000000");
        try (Connection conexao = Conexao_Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                try (Statement stmt = conexao.createStatement()) {
                    stmt.execute("ALTER TABLE public.\"B2C\" ALTER COLUMN \"CPF\" TYPE VARCHAR(255)");
                    stmt.execute("ALTER TABLE public.\"B2C\" DROP CONSTRAINT IF EXISTS \"B2C_CPF_check\"");
                    stmt.execute("ALTER TABLE public.\"B2C\" ADD CONSTRAINT \"B2C_CPF_check\" CHECK (length(\"CPF\") = 11 OR \"CPF\" LIKE 'aesgcm$%')");
                }
                int cpfs = atualizarCpfs(conexao);
                int senhas = atualizarSenhas(conexao, "USUARIO", "ID_usuario");
                senhas += atualizarSenhas(conexao, "ADM", "ID_ADM");
                conexao.commit();
                System.out.println("Protecao concluida: " + cpfs + " CPFs e " + senhas + " senhas convertidos.");
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        } finally {
            Conexao_Banco.fechar();
        }
    }

    private static int atualizarCpfs(Connection conexao) throws SQLException {
        int quantidade = 0;
        String consulta = "SELECT \"ID_B2C\", \"CPF\" FROM public.\"B2C\" FOR UPDATE";
        String atualizacao = "UPDATE public.\"B2C\" SET \"CPF\" = ? WHERE \"ID_B2C\" = ?";
        try (PreparedStatement buscar = conexao.prepareStatement(consulta);
             PreparedStatement salvar = conexao.prepareStatement(atualizacao);
             ResultSet rs = buscar.executeQuery()) {
            while (rs.next()) {
                String cpf = rs.getString("CPF");
                if (cpf.startsWith("aesgcm$")) {
                    // interrompe a conversao se a chave nao abrir os dados ja protegidos
                    CpfUtil.descriptografar(cpf);
                } else {
                    salvar.setString(1, CpfUtil.criptografar(cpf));
                    salvar.setInt(2, rs.getInt("ID_B2C"));
                    salvar.executeUpdate();
                    quantidade++;
                }
            }
        }
        return quantidade;
    }

    private static int atualizarSenhas(Connection conexao, String tabela, String id) throws SQLException {
        int quantidade = 0;
        String consulta = "SELECT \"" + id + "\", senha FROM public.\"" + tabela + "\" FOR UPDATE";
        String atualizacao = "UPDATE public.\"" + tabela + "\" SET senha = ? WHERE \"" + id + "\" = ?";
        try (PreparedStatement buscar = conexao.prepareStatement(consulta);
             PreparedStatement salvar = conexao.prepareStatement(atualizacao);
             ResultSet rs = buscar.executeQuery()) {
            while (rs.next()) {
                String senha = rs.getString("senha");
                if (senha != null && !senha.startsWith("pbkdf2$")) {
                    salvar.setString(1, SenhaUtil.gerarHash(senha));
                    salvar.setInt(2, rs.getInt(id));
                    salvar.executeUpdate();
                    quantidade++;
                }
            }
        }
        return quantidade;
    }
}
