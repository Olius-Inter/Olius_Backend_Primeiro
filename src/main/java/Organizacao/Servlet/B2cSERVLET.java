package Organizacao.Servlet;

import Organizacao.Dao.B2cDAO;
import Organizacao.Model.B2cModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
// atende requisicoes http de usuario b dois c
public class B2cSERVLET extends HttpServlet {

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private B2cDAO usuariosDAO;

    @Override
    // inicializa o acesso aos dados usado pelas requisicoes
    public void init() throws ServletException {
        usuariosDAO = new B2cDAO();
    }

    @Override
    // consulta os registros de usuario b dois c e devolve a resposta json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // consulta os registros e monta a resposta json
        try {
            List<B2cModel> clientes = usuariosDAO.listarB2c();
            StringBuilder json = new StringBuilder("[");

            // percorre os registros e acrescenta cada objeto a resposta json
            for (int i = 0; i < clientes.size(); i++) {
                B2cModel cliente = clientes.get(i);
                json.append("{")
                        .append("\"id_usuario\":").append(cliente.getId_usuario()).append(",")
                        .append("\"cpf\":\"").append(escape(cliente.getCpf())).append("\",")
                        .append("\"telefone\":\"").append(escape(cliente.getTelefone())).append("\"")
                        .append("}");

                // acrescenta uma virgula somente entre objetos json
                if (i < clientes.size() - 1) {
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
                    "Erro ao listar clientes B2C: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra usuario b dois c
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // valida os campos da requisicao e cadastra usuario b dois c
        try {
            usuariosDAO.inserirB2c(criarCliente(request, obterId(request)));
            // define o status de criacao e confirma o cadastro em json
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Cliente B2C cadastrado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar cliente B2C: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza usuario b dois c
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // valida os campos da requisicao e atualiza usuario b dois c
        try {
            usuariosDAO.atualizarB2c(criarCliente(request, obterId(request)));
            // define o status de atualizacao e confirma a alteracao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Cliente B2C atualizado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar cliente B2C: " + mensagem(e));
        }
    }

    @Override
    // valida o identificador e remove usuario b dois c
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // valida o identificador da requisicao e remove usuario b dois c
        try {
            usuariosDAO.deletarB2c(obterId(request));
            // define o status de exclusao e confirma a remocao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Cliente B2C deletado com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar cliente B2C: " + mensagem(e));
        }
    }

    // monta os dados do cliente com os campos obrigatorios da requisicao
    private B2cModel criarCliente(HttpServletRequest request, int idUsuario) {
        return new B2cModel(
                idUsuario,
                obterParametroObrigatorio(request, "cpf"),
                obterParametroObrigatorio(request, "telefone")
        );
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

    // valida e devolve um parametro de texto obrigatorio
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    // define o formato json e a codificacao da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write(
                "{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}"
        );
    }

    // devolve a mensagem da falha ou o nome da excecao
    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
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
}