package Organizacao.Servlet;

import Organizacao.Dao.PontosMovimentacaoDAO;
import Organizacao.Model.PontosMovimentacaoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PontosMovimentacaoSERVLET", value = "/pontos-movimentacao")
// atende requisicoes http de movimentacao de pontos
public class PontosMovimentacaoSERVLET extends HttpServlet {

    // mantem o acesso aos dados das movimentacoes
    private PontosMovimentacaoDAO movimentacaoDAO;

    @Override
    // prepara o acesso ao banco de dados
    public void init() throws ServletException {
        movimentacaoDAO = new PontosMovimentacaoDAO();
    }

    @Override
    // lista as movimentacoes e devolve os dados em json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            List<PontosMovimentacaoModel> items = movimentacaoDAO.listarPontosMovimentacao();
            StringBuilder json = new StringBuilder("[");
            // percorre as movimentacoes e monta cada objeto da resposta
            for (int i = 0; i < items.size(); i++) {
                PontosMovimentacaoModel item = items.get(i);
                json.append("{\"id_movimentacao\":").append(item.getId_movimentacao())
                        .append(",\"pontos_ganhos\":").append(item.getPontos_ganhos())
                        .append(",\"tipo_movimentacao\":").append(json(item.getTipo_movimentacao()))
                        .append(",\"dt_movimentacao\":").append(json(item.getDt_movimentacao()))
                        .append(",\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"id_carteira\":").append(item.getId_carteira())
                        .append(",\"id_participacao\":").append(item.getId_participacao()).append("}");
                // separa os objetos sem acrescentar virgula no final
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            // informa falhas ocorridas ao consultar as movimentacoes
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar movimentações: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra uma movimentacao
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            movimentacaoDAO.inserirPontosMovimentacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Movimentação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar movimentação: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza uma movimentacao
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            PontosMovimentacaoModel item = criar(request);
            item.setId_movimentacao(requiredInt(request, "id_movimentacao"));
            movimentacaoDAO.atualizarPontosMovimentacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar movimentação: " + message(e));
        }
    }

    @Override
    // valida o identificador e remove uma movimentacao
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            movimentacaoDAO.deletarPontosMovimentacao(
                    requiredInt(request, "id_movimentacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover movimentação: " + message(e));
        }
    }

    // valida os campos recebidos e cria o objeto da movimentacao
    private PontosMovimentacaoModel criar(HttpServletRequest request) {
        int points;
        try {
            points = Integer.parseInt(requiredText(request, "pontos_ganhos"));
        } catch (NumberFormatException e) {
            // informa quando a quantidade de pontos nao e um numero inteiro
            throw new IllegalArgumentException("O parâmetro 'pontos_ganhos' deve ser inteiro.", e);
        }
        return new PontosMovimentacaoModel(
                points,
                requiredText(request, "tipo_movimentacao"),
                requiredDate(request, "dt_movimentacao").toString(),
                requiredInt(request, "id_entrega"),
                requiredInt(request, "id_carteira"),
                requiredInt(request, "id_participacao"));
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
