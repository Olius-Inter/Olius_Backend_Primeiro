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

import Organizacao.Dao.AdmDAO;

import java.io.IOException;

// Barra quem não é administrador logado: protege as telas do painel (/adm/*),
// as telas antigas do CRUD na raiz do site e as APIs do CRUD (os servlets de cadastro).
// Todo o resto (site público, login, css, js, imagens) passa direto.
@WebFilter("/*")
public class AdmFiltro implements Filter {

    private final AdmDAO admDAO = new AdmDAO();

    // APIs do CRUD (servlets da Bruna) que só o administrador logado pode usar
    private static final String[] APIS = {
            "/empresas", "/b2c-usuarios", "/usuarios", "/motoristas", "/pev", "/coleta", "/certificado",
            "/solicitacoes-coleta", "/enderecos", "/carteira", "/entregas-pev", "/participacoes-evento",
            "/pontuacao", "/pontos-movimentacao"
    };

    // telas antigas do CRUD que ficaram na raiz do site (as novas ficam em /adm)
    private static final String[] PAGINAS_ANTIGAS = {
            "/Certificados", "/Coletas", "/Empresas", "/Motoristas", "/PEV", "/Solicitações", "/Usuarios"
    };

    private static boolean comeca(String caminho, String[] prefixos) {
        for (String p : prefixos) {
            if (caminho.equals(p) || caminho.startsWith(p + "/")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // caminho sem o contexto, já decodificado (/adm/Empresas/empresa.jsp, /empresas...)
        String caminho = request.getServletPath() + (request.getPathInfo() != null ? request.getPathInfo() : "");
        // caminhos antigos do login de usuario voltam ao site publico
        if (caminho.equals("/login") || caminho.equals("/login.jsp")) {
            response.sendRedirect(request.getContextPath() + "/index/index.html");
            return;
        }
        boolean pagina = caminho.equals("/adm") || caminho.startsWith("/adm/")
                || comeca(caminho, PAGINAS_ANTIGAS);
        boolean api = comeca(caminho, APIS);

        // rota pública: segue sem pedir login
        if (!pagina && !api) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession sessao = request.getSession(false);
        boolean logado = sessao != null && sessao.getAttribute("admId") != null;

        // confere no banco se o administrador continua cadastrado e ativo
        if (logado && !admDAO.estaAtivo((Integer) sessao.getAttribute("admId"))) {
            sessao.invalidate();
            logado = false;
        }

        // proteção contra CSRF: pedido que altera dados e vem de outro site é recusado
        // (o navegador envia o Origin; o painel envia o mesmo endereço do servidor)
        boolean altera = !request.getMethod().equals("GET") && !request.getMethod().equals("HEAD");
        String origem = request.getHeader("Origin");
        if (logado && api && altera && origem != null && !origemDoMesmoSite(origem, request)) {
            responderJson(response, HttpServletResponse.SC_FORBIDDEN, "Pedido não autorizado.");
            return;
        }

        if (logado) {
            // não deixa o navegador guardar o painel (depois de sair, o "voltar" não mostra nada)
            response.setHeader("Cache-Control", "no-store");
            chain.doFilter(req, resp);
            return;
        }

        if (pagina) {
            // tela do painel sem login: volta para a tela inicial do site (login)
            response.sendRedirect(request.getContextPath() + "/login-adm");
        } else {
            // API do CRUD: responde 401 em JSON
            responderJson(response, HttpServletResponse.SC_UNAUTHORIZED, "Acesso restrito aos administradores. Faça login.");
        }
    }

    // o Origin tem de apontar para o mesmo host:porta que recebeu o pedido
    private boolean origemDoMesmoSite(String origem, HttpServletRequest request) {
        String host = request.getHeader("Host");
        return host != null && (origem.equals("http://" + host) || origem.equals("https://" + host));
    }

    private void responderJson(HttpServletResponse response, int status, String erro) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"erro\":\"" + erro + "\"}");
    }
}
