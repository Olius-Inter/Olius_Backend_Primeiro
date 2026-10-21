package Organizacao.Servlet;

import Organizacao.Dao.CarteiraDAO;
import Organizacao.Model.CarteiraModel;
import Organizacao.Model.Enum.Patente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/carteiras")
// atende requisicoes http para gerenciar carteiras de pontos e patentes
public class CarteiraServlet extends HttpServlet {

    private CarteiraDAO carteiraDAO;

    @Override
    public void init() throws ServletException {
        carteiraDAO = new CarteiraDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<CarteiraModel> lista = carteiraDAO.listarCarteira();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                CarteiraModel c = lista.get(i);
                json.append("{")
                        .append("\"id_carteira\":").append(c.getIdCarteira()).append(",")
                        .append("\"pontos_totais\":").append(c.getPontosTotais()).append(",")
                        .append("\"patente\":\"").append(c.getPatente()).append("\",")
                        .append("\"nivel\":").append(c.getNivel()).append(",")
                        .append("\"id_b2c\":").append(c.getIdB2C() != null ? c.getIdB2C() : "null").append(",")
                        .append("\"id_b2b\":").append(c.getIdB2B() != null ? c.getIdB2B() : "null")
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
                    "Erro ao listar carteiras: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            CarteiraModel novaCarteira = criarCarteira(request);
            carteiraDAO.inserirCarteira(novaCarteira);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Carteira cadastrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar carteira: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idCarteira = obterInteiroObrigatorio(request, "id_carteira", "ID da carteira");
            CarteiraModel carteira = criarCarteira(request);
            carteira.setIdCarteira(idCarteira);

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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_carteira", "ID da carteira");
            carteiraDAO.deletarCarteira(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Carteira deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar carteira: " + mensagem(e));
        }
    }

    private CarteiraModel criarCarteira(HttpServletRequest request) {
        String patenteStr = obterParametroObrigatorio(request, "patente");
        Patente patente;
        try {
            patente = Patente.valueOf(patenteStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Patente inválida (Valores aceitos: Ouro, Prata, Bronze).");
        }

        int pontos = 0;
        String pontosStr = request.getParameter("pontos_totais");
        if (pontosStr != null && !pontosStr.trim().isEmpty()) {
            try {
                pontos = Integer.parseInt(pontosStr.trim());
                if (pontos < 0) throw new IllegalArgumentException();
            } catch (Exception e) {
                throw new IllegalArgumentException("Pontos totais inválidos.");
            }
        }

        int nivel = 1;
        String nivelStr = request.getParameter("nivel");
        if (nivelStr != null && !nivelStr.trim().isEmpty()) {
            try {
                nivel = Integer.parseInt(nivelStr.trim());
                if (nivel < 1) throw new IllegalArgumentException();
            } catch (Exception e) {
                throw new IllegalArgumentException("Nível inválido. Deve ser maior ou igual a 1.");
            }
        }

        // Validação da regra de exclusividade (pertence ou a B2C ou a B2B)
        String idB2cStr = request.getParameter("id_b2c");
        String idB2bStr = request.getParameter("id_b2b");

        Integer idB2C = (idB2cStr != null && !idB2cStr.trim().isEmpty()) ? Integer.parseInt(idB2cStr.trim()) : null;
        Integer idB2B = (idB2bStr != null && !idB2bStr.trim().isEmpty()) ? Integer.parseInt(idB2bStr.trim()) : null;

        if ((idB2C != null && idB2B != null) || (idB2C == null && idB2B == null)) {
            throw new IllegalArgumentException("A carteira deve pertencer obrigatoriamente a apenas um perfil (informe ID de B2C ou ID de B2B, nunca ambos ou nenhum).");
        }

        return new CarteiraModel(0, pontos, patente, nivel, idB2C, idB2B);
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