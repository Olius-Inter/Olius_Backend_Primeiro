package Organizacao.Servlet;

import Organizacao.Dao.B2CDAO;
import Organizacao.Model.B2CModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/consumidores")
// atende requisicoes http para clientes b2c (consumidores pessoas fisicas)
public class B2CServlet extends HttpServlet {

    private B2CDAO b2cDAO;

    // Regex para validar CPF (exatamente 11 dígitos numéricos)
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    @Override
    public void init() throws ServletException {
        b2cDAO = new B2CDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<B2CModel> lista = b2cDAO.listarB2C();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                B2CModel item = lista.get(i);
                json.append("{")
                        .append("\"id_b2c\":").append(item.getIdB2C()).append(",")
                        .append("\"cpf\":\"").append(escape(item.getCpf())).append("\",")
                        .append("\"id_usuario\":").append(item.getIdUsuario())
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
                    "Erro ao listar B2C: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            B2CModel novoB2C = criarB2C(request);
            b2cDAO.inserirB2C(novoB2C);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Consumidor B2C cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar B2C: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idB2C = obterInteiroObrigatorio(request, "id_b2c", "ID do B2C");
            B2CModel b2c = criarB2C(request);
            b2c.setIdB2C(idB2C);

            b2cDAO.atualizarB2C(b2c);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Consumidor B2C atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar B2C: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_b2c", "ID do B2C");
            b2cDAO.deletarB2C(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Consumidor B2C deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar B2C: " + mensagem(e));
        }
    }

    private B2CModel criarB2C(HttpServletRequest request) {
        String cpf = obterParametroObrigatorio(request, "cpf");
        if (!CPF_PATTERN.matcher(cpf).matches()) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
        }

        return new B2CModel(
                0,
                cpf,
                obterInteiroObrigatorio(request, "id_usuario", "ID do usuário")
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