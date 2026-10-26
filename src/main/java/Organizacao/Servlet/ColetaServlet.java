package Organizacao.Servlet;

import Organizacao.Dao.ColetaDAO;
import Organizacao.Model.ColetaModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/coletas")
public class ColetaServlet extends HttpServlet {

    private ColetaDAO coletaDAO;

    @Override
    public void init() {
        coletaDAO = new ColetaDAO();
    }

    // Listar coletas (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<ColetaModel> lista = coletaDAO.listarColeta();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                ColetaModel c = lista.get(i);
                if (i > 0) json.append(",");

                json.append("{\"id_coleta\":").append(c.getIdColeta())
                        .append(",\"dt_coleta\":\"").append(c.getDtColeta())
                        .append("\",\"volume_coletado\":").append(c.getVolumeColetado())
                        .append(",\"avaliacao_coleta\":").append(c.getAvaliacaoColeta())
                        .append(",\"id_solicitacao\":").append(c.getIdSolicitacao())
                        .append(",\"id_motorista\":").append(c.getIdMotorista())
                        .append(",\"id_b2b\":").append(c.getIdB2B())
                        .append("}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar coletas: " + mensagem(e));
        }
    }

    // Cadastrar nova coleta (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            coletaDAO.inserirColeta(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Coleta cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar coleta: " + mensagem(e));
        }
    }

    // Atualizar registro de coleta (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_coleta");
            ColetaModel c = criar(req);
            c.setIdColeta(id);
            coletaDAO.atualizarColeta(c);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Coleta atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar coleta: " + mensagem(e));
        }
    }

    // Remover coleta (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            coletaDAO.deletarColeta(inteiro(req, "id_coleta"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Coleta deletada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar coleta: " + mensagem(e));
        }
    }

    // Validação de dados da coleta
    private ColetaModel criar(HttpServletRequest req) {
        double volume = decimal(req, "volume_coletado");
        if (volume <= 0) {
            throw new IllegalArgumentException("O volume coletado deve ser maior que zero.");
        }

        int avaliacao = inteiro(req, "avaliacao_coleta");
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5.");
        }

        return new ColetaModel(
                data(req, "dt_coleta"), volume, avaliacao,
                inteiro(req, "id_solicitacao"), inteiro(req, "id_motorista"), inteiro(req, "id_b2b")
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

    private double decimal(HttpServletRequest request, String nome) {
        try {
            double valor = Double.parseDouble(parametro(request, nome));
            if (!Double.isFinite(valor)) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' deve ser um número válido.");
        }
    }

    private java.time.LocalDate data(HttpServletRequest request, String nome) {
        try {
            return java.time.LocalDate.parse(parametro(request, nome));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida no parâmetro '" + nome + "'. Use AAAA-MM-DD.");
        }
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}