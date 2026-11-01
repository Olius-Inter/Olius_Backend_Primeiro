package Organizacao.Servlet;

import Organizacao.Dao.UsuarioDAO;
import Organizacao.Model.UsuarioModel;
import Organizacao.Utilitarios.SenhaUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
// controla o acesso e o encerramento da sessao do usuario
public class LoginSERVLET extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    // encerra a sessao ou exibe a pagina de acesso
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
    // valida as credenciais e inicia a sessao do usuario
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

            // usa uma mensagem unica para falhas de autenticacao
            if (usuario == null || !senhaConfere(senha, usuario.getSenha())) {
                voltarComErro(request, response, "E-mail ou senha incorretos.", email);
                return;
            }

            // cria um novo identificador de sessao apos o acesso
            HttpSession antiga = request.getSession(false);
            if (antiga != null) {
                antiga.invalidate();
            }
            HttpSession sessao = request.getSession(true);
            sessao.setAttribute("usuarioId", usuario.getIdUsuario());
            sessao.setAttribute("usuarioNome", usuario.getNome());
            sessao.setAttribute("usuarioTipo", usuario.getTipoUsuario());
            sessao.setMaxInactiveInterval(30 * 60);

            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            voltarComErro(request, response,
                    "Não foi possível entrar agora. Tente novamente em instantes.", email);
        }
    }

    // usa o mesmo hash do cadastro de usuarios e administradores
    private boolean senhaConfere(String informada, String armazenada) {
        return SenhaUtil.conferir(informada, armazenada);
    }

    private String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    // devolve a pagina com o erro e o email informado
    private void voltarComErro(HttpServletRequest request, HttpServletResponse response,
                               String mensagem, String email) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        request.setAttribute("erro", mensagem);
        request.setAttribute("emailDigitado", email);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
