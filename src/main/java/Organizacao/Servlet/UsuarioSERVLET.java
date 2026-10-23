package Organizacao.Servlet;

import Organizacao.Dao.UsuarioDAO;
import Organizacao.Model.UsuarioModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/usuarios")
// atende requisicoes http de usuario
public class UsuarioSERVLET extends HttpServlet {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+");
    private static final Pattern TELEFONE_PATTERN = Pattern.compile(
            "(?:\\+55\\s?)?(?:\\([1-9]\\d\\)|[1-9]\\d)\\s?(?:9\\d{4}|[2-5]\\d{3})-?\\d{4}");
    private static final Pattern NOME_PATTERN = Pattern.compile("[\\p{L}\\p{M}][\\p{L}\\p{M} .'-]*");
    private static final Pattern TIPO_USUARIO_PATTERN = Pattern.compile("[\\p{L}\\p{M}][\\p{L}\\p{M}_-]*");
    private static final Pattern DATA_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private UsuarioDAO usuarioDAO;

    @Override
    // inicializa o acesso aos dados usado pelas requisicoes
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    // consulta os registros de usuario e devolve a resposta json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // consulta os registros e monta a resposta json
        try {
            List<UsuarioModel> usuarios = usuarioDAO.listar();
            StringBuilder json = new StringBuilder();
            json.append("[");

            // percorre os registros e acrescenta cada objeto a resposta json
            for (int i = 0; i < usuarios.size(); i++) {
                UsuarioModel usuario = usuarios.get(i);
                json.append("{");
                json.append("\"id_usuario\":").append(usuario.getId_usuario()).append(",");
                json.append("\"nome\":\"").append(escape(usuario.getNome())).append("\",");
                json.append("\"email\":\"").append(escape(usuario.getEmail())).append("\",");
                json.append("\"primeiro_registro\":\"")
                        .append(escape(formatarData(usuario.getPrimeiroRegistro())))
                        .append("\",");
                json.append("\"tipo_usuario\":\"")
                        .append(escape(usuario.getTipoUsuario()))
                        .append("\",");
                json.append("\"telefone\":\"")
                        .append(escape(usuario.getTelefone()))
                        .append("\"");
                json.append("}");

                // acrescenta uma virgula somente entre objetos json
                if (i < usuarios.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            // define o status de sucesso e envia a lista json consultada
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar usuários: " + e.getMessage());
        }
    }

    @Override
    // valida os dados recebidos e cadastra usuario
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e cadastra usuario
        try {
            int idUsuario = obterId(request);
            String nome = validarFormato(
                    obterParametroObrigatorio(request, "nome"), NOME_PATTERN, "Nome inválido.");
            String email = validarFormato(
                    obterParametroObrigatorio(request, "email"), EMAIL_PATTERN, "E-mail inválido.");
            String senha = obterParametroObrigatorio(request, "senha");
            String tipoUsuario = validarFormato(obterParametroObrigatorio(request, "tipo_usuario"),
                    TIPO_USUARIO_PATTERN, "Tipo de usuário contém caracteres inválidos.");
            String telefone = validarFormato(
                    obterParametroObrigatorio(request, "telefone"), TELEFONE_PATTERN,
                    "Telefone inválido. Informe DDD e um telefone brasileiro válido.");
            String primeiroRegistroString = request.getParameter("primeiro_registro");

            Date primeiroRegistro = obterData(primeiroRegistroString);

            UsuarioModel usuario = new UsuarioModel(
                    idUsuario,
                    nome,
                    email,
                    senha,
                    primeiroRegistro,
                    tipoUsuario,
                    telefone
            );

            usuarioDAO.salvar(usuario);
            // define o status de criacao e confirma o cadastro em json
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Usuário cadastrado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @Override
    // valida os dados recebidos e atualiza usuario
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e atualiza usuario
        try {
            int idUsuario = obterId(request);
            String nome = validarFormato(
                    obterParametroObrigatorio(request, "nome"), NOME_PATTERN, "Nome inválido.");
            String email = validarFormato(
                    obterParametroObrigatorio(request, "email"), EMAIL_PATTERN, "E-mail inválido.");
            String senha = obterParametroObrigatorio(request, "senha");
            String tipoUsuario = validarFormato(obterParametroObrigatorio(request, "tipo_usuario"),
                    TIPO_USUARIO_PATTERN, "Tipo de usuário contém caracteres inválidos.");
            String telefone = validarFormato(
                    obterParametroObrigatorio(request, "telefone"), TELEFONE_PATTERN,
                    "Telefone inválido. Informe DDD e um telefone brasileiro válido.");

            UsuarioModel usuario = new UsuarioModel(
                    idUsuario,
                    nome,
                    email,
                    senha,
                    null,
                    tipoUsuario,
                    telefone
            );

            usuarioDAO.atualizarUsuario(usuario);
            // define o status de atualizacao e confirma a alteracao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário atualizado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Override
    // valida o identificador e remove usuario
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // valida o identificador da requisicao e remove usuario
        try {
            int idUsuario = obterId(request);
            usuarioDAO.deletarUsuario(idUsuario);
            // define o status de exclusao e confirma a remocao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário deletado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar usuário: " + e.getMessage());
        }
    }

    // protege caracteres especiais do texto antes de incluir no json
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

    // valida e devolve um parametro de texto obrigatorio
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    // valida e converte o identificador recebido na requisicao
    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_usuario");
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }
        try {
            int id = Integer.parseInt(valor.trim());
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do usuário deve ser um inteiro positivo.", e);
        }
    }

    // valida e converte a data recebida para o tipo de data local
    private Date obterData(String valor) {
        // rejeita a requisicao quando a data obrigatoria esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de primeiro registro é obrigatória.");
        }

        // interpreta o texto recebido como data local
        try {
            // devolve o valor validado e convertido
            String data = valor.trim();
            if (!DATA_PATTERN.matcher(data).matches()) {
                throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.");
            }
            return Date.valueOf(data);
        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
        }
    }

    private String validarFormato(String valor, Pattern padrao, String mensagem) {
        if (!padrao.matcher(valor).matches()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor;
    }

    // converte a data para o formato exibido na resposta
    private String formatarData(Date data) {
        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (data == null) {

            return "";
        }


        return data.toLocalDate().toString();
    }

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }
}
