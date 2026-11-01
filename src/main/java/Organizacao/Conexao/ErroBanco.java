package Organizacao.Conexao;

import java.sql.SQLException;
import jakarta.servlet.http.HttpServletResponse;

// identifica falhas de integridade para a resposta das requisicoes
public class ErroBanco {

    public static int status(Exception erro) {
        Throwable causa = erro;
        while (causa != null) {
            if (causa instanceof java.util.NoSuchElementException) {
                return HttpServletResponse.SC_NOT_FOUND;
            }
            if (causa instanceof SQLException) {
                String estado = ((SQLException) causa).getSQLState();
                if ("23503".equals(estado) || "23505".equals(estado)) {
                    return HttpServletResponse.SC_CONFLICT;
                }
                if (estado != null && (estado.startsWith("22") || estado.startsWith("23"))) {
                    return HttpServletResponse.SC_BAD_REQUEST;
                }
            }
            causa = causa.getCause();
        }
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}
