package Organizacao.Servlet;

import Organizacao.Dao.CertificadoDAO;
import Organizacao.Model.CertificadoModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/certificados")
// gerencia requisicoes http para emissao e consulta de certificados
public class CertificadoServlet extends HttpServlet {

    private CertificadoDAO certificadoDAO;

    @Override
    public void init() throws ServletException {
        certificadoDAO = new CertificadoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<CertificadoModel> lista = certificadoDAO.listarCertificado();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < lista.size(); i++) {
                CertificadoModel c = lista.get(i);
                json.append("{")
                        .append("\"id_certificado\":").append(c.getIdCertificado()).append(",")
                        .append("\"volume_total\":").append(c.getVolumeTotal()).append(",")
                        .append("\"dt_emissao\":\"").append(c.getDtEmissao()).append("\",")
                        .append("\"pdf\":\"").append(escape(c.getPdf())).append("\",")
                        .append("\"id_b2b\":").append(c.getIdB2B()).append(",")
                        .append("\"id_coleta\":").append(c.getIdColeta())
                        .append("}");

                if (i < lista.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar certificados: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            CertificadoModel novoCertificado = criarCertificado(request);
            certificadoDAO.inserirCertificado(novoCertificado);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Certificado cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar certificado: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idCertificado = obterInteiroObrigatorio(request, "id_certificado", "ID do certificado");
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_certificado", "ID do certificado");
            certificadoDAO.deletarCertificado(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Certificado deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar certificado: " + mensagem(e));
        }
    }

    private CertificadoModel criarCertificado(HttpServletRequest request) throws NumberFormatException, IllegalArgumentException {
        double volume;
        volume = Double.parseDouble(obterParametroObrigatorio(request, "volume_total"));
        if (volume < 0) {
            throw new IllegalArgumentException();
        }

        LocalDate dataEmissao;
        try {
            dataEmissao = LocalDate.parse(obterParametroObrigatorio(request, "dt_emissao"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de emissão inválida. Use o formato AAAA-MM-DD.");
        }

        return new CertificadoModel(
                0,
                volume,
                dataEmissao,
                obterParametroObrigatorio(request, "pdf"),
                obterInteiroObrigatorio(request, "id_b2b", "ID da empresa B2B"),
                obterInteiroObrigatorio(request, "id_coleta", "ID da coleta")
        );
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

    private void enviarErro(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}");
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
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
}