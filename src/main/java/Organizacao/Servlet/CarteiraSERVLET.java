package Organizacao.Servlet;

import Organizacao.Dao.CarteiraDAO;
import Organizacao.Model.CarteiraModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CarteiraSERVLET", value = "/carteira")
// gerencia as requisicoes de carteiras de pontos
public class CarteiraSERVLET extends HttpServlet {

    private CarteiraDAO carteiraDAO;

    @Override
    public void init() throws ServletException {
        carteiraDAO = new CarteiraDAO();
    }

    // lista as carteiras em formato json
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            List<CarteiraModel> carteiras = carteiraDAO.listarCarteiras();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < carteiras.size(); i++) {
                CarteiraModel carteira = carteiras.get(i);
                json.append("{")
                        .append("\"id_carteira\":").append(carteira.getId_carteira()).append(",")
                        .append("\"pontuacao\":").append(carteira.getPontuacao()).append(",")
                        .append("\"patente\":").append(jsonString(carteira.getPatente())).append(",")
                        .append("\"nivel\":").append(jsonString(carteira.getNivel())).append(",")
                        .append("\"id_b2c\":").append(carteira.getId_b2c())
                        .append("}");

                if (i < carteiras.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar carteiras: " + mensagem(e));
        }
    }

    // cadastra uma nova carteira
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            carteiraDAO.inserirCarteira(criarCarteira(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Carteira cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar carteira: " + mensagem(e));
        }
    }

    // atualiza os dados de uma carteira
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            CarteiraModel carteira = criarCarteira(request);
            carteira.setId_carteira(obterInteiroObrigatorio(request, "id_carteira"));
            carteiraDAO.atualizarCarteira(carteira);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Carteira atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar carteira: " + mensagem(e));
        }
    }

    // remove uma carteira pelo identificador
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            carteiraDAO.deletarCarteira(obterInteiroObrigatorio(request, "id_carteira"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Carteira deletada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar carteira: " + mensagem(e));
        }
    }

    // cria uma carteira com os dados recebidos
    private CarteiraModel criarCarteira(HttpServletRequest request) {
        return new CarteiraModel(
                obterInteiroObrigatorio(request, "pontuacao"),
                obterParametroObrigatorio(request, "patente"),
                obterParametroObrigatorio(request, "nivel"),
                obterInteiroObrigatorio(request, "id_b2c")
        );
    }

    // valida e converte um parâmetro para número inteiro
    private int obterInteiroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + nome + "' deve ser um número inteiro.", e);
        }
    }

    // valida e retorna um parâmetro de texto
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }
        return valor.trim();
    }

    // configura o formato e a codificação da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // envia uma resposta de erro em formato json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + jsonString(
                mensagem == null ? "Erro interno." : mensagem) + "}");
    }

    // converte um texto para uma string json segura
    private String jsonString(String valor) {
        if (valor == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder("\"");
        for (int i = 0; i < valor.length(); i++) {
            char caractere = valor.charAt(i);
            switch (caractere) {
                case '"':
                    json.append("\\\"");
                    break;
                case '\\':
                    json.append("\\\\");
                    break;
                case '\b':
                    json.append("\\b");
                    break;
                case '\f':
                    json.append("\\f");
                    break;
                case '\n':
                    json.append("\\n");
                    break;
                case '\r':
                    json.append("\\r");
                    break;
                case '\t':
                    json.append("\\t");
                    break;
                default:
                    if (caractere < 0x20) {
                        json.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        json.append(caractere);
                    }
            }
        }
        return json.append('"').toString();
    }

    // obtem uma mensagem util da excecao
    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}