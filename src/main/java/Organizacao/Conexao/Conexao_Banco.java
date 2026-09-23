package Organizacao.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao_Banco {

    static String username = System.getenv("DB_USERNAME");
    static String password = System.getenv("DB_PASSWORD");
    static String URL = System.getenv("DB_URL");

    public static Connection conectar() {

        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                    URL,
                    username,
                    password
            );

        } catch (ClassNotFoundException erro) {

            System.out.println("Driver não localizado");
            erro.printStackTrace();

        } catch (SQLException erro) {

            System.out.println("Erro ao conectar com o banco");
            erro.printStackTrace();
        }

        return null;
    }
}