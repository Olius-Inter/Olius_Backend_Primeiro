package Organizacao.Servlet;

import Organizacao.Dao.B2cDAO;
import Organizacao.Model.B2cModel;
import Organizacao.Utilitarios.ValidarDocumentos;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static Organizacao.Utilitarios.ServletUtil.*;

@WebServlet("/b2c")
public class B2cSERVLET extends HttpServlet {

    private B2cDAO b2cDAO;

    @Override
    public void init() {
        b2cDAO = new B2cDAO();
    }

    // Lista os clientes B2C (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<B2cModel> lista = b2cDAO.listarB2c();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                B2cModel c = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_b2c\":").append(c.getIdB2c())
                        .append(",\"id_usuario\":").append(c.getIdUsuario())
                        .append(",\"cpf\":").append(json(c.getCpf())).append("}");
            }
            enviar(resp, 200, json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar clientes B2C: " + mensagem(e));
        }
    }

    // Cadastra um cliente B2C (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            b2cDAO.inserirB2c(criar(req));
            enviarMensagem(resp, 201, "Cliente B2C cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar cliente B2C: " + mensagem(e));
        }
    }

    // Atualiza o CPF de um cliente B2C (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            b2cDAO.atualizarB2c(criar(req));
            enviarMensagem(resp, 200, "Cliente B2C atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar cliente B2C: " + mensagem(e));
        }
    }

    // Remove um cliente B2C pelo id_usuario (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            b2cDAO.deletarB2c(inteiro(req, "id_usuario"));
            enviarMensagem(resp, 200, "Cliente B2C deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar cliente B2C: " + mensagem(e));
        }
    }

    // Valida o CPF (formato e dígitos verificadores) e guarda só os números
    private B2cModel criar(HttpServletRequest req) {
        String cpf = texto(req, "cpf");
        if (!ValidarDocumentos.validarCpf(cpf)) {
            throw new IllegalArgumentException("CPF inválido. Informe 11 dígitos, com ou sem pontuação.");
        }
        return new B2cModel(inteiro(req, "id_usuario"), cpf.replaceAll("\\D", ""));
    }
}
