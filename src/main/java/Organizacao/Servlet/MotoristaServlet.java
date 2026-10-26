package Organizacao.Servlet;

import Organizacao.Dao.MotoristaDAO;
import Organizacao.Model.MotoristaModel;
import Organizacao.Model.Enum.StatusMotorista;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/motoristas")
public class MotoristaServlet extends HttpServlet {
    private MotoristaDAO motoristaDAO;

    @Override
    public void init() {
        motoristaDAO = new MotoristaDAO();
    }

    // Lista os motoristas (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<MotoristaModel> lista = motoristaDAO.listarMotorista();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                MotoristaModel m = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_motorista\":").append(m.getIdMotorista())
                        .append(",\"status\":\"").append(escape(m.getStatus().name()))
                        .append("\",\"empresa_resp\":\"").append(escape(m.getEmpresaResp()))
                        .append("\",\"telefone\":\"").append(escape(m.getTelefone()))
                        .append("\",\"nome\":\"").append(escape(m.getNome()))
                        .append("\",\"licenca\":\"").append(escape(m.getLicenca())).append("\"}");
            }
            resp.setStatus(200);
            resp.getWriter().write(json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar motoristas: " + mensagem(e));
        }
    }

    // Cadastra um motorista (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            MotoristaModel m = criar(req);
            motoristaDAO.inserirMotorista(m);
            resp.setStatus(201);
            resp.getWriter().write("{\"mensagem\":\"Motorista cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar motorista: " + mensagem(e));
        }
    }

    // Atualiza dados do motorista (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            int id = inteiro(req, "id_motorista");
            MotoristaModel m = criar(req);
            m.setIdMotorista(id);
            motoristaDAO.atualizarMotorista(m);
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Motorista atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar motorista: " + mensagem(e));
        }
    }

    // Remove um motorista (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            motoristaDAO.deletarMotorista(inteiro(req, "id_motorista"));
            resp.setStatus(200);
            resp.getWriter().write("{\"mensagem\":\"Motorista deletado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar motorista: " + mensagem(e));
        }
    }

    private MotoristaModel criar(HttpServletRequest req) {
        StatusMotorista status = enumeracao(StatusMotorista.class, req, "status");
        String empresa = parametro(req, "empresa_resp");
        String telefone = parametro(req, "telefone");
        String nome = parametro(req, "nome");
        String licenca = parametro(req, "licenca");

        if (telefone.length() > 15) throw new IllegalArgumentException("Telefone deve ter no máximo 15 caracteres.");
        if (licenca.length() > 50) throw new IllegalArgumentException("Licença deve ter no máximo 50 caracteres.");
        return new MotoristaModel(status, empresa, telefone, nome, licenca);
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