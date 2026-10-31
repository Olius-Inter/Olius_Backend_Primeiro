package Organizacao.Servlet;

import Organizacao.Dao.PontosMovimentacaoDAO;
import Organizacao.Model.PontosMovimentacaoModel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static Organizacao.Utilitarios.ServletUtil.*;

@WebServlet(name = "PontosMovimentacaoSERVLET", value = "/pontos-movimentacao")
public class PontosMovimentacaoSERVLET extends HttpServlet {

    private PontosMovimentacaoDAO movimentacaoDAO;

    @Override
    public void init() {
        movimentacaoDAO = new PontosMovimentacaoDAO();
    }

    // Lista as movimentações (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<PontosMovimentacaoModel> lista = movimentacaoDAO.listarMovimentacoes();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                PontosMovimentacaoModel m = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_movimentacao\":").append(m.getIdMovimentacao())
                        .append(",\"quantidade_pontos\":").append(m.getQuantidadePontos())
                        .append(",\"id_carteira\":").append(m.getIdCarteira())
                        .append(",\"id_coleta\":").append(m.getIdColeta())
                        .append(",\"id_entrega\":").append(m.getIdEntrega()).append("}");
            }
            enviar(resp, 200, json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar movimentações de pontos: " + mensagem(e));
        }
    }

    // Cadastra uma movimentação (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            movimentacaoDAO.inserirMovimentacao(criar(req));
            enviarMensagem(resp, 201, "Movimentação de pontos cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar movimentação de pontos: " + mensagem(e));
        }
    }

    // Atualiza uma movimentação (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            PontosMovimentacaoModel mov = criar(req);
            mov.setIdMovimentacao(inteiro(req, "id_movimentacao"));
            movimentacaoDAO.atualizarMovimentacao(mov);
            enviarMensagem(resp, 200, "Movimentação de pontos atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar movimentação de pontos: " + mensagem(e));
        }
    }

    // Remove uma movimentação (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            movimentacaoDAO.deletarMovimentacao(inteiro(req, "id_movimentacao"));
            enviarMensagem(resp, 200, "Movimentação de pontos deletada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar movimentação de pontos: " + mensagem(e));
        }
    }

    // Valida os parâmetros e monta o modelo (a origem é coleta OU entrega)
    private PontosMovimentacaoModel criar(HttpServletRequest req) {
        Integer idColeta = inteiroOpcional(req, "id_coleta");
        Integer idEntrega = inteiroOpcional(req, "id_entrega");
        if ((idColeta == null) == (idEntrega == null)) {
            throw new IllegalArgumentException("Informe somente um dos campos: id_coleta ou id_entrega.");
        }
        int pontos;
        try {
            pontos = Integer.parseInt(texto(req, "quantidade_pontos"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'quantidade_pontos' deve ser um número inteiro.");
        }
        return new PontosMovimentacaoModel(pontos, inteiro(req, "id_carteira"), idColeta, idEntrega);
    }
}
