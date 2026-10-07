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

@WebServlet("/enderecos")
public class EnderecoSERVLET extends HttpServlet {

    private EnderecoDAO enderecoDAO;

    @Override
    public void init() throws ServletException {
        enderecoDAO = new EnderecoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            List<EnderecoModel> enderecos = enderecoDAO.listarEnderecos();
            StringBuilder json = new StringBuilder("[");

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            enderecoDAO.inserirEndereco(criarEndereco(request));
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            EnderecoModel endereco = criarEndereco(request);
            endereco.setId_endereco(obterId(request));
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            enderecoDAO.deletarEndereco(obterId(request));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Endereço deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar endereço: " + mensagem(e));
        }
    }

    private EnderecoModel criarEndereco(HttpServletRequest request) {
        return new EnderecoModel(
                obterParametroObrigatorio(request, "cep"),
                obterParametroObrigatorio(request, "logradouro"),
                obterParametroObrigatorio(request, "numero"),
                obterParametroOpcional(request, "complemento"),
                obterParametroObrigatorio(request, "bairro"),
                obterParametroObrigatorio(request, "cidade"),
                obterParametroObrigatorio(request, "estado"),
                obterParametroObrigatorio(request, "pais")
        );
    }

    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_endereco");

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do endereço é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do endereço inválido.", e);
        }
    }

    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    private String obterParametroOpcional(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        return valor == null ? "" : valor.trim();
    }

    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write(
                "{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}"
        );
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
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
}
