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

// Servlet responsável por expor, via HTTP.
@WebServlet("/usuarios")
public class UsuarioSERVLET extends HttpServlet {

    // Formato usado para exibir a data de primeiro registro na resposta JSON
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private UsuarioDAO usuarioDAO;

    // Executado uma única vez, na inicialização do servlet, para
    // instanciar o DAO.
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    // Lista todos os usuários cadastrados.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<UsuarioModel> usuarios = usuarioDAO.listar();
            StringBuilder json = new StringBuilder();
            json.append("[");

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

                if (i < usuarios.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        // Qualquer falha (DAO, banco, etc.) vira um erro.
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar usuários: " + e.getMessage());
        }
    }

    // Lê e valida os parâmetros obrigatórios da requisição e cadastra
    // um novo usuário.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

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
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Usuário cadastrado com sucesso!\"}");

        // Dados inválidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // Qualquer outra falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // Lê os parâmetros da requisição e atualiza o usuário correspondente
    // ao id_usuario informado.
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

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
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    // Remove o usuário pelo id_usuario é informado como parâmetro.
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idUsuario = obterId(request);
            usuarioDAO.deletarUsuario(idUsuario);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Usuário deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar usuário: " + e.getMessage());
        }
    }

    private String escape(String texto) {
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

    // Lê um parâmetro de texto obrigatório da requisição.

    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    // Lê e valida o id_usuario enviado como parâmetro na requisição.

    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_usuario");

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do usuário inválido.", e);
        }
    }

    // Converte a String recebida (formato yyyy-MM-dd) para java.sql.Date.

    private Date obterData(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de primeiro registro é obrigatória.");
        }

        try {
            return Date.valueOf(valor.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
        }
    }

    private String formatarData(Date data) {
        if (data == null) {
            return "";
        }

        return data.toLocalDate().format(FORMATO_DATA);
    }

    // Monta e envia uma resposta de erro padronizada.
    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }
}
