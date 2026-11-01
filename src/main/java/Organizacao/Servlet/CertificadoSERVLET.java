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
import java.util.List;

@WebServlet(name = "CertificadoSERVLET", value = "/certificado")
// gerencia as requisições de certificados
public class CertificadoSERVLET extends HttpServlet {

    private CertificadoDAO certificadoDAO;

    // prepara o acesso aos dados dos certificados
    @Override
    public void init() throws ServletException {
        certificadoDAO = new CertificadoDAO();
    }

    // lista os certificados em formato json
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            List<CertificadoModel> certificados = certificadoDAO.listarCertificados();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < certificados.size(); i++) {
                CertificadoModel certificado = certificados.get(i);
                json.append("{")
                        .append("\"id_coleta\":").append(certificado.getId_coleta()).append(",")
                        .append("\"id_certificado\":").append(certificado.getId_certificado()).append(",")
                        .append("\"id_usuario_b2b\":").append(certificado.getId_usuario_b2b()).append(",")
                        .append("\"codigo\":").append(jsonString(certificado.getCodigo())).append(",")
                        .append("\"nivel\":").append(jsonString(certificado.getNivel())).append(",")
                        .append("\"volume_total\":").append(Double.isFinite(certificado.getVolume_total()) ? Double.toString(certificado.getVolume_total()) : "null").append(",")
                        .append("\"dt_emissao\":").append(jsonString(
                                certificado.getDt_emissao() == null
                                        ? null : certificado.getDt_emissao().toString())).append(",")
                        .append("\"arquivo_pdf\":").append(jsonString(certificado.getArquivo_pdf()))
                        .append("}");

                if (i < certificados.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao listar certificações: " + mensagem(e));
        }
    }

    // cadastra um novo certificado
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            CertificadoModel item = criarCertificado(request);
            certificadoDAO.inserirCertificado(item);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Certificação cadastrada com sucesso!\",\"id_certificado\":" + item.getId_certificado() + "}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao cadastrar certificação: " + mensagem(e));
        }
    }

    // atualiza os dados de um certificado
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            CertificadoModel certificado = criarCertificado(request);
            certificado.setId_certificado(obterInteiroObrigatorio(request, "id_certificado"));
            certificadoDAO.atualizarCertificado(certificado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Certificação atualizada com sucesso!\"}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao atualizar certificação: " + mensagem(e));
        }
    }

    // remove um certificado pelo identificador
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        try {
            certificadoDAO.deletarCertificado(obterInteiroObrigatorio(request, "id_certificado"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Certificação excluída com sucesso!\"}");
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao excluir certificação: " + mensagem(e));
        }
    }

    // cria um certificado com os dados recebidos
    private CertificadoModel criarCertificado(HttpServletRequest request) {
        CertificadoModel certificado = new CertificadoModel(
                obterInteiroObrigatorio(request, "id_usuario_b2b"),
                null,
                null,
                obterDoubleObrigatorio(request, "volume_total"),
                java.time.LocalDate.now(),
                obterParametroObrigatorio(request, "arquivo_pdf")
        );
        if (certificado.getArquivo_pdf().length() > 255) throw new IllegalArgumentException("Caminho do PDF muito longo.");
        return certificado;
    }

    // valida e converte um parâmetro para número inteiro
    private int obterInteiroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new IllegalArgumentException("Identificador deve ser maior que zero.");
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + nome + "' deve ser um número inteiro.", e);
        }
    }

    // valida e converte um parâmetro para número decimal
    private double obterDoubleObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        try {
            double numero = Double.parseDouble(valor.trim());
            if (Double.isNaN(numero) || Double.isInfinite(numero)) {
                throw new NumberFormatException();
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + nome + "' deve ser um número válido.", e);
        }
    }

    // valida e retorna um parâmetro de texto
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }
        return valor.trim();
    }

    // valida e converte a data de emissão


    // configura o formato e a codificação da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // envia uma resposta de erro em formato json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + jsonString(
                mensagem == null ? "Erro interno." : mensagem) + "}");
    }

    // converte um texto para uma string json segura
    private String jsonString(String valor) {
        if (valor == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder("\"");
        for (int i = 0; i < valor.length(); i++) {
            char caractere = valor.charAt(i);
            switch (caractere) {
                case '"':
                    json.append("\\\"");
                    break;
                case '\\':
                    json.append("\\\\");
                    break;
                case '\b':
                    json.append("\\b");
                    break;
                case '\f':
                    json.append("\\f");
                    break;
                case '\n':
                    json.append("\\n");
                    break;
                case '\r':
                    json.append("\\r");
                    break;
                case '\t':
                    json.append("\\t");
                    break;
                default:
                    if (caractere < 0x20) {
                        json.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        json.append(caractere);
                    }
            }
        }
        return json.append('"').toString();
    }

    // obtém uma mensagem útil da exceção
    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}
