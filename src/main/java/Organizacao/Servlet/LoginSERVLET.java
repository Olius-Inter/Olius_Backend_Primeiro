package Organizacao.Servlet;

import Organizacao.Dao.UsuarioDAO;
import Organizacao.Model.UsuarioModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@WebServlet("/login")
// atende o login (POST) e o logout (GET /login?sair=1) de usuario
public class LoginSERVLET extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    // encerra a sessao quando pedido; do contrario mostra a pagina de login
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("sair") != null) {
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp?saiu=1");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Override
    // valida e-mail e senha e abre a sessao do usuario
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String email = limpar(request.getParameter("email"));
        String senha = request.getParameter("senha");

        if (email.isEmpty() || senha == null || senha.isEmpty()) {
            voltarComErro(request, response, "Informe e-mail e senha.", email);
            return;
        }

        try {
            UsuarioModel usuario = usuarioDAO.buscarPorEmail(email);

            // mesma mensagem para e-mail inexistente e senha errada (nao revela qual falhou)
            if (usuario == null || !senhaConfere(senha, usuario.getSenha())) {
                voltarComErro(request, response, "E-mail ou senha incorretos.", email);
                return;
            }

            // troca o id de sessao ao autenticar (evita fixacao de sessao)
            HttpSession antiga = request.getSession(false);
            if (antiga != null) {
                antiga.invalidate();
            }
            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("usuarioId", usuario.getId_usuario());
            sessao.setAttribute("usuarioNome", usuario.getNome());
            sessao.setAttribute("usuarioTipo", usuario.getTipoUsuario());
            sessao.setMaxInactiveInterval(30 * 60);

            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            voltarComErro(request, response,
                    "Não foi possível entrar agora. Tente novamente em instantes.", email);
        }
    }

    // compara as senhas em tempo constante
    private boolean senhaConfere(String informada, String armazenada) {
        if (armazenada == null) {
            return false;
        }
        return MessageDigest.isEqual(
                informada.getBytes(StandardCharsets.UTF_8),
                armazenada.getBytes(StandardCharsets.UTF_8));
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    // reabre a pagina de login com a mensagem de erro e o e-mail digitado
    private void voltarComErro(HttpServletRequest request, HttpServletResponse response,
                               String mensagem, String email) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        request.setAttribute("erro", mensagem);
        request.setAttribute("emailDigitado", email);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
