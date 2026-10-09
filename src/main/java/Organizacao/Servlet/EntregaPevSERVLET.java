package Organizacao.Servlet;

import Organizacao.Dao.EntregaPevDAO;
import Organizacao.Model.EntregaPevModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EntregaPevSERVLET", value = "/entregas-pev")
// atende as requisicoes de entrega nos pontos de entrega voluntaria
public class EntregaPevSERVLET extends HttpServlet {

    // mantem o acesso aos dados das entregas
    private EntregaPevDAO entregaDAO;

    @Override
    // prepara o acesso ao banco de dados
    public void init() throws ServletException {
        entregaDAO = new EntregaPevDAO();
    }

    @Override
    // lista as entregas e devolve os dados em json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            List<EntregaPevModel> items = entregaDAO.listarEntregasPev();
            StringBuilder json = new StringBuilder("[");
            // percorre as entregas e monta cada objeto da resposta
            for (int i = 0; i < items.size(); i++) {
                EntregaPevModel item = items.get(i);
                json.append("{\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"id_usuario_b2c\":").append(item.getId_usuario_b2c())
                        .append(",\"id_pev\":").append(item.getId_pev())
                        .append(",\"qtd_litros\":").append(item.getQtd_litros())
                        .append(",\"pontos_gerados\":").append(item.getPontos_gerados())
                        .append(",\"dt_entrega\":").append(json(
                                item.getDt_entrega() == null ? null : item.getDt_entrega().toString()))
                        .append("}");
                // separa os objetos sem acrescentar virgula no final
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            // informa falhas ocorridas ao consultar as entregas
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar entregas PEV: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra uma entrega
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            entregaDAO.inserirEntregaPev(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar entrega PEV: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza uma entrega
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            EntregaPevModel item = criar(request);
            item.setId_entrega(requiredInt(request, "id_entrega"));
            entregaDAO.atualizarEntregaPev(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar entrega PEV: " + message(e));
        }
    }

    @Override
    // valida o identificador e remove uma entrega
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            entregaDAO.deletarEntregaPev(requiredInt(request, "id_entrega"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover entrega PEV: " + message(e));
        }
    }

    // valida os campos recebidos e cria o objeto da entrega
    private EntregaPevModel criar(HttpServletRequest request) {
        int points;
        try {
            points = Integer.parseInt(requiredText(request, "pontos_gerados"));
            // impede o cadastro de uma quantidade negativa de pontos
            if (points < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'pontos_gerados' deve ser inteiro não negativo.", e);
        }
        return new EntregaPevModel(
                requiredInt(request, "id_usuario_b2c"),
                requiredInt(request, "id_pev"),
                requiredNonNegativeDouble(request, "qtd_litros"),
                points,
                requiredDate(request, "dt_entrega"));
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
            throw new IllegalArgumentException("O parâmetro '" + name + "' é obrigatório.");
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
                    "O parâmetro '" + name + "' deve ser um inteiro positivo.", e);
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
                    "O parâmetro '" + name + "' deve ser um número válido não negativo.", e);
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
                    "O parâmetro '" + name + "' deve usar o formato yyyy-MM-dd.", e);
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

    // define o status e devolve uma mensagem de erro em json
    private void error(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + json(message == null ? "Erro interno." : message) + "}");
    }

    // devolve a mensagem da falha ou o nome da excecao
    private String message(Exception exception) {
        return exception.getMessage() == null
                ? exception.getClass().getSimpleName() : exception.getMessage();
    }
}
