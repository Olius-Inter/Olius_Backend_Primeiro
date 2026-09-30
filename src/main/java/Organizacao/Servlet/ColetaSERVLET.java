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

@WebServlet(name = "ColetaSERVLET", value = "/coleta")
// atende requisicoes http de coleta
public class ColetaSERVLET extends HttpServlet {

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private ColetaDAO coletaDAO;

    @Override
    // inicializa o acesso aos dados usado pelas requisicoes
    public void init() throws ServletException {
        coletaDAO = new ColetaDAO();
    }

    @Override
    // consulta os registros de coleta e devolve a resposta json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // consulta os registros e monta a resposta json
        try {
            List<ColetaModel> coletas = coletaDAO.listarColetas();
            StringBuilder json = new StringBuilder();
            json.append("[");

            // percorre os registros e acrescenta cada objeto a resposta json
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

                // acrescenta uma virgula somente entre objetos json
                if (i < coletas.size() - 1) {
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
                    "Erro ao listar coletas: " + e.getMessage());
        }
    }


    @Override
    // valida os dados recebidos e cadastra coleta
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e cadastra coleta
        try {
            int idSolicitacao = obterParametroInt(request, "id_solicitacao");
            int idMotorista = obterParametroInt(request, "id_motorista");
            LocalDate dtColeta = obterData(request.getParameter("dt_coleta"));
            double volume = obterParametroDouble(request, "volume");
            String observacao = request.getParameter("observacao");

            ColetaModel coleta = new ColetaModel(idSolicitacao, idMotorista, dtColeta, volume, observacao);

            coletaDAO.inserirColeta(coleta);
            // define o status de criacao e confirma o cadastro em json
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Coleta cadastrada com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar coleta: " + e.getMessage());
        }
    }

    @Override
    // valida os dados recebidos e atualiza coleta
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e atualiza coleta
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
            // define o status de atualizacao e confirma a alteracao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta atualizada com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar coleta: " + e.getMessage());
        }
    }

    @Override
    // valida o identificador e remove coleta
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // valida o identificador da requisicao e remove coleta
        try {
            int idColeta = obterParametroInt(request, "id_coleta");
            coletaDAO.deletarColeta(idColeta);
            // define o status de exclusao e confirma a remocao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Coleta deletada com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar coleta: " + e.getMessage());
        }
    }


    // valida e converte um parametro obrigatorio para inteiro
    private int obterParametroInt(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        // converte o texto do identificador para inteiro
        try {
            // devolve o valor validado e convertido
            return Integer.parseInt(valor.trim());
        // converte erro de conversao em uma validacao clara do parametro
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número inteiro.", e);
        }
    }


    // valida e converte um parametro obrigatorio para decimal
    private double obterParametroDouble(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        // converte o texto do parametro para decimal
        try {
            // devolve o valor validado e convertido
            return Double.parseDouble(valor.trim());
        // converte erro de conversao em uma validacao clara do parametro
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número.", e);
        }
    }


    // valida e converte a data recebida para o tipo de data local
    private LocalDate obterData(String valor) {
        // rejeita a requisicao quando a data obrigatoria esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("A data da coleta é obrigatória.");
        }

        // interpreta o texto recebido como data local
        try {
            // devolve o valor validado e convertido
            return LocalDate.parse(valor.trim());
        // informa que o texto recebido nao representa uma data valida
        } catch (Exception e) {
            throw new IllegalArgumentException("Data inválida. Use o formato yyyy-MM-dd.", e);
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

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }
}