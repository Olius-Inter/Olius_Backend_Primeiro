package Organizacao.Servlet;

import Organizacao.Dao.ColetaDAO;
import Organizacao.Model.ColetaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/coletas")
// atende requisicoes http para registrar e consultar coletas realizadas
public class ColetaServlet extends HttpServlet {

    private ColetaDAO coletaDAO;

    @Override
    public void init() throws ServletException {
        coletaDAO = new ColetaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<ColetaModel> lista = coletaDAO.listarColeta();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                ColetaModel c = lista.get(i);
                json.append("{")
                        .append("\"id_coleta\":").append(c.getIdColeta()).append(",")
                        .append("\"dt_coleta\":\"").append(c.getDtColeta()).append("\",")
                        .append("\"volume_coletado\":").append(c.getVolumeColetado()).append(",")
                        .append("\"avaliacao_coleta\":").append(c.getAvaliacaoColeta()).append(",")
                        .append("\"id_solicitacao\":").append(c.getIdSolicitacao()).append(",")
                        .append("\"id_motorista\":").append(c.getIdMotorista()).append(",")
                        .append("\"id_b2b\":").append(c.getIdB2B())
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
                    "Erro ao listar coletas: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            ColetaModel novaColeta = criarColeta(request);
            coletaDAO.inserirColeta(novaColeta);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Coleta cadastrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar coleta: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idColeta = obterInteiroObrigatorio(request, "id_coleta", "ID da coleta");
            ColetaModel coleta = criarColeta(request);
            coleta.setIdColeta(idColeta);

            coletaDAO.atualizarColeta(coleta);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar coleta: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_coleta", "ID da coleta");
            coletaDAO.deletarColeta(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar coleta: " + mensagem(e));
        }
    }

    private ColetaModel criarColeta(HttpServletRequest request) throws NumberFormatException, IllegalArgumentException {
        LocalDate dataColeta;
        try {
            dataColeta = LocalDate.parse(obterParametroObrigatorio(request, "dt_coleta"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data da coleta inválida. Use o formato AAAA-MM-DD.");
        }

        double volume;
        volume = Double.parseDouble(obterParametroObrigatorio(request, "volume_coletado"));
        if (volume <= 0) {
            throw new IllegalArgumentException();
        }

        int avaliacao = obterInteiroObrigatorio(request, "avaliacao_coleta", "Avaliação da coleta");
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação da coleta deve estar entre 1 e 5.");
        }

        return new ColetaModel(
                0,
                dataColeta,
                volume,
                avaliacao,
                obterInteiroObrigatorio(request, "id_solicitacao", "ID da solicitação"),
                obterInteiroObrigatorio(request, "id_motorista", "ID do motorista"),
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