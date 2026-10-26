package Organizacao.Servlet;

import Organizacao.Dao.UsuarioDAO;
import Organizacao.Model.UsuarioModel;
import Organizacao.Model.Enum.TipoUsuario;
import Organizacao.Utilitarios.ValidarDocumentos;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // Listar todos os usuários (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<UsuarioModel> lista = usuarioDAO.listarUsuario();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                UsuarioModel u = lista.get(i);
                if (i > 0) json.append(",");

                // Omitida a propriedade 'senha' na saída por motivos de segurança
                json.append("{\"id_usuario\":").append(u.getIdUsuario())
                        .append(",\"email\":\"").append(escape(u.getEmail()))
                        .append("\",\"telefone\":\"").append(escape(u.getTelefone()))
                        .append("\",\"nome\":\"").append(escape(u.getNome()))
                        .append("\",\"primeiro_registro\":\"").append(u.getPrimeiroRegistro())
                        .append("\",\"tipo_usuario\":\"").append(escape(u.getTipoUsuario().name()))
                        .append("\",\"id_endereco\":").append(u.getIdEndereco())
                        .append("}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar usuários: " + mensagem(e));
        }
    }

    // Cadastrar usuário (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            usuarioDAO.inserirUsuario(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Usuário cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar usuário: " + mensagem(e));
        }
    }

    // Atualizar usuário (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_usuario");
            UsuarioModel u = criar(req);
            u.setIdUsuario(id);
            usuarioDAO.atualizarUsuario(u);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Usuário atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar usuário: " + mensagem(e));
        }
    }

    // Excluir usuário (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            usuarioDAO.deletarUsuario(inteiro(req, "id_usuario"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Usuário deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar usuário: " + mensagem(e));
        }
    }

    // Validação e construção do modelo
    private UsuarioModel criar(HttpServletRequest req) {
        String email = parametro(req, "email");
        if (!ValidarDocumentos.validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        String telefone = parametro(req, "telefone");
        if (telefone.length() > 15) {
            throw new IllegalArgumentException("Telefone deve ter no máximo 15 caracteres.");
        }

        LocalDate registro = (req.getParameter("primeiro_registro") == null || req.getParameter("primeiro_registro").trim().isEmpty())
                ? LocalDate.now()
                : data(req, "primeiro_registro");

        String senha = parametro(req, "senha");
        if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        return new UsuarioModel(
                email, telefone, parametro(req, "nome"), registro, senha,
                enumeracao(TipoUsuario.class, req, "tipo_usuario"), inteiro(req, "id_endereco")
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
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }
        return valor.trim();
    }

    private int inteiro(HttpServletRequest request, String nome) {
        try {
            return Integer.parseInt(parametro(request, nome));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número inteiro.");
        }
    }

    private java.time.LocalDate data(HttpServletRequest request, String nome) {
        try {
            return java.time.LocalDate.parse(parametro(request, nome));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida no parâmetro '" + nome + "'. Use AAAA-MM-DD.");
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