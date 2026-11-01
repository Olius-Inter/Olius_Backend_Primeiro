package Organizacao.Servlet;

import Organizacao.Dao.B2bDAO;
import Organizacao.Model.B2bModel;

import Organizacao.Utilitarios.ValidarDocumentos;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/empresas")
// atende requisicoes http de empresa b dois b
public class B2bSERVLET extends HttpServlet {

    // mantem as dependencias e configuracoes usadas nas requisicoes
    private B2bDAO empresasDAO;

    @Override
    // inicializa o acesso aos dados usado pelas requisicoes
    public void init() throws ServletException {
        empresasDAO = new B2bDAO();
    }

    @Override
    // consulta os registros de empresa b dois b e devolve a resposta json
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // consulta os registros e monta a resposta json
        try {
            List<B2bModel> empresas = empresasDAO.listarB2b();
            StringBuilder json = new StringBuilder("[");

            // percorre os registros e acrescenta cada objeto a resposta json
            for (int i = 0; i < empresas.size(); i++) {
                B2bModel empresa = empresas.get(i);
                json.append("{")
                        .append("\"id_usuario\":").append(empresa.getId_usuario()).append(",")
                        .append("\"plano\":\"").append(escape(empresa.getPlano())).append("\",")
                        .append("\"cnpj\":\"").append(escape(empresa.getCnpj())).append("\",")
                        .append("\"razao_social\":\"").append(escape(empresa.getRazao_social())).append("\",")
                        .append("\"nome_empresa\":\"").append(escape(empresa.getNome_empresa())).append("\",")
                        .append("\"telefone\":\"").append(escape(empresa.getTelefone())).append("\",")
                        .append("\"email\":\"").append(escape(empresa.getEmail())).append("\",")
                        .append("\"id_usuario_vinculado\":").append(empresa.getId_usuario_vinculado()).append(",")
                        .append("\"id_endereco\":").append(empresa.getId_endereco())
                        .append("}");

                // acrescenta uma virgula somente entre objetos json
                if (i < empresas.size() - 1) {
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
                    "Erro ao listar empresas B2B: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e cadastra empresa b dois b
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e cadastra empresa b dois b
        if ("PUT".equals(request.getParameter("_metodo"))) {
            doPut(request, response);
            return;
        }
        try {
            B2bModel item = criarEmpresa(request);
            empresasDAO.salvarComEndereco(item, EnderecoSERVLET.criarEndereco(request), false);
            // define o status de criacao e confirma o cadastro em json
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B cadastrada com sucesso!\",\"id_usuario\":" + item.getId_usuario() + "}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao cadastrar empresa B2B: " + mensagem(e));
        }
    }

    @Override
    // valida os dados recebidos e atualiza empresa b dois b
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);
        request.setCharacterEncoding("UTF-8");

        // valida os campos da requisicao e atualiza empresa b dois b
        try {
            int idUsuario = obterId(request);
            B2bModel empresa = criarEmpresa(request);
            empresa.setId_usuario(idUsuario);
            empresasDAO.salvarComEndereco(empresa, EnderecoSERVLET.criarEndereco(request), true);

            // define o status de atualizacao e confirma a alteracao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B atualizada com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao atualizar empresa B2B: " + mensagem(e));
        }
    }

    @Override
    // valida o identificador e remove empresa b dois b
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararResposta(response);

        // valida o identificador da requisicao e remove empresa b dois b
        try {
            empresasDAO.deletarB2b(obterId(request));
            // define o status de exclusao e confirma a remocao em json
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Empresa B2B deletada com sucesso!\"}");

        // devolve erro de requisicao quando os dados recebidos sao invalidos
        } catch (java.util.NoSuchElementException e) {
            enviarErro(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            enviarErro(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        // devolve erro interno quando a operacao da requisicao falha
        } catch (Exception e) {
            enviarErro(response, Organizacao.Conexao.ErroBanco.status(e),
                    "Erro ao deletar empresa B2B: " + mensagem(e));
        }
    }

    // monta os dados da empresa com os campos obrigatorios da requisicao
    private B2bModel criarEmpresa(HttpServletRequest request) {
        String cnpj = obterParametroObrigatorio(request, "cnpj").replaceAll("[.\\-/\\s]", "");
        if (!ValidarDocumentos.validarCnpj(cnpj)) throw new IllegalArgumentException("CNPJ inválido.");
        String telefone = obterParametroObrigatorio(request, "telefone");
        if (telefone.length() > 15) throw new IllegalArgumentException("Telefone deve ter no máximo 15 caracteres.");
        String plano = obterParametroObrigatorio(request, "plano");
        if (!java.util.Arrays.asList("Empresa", "Basico", "Profissional").contains(plano)) throw new IllegalArgumentException("Plano inválido.");
        String nome = obterParametroObrigatorio(request, "nome_empresa");
        if (nome.length() > 255) throw new IllegalArgumentException("Nome da empresa muito longo.");
        B2bModel empresa = new B2bModel(cnpj, nome, nome, telefone, null);
        empresa.setPlano(plano);
        String email = obterParametroObrigatorio(request, "email");
        if (!ValidarDocumentos.validarEmail(email)) throw new IllegalArgumentException("Informe um e-mail válido.");
        if (email.length() > 255) throw new IllegalArgumentException("E-mail muito longo.");
        empresa.setEmail(email);
        empresa.setSenha(request.getParameter("senha"));
        return empresa;
    }

    // valida e converte o identificador recebido na requisicao
    private int obterId(HttpServletRequest request) {
        return obterInteiroObrigatorio(request, "id_usuario", "ID do usuário");
    }

    // valida e converte um parametro obrigatorio para inteiro
    private int obterInteiroObrigatorio(HttpServletRequest request, String nome, String descricao) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(descricao + " é obrigatório.");
        }

        // valida e converte os dados recebidos antes de usa los
        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new IllegalArgumentException("Identificador deve ser maior que zero.");
            }
            return numero;
        // converte erro de conversao em uma validacao clara do parametro
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(descricao + " inválido.", e);
        }
    }

    // valida e devolve um parametro de texto obrigatorio
    private String obterParametroObrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);

        // rejeita a requisicao quando o parametro obrigatorio esta ausente
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro '" + nome + "' é obrigatório.");
        }

        return valor.trim();
    }

    // define o formato json e a codificacao da resposta
    private void prepararResposta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // define o status de erro e devolve uma resposta json
    private void enviarErro(HttpServletResponse response, int status, String mensagem)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write(
                "{\"erro\":\"" + escape(mensagem == null ? "Erro interno." : mensagem) + "\"}"
        );
    }

    // devolve a mensagem da falha ou o nome da excecao
    private String mensagem(Exception e) {
        return e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
    }

    // protege caracteres especiais do texto antes de incluir no json
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
