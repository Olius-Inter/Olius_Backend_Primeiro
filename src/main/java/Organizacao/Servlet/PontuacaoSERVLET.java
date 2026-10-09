package Organizacao.Servlet;

import Organizacao.Dao.PontuacaoDAO;
import Organizacao.Model.PontuacaoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PontuacaoSERVLET", value = "/pontuacao")
// atende as requisicoes relacionadas as pontuacoes
public class PontuacaoSERVLET extends HttpServlet {

    // mantem o acesso aos dados das pontuacoes
    private PontuacaoDAO pontuacaoDAO;

    @Override
    // prepara o acesso ao banco de dados
    public void init() throws ServletException {
        pontuacaoDAO = new PontuacaoDAO();
    }

    @Override
    // lista as pontuacoes e devolve os dados em json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            List<PontuacaoModel> items = pontuacaoDAO.listarPontuacoes();
            StringBuilder json = new StringBuilder("[");
            // percorre as pontuacoes e monta cada objeto da resposta
            for (int i = 0; i < items.size(); i++) {
                PontuacaoModel item = items.get(i);
                json.append("{\"id_pontuacao\":").append(item.getId_pontuacao())
                        .append(",\"id_usuario_b2c\":").append(item.getId_usuario_b2c())
                        .append(",\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"pontos\":").append(item.getPontos())
                        .append(",\"tp_movimentacao\":").append(json(item.getTp_movimentacao()))
                        .append(",\"descricao\":").append(json(item.getDescricao()))
                        .append(",\"dt_movimentacao\":").append(json(
                                item.getDt_movimentacao() == null ? null : item.getDt_movimentacao().toString()))
                        .append("}");
                // separa os objetos sem acrescentar virgula no final
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            // informa falhas ocorridas ao consultar as pontuacoes
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar pontuações: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra uma pontuacao
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            pontuacaoDAO.inserirPontuacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Pontuação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar pontuação: " + message(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza uma pontuacao
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        prepareRequest(request);
        try {
            PontuacaoModel item = criar(request);
            item.setId_pontuacao(requiredInt(request, "id_pontuacao"));
            pontuacaoDAO.atualizarPontuacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Pontuação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar pontuação: " + message(e));
        }
    }

    @Override
    // valida o identificador e remove uma pontuacao
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepare(response);
        try {
            pontuacaoDAO.deletarPontuacao(requiredInt(request, "id_pontuacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Pontuação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover pontuação: " + message(e));
        }
    }

    // valida os campos recebidos e cria o objeto da pontuacao
    private PontuacaoModel criar(HttpServletRequest request) {
        int pontos;
        try {
            pontos = Integer.parseInt(requiredText(request, "pontos"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'pontos' deve ser inteiro.", e);
        }
        return new PontuacaoModel(
                requiredInt(request, "id_usuario_b2c"),
                requiredInt(request, "id_entrega"),
                pontos,
                requiredText(request, "tp_movimentacao"),
                requiredText(request, "descricao"),
                requiredDate(request, "dt_movimentacao")
        );
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
