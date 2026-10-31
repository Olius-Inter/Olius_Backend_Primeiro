package Organizacao.Servlet;

import Organizacao.Dao.PevDAO;
import Organizacao.Model.PevModel;
import Organizacao.Model.Enum.StatusPev;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/pevs")
public class PevServlet extends HttpServlet {
    private PevDAO pevDAO;

    @Override
    public void init() {
        pevDAO = new PevDAO();
    }

    // Lista os Pontos de Entrega Voluntária (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<PevModel> lista = pevDAO.listarPev();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                PevModel p = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_pev\":").append(p.getIdPev())
                        .append(",\"status\":\"").append(escape(p.getStatus().name()))
                        .append("\",\"dt_aprovacao\":\"").append(p.getDtAprovacao())
                        .append("\",\"id_usuario\":").append(p.getIdUsuario()).append("}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar PEVs: " + mensagem(e));
        }
    }

    // Cadastra um PEV (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            pevDAO.inserirPev(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"PEV cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar PEV: " + mensagem(e));
        }
    }

    // Atualiza um PEV (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_pev");
            PevModel p = criar(req);
            p.setIdPev(id);
            pevDAO.atualizarPev(p);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"PEV atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar PEV: " + mensagem(e));
        }
    }

    // Remove um PEV (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            pevDAO.deletarPev(inteiro(req, "id_pev"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"PEV deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar PEV: " + mensagem(e));
        }
    }

    private PevModel criar(HttpServletRequest req) {
        return new PevModel(enumeracao(StatusPev.class, req, "status"), data(req, "dt_aprovacao"), inteiro(req, "id_usuario"));
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

    private java.time.LocalDate data(HttpServletRequest request, String nome) {
        try {
            return java.time.LocalDate.parse(parametro(request, nome));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida no parâmetro '" + nome + "'. Use AAAA-MM-DD.");
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