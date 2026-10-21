package Organizacao.Servlet;

import Organizacao.Dao.EntregaPevDAO;
import Organizacao.Model.EntregaPevModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/entregas-pev")
// atende requisicoes http para os registros de entrega de oleo em PEVs
public class EntregaPevServlet extends HttpServlet {

    private EntregaPevDAO entregaDAO;

    @Override
    public void init() throws ServletException {
        entregaDAO = new EntregaPevDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<EntregaPevModel> lista = entregaDAO.listarEntregaPev();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                EntregaPevModel e = lista.get(i);
                json.append("{")
                        .append("\"id_entrega\":").append(e.getIdEntrega()).append(",")
                        .append("\"dt_entrega\":\"").append(e.getDtEntrega()).append("\",")
                        .append("\"volume_entrega\":").append(e.getVolumeEntrega()).append(",")
                        .append("\"id_pev\":").append(e.getIdPev()).append(",")
                        .append("\"id_b2c\":").append(e.getIdB2C())
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
                    "Erro ao listar entregas PEV: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            EntregaPevModel novaEntrega = criarEntrega(request);
            entregaDAO.inserirEntregaPev(novaEntrega);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV cadastrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar entrega PEV: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idEntrega = obterInteiroObrigatorio(request, "id_entrega", "ID da entrega");
            EntregaPevModel entrega = criarEntrega(request);
            entrega.setIdEntrega(idEntrega);

            entregaDAO.atualizarEntregaPev(entrega);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar entrega PEV: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_entrega", "ID da entrega");
            entregaDAO.deletarEntregaPev(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar entrega PEV: " + mensagem(e));
        }
    }

    private EntregaPevModel criarEntrega(HttpServletRequest request) throws NumberFormatException, IllegalArgumentException {
        LocalDate dataEntrega;
        try {
            dataEntrega = LocalDate.parse(obterParametroObrigatorio(request, "dt_entrega"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de entrega inválida. Use o formato AAAA-MM-DD.");
        }

        double volume;
        volume = Double.parseDouble(obterParametroObrigatorio(request, "volume_entrega"));
        if (volume <= 0) {
            throw new IllegalArgumentException();
        }

        return new EntregaPevModel(
                0,
                dataEntrega,
                volume,
                obterInteiroObrigatorio(request, "id_pev", "ID do PEV"),
                obterInteiroObrigatorio(request, "id_b2c", "ID do B2C")
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