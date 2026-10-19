package Organizacao.Servlet;

import Organizacao.Dao.ColetaDAO;
import Organizacao.Model.ColetaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// atende requisicoes http relacionadas as coletas
@WebServlet(name = "ColetaSERVLET", value = "/coleta")
public class ColetaSERVLET extends HttpServlet {

    private ColetaDAO coletaDAO;

    @Override
    public void init() throws ServletException {
        coletaDAO = new ColetaDAO();
    }

    // lista as coletas e devolve os dados em json
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
                json.append("\"id_coleta\":").append(coleta.getId_coleta()).append(",");
                json.append("\"id_solicitacao\":").append(coleta.getId_solicitacao()).append(",");
                json.append("\"id_motorista\":").append(coleta.getId_motorista()).append(",");
                json.append("\"dt_coleta\":\"").append(coleta.getDt_coleta()).append("\",");
                json.append("\"volume\":").append(coleta.getVolume()).append(",");
                json.append("\"observacao\":\"").append(escape(coleta.getObservacao())).append("\"");
                json.append("}");

                if (i < coletas.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

            // informa falhas ocorridas ao consultar as coletas
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar coletas: " + e.getMessage());
        }
    }


    // valida os dados recebidos e cadastra uma coleta
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            int idSolicitacao = obterParametroInt(request, "id_solicitacao");
            int idMotorista = obterParametroInt(request, "id_motorista");
            LocalDate dtColeta = obterData(request.getParameter("dt_coleta"));
            double volume = obterParametroDouble(request, "volume");
            String observacao = request.getParameter("observacao");

            ColetaModel coleta = new ColetaModel(idSolicitacao, idMotorista, dtColeta, volume, observacao);

            coletaDAO.inserirColeta(coleta);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Coleta cadastrada com sucesso!\"}");

            // informa quando os dados enviados sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante o cadastro
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar coleta: " + e.getMessage());
        }
    }

    // valida os dados recebidos e atualiza uma coleta
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            int idColeta = obterParametroInt(request, "id_coleta");
            int idSolicitacao = obterParametroInt(request, "id_solicitacao");
            int idMotorista = obterParametroInt(request, "id_motorista");
            LocalDate dtColeta = obterData(request.getParameter("dt_coleta"));
            double volume = obterParametroDouble(request, "volume");
            String observacao = request.getParameter("observacao");

            ColetaModel coleta = new ColetaModel(idSolicitacao, idMotorista, dtColeta, volume, observacao);
            coleta.setId_coleta(idColeta);

            coletaDAO.atualizarColeta(coleta);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta atualizada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a atualizacao
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar coleta: " + e.getMessage());
        }
    }

    // valida o identificador e remove uma coleta
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int idColeta = obterParametroInt(request, "id_coleta");
            coletaDAO.deletarColeta(idColeta);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta deletada com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            // informa quando os dados enviados sao invalidos
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // informa falhas ocorridas durante a remocao
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar coleta: " + e.getMessage());
        }
    }

    // valida e converte um parametro obrigatorio para inteiro

    private int obterParametroInt(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número inteiro.", e);
        }
    }

    // valida e converte um parametro obrigatorio para numero decimal

    private double obterParametroDouble(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        try {
            return Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número.", e);
        }
    }

    // valida e converte a data recebida para data local

    private LocalDate obterData(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("A data da coleta é obrigatória.");
        }

        try {
            return LocalDate.parse(valor.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
        }
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

    // monta e envia uma resposta de erro em json
    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }
}