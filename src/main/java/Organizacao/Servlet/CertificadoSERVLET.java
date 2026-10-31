package Organizacao.Servlet;

import Organizacao.Dao.CertificadoDAO;
import Organizacao.Model.CertificadoModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/certificados")
public class CertificadoServlet extends HttpServlet {
    private CertificadoDAO certificadoDAO;

    @Override
    public void init() {
        certificadoDAO = new CertificadoDAO();
    }

    // Lista os certificados emitidos (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        try {
            List<CertificadoModel> lista = certificadoDAO.listarCertificado();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                CertificadoModel certificado = lista.get(i);
                if (i > 0) json.append(",");

                json.append("{\"id_certificado\":").append(certificado.getIdCertificado())
                        .append(",\"volume_total\":").append(certificado.getVolumeTotal())
                        .append(",\"dt_emissao\":\"").append(certificado.getDtEmissao()).append("\"")
                        .append(",\"pdf\":\"").append(escape(certificado.getPdf())).append("\"")
                        .append(",\"id_b2b\":").append(certificado.getIdB2B())
                        .append(",\"id_coleta\":").append(certificado.getIdColeta())
                        .append("}");
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar certificados: " + mensagem(e));
        }
    }

    // Cadastra um novo certificado (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");
        try {
            CertificadoModel certificado = criarCertificado(request);
            certificadoDAO.inserirCertificado(certificado);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Certificado cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar certificado: " + mensagem(e));
        }
    }

    // Atualiza um certificado existente (PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");
        try {
            int idCertificado = inteiro(request, "id_certificado");
            CertificadoModel certificado = criarCertificado(request);
            certificado.setIdCertificado(idCertificado);
            certificadoDAO.atualizarCertificado(certificado);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Certificado atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar certificado: " + mensagem(e));
        }
    }

    // Remove um certificado (DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepararResposta(response);
        try {
            int idCertificado = inteiro(request, "id_certificado");
            certificadoDAO.deletarCertificado(idCertificado);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Certificado deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar certificado: " + mensagem(e));
        }
    }

    private CertificadoModel criarCertificado(HttpServletRequest request) {
        double volumeTotal = decimal(request, "volume_total");
        if (volumeTotal < 0) {
            throw new IllegalArgumentException("O volume total não pode ser negativo.");
        }
        return new CertificadoModel(
                volumeTotal,
                data(request, "dt_emissao"),
                parametro(request, "pdf"),
                inteiro(request, "id_b2b"),
                inteiro(request, "id_coleta")
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
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
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