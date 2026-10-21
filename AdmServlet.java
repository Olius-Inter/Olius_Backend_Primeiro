package Organizacao.Servlet;

import Organizacao.Dao.AdmDAO;
import Organizacao.Model.AdmModel;
import Organizacao.Model.Enum.EmAtivo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/administradores")
// gerencia requisicoes http para administradores do sistema
public class AdmServlet extends HttpServlet {

    private AdmDAO admDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public void init() throws ServletException {
        admDAO = new AdmDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<AdmModel> adms = admDAO.listarAdm();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < adms.size(); i++) {
                AdmModel adm = adms.get(i);
                json.append("{")
                        .append("\"id_adm\":").append(adm.getIdAdm()).append(",")
                        .append("\"nome\":\"").append(escape(adm.getNome())).append("\",")
                        .append("\"email\":\"").append(escape(adm.getEmail())).append("\",")
                        .append("\"em_ativo\":\"").append(adm.getEmAtivo()).append("\"")
                        .append("}");

                if (i < adms.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar administradores: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            AdmModel novoAdm = criarAdm(request);
            admDAO.inserirAdm(novoAdm);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Administrador cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar administrador: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idAdm = obterInteiroObrigatorio(request, "id_adm", "ID do administrador");
            AdmModel adm = criarAdm(request);
            adm.setIdAdm(idAdm);

            admDAO.atualizarAdm(adm);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Administrador atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar administrador: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_adm", "ID do administrador");
            admDAO.deletarAdm(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Administrador deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar administrador: " + mensagem(e));
        }
    }

    private AdmModel criarAdm(HttpServletRequest request) {
        String email = obterParametroObrigatorio(request, "email");
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail do administrador inválido.");
        }

        String statusStr = request.getParameter("em_ativo");
        EmAtivo emAtivo = (statusStr == null || statusStr.trim().isEmpty())
                ? EmAtivo.Ativo
                : EmAtivo.valueOf(statusStr);

        return new AdmModel(
                0,
                obterParametroObrigatorio(request, "nome"),
                email,
                emAtivo
        );
    }

    private int obterInteiroObrigatorio(HttpServletRequest request, String nome, String descricao) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(descricao + " é obrigatório.");
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(descricao + " inválido.", e);
        }
    }

    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }
        return valor.trim();
    }

    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }

    private String escape(String texto) {
        StringBuilder escaped = new StringBuilder();
        String value = texto == null ? "" : texto;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            switch (character) {
                case '"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default:
                    if (character < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) character));
                    } else {
                        escaped.append(character);
                    }
            }
        }
        return escaped.toString();
    }
}