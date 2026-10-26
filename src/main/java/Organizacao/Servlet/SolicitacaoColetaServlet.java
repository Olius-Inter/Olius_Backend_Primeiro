package Organizacao.Servlet;

import Organizacao.Dao.SolicitacaoColetaDAO;
import Organizacao.Model.SolicitacaoColetaModel;
import Organizacao.Model.Enum.StatusSolicitacao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/solicitacoes-coleta")
public class SolicitacaoColetaServlet extends HttpServlet {
    private SolicitacaoColetaDAO solicitacaoDAO;

    @Override
    public void init() {
        solicitacaoDAO = new SolicitacaoColetaDAO();
    }

    // Lista as solicitações de coleta (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<SolicitacaoColetaModel> lista = solicitacaoDAO.listarSolicitacao();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                SolicitacaoColetaModel s = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_solicitacao\":").append(s.getIdSolicitacao())
                        .append(",\"status\":\"").append(s.getStatus().name())
                        .append("\",\"volume_estimado\":").append(s.getVolumeEstimado())
                        .append(",\"dt_solicitacao\":\"").append(s.getDtSolicitacao())
                        .append("\",\"id_b2b\":").append(s.getIdB2B()).append("}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar solicitações: " + mensagem(e));
        }
    }

    // Cadastra uma solicitação de coleta (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            solicitacaoDAO.inserirSolicitacao(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Solicitação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar solicitação: " + mensagem(e));
        }
    }

    // Atualiza uma solicitação de coleta (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_solicitacao");
            SolicitacaoColetaModel s = criar(req);
            s.setIdSolicitacao(id);
            solicitacaoDAO.atualizarSolicitacao(s);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Solicitação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar solicitação: " + mensagem(e));
        }
    }

    // Remove uma solicitação de coleta (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            solicitacaoDAO.deletarSolicitacao(inteiro(req, "id_solicitacao"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Solicitação deletada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar solicitação: " + mensagem(e));
        }
    }

    private SolicitacaoColetaModel criar(HttpServletRequest req) {
        double volume = decimal(req, "volume_estimado");
        if (volume <= 0) throw new IllegalArgumentException("O volume estimado deve ser maior que zero.");
        return new SolicitacaoColetaModel(enumeracao(StatusSolicitacao.class, req, "status"), volume, data(req, "dt_solicitacao"), inteiro(req, "id_b2b"));
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

    private double decimal(HttpServletRequest request, String nome) {
        try {
            double valor = Double.parseDouble(parametro(request, nome));
            if (!Double.isFinite(valor)) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número válido.");
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