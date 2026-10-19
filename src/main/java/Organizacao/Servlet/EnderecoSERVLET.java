package Organizacao.Servlet;

import Organizacao.Dao.EnderecoDAO;
import Organizacao.Model.EnderecoModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@WebServlet("/enderecos")
// atende as requisicoes relacionadas aos enderecos
public class EnderecoSERVLET extends HttpServlet {

    // Aceita CEP com oito dígitos, com ou sem hífen entre os grupos.
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-?\\d{3}");

    // Aceita números, complementos de letra, intervalos e a indicação S/N.
    private static final Pattern NUMERO_PATTERN =
            Pattern.compile("(?:\\d+[a-z]?(?:[-/]\\d+[a-z]?)?|s/?n)", Pattern.CASE_INSENSITIVE);

    // Aceita somente siglas oficiais de estados brasileiros e do Distrito Federal.
    private static final Pattern ESTADO_PATTERN = Pattern.compile(
            "(?:AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)",
            Pattern.CASE_INSENSITIVE
    );

    // Aceita texto Unicode, números e pontuação comum em nomes e endereços.
    private static final Pattern TEXTO_PATTERN =
            Pattern.compile("[\\p{L}\\p{M}0-9][\\p{L}\\p{M}0-9 .,'’ºª#&():;/\\-]*");

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
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
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
            enderecoDAO.inserirEndereco(criarEndereco(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Endereço cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
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
        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
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
        } catch (IllegalArgumentException e) {
            // informa quando o identificador enviado e invalido
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar endereço: " + mensagem(e));
        }
    }

    // valida os campos recebidos e cria o objeto do endereco
    private EnderecoModel criarEndereco(HttpServletRequest request) {
        String cep = validarFormato(
                obterParametroObrigatorio(request, "cep"),
                CEP_PATTERN,
                "CEP inválido. Use 00000-000 ou 00000000."
        );
        String logradouro = validarFormato(
                obterParametroObrigatorio(request, "logradouro"),
                TEXTO_PATTERN,
                "O logradouro contém caracteres inválidos."
        );
        String numero = validarFormato(
                obterParametroObrigatorio(request, "numero"),
                NUMERO_PATTERN,
                "Número inválido. Use números, como 123, 123A, ou S/N."
        );
        String complemento = obterParametroOpcional(request, "complemento");
        if (!complemento.isEmpty()) {
            complemento = validarFormato(
                    complemento,
                    TEXTO_PATTERN,
                    "O complemento contém caracteres inválidos."
            );
        }
        String bairro = validarFormato(
                obterParametroObrigatorio(request, "bairro"),
                TEXTO_PATTERN,
                "O bairro contém caracteres inválidos."
        );
        String cidade = validarFormato(
                obterParametroObrigatorio(request, "cidade"),
                TEXTO_PATTERN,
                "A cidade contém caracteres inválidos."
        );
        String estado = validarFormato(
                obterParametroObrigatorio(request, "estado"),
                ESTADO_PATTERN,
                "Estado inválido. Informe a sigla de duas letras, como SP."
        ).toUpperCase(Locale.ROOT);
        String pais = validarFormato(
                obterParametroObrigatorio(request, "pais"),
                TEXTO_PATTERN,
                "O país contém caracteres inválidos."
        );

        return new EnderecoModel(
                cep,
                logradouro,
                numero,
                complemento,
                bairro,
                cidade,
                estado,
                pais
        );
    }

    // valida e devolve o identificador do endereco
    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_endereco");
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do endereço é obrigatório.");
        }
        try {
            int id = Integer.parseInt(valor.trim());
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do endereço deve ser um inteiro positivo.", e);
        }
    }

    // valida e devolve um parametro obrigatorio
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita parametros ausentes ou vazios
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    // valida o formato do campo e fornece uma mensagem clara quando ele não corresponde ao padrão
    private String validarFormato(String valor, Pattern padrao, String mensagemErro) {
        if (!padrao.matcher(valor).matches()) {
            throw new IllegalArgumentException(mensagemErro);
        }

        return valor;
    }

    // devolve o valor informado ou um texto vazio
    private String obterParametroOpcional(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
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
