package Organizacao.Servlet;

import Organizacao.Dao.AtualizacaoDAO;
import Organizacao.Model.AtualizacaoModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/atualizacoes")
public class AtualizacaoServlet extends HttpServlet {
    private AtualizacaoDAO atualizacaoDAO;

    @Override
    public void init() {
        atualizacaoDAO = new AtualizacaoDAO();
    }

    // Lista todas as atualizações (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<AtualizacaoModel> lista = atualizacaoDAO.listarAtualizacao();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                AtualizacaoModel a = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_atualizacao\":").append(a.getIdAtualizacao())
                        .append(",\"motivo\":\"").append(escape(a.getMotivo()))
                        .append("\",\"autorizacao\":").append(a.getAutorizacao())
                        .append(",\"mudanca\":\"").append(escape(a.getMudanca())).append("\"}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar atualizações: " + mensagem(e));
        }
    }

    // Cadastra uma nova atualização (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            atualizacaoDAO.inserirAtualizacao(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Atualização cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar atualização: " + mensagem(e));
        }
    }

    // Atualiza um registro de atualização (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_atualizacao");
            AtualizacaoModel a = criar(req);
            a.setIdAtualizacao(id);
            atualizacaoDAO.atualizarAtualizacao(a);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Atualização alterada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar registro: " + mensagem(e));
        }
    }

    // Remove uma atualização (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            atualizacaoDAO.deletarAtualizacao(inteiro(req, "id_atualizacao"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Atualização deletada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar atualização: " + mensagem(e));
        }
    }

    private AtualizacaoModel criar(HttpServletRequest req) {
        return new AtualizacaoModel(parametro(req, "motivo"), inteiro(req, "autorizacao"), parametro(req, "mudanca"));
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

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}