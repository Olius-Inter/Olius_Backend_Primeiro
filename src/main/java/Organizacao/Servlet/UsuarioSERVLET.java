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
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/usuarios")
// atende requisicoes http de usuario
public class UsuarioSERVLET extends HttpServlet {

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

        // valida os campos da requisicao e cadastra usuario
        try {
            int idUsuario = obterId(request);
            String nome = obterParametroObrigatorio(request, "nome");
            String email = obterParametroObrigatorio(request, "email");
            String senha = obterParametroObrigatorio(request, "senha");
            String tipoUsuario = obterParametroObrigatorio(request, "tipo_usuario");
            String telefone = obterParametroObrigatorio(request, "telefone");
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

        // valida os campos da requisicao e atualiza usuario
        try {
            int idUsuario = obterId(request);
            String nome = obterParametroObrigatorio(request, "nome");
            String email = obterParametroObrigatorio(request, "email");
            String senha = obterParametroObrigatorio(request, "senha");
            String tipoUsuario = obterParametroObrigatorio(request, "tipo_usuario");
            String telefone = obterParametroObrigatorio(request, "telefone");

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
        // devolve texto vazio quando o valor recebido e nulo
        if (texto == null) {

            return "";
        }


        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\f", "\\f");
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

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }

        // converte o texto do identificador para inteiro
        try {

            return Integer.parseInt(valor.trim());
        // converte erro de conversao em uma validacao clara do parametro
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do usuário inválido.", e);
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
            return Date.valueOf(valor.trim());
        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
        }
    }

    // converte a data para o formato exibido na resposta
    private String formatarData(Date data) {
        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (data == null) {

            return "";
        }


        return data.toLocalDate().format(FORMATO_DATA);
    }

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }
}
