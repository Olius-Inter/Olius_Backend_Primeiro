package Organizacao.Servlet;

import Organizacao.Dao.B2bDAO;
import Organizacao.Model.B2bModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/b2b")
public class B2bSERVLET extends HttpServlet {

    private B2bDAO b2bDAO;

    @Override
    public void init() throws ServletException {
        b2bDAO = new B2bDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            List<B2bModel> empresas = b2bDAO.listarB2b();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < empresas.size(); i++) {
                B2bModel empresa = empresas.get(i);
                json.append("{")
                        .append("\"id_usuario\":").append(empresa.getId_usuario()).append(",")
                        .append("\"cnpj\":\"").append(escape(empresa.getCnpj())).append("\",")
                        .append("\"razao_social\":\"").append(escape(empresa.getRazao_social())).append("\",")
                        .append("\"nome_fantasia\":\"").append(escape(empresa.getNome_fantasia())).append("\",")
                        .append("\"telefone\":\"").append(escape(empresa.getTelefone())).append("\",")
                        .append("\"id_endereco\":").append(empresa.getId_endereco())
                        .append("}");

                if (i < empresas.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar empresas B2B: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            b2bDAO.inserirB2b(criarEmpresa(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B cadastrada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar empresa B2B: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            int idUsuario = obterId(request);
            B2bModel empresa = criarEmpresa(request);
            b2bDAO.atualizarB2b(new B2bModel(
                    idUsuario,
                    empresa.getCnpj(),
                    empresa.getRazao_social(),
                    empresa.getNome_fantasia(),
                    empresa.getTelefone(),
                    empresa.getId_endereco()
            ));

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar empresa B2B: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            b2bDAO.deletarB2b(obterId(request));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar empresa B2B: " + mensagem(e));
        }
    }

    private B2bModel criarEmpresa(HttpServletRequest request) {
        return new B2bModel(
                obterParametroObrigatorio(request, "cnpj"),
                obterParametroObrigatorio(request, "razao_social"),
                obterParametroObrigatorio(request, "nome_fantasia"),
                obterParametroObrigatorio(request, "telefone"),
                obterIdEndereco(request)
        );
    }

    private int obterId(HttpServletRequest request) {
        return obterInteiroObrigatorio(request, "id_usuario", "ID do usuário");
    }

    private int obterIdEndereco(HttpServletRequest request) {
        return obterInteiroObrigatorio(request, "id_endereco", "ID do endereço");
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
