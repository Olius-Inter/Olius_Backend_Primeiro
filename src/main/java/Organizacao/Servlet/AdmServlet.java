package Organizacao.Servlet;

import Organizacao.Dao.AdmDAO;
import Organizacao.Model.AdmModel;
import Organizacao.Model.Enum.EmAtivo;
import Organizacao.Utilitarios.ValidarDocumentos;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/adms")
public class AdmServlet extends HttpServlet {
    private AdmDAO admDAO;

    // Inicializa o DAO na criação do servlet
    @Override
    public void init() {
        admDAO = new AdmDAO();
    }

    // Lista todos os administradores (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<AdmModel> lista = admDAO.listarAdm();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                AdmModel a = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_adm\":").append(a.getIdAdm())
                        .append(",\"nome\":\"").append(escape(a.getNome()))
                        .append("\",\"email\":\"").append(escape(a.getEmail()))
                        .append("\",\"em_ativo\":\"").append(a.getEmAtivo().name()).append("\"}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar administradores: " + mensagem(e));
        }
    }

    // Insere um novo administrador (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            admDAO.inserirAdm(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Administrador cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar administrador: " + mensagem(e));
        }
    }

    // Atualiza um administrador existente (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_adm");
            AdmModel a = criar(req);
            a.setIdAdm(id);
            admDAO.atualizarAdm(a);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Administrador atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar administrador: " + mensagem(e));
        }
    }

    // Remove um administrador por ID (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            admDAO.deletarAdm(inteiro(req, "id_adm"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Administrador deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar administrador: " + mensagem(e));
        }
    }

    // Valida e instancia o modelo de Administrador
    private AdmModel criar(HttpServletRequest req) {
        String email = parametro(req, "email");
        if (!ValidarDocumentos.validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        return new AdmModel(parametro(req, "nome"), email, enumeracao(EmAtivo.class, req, "em_ativo"));
    }

    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }

    private String escape(String texto) {
        if (texto == null) return "";
        StringBuilder saida = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"': saida.append("\\\""); break;
                case '\\': saida.append("\\\\"); break;
                case '\b': saida.append("\\b"); break;
                case '\f': saida.append("\\f"); break;
                case '\n': saida.append("\\n"); break;
                case '\r': saida.append("\\r"); break;
                case '\t': saida.append("\\t"); break;
                default:
                    if (c < 0x20) saida.append(String.format("\\u%04x", (int) c));
                    else saida.append(c);
            }
        }
        return saida.toString();
    }

    private String parametro(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        return valor.trim();
    }

    private int inteiro(HttpServletRequest request, String nome) {
        try {
            return Integer.parseInt(parametro(request, nome));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número inteiro.");
        }
    }

    private <E extends Enum<E>> E enumeracao(Class<E> tipo, HttpServletRequest request, String nome) {
        try {
            return Enum.valueOf(tipo, parametro(request, nome));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para '" + nome + "'. Valores aceitos: "
                    + java.util.Arrays.toString(tipo.getEnumConstants()));
        }
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}