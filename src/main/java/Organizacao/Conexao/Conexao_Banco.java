package Organizacao.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe responsável por abrir a conexão com o banco de dados

public class Conexao_Banco {

    // Credenciais do banco, obtidas do servidor
    static String username = System.getenv("DB_USERNAME");
    static String password = System.getenv("DB_PASSWORD");
    static String URL = System.getenv("DB_URL");

    // Abre e retorna uma nova conexão com o banco de dados PostgreSQL.

    public static Connection conectar() {

        try {
            // Registra o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Efetivamente abre a conexão usando URL/usuário/senha
            return DriverManager.getConnection(
                    URL,
                    username,
                    password
            );

        } catch (ClassNotFoundException erro) {
            // Ocorre quando o driver do PostgreSQL não está disponível

            System.out.println("Driver não localizado");
            erro.printStackTrace();

        } catch (SQLException erro) {
            // Ocorre quando a URL/usuário/senha estão incorretos, o banco
            // está fora do ar, ou há qualquer outra falha de conexão/SQL
            System.out.println("Erro ao conectar com o banco");
            erro.printStackTrace();
        }

        // Nenhuma conexão pôde ser estabelecida
        return null;
    }
}
