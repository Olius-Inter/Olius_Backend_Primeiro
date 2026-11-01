package Organizacao.Servlet;

import Organizacao.Dao.PevDAO;
import Organizacao.Model.PevModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "PevSERVLET", value = "/pev")
// atende requisicoes http de ponto de entrega voluntaria
public class PevSERVLET extends HttpServlet {

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private PevDAO pevDAO;

    @Override
    // inicializa o acesso aos dados usado pelas requisicoes
    public void init() throws ServletException {
        pevDAO = new PevDAO();
    }

    @Override
    // consulta os registros de ponto de entrega voluntaria e devolve a resposta json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // consulta os registros e monta a resposta json
        try {
            List<PevModel> pevs = pevDAO.listarPevs();
            StringBuilder json = new StringBuilder("[");

            // percorre os registros e acrescenta cada objeto a resposta json
            for (int i = 0; i < pevs.size(); i++) {
                PevModel pev = pevs.get(i);
                json.append("{")
                        .append("\"id_pev\":").append(pev.getId_pev()).append(",")
                        .append("\"endereco_completo\":").append(jsonString(pev.getEndereco() == null ? "Endereço não informado" :
                                pev.getEndereco().getLogradouro() + ", " + pev.getEndereco().getNumero() + " - " +
                                pev.getEndereco().getBairro() + ", " + pev.getEndereco().getCidade() + "/" + pev.getEndereco().getEstado())).append(",")
                        .append("\"id_usuario\":").append(pev.getId_usuario()).append(",")
                        .append("\"id_endereco\":").append(pev.getId_endereco()).append(",")
                        .append("\"id_usuario_b2b\":").append(pev.getId_usuario_b2b()).append(",")
                        .append("\"id_usuario_b2c\":").append(pev.getId_usuario_b2c()).append(",")
                        .append("\"qr_code\":").append(jsonString(pev.getQr_code())).append(",")
                        .append("\"status\":").append(jsonString(pev.getStatus())).append(",")
                        .append("\"dt_aprovacao\":").append(jsonString(
                                pev.getDt_aprovacao() == null ? null : pev.getDt_aprovacao().toString()))
                        .append("}");

                // acrescenta uma virgula somente entre objetos json
                if (i < pevs.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            // define o status de sucesso e envia a lista json consultada
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.toString());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao listar PEVs: " + e.getMessage());
        }
    }

    @Override
    // valida os dados recebidos e cadastra ponto de entrega voluntaria
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e cadastra ponto de entrega voluntaria
        try {
            PevModel pev = new PevModel(
                    null,
                    null,
                    null,
                    null,
                    request.getParameter("status"),
                    java.time.LocalDate.now()
            );

            pev.setId_usuario(obterParametroIntOpcional(request, "id_usuario"));
            validarStatus(pev);
            pevDAO.salvarComEndereco(pev, EnderecoSERVLET.criarEndereco(request), false);
            // define o status de criacao e confirma o cadastro em json
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"PEV cadastrado com sucesso!\",\"id_pev\":" + pev.getId_pev() + "}");
        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao cadastrar PEV: " + e.getMessage());
        }
    }

    @Override
    // valida os dados recebidos e atualiza ponto de entrega voluntaria
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e atualiza ponto de entrega voluntaria
        try {
            PevModel pev = new PevModel(
                    null,
                    null,
                    null,
                    null,
                    request.getParameter("status"),
                    java.time.LocalDate.now()
            );
            pev.setId_pev(obterParametroInt(request, "id_pev"));
            pev.setId_usuario(obterParametroIntOpcional(request, "id_usuario"));

            validarStatus(pev);
            pevDAO.salvarComEndereco(pev, EnderecoSERVLET.criarEndereco(request), true);
            // define o status de atualizacao e confirma a alteracao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"PEV atualizado com sucesso!\"}");
        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao atualizar PEV: " + e.getMessage());
        }
    }

    @Override
    // valida o identificador e remove ponto de entrega voluntaria
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // valida o identificador da requisicao e remove ponto de entrega voluntaria
        try {
            int idPev = obterParametroInt(request, "id_pev");
            pevDAO.deletarPev(idPev);
            // define o status de exclusao e confirma a remocao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"PEV deletado com sucesso!\"}");
        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao deletar PEV: " + e.getMessage());
        }
    }

    private void validarStatus(PevModel pev) {
        if (!java.util.Arrays.asList("Aprovada", "Negado", "Pendente").contains(pev.getStatus())) {
            throw new IllegalArgumentException("Status do PEV inválido.");
        }
    }

    // define o formato json e a codificacao da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new IllegalArgumentException("Identificador deve ser maior que zero.");
            }
            return numero;
        // converte erro de conversao em uma validacao clara do parametro
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "O parâmetro '" + nome + "' deve ser um número inteiro.", e);
        }
    }

    // valida e converte um parametro opcional para inteiro
    private Integer obterParametroIntOpcional(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) {
            return null;
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

    // valida e converte a data recebida para o tipo de data local


    // converte o valor em texto json com caracteres especiais protegidos
    private String jsonString(String valor) {
        // devolve o valor json nulo quando nao existe texto recebido
        if (valor == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder("\"");
        // percorre os caracteres e acrescenta a sequencia de escape correspondente
        for (int i = 0; i < valor.length(); i++) {
            char caractere = valor.charAt(i);
            // seleciona a sequencia de escape adequada para cada caractere
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
                    // identifica caracteres de controle que exigem escape no json
                    if (caractere < 0x20) {
                        json.append(String.format("\\u%04x", (int) caractere));
                    } else {
                        json.append(caractere);
                    }
            }
        }
        return json.append('"').toString();
    }

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write("{\"erro\":" + jsonString(
                mensagem == null ? "Erro interno." : mensagem) + "}");
    }
}
