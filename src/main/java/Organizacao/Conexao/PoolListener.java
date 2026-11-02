package Organizacao.Conexao;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// fecha o pool de conexões quando a aplicação para (evita conexões "presas" ao reiniciar o Tomcat)
@WebListener
public class PoolListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent evento) {
        Conexao_Banco.fechar();
    }
}
