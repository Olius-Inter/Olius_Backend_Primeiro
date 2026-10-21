package Organizacao.Servlet;

import Organizacao.Dao.MotoristaDAO;
import Organizacao.Model.MotoristaModel;
import Organizacao.Model.Enum.StatusMotorista;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/motoristas")
// gerencia requisicoes http para motoristas de coleta
public class MotoristaServlet extends HttpServlet {

    private MotoristaDAO motoristaDAO;
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\d{10,11}$");

    @Override
    public void init() throws ServletException {
        motoristaDAO = new MotoristaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            List<MotoristaModel> motoristas = motoristaDAO.listarMotorista();
            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < motoristas.size(); i++) {
                MotoristaModel m = motoristas.get(i);
                json.append("{")
                        .append("\"id_motorista\":").append(m.getIdMotorista()).append(",")
                        .append("\"status\":\"").append(m.getStatus()).append("\",")
                        .append("\"empresa_resp\":\"").append(escape(m.getEmpresaResp())).append("\",")
                        .append("\"telefone\":\"").append(escape(m.getTelefone())).append("\",")
                        .append("\"nome\":\"").append(escape(m.getNome())).append("\",")
                        .append("\"licenca\":\"").append(escape(m.getLicenca())).append("\"")
                        .append("}");

                if (i < motoristas.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());

        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar motoristas: " + mensagem(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            MotoristaModel novoMotorista = criarMotorista(request);
            motoristaDAO.inserirMotorista(novoMotorista);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Motorista cadastrado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar motorista: " + mensagem(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        try {
            int idMotorista = obterInteiroObrigatorio(request, "id_motorista", "ID do motorista");
            MotoristaModel motorista = criarMotorista(request);
            motorista.setIdMotorista(idMotorista);

            motoristaDAO.atualizarMotorista(motorista);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Motorista atualizado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar motorista: " + mensagem(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararResposta(response);

        try {
            int id = obterInteiroObrigatorio(request, "id_motorista", "ID do motorista");
            motoristaDAO.deletarMotorista(id);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Motorista deletado com sucesso!\"}");

        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            enviarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao deletar motorista: " + mensagem(e));
        }
    }

    private MotoristaModel criarMotorista(HttpServletRequest request) {
        String telefone = obterParametroObrigatorio(request, "telefone");
        if (!TELEFONE_PATTERN.matcher(telefone).matches()) {
            throw new IllegalArgumentException("Telefone do motorista inválido.");
        }

        String statusStr = obterParametroObrigatorio(request, "status");
        StatusMotorista status;
        try {
            status = StatusMotorista.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status do motorista inválido (Valores aceitos: Disponivel, Ocupado).");
        }

        return new MotoristaModel(
                0,
                status,
                obterParametroObrigatorio(request, "empresa_resp"),
                telefone,
                obterParametroObrigatorio(request, "nome"),
                obterParametroObrigatorio(request, "licenca")
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