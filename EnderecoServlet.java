package Organizacao.Servlet;

import Organizacao.Dao.EnderecoDAO;
import Organizacao.Model.EnderecoModel;
import Organizacao.Model.Enum.Estados;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/enderecos")
// atende requisicoes http para gerenciar enderecos
public class EnderecoServlet extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    // Regex para validar CEP (exatamente 8 dígitos numéricos)
    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{8}$");

    @Override
    public void init() throws ServletException {
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    // consulta os registros de enderecos e devolve em formato json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<EnderecoModel> enderecos = enderecoDAO.listarEndereco();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < enderecos.size(); i++) {
                EnderecoModel end = enderecos.get(i);
                json.append("{")
                        .append("\"id_endereco\":").append(end.getIdEndereco()).append(",")
                        .append("\"rua\":\"").append(escape(end.getRua())).append("\",")
                        .append("\"cidade\":\"").append(escape(end.getCidade())).append("\",")
                        .append("\"bairro\":\"").append(escape(end.getBairro())).append("\",")
                        .append("\"cep\":\"").append(escape(end.getCep())).append("\",")
                        .append("\"numero\":\"").append(escape(end.getNumero())).append("\",")
                        .append("\"complemento\":\"").append(escape(end.getComplemento())).append("\",")
                        .append("\"estado\":\"").append(end.getEstado()).append("\"")
                        .append("}");

                if (i < enderecos.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar endereços: " + mensagem(e));
        }
    }

    @Override
    // valida dados com regex e cadastra um novo endereco
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            EnderecoModel novoEndereco = criarEndereco(request);
            enderecoDAO.inserirEndereco(novoEndereco);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Endereço cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar endereço: " + mensagem(e));
        }
    }

    @Override
    // valida dados e atualiza o endereco existente
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idEndereco = obterInteiroObrigatorio(request, "id_endereco", "ID do endereço");
            EnderecoModel endereco = criarEndereco(request);
            endereco.setIdEndereco(idEndereco);

            enderecoDAO.atualizarEndereco(endereco);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar endereço: " + mensagem(e));
        }
    }

    @Override
    // remove endereco pelo id
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_endereco", "ID do endereço");
            enderecoDAO.deletarEndereco(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar endereço: " + mensagem(e));
        }
    }

    // monta e valida o model de endereco aplicando regex no CEP
    private EnderecoModel criarEndereco(HttpServletRequest request) {
        String cep = obterParametroObrigatorio(request, "cep");
        if (!CEP_PATTERN.matcher(cep).matches()) {
            throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 dígitos numéricos.");
        }

        String estadoStr = obterParametroObrigatorio(request, "estado");
        Estados estado;
        try {
            estado = Estados.valueOf(estadoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado (UF) inválido.");
        }

        return new EnderecoModel(
                0,
                obterParametroObrigatorio(request, "rua"),
                obterParametroObrigatorio(request, "cidade"),
                obterParametroObrigatorio(request, "bairro"),
                cep,
                obterParametroObrigatorio(request, "numero"),
                request.getParameter("complemento") != null ? request.getParameter("complemento").trim() : "",
                estado
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