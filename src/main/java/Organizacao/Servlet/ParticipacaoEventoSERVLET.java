package Organizacao.Servlet;

import Organizacao.Dao.ParticipacaoEventoDAO;
import Organizacao.Model.ParticipacaoEventoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ParticipacaoEventoSERVLET", value = "/participacoes-evento")
// atende requisicoes http de participacao em evento
public class ParticipacaoEventoSERVLET extends HttpServlet {

    // mantem o acesso aos dados das participacoes
    private ParticipacaoEventoDAO participacaoDAO;

    @Override
    // prepara o acesso ao banco de dados
    public void init() throws ServletException {
        participacaoDAO = new ParticipacaoEventoDAO();
    }

    @Override
    // lista as participacoes e devolve os dados em json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            List<ParticipacaoEventoModel> items = participacaoDAO.listarParticipacoes();
            StringBuilder json = new StringBuilder("[");
            // percorre as participacoes e monta cada objeto da resposta
            for (int i = 0; i < items.size(); i++) {
                ParticipacaoEventoModel item = items.get(i);
                json.append("{\"id_participacao\":").append(item.getId_participacao())
                        .append(",\"status\":").append(json(item.getStatus()))
                        .append(",\"id_b2c\":").append(item.getId_b2c())
                        .append(",\"id_evento\":").append(item.getId_evento()).append("}");
                // separa os objetos sem acrescentar virgula no final
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            // informa falhas ocorridas ao consultar as participacoes
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar participações: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra uma participacao
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            participacaoDAO.inserirParticipacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Participação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar participação: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza uma participacao
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            ParticipacaoEventoModel item = criar(request);
            item.setId_participacao(requiredInt(request, "id_participacao"));
            participacaoDAO.atualizarParticipacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Participação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar participação: " + message(e));
        }
    }

    @Override
    // valida o identificador e remove uma participacao
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            participacaoDAO.deletarParticipacao(
                    requiredInt(request, "id_participacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Participação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover participação: " + message(e));
        }
    }

    // valida os campos recebidos e cria o objeto da participacao
    private ParticipacaoEventoModel criar(HttpServletRequest request) {
        return new ParticipacaoEventoModel(
                requiredText(request, "status"),
                requiredInt(request, "id_b2c"),
                requiredInt(request, "id_evento"));
    }


    // configura o formato e a codificacao da resposta
    private void prepare(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // configura a codificacao dos dados recebidos
    private void prepareRequest(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
    }

    // valida e devolve um parametro de texto obrigatorio
    private String requiredText(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        // rejeita parametros ausentes ou vazios
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O par?metro '" + name + "' ? obrigat?rio.");
        }
        return value.trim();
    }

    // valida e converte um parametro para inteiro positivo
    private int requiredInt(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            int number = Integer.parseInt(value);
            // rejeita valores iguais ou menores que zero
            if (number <= 0) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O par?metro '" + name + "' deve ser um inteiro positivo.", e);
        }
    }

    // valida e converte um parametro para numero nao negativo
    private double requiredNonNegativeDouble(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            double number = Double.parseDouble(value);
            // rejeita valores negativos e valores que nao representam um numero finito
            if (!Double.isFinite(number) || number < 0) throw new NumberFormatException();
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O par?metro '" + name + "' deve ser um n?mero v?lido n?o negativo.", e);
        }
    }

    // valida e converte um parametro para data
    private java.time.LocalDate requiredDate(HttpServletRequest request, String name) {
        String value = requiredText(request, name);
        try {
            return java.time.LocalDate.parse(value);
        } catch (RuntimeException e) {
            // informa o formato esperado para a data
            throw new IllegalArgumentException(
                    "O par?metro '" + name + "' deve usar o formato yyyy-MM-dd.", e);
        }
    }

    // protege os caracteres especiais antes de montar o json
    private String json(String value) {
        // representa valores ausentes como nulo em json
        if (value == null) return "null";
        StringBuilder result = new StringBuilder("\"");
        // percorre os caracteres para aplicar o escape correspondente
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            switch (character) {
                case '"': result.append("\\\""); break;
                case '\\': result.append("\\\\"); break;
                case '\b': result.append("\\b"); break;
                case '\f': result.append("\\f"); break;
                case '\n': result.append("\\n"); break;
                case '\r': result.append("\\r"); break;
                case '\t': result.append("\\t"); break;
                default:
                    // converte caracteres de controle para a representacao unicode
                    if (character < 0x20) {
                        result.append(String.format("\\u%04x", (int) character));
                    } else {
                        result.append(character);
                    }
            }
        }
        return result.append('"').toString();
    }

    // envia uma resposta de erro em formato json
    private void error(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + json(message == null ? "Erro interno." : message) + "}");
    }

    // devolve uma mensagem util da excecao
    private String message(Exception exception) {
        return exception.getMessage() == null
                ? exception.getClass().getSimpleName() : exception.getMessage();
    }
}
