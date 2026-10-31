package Organizacao.Servlet;

import Organizacao.Dao.EnderecoDAO;
import Organizacao.Model.EnderecoModel;
import Organizacao.Model.Enum.Estados;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/enderecos")
public class EnderecoServlet extends HttpServlet {
    private EnderecoDAO enderecoDAO;

    @Override
    public void init() {
        enderecoDAO = new EnderecoDAO();
    }

    // Lista os endereços cadastrados (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        try {
            List<EnderecoModel> lista = enderecoDAO.listarEndereco();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                EnderecoModel e = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_endereco\":").append(e.getIdEndereco())
                        .append(",\"rua\":\"").append(escape(e.getRua())).append("\"")
                        .append(",\"cidade\":\"").append(escape(e.getCidade())).append("\"")
                        .append(",\"bairro\":\"").append(escape(e.getBairro())).append("\"")
                        .append(",\"cep\":\"").append(escape(e.getCep())).append("\"")
                        .append(",\"numero\":\"").append(escape(e.getNumero())).append("\"")
                        .append(",\"complemento\":");
                if (e.getComplemento() == null) json.append("null");
                else json.append("\"").append(escape(e.getComplemento())).append("\"");
                json.append(",\"estado\":\"").append(e.getEstado().name()).append("\"}");
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar endereços: " + mensagem(e));
        }
    }

    // Cadastra um novo endereço (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");
        try {
            enderecoDAO.inserirEndereco(criarEndereco(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Endereço cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar endereço: " + mensagem(e));
        }
    }

    // Atualiza um endereço existente (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");
        try {
            int idEndereco = inteiro(request, "id_endereco");
            EnderecoModel endereco = criarEndereco(request);
            endereco.setIdEndereco(idEndereco);
            enderecoDAO.atualizarEndereco(endereco);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar endereço: " + mensagem(e));
        }
    }

    // Remove um endereço (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        try {
            enderecoDAO.deletarEndereco(inteiro(request, "id_endereco"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço excluído com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao excluir endereço: " + mensagem(e));
        }
    }

    private EnderecoModel criarEndereco(HttpServletRequest request) {
        String cep = parametro(request, "cep").replaceAll("[\\s-]", "");
        if (!cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("O CEP deve conter exatamente 8 números.");
        }

        String complemento = request.getParameter("complemento");
        if (complemento != null) {
            complemento = complemento.trim();
            if (complemento.isEmpty()) complemento = null;
        }

        Estados estado = enumeracao(Estados.class, request, "estado");
        return new EnderecoModel(
                parametro(request, "rua"),
                parametro(request, "cidade"),
                parametro(request, "bairro"),
                cep,
                parametro(request, "numero"),
                complemento,
                estado
        );
    }

    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }

    private String escape(String texto) {
        if (texto == null) return "";
        StringBuilder saida = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"': saida.append("\\\""); break;
                case '\\': saida.append("\\\\"); break;
                case '\b': saida.append("\\b"); break;
                case '\f': saida.append("\\f"); break;
                case '\n': saida.append("\\n"); break;
                case '\r': saida.append("\\r"); break;
                case '\t': saida.append("\\t"); break;
                default:
                    if (c < 0x20) saida.append(String.format("\\u%04x", (int) c));
                    else saida.append(c);
            }
        }
        return saida.toString();
    }

    private String parametro(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        return valor.trim();
    }

    private int inteiro(HttpServletRequest request, String nome) {
        try {
            return Integer.parseInt(parametro(request, nome));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número inteiro.");
        }
    }

    private <E extends Enum<E>> E enumeracao(Class<E> tipo, HttpServletRequest request, String nome) {
        try {
            return Enum.valueOf(tipo, parametro(request, nome));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para '" + nome + "'. Valores aceitos: "
                    + java.util.Arrays.toString(tipo.getEnumConstants()));
        }
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}