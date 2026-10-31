package Organizacao.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



// abre conexoes com o banco de dados
public class Conexao_Banco {
    // mantem as credenciais carregadas do ambiente

    static String username = System.getenv("DB_USERNAME");
    static String password = System.getenv("DB_PASSWORD");
    static String URL = System.getenv("DB_URL");


    // carrega o driver e abre uma conexao com o banco de dados
    public static Connection conectar() {

        // carrega o driver e tenta abrir a conexao com as credenciais do ambiente
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    URL,
                    username,
                    password
            );

        // trata a ausencia do driver necessario para acessar o banco
        } catch (ClassNotFoundException erro) {

            System.out.println("Driver não localizado");
            erro.printStackTrace();

        // trata falhas ao abrir a conexao ou executar operacoes sql
        } catch (SQLException erro) {
            System.out.println("Erro ao conectar com o banco");
            erro.printStackTrace();
        }

        // informa que nenhuma conexao pode ser devolvida
        return null;
    }
}