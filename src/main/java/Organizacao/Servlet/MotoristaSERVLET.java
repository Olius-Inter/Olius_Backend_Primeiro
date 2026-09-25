package Organizacao.Servlet;

import Organizacao.Dao.MotoristaDAO;
import Organizacao.Model.MotoristaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

// Servlet responsável por expor, via HTTP

@WebServlet("/motoristas")
public class MotoristaSERVLET extends HttpServlet {

    private MotoristaDAO motoristaDAO;

    // Executado uma única vez, na inicialização do servlet, para
    // instanciar o DAO.
    @Override
    public void init() throws ServletException {
        motoristaDAO = new MotoristaDAO();
    }

    // Lista todos os motoristas cadastrados.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            List<MotoristaModel> motoristas = motoristaDAO.listarMotoristas();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < motoristas.size(); i++) {
                MotoristaModel motorista = motoristas.get(i);

                json.append("{")
                        .append("\"id_motorista\":").append(motorista.getId_motorista()).append(",")
                        .append("\"nome\":\"").append(escape(motorista.getNome())).append("\",")
                        .append("\"cpf\":\"").append(escape(motorista.getCpf())).append("\",")
                        .append("\"telefone\":\"").append(escape(motorista.getTelefone())).append("\",")
                        .append("\"cnh\":\"").append(escape(motorista.getCnh())).append("\",")
                        .append("\"empresa\":\"").append(escape(motorista.getEmpresa())).append("\",")
                        .append("\"status\":\"").append(escape(motorista.getStatus())).append("\"")
                        .append("}");

                if (i < motoristas.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        // Qualquer falha (DAO, banco, etc.) vira um erro.
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar motoristas: " + mensagem(e));
        }
    }

    // Monta um MotoristaModel a partir dos parâmetros da requisição
    // (com validação) e o cadastra no banco.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            motoristaDAO.inserirMotorista(criarMotorista(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(
                    "{\"mensagem\":\"Motorista cadastrado com sucesso!\"}"
            );

        // Dados inválidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // Qualquer outra falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar motorista: " + mensagem(e));
        }
    }


    // Reconstrói o motorista a partir dos parâmetros, associa o
    // id_motorista informado e atualiza o registro correspondente.
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            MotoristaModel motorista = criarMotorista(request);
            motorista.setId_motorista(obterId(request));
            motoristaDAO.atualizarMotorista(motorista);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(
                    "{\"mensagem\":\"Motorista atualizado com sucesso!\"}"
            );

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar motorista: " + mensagem(e));
        }
    }

    // Remove o motorista pelo id_motorista é informado como parâmetro.
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            motoristaDAO.deletarMotorista(obterId(request));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(
                    "{\"mensagem\":\"Motorista deletado com sucesso!\"}"
            );

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar motorista: " + mensagem(e));
        }
    }

    // Constrói um MotoristaModel lendo e validando os parâmetros
    private MotoristaModel criarMotorista(HttpServletRequest request) {
        return new MotoristaModel(
                obterParametroObrigatorio(request, "nome"),
                obterParametroObrigatorio(request, "cpf"),
                obterParametroObrigatorio(request, "telefone"),
                obterParametroObrigatorio(request, "cnh"),
                obterParametroObrigatorio(request, "empresa"),
                obterParametroObrigatorio(request, "status")
        );
    }

    // Lê e valida o id_motorista enviado como parâmetro na requisição.

    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_motorista");

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do motorista é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do motorista inválido.", e);
        }
    }

    // Lê um parâmetro de texto obrigatório da requisição.

    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + nome + "' é obrigatório."
            );
        }

        return valor.trim();
    }


    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // Monta e envia uma resposta de erro padronizada.
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