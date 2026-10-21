package Organizacao.Servlet;

import Organizacao.Dao.MovimentacaoPontosDAO;
import Organizacao.Model.MovimentacaoPontosModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/movimentacoes-pontos")
// atende requisicoes http para extrato e movimentacao de pontos nas carteiras
public class MovimentacaoPontosServlet extends HttpServlet {

    private MovimentacaoPontosDAO movimentacaoDAO;

    @Override
    public void init() throws ServletException {
        movimentacaoDAO = new MovimentacaoPontosDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<MovimentacaoPontosModel> lista = movimentacaoDAO.listarMovimentacao();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                MovimentacaoPontosModel m = lista.get(i);
                json.append("{")
                        .append("\"id_movimentacao\":").append(m.getIdMovimentacao()).append(",")
                        .append("\"quantidade_pontos\":").append(m.getQuantidadePontos()).append(",")
                        .append("\"id_carteira\":").append(m.getIdCarteira()).append(",")
                        .append("\"id_coleta\":").append(m.getIdColeta() != null ? m.getIdColeta() : "null").append(",")
                        .append("\"id_entrega\":").append(m.getIdEntrega() != null ? m.getIdEntrega() : "null")
                        .append("}");

                if (i < lista.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar movimentações: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            MovimentacaoPontosModel novaMovimentacao = criarMovimentacao(request);
            movimentacaoDAO.inserirMovimentacao(novaMovimentacao);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Movimentação de pontos registrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao registrar movimentação: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idMovimentacao = obterInteiroObrigatorio(request, "id_movimentacao", "ID da movimentação");
            MovimentacaoPontosModel movimentacao = criarMovimentacao(request);
            movimentacao.setIdMovimentacao(idMovimentacao);

            movimentacaoDAO.atualizarMovimentacao(movimentacao);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação de pontos atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar movimentação: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_movimentacao", "ID da movimentação");
            movimentacaoDAO.deletarMovimentacao(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação de pontos deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar movimentação: " + mensagem(e));
        }
    }

    private MovimentacaoPontosModel criarMovimentacao(HttpServletRequest request) {
        int pontos = obterInteiroObrigatorio(request, "quantidade_pontos", "Quantidade de pontos");
        int idCarteira = obterInteiroObrigatorio(request, "id_carteira", "ID da carteira");

        // Validação da regra de origem exclusiva (ou veio de uma coleta B2B ou de uma entrega PEV B2C)
        String idColetaStr = request.getParameter("id_coleta");
        String idEntregaStr = request.getParameter("id_entrega");

        Integer idColeta = (idColetaStr != null && !idColetaStr.trim().isEmpty()) ? Integer.parseInt(idColetaStr.trim()) : null;
        Integer idEntrega = (idEntregaStr != null && !idEntregaStr.trim().isEmpty()) ? Integer.parseInt(idEntregaStr.trim()) : null;

        if ((idColeta != null && idEntrega != null) || (idColeta == null && idEntrega == null)) {
            throw new IllegalArgumentException("A movimentação deve possuir exatamente uma origem vinculada (informe apenas ID de coleta ou ID de entrega).");
        }

        return new MovimentacaoPontosModel(0, pontos, idCarteira, idColeta, idEntrega);
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