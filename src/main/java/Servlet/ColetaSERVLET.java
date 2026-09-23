package Servlet;

import Organizacao.Dao.ColetaDAO;
import Organizacao.Model.ColetaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/coleta")
public class ColetaSERVLET extends HttpServlet {

    // Define o formato da data de coleta nas respostas da API.
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private ColetaDAO coletaDAO;

    // Inicializa o DAO responsável pelas operações de acesso às coletas.
    @Override
    public void init() throws ServletException {
        coletaDAO = new ColetaDAO();
    }

    // Processa GET, consulta as coletas e devolve os registros em JSON.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            List<ColetaModel> coletas = coletaDAO.listarColetas();

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (int i = 0; i < coletas.size(); i++) {

                ColetaModel coleta = coletas.get(i);

                json.append("{");

                json.append("\"id_coleta\":")
                        .append(coleta.getId_coleta())
                        .append(",");

                json.append("\"id_solicitacao\":")
                        .append(coleta.getId_solicitacao())
                        .append(",");

                json.append("\"id_motorista\":")
                        .append(coleta.getId_motorista())
                        .append(",");

                json.append("\"dt_coleta\":\"")
                        .append(escape(formatarData(coleta.getDt_coleta())))
                        .append("\",");

                json.append("\"volume\":")
                        .append(coleta.getVolume())
                        .append(",");

                json.append("\"observacao\":\"")
                        .append(escape(coleta.getObservacao()))
                        .append("\"");

                json.append("}");

                if (i < coletas.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {

            enviarErro(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar coletas: " + e.getMessage()
            );
        }
    }

    // Processa POST, valida os dados e cadastra uma nova coleta no banco.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int idSolicitacao = obterInteiroObrigatorio(request, "id_solicitacao", "ID da solicitação");
            int idMotorista = obterInteiroObrigatorio(request, "id_motorista", "ID do motorista");
            LocalDate dtColeta = obterData(request.getParameter("dt_coleta"));
            double volume = obterVolume(request);
            String observacao = request.getParameter("observacao");

            ColetaModel coleta = new ColetaModel(
                    idSolicitacao,
                    idMotorista,
                    dtColeta,
                    volume,
                    observacao == null ? "" : observacao.trim()
            );

            coletaDAO.inserirColeta(coleta);

            response.setStatus(HttpServletResponse.SC_CREATED);

            response.getWriter().write(
                    "{\"mensagem\":\"Coleta cadastrada com sucesso!\"}"
            );

        } catch (IllegalArgumentException e) {

            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (Exception e) {

            enviarErro(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar coleta: " + e.getMessage()
            );
        }
    }

    // Processa PUT, valida os dados e atualiza uma coleta existente.
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int idColeta = obterId(request);
            int idSolicitacao = obterInteiroObrigatorio(request, "id_solicitacao", "ID da solicitação");
            int idMotorista = obterInteiroObrigatorio(request, "id_motorista", "ID do motorista");
            LocalDate dtColeta = obterData(request.getParameter("dt_coleta"));
            double volume = obterVolume(request);
            String observacao = request.getParameter("observacao");

            ColetaModel coleta = new ColetaModel(
                    idSolicitacao,
                    idMotorista,
                    dtColeta,
                    volume,
                    observacao == null ? "" : observacao.trim()
            );
            coleta.setId_coleta(idColeta);

            coletaDAO.atualizarColeta(coleta);

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    "{\"mensagem\":\"Coleta atualizada com sucesso!\"}"
            );

        } catch (IllegalArgumentException e) {

            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (Exception e) {

            enviarErro(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar coleta: " + e.getMessage()
            );
        }
    }

    // Processa DELETE, obtém o ID e remove a coleta do banco de dados.
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            int idColeta = obterId(request);

            coletaDAO.deletarColeta(idColeta);

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    "{\"mensagem\":\"Coleta deletada com sucesso!\"}"
            );

        } catch (IllegalArgumentException e) {

            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (Exception e) {

            enviarErro(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar coleta: " + e.getMessage()
            );
        }
    }

    // Escapa caracteres especiais para manter a resposta JSON válida.
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

    // Obtém o ID da coleta enviado na requisição e converte o valor para inteiro.
    private int obterId(HttpServletRequest request) {
        String valor = request.getParameter("id_coleta");

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da coleta é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID da coleta inválido.", e);
        }
    }

    // Obtém um parâmetro inteiro obrigatório e converte o valor.
    private int obterInteiroObrigatorio(HttpServletRequest request, String nome, String rotulo) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(rotulo + " é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(rotulo + " inválido.", e);
        }
    }

    // Obtém e valida o volume informado na requisição.
    private double obterVolume(HttpServletRequest request) {
        String valor = request.getParameter("volume");

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Volume é obrigatório.");
        }

        try {
            return Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Volume inválido.", e);
        }
    }

    // Converte a data yyyy-MM-dd recebida na requisição para LocalDate.
    private LocalDate obterData(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de coleta é obrigatória.");
        }

        try {
            // LocalDate.parse espera o formato ISO yyyy-MM-dd.
            return LocalDate.parse(valor.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
        }
    }

    // Converte a data para dd-MM-yyyy ou retorna vazio quando nula.
    private String formatarData(LocalDate data) {
        if (data == null) {
            return "";
        }

        return data.format(FORMATO_DATA);
    }

    // Envia uma resposta de erro padronizada em formato JSON.
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {

        response.setStatus(status);

        response.getWriter().write(
                "{\"erro\":\""
                        + escape(mensagem == null ? "Erro interno." : mensagem)
                        + "\"}"
        );
    }
}