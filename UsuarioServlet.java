package Organizacao.Servlet;

import Organizacao.Dao.UsuarioDAO;
import Organizacao.Model.Enum.TipoUsuario;
import Organizacao.Model.UsuarioModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/usuarios")
// atende requisicoes http para gerenciar usuarios do sistema
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    // Regex para Email, Telefone (10 a 11 dígitos)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\d{10,11}$");

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    // lista todos os usuarios em formato json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<UsuarioModel> usuarios = usuarioDAO.listarUsuario();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < usuarios.size(); i++) {
                UsuarioModel u = usuarios.get(i);
                json.append("{")
                        .append("\"id_usuario\":").append(u.getIdUsuario()).append(",")
                        .append("\"email\":\"").append(escape(u.getEmail())).append("\",")
                        .append("\"telefone\":\"").append(escape(u.getTelefone())).append("\",")
                        .append("\"nome\":\"").append(escape(u.getNome())).append("\",")
                        .append("\"primeiro_registro\":\"").append(u.getPrimeiroRegistro()).append("\",")
                        .append("\"tipo_usuario\":\"").append(u.getTipoUsuario()).append("\",")
                        .append("\"id_endereco\":").append(u.getIdEndereco())
                        .append("}");

                if (i < usuarios.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar usuários: " + mensagem(e));
        }
    }

    @Override
    // valida campos com regex e cadastra novo usuario
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            UsuarioModel novoUsuario = criarUsuario(request);
            usuarioDAO.inserirUsuario(novoUsuario);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Usuário cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar usuário: " + mensagem(e));
        }
    }

    @Override
    // atualiza dados do usuario com validacao por regex
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idUsuario = obterInteiroObrigatorio(request, "id_usuario", "ID do usuário");
            UsuarioModel usuario = criarUsuario(request);
            usuario.setIdUsuario(idUsuario);

            usuarioDAO.atualizarUsuario(usuario);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar usuário: " + mensagem(e));
        }
    }

    @Override
    // remove usuario pelo identificador
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_usuario", "ID do usuário");
            usuarioDAO.deletarUsuario(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar usuário: " + mensagem(e));
        }
    }

    // monta objeto validando email e telefone via regex
    private UsuarioModel criarUsuario(HttpServletRequest request) {
        String email = obterParametroObrigatorio(request, "email");
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        String telefone = obterParametroObrigatorio(request, "telefone");
        if (!TELEFONE_PATTERN.matcher(telefone).matches()) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter entre 10 e 11 dígitos numéricos.");
        }

        String tipoStr = obterParametroObrigatorio(request, "tipo_usuario");
        TipoUsuario tipoUsuario;
        try {
            tipoUsuario = TipoUsuario.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuário inválido (Valores aceitos: B2B, B2C, PEV).");
        }

        return new UsuarioModel(
                0,
                email,
                telefone,
                obterParametroObrigatorio(request, "nome"),
                LocalDate.now(),
                obterParametroObrigatorio(request, "senha"),
                tipoUsuario,
                obterInteiroObrigatorio(request, "id_endereco", "ID do endereço")
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