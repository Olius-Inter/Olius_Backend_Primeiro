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

@WebServlet("/enderecos")
// atende as requisicoes relacionadas aos enderecos
public class EnderecoSERVLET extends HttpServlet {

    // mantem o acesso aos dados dos enderecos
    private EnderecoDAO enderecoDAO;

    @Override
    // prepara o acesso ao banco de dados
    public void init() throws ServletException {
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    // lista os enderecos e devolve os dados em json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // configura o formato e a codificacao da resposta
        prepararResposta(response);

        try {
            List<EnderecoModel> enderecos = enderecoDAO.listarEnderecos();
            StringBuilder json = new StringBuilder("[");

            // percorre os enderecos e monta cada objeto da resposta
            for (int i = 0; i < enderecos.size(); i++) {
                EnderecoModel endereco = enderecos.get(i);
                json.append("{")
                        .append("\"id_endereco\":").append(endereco.getId_endereco()).append(",")
                        .append("\"cep\":\"").append(escape(endereco.getCep())).append("\",")
                        .append("\"logradouro\":\"").append(escape(endereco.getLogradouro())).append("\",")
                        .append("\"numero\":\"").append(escape(endereco.getNumero())).append("\",")
                        .append("\"complemento\":\"").append(escape(endereco.getComplemento())).append("\",")
                        .append("\"bairro\":\"").append(escape(endereco.getBairro())).append("\",")
                        .append("\"cidade\":\"").append(escape(endereco.getCidade())).append("\",")
                        .append("\"estado\":\"").append(escape(endereco.getEstado())).append("\",")
                        .append("\"pais\":\"").append(escape(endereco.getPais())).append("\"")
                        .append("}");

                // separa os objetos sem acrescentar virgula no final
                if (i < enderecos.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            // informa falhas ocorridas ao consultar os enderecos
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao listar endereços: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra um endereco
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        // configura a codificacao dos dados recebidos
        request.setCharacterEncoding("UTF-8");

        try {
            EnderecoModel item = criarEndereco(request);
            enderecoDAO.inserirEndereco(item);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Endereço cadastrado com sucesso!\",\"id_endereco\":" + item.getId_endereco() + "}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao cadastrar endereço: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza um endereco
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        // configura a codificacao dos dados recebidos
        request.setCharacterEncoding("UTF-8");

        try {
            EnderecoModel endereco = criarEndereco(request);
            endereco.setId_endereco(obterId(request));
            enderecoDAO.atualizarEndereco(endereco);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço atualizado com sucesso!\"}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao atualizar endereço: " + mensagem(e));
        }
    }

    @Override
    // valida o identificador e remove um endereco
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            enderecoDAO.deletarEndereco(obterId(request));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço deletado com sucesso!\"}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao deletar endereço: " + mensagem(e));
        }
    }

    // valida os campos recebidos e cria o objeto do endereco
    public static EnderecoModel criarEndereco(HttpServletRequest request) {
        String cep = obterParametroObrigatorio(request, "cep").replaceAll("[\\s-]", "");
        if (!cep.matches("\\d{8}")) throw new IllegalArgumentException("O CEP deve conter exatamente 8 números.");
        String estado = obterParametroObrigatorio(request, "estado").toUpperCase(java.util.Locale.ROOT);
        try {
            Estados.valueOf(estado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Informe a sigla do estado com 2 letras.");
        }
        String numero = obterParametroObrigatorio(request, "numero");
        if (numero.length() > 10) throw new IllegalArgumentException("Número aceita até 10 caracteres.");
        return new EnderecoModel(
                cep,
                obterParametroObrigatorio(request, "logradouro"),
                numero,
                obterParametroOpcional(request, "complemento"),
                obterParametroObrigatorio(request, "bairro"),
                obterParametroObrigatorio(request, "cidade"),
                estado,
                "Brasil"
        );
    }

    // valida e devolve o identificador do endereco
    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_endereco");

        // rejeita identificadores ausentes ou vazios
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do endereço é obrigatório.");
        }

        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new IllegalArgumentException("Identificador deve ser maior que zero.");
            }
            return numero;
        } catch (NumberFormatException e) {
            // informa quando o identificador nao e um numero inteiro
            throw new IllegalArgumentException("ID do endereço inválido.", e);
        }
    }

    // valida e devolve um parametro obrigatorio
    private static String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita parametros ausentes ou vazios
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        int limite = 50;
        if ("cep".equals(nome)) limite = 15;
        if ("logradouro".equals(nome)) limite = 200;
        if ("numero".equals(nome)) limite = 20;
        if ("bairro".equals(nome) || "cidade".equals(nome)) limite = 100;
        if (valor.trim().length() > limite) {
            throw new IllegalArgumentException("O campo '" + nome + "' aceita até " + limite + " caracteres.");
        }
        return valor.trim();
    }

    // devolve o valor informado ou um texto vazio
    private static String obterParametroOpcional(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor != null && valor.trim().length() > 100) {
            throw new IllegalArgumentException("O complemento aceita até 100 caracteres.");
        }
        return valor == null ? "" : valor.trim();
    }

    // configura o formato e a codificacao da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // envia uma resposta de erro em formato json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write(
                "{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}"
        );
    }

    // devolve uma mensagem util da excecao
    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }

    // protege caracteres especiais antes de montar o json
    private String escape(String texto) {
        StringBuilder escaped = new StringBuilder();
        String value = texto == null ? "" : texto;
        // percorre os caracteres para aplicar o escape correspondente
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
                    // converte caracteres de controle para a representacao unicode
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
