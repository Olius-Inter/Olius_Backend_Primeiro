package Organizacao.Servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Barra quem não é administrador logado: protege as telas do painel (/adm/*)
// e também as APIs do CRUD (os servlets de cadastro).
@WebFilter(urlPatterns = {
        "/adm/*",
        "/adms", "/atualizacoes", "/b2b", "/b2c", "/carteira", "/certificados", "/coletas",
        "/enderecos", "/entregas-pev", "/motoristas", "/pevs", "/pontos-movimentacao",
        "/solicitacoes-coleta", "/usuarios"
})
public class AdmFiltro implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession sessao = request.getSession(false);
        boolean logado = sessao != null && sessao.getAttribute("admId") != null;

        if (logado) {
            // não deixa o navegador guardar o painel (depois de sair, o "voltar" não mostra nada)
            response.setHeader("Cache-Control", "no-store");
            chain.doFilter(req, resp);
            return;
        }

        String caminho = request.getRequestURI().substring(request.getContextPath().length());
        if (caminho.startsWith("/adm/")) {
            // tela do painel: manda para o login do administrador
            response.sendRedirect(request.getContextPath() + "/login-adm");
        } else {
            // API do CRUD: responde 401 em JSON
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"erro\":\"Acesso restrito aos administradores. Faça login.\"}");
        }
    }
}
