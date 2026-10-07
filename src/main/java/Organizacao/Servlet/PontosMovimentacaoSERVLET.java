package Organizacao.Servlet;

import Organizacao.Dao.PontosMovimentacaoDAO;
import Organizacao.Model.PontosMovimentacaoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PontosMovimentacaoSERVLET", value = "/pontos-movimentacao")
public class PontosMovimentacaoSERVLET extends HttpServlet {

    private PontosMovimentacaoDAO movimentacaoDAO;

    @Override
    public void init() throws ServletException {
        movimentacaoDAO = new PontosMovimentacaoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<PontosMovimentacaoModel> items = movimentacaoDAO.listarPontosMovimentacao();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                PontosMovimentacaoModel item = items.get(i);
                json.append("{\"id_movimentacao\":").append(item.getId_movimentacao())
                        .append(",\"pontos_ganhos\":").append(item.getPontos_ganhos())
                        .append(",\"tipo_movimentacao\":").append(ServletSupport.json(item.getTipo_movimentacao()))
                        .append(",\"dt_movimentacao\":").append(ServletSupport.json(item.getDt_movimentacao()))
                        .append(",\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"id_carteira\":").append(item.getId_carteira())
                        .append(",\"id_participacao\":").append(item.getId_participacao()).append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar movimentações: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            movimentacaoDAO.inserirPontosMovimentacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Movimentação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar movimentação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            PontosMovimentacaoModel item = criar(request);
            item.setId_movimentacao(ServletSupport.requiredInt(request, "id_movimentacao"));
            movimentacaoDAO.atualizarPontosMovimentacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar movimentação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            movimentacaoDAO.deletarPontosMovimentacao(
                    ServletSupport.requiredInt(request, "id_movimentacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Movimentação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover movimentação: " + ServletSupport.message(e));
        }
    }

    private PontosMovimentacaoModel criar(HttpServletRequest request) {
        int points;
        try {
            points = Integer.parseInt(ServletSupport.requiredText(request, "pontos_ganhos"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'pontos_ganhos' deve ser inteiro.", e);
        }
        return new PontosMovimentacaoModel(
                points,
                ServletSupport.requiredText(request, "tipo_movimentacao"),
                ServletSupport.requiredDate(request, "dt_movimentacao").toString(),
                ServletSupport.requiredInt(request, "id_entrega"),
                ServletSupport.requiredInt(request, "id_carteira"),
                ServletSupport.requiredInt(request, "id_participacao"));
    }
}
