package Organizacao.Conexao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

// entrega conexoes com o banco de dados usando um pool (hikaricp):
// em vez de abrir uma conexao nova (com ssl) a cada consulta, reaproveita conexoes ja abertas
public class Conexao_Banco {

    // mantem as credenciais carregadas do ambiente (variavel de ambiente ou vm option -DDB_URL=...)
    static String username = ler("DB_USERNAME");
    static String password = ler("DB_PASSWORD");
    static String URL = ler("DB_URL");

    // o pool e criado na primeira vez que alguem pede uma conexao
    private static HikariDataSource pool;

    // le a variavel de ambiente e, se nao existir, a vm option do intellij
    private static String ler(String nome) {
        String valor = System.getenv(nome);
        return valor != null ? valor : System.getProperty(nome);
    }

    // cria o pool uma unica vez
    private static synchronized HikariDataSource obterPool() {
        if (pool != null) {
            return pool;
        }

        if (URL == null || username == null || password == null) {
            throw new IllegalStateException("Configure DB_URL, DB_USERNAME e DB_PASSWORD (variaveis de ambiente ou VM options do Tomcat).");
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(URL);
        config.setUsername(username);
        config.setPassword(password);

        // o aiven limita o total de conexoes: poucas conexoes, bem reaproveitadas
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10000);
        config.setMaxLifetime(25 * 60 * 1000);
        config.setPoolName("OliusPool");

        pool = new HikariDataSource(config);
        return pool;
    }

    // devolve uma conexao do pool: ao fechar (close) ela volta para o pool em vez de ser desligada
    public static Connection conectar() {
        try {
            return obterPool().getConnection();

            // trata falhas ao abrir a conexao ou executar operacoes sql
        } catch (SQLException erro) {
            erro.printStackTrace();

            throw new IllegalStateException(
                    "Não foi possível conectar ao banco de dados: " + erro.getMessage(),
                    erro
            );

            // trata falha ao criar o pool (driver ausente, senha errada, banco fora do ar)
        } catch (RuntimeException erro) {
            if (erro instanceof IllegalStateException) {
                throw erro;
            }
            throw new IllegalStateException(
                    "Não foi possível conectar ao banco de dados: " + erro.getMessage(),
                    erro
            );
        }
    }

    // encerra o pool quando o tomcat para ou recarrega a aplicacao
    public static synchronized void fechar() {
        if (pool != null) {
            pool.close();
            pool = null;
        }
    }
}
