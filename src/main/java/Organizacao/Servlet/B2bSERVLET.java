package Organizacao.Servlet;

import Organizacao.Dao.B2BDAO;
import Organizacao.Model.B2BModel;
import Organizacao.Model.Enum.Plano;
import Organizacao.Utilitarios.ValidarDocumentos;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/b2b")
public class B2BServlet extends HttpServlet {
    private B2BDAO b2bDAO;

    @Override
    public void init() {
        b2bDAO = new B2BDAO();
    }

    // Lista as empresas B2B (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<B2BModel> lista = b2bDAO.listarB2B();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                B2BModel b = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_b2b\":").append(b.getIdB2B())
                        .append(",\"cnpj\":\"").append(escape(b.getCnpj()))
                        .append("\",\"plano\":\"").append(b.getPlano().name())
                        .append("\",\"id_usuario\":").append(b.getIdUsuario()).append("}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar empresas B2B: " + mensagem(e));
        }
    }

    // Cadastra uma empresa B2B (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            b2bDAO.inserirB2B(criar(req));
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Empresa B2B cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar B2B: " + mensagem(e));
        }
    }

    // Atualiza dados da empresa B2B (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_b2b");
            B2BModel b = criar(req);
            b.setIdB2B(id);
            b2bDAO.atualizarB2B(b);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Empresa B2B atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar B2B: " + mensagem(e));
        }
    }

    // Remove uma empresa B2B (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            b2bDAO.deletarB2B(inteiro(req, "id_b2b"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Empresa B2B deletada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar B2B: " + mensagem(e));
        }
    }

    // Valida o CNPJ e cria o modelo B2B
    private B2BModel criar(HttpServletRequest req) {
        String cnpj = parametro(req, "cnpj").replaceAll("[.\\-/\\s]", "");
        if (!ValidarDocumentos.validarCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }
        return new B2BModel(cnpj, enumeracao(Plano.class, req, "plano"), inteiro(req, "id_usuario"));
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

    private <E extends Enum<E>> E enumeracao(Class<E> tipo, HttpServletRequest request, String nome) {
        try {
            return Enum.valueOf(tipo, parametro(request, nome));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para '" + nome + "'. Valores aceitos: "
                    + java.util.Arrays.toString(tipo.getEnumConstants()));
        }
    }

    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }
}