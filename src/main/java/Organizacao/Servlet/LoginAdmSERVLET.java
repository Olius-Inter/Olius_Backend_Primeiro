package Organizacao.Servlet;

import Organizacao.Dao.AdmDAO;
import Organizacao.Model.AdmModel;
import Organizacao.Model.Enum.EmAtivo;
import Organizacao.Utilitarios.SenhaUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// Login do painel administrativo. É aberto pelo link discreto "Acesso adm" no rodapé do site.
// Só entra quem tem registro ATIVO na tabela adm e acerta NOME, E-MAIL e SENHA do mesmo administrador.
//   GET  /login-adm          -> mostra a tela de login (ou vai direto ao painel se já estiver logado)
//   GET  /login-adm?sair=1   -> encerra a sessão do administrador
//   POST /login-adm          -> valida nome, e-mail e senha no banco
@WebServlet("/login-adm")
public class LoginAdmSERVLET extends HttpServlet {
    private static final String HASH_FALSO = SenhaUtil.gerarHash("senha-falsa");


    private AdmDAO admDAO;

    @Override
    public void init() throws ServletException {
        admDAO = new AdmDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // sair: encerra a sessão e volta para o site
        if (request.getParameter("sair") != null) {
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            response.sendRedirect("https://olius-inter.github.io/Olius_Backend_Primeiro/");
            return;
        }

        // já logado: não precisa digitar de novo
        HttpSession sessao = request.getSession(false);
        if (sessao != null && sessao.getAttribute("admId") != null) {
            response.sendRedirect(request.getContextPath() + "/adm/index.jsp");
            return;
        }

        request.getRequestDispatcher("/login_adm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome") == null ? "" : request.getParameter("nome").trim();
        String email = request.getParameter("email") == null ? "" : request.getParameter("email").trim();
        String senha = request.getParameter("senha");

        if (nome.isEmpty() || email.isEmpty() || senha == null || senha.isEmpty()) {
            voltarComErro(request, response, "Preencha nome, e-mail e senha para entrar.", nome, email);
            return;
        }

        try {
            AdmModel adm = admDAO.buscarPorEmail(email);

            // os três dados precisam ser do MESMO administrador; a mensagem é a mesma para qualquer
            // erro (não revela se foi o nome, o e-mail ou a senha)
            // com e-mail inexistente também confere um hash, para a resposta demorar o mesmo
            boolean senhaCerta = SenhaUtil.conferir(senha, adm == null ? HASH_FALSO : adm.getSenha());
            boolean nomeCerto = adm != null && adm.getNome().trim().equalsIgnoreCase(nome);
            if (adm == null || !senhaCerta || !nomeCerto) {
                voltarComErro(request, response, "Credenciais inválidas.", nome, email);
                return;
            }

            // administrador cadastrado, mas desativado
            if (adm.getEmAtivo() != EmAtivo.Ativo) {
                voltarComErro(request, response,
                        "O seu acesso está desativado. Fale com a coordenação do projeto.", nome, email);
                return;
            }

            // troca o id da sessão ao autenticar (evita fixação de sessão)
            HttpSession antiga = request.getSession(false);
            if (antiga != null) {
                antiga.invalidate();
            }
            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("admId", adm.getIdAdm());
            sessao.setAttribute("admNome", adm.getNome());
            sessao.setMaxInactiveInterval(30 * 60); // 30 minutos sem uso

            response.sendRedirect(request.getContextPath() + "/adm/index.jsp");

        } catch (Exception e) {
            // falha de banco, por exemplo: não mostra detalhes técnicos para quem tenta entrar
            java.util.logging.Logger.getLogger("Olius").log(java.util.logging.Level.SEVERE, "Falha ao validar login do administrador", e);
            voltarComErro(request, response, HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                    "Não foi possível validar o seu acesso agora. Tente novamente em instantes.", nome, email);
        }
    }

    // reabre a tela de login com a mensagem de erro, o nome e o e-mail digitados (a senha nunca volta)
    private void voltarComErro(HttpServletRequest request, HttpServletResponse response,
                               String mensagem, String nome, String email) throws ServletException, IOException {
        voltarComErro(request, response, HttpServletResponse.SC_UNAUTHORIZED, mensagem, nome, email);
    }

    private void voltarComErro(HttpServletRequest request, HttpServletResponse response, int status,
                               String mensagem, String nome, String email) throws ServletException, IOException {
        response.setStatus(status);
        request.setAttribute("erro", mensagem);
        request.setAttribute("nomeDigitado", nome);
        request.setAttribute("emailDigitado", email);
        request.getRequestDispatcher("/login_adm.jsp").forward(request, response);
    }
}
