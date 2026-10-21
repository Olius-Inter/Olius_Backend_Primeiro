package Organizacao.Servlet;

import Organizacao.Dao.SolicitacaoColetaDAO;
import Organizacao.Model.SolicitacaoColetaModel;
import Organizacao.Model.Enum.StatusSolicitacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/solicitacoes")
// atende requisicoes http para solicitacoes de coleta de oleo
public class SolicitacaoColetaServlet extends HttpServlet {

    private SolicitacaoColetaDAO solicitacaoDAO;

    @Override
    public void init() throws ServletException {
        solicitacaoDAO = new SolicitacaoColetaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<SolicitacaoColetaModel> lista = solicitacaoDAO.listarSolicitacao();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                SolicitacaoColetaModel s = lista.get(i);
                json.append("{")
                        .append("\"id_solicitacao\":").append(s.getIdSolicitacao()).append(",")
                        .append("\"status\":\"").append(s.getStatus()).append("\",")
                        .append("\"volume_estimado\":").append(s.getVolumeEstimado()).append(",")
                        .append("\"dt_solicitacao\":\"").append(s.getDtSolicitacao()).append("\",")
                        .append("\"id_b2b\":").append(s.getIdB2B())
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
                    "Erro ao listar solicitações: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            SolicitacaoColetaModel novaSolicitacao = criarSolicitacao(request);
            solicitacaoDAO.inserirSolicitacao(novaSolicitacao);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Solicitação de coleta cadastrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar solicitação: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idSolicitacao = obterInteiroObrigatorio(request, "id_solicitacao", "ID da solicitação");
            SolicitacaoColetaModel solicitacao = criarSolicitacao(request);
            solicitacao.setIdSolicitacao(idSolicitacao);

            solicitacaoDAO.atualizarSolicitacao(solicitacao);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Solicitação de coleta atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar solicitação: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_solicitacao", "ID da solicitação");
            solicitacaoDAO.deletarSolicitacao(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Solicitação de coleta deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar solicitação: " + mensagem(e));
        }
    }

    private SolicitacaoColetaModel criarSolicitacao(HttpServletRequest request) throws NumberFormatException, IllegalArgumentException {
        String statusStr = obterParametroObrigatorio(request, "status");
        StatusSolicitacao status;
        try {
            status = StatusSolicitacao.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido (Valores aceitos: Realizada, Pendente, Cancelada).");
        }

        double volume;
        volume = Double.parseDouble(obterParametroObrigatorio(request, "volume_estimado"));
        if (volume <= 0) {
            throw new IllegalArgumentException();
        }

        LocalDate dataSolicitacao;
        try {
            dataSolicitacao = LocalDate.parse(obterParametroObrigatorio(request, "dt_solicitacao"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de solicitação inválida. Use o formato AAAA-MM-DD.");
        }

        return new SolicitacaoColetaModel(
                0,
                status,
                volume,
                dataSolicitacao,
                obterInteiroObrigatorio(request, "id_b2b", "ID da empresa B2B")
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