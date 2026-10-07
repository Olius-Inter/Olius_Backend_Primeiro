package Organizacao.Servlet;

import Organizacao.Dao.PontuacaoDAO;
import Organizacao.Model.PontuacaoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PontuacaoSERVLET", value = "/pontuacao")
public class PontuacaoSERVLET extends HttpServlet {

    private PontuacaoDAO pontuacaoDAO;

    @Override
    public void init() throws ServletException {
        pontuacaoDAO = new PontuacaoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<PontuacaoModel> items = pontuacaoDAO.listarPontuacoes();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                PontuacaoModel item = items.get(i);
                json.append("{\"id_pontuacao\":").append(item.getId_pontuacao())
                        .append(",\"id_usuario_b2c\":").append(item.getId_usuario_b2c())
                        .append(",\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"pontos\":").append(item.getPontos())
                        .append(",\"tp_movimentacao\":").append(ServletSupport.json(item.getTp_movimentacao()))
                        .append(",\"descricao\":").append(ServletSupport.json(item.getDescricao()))
                        .append(",\"dt_movimentacao\":").append(ServletSupport.json(
                                item.getDt_movimentacao() == null ? null : item.getDt_movimentacao().toString()))
                        .append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar pontuações: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            pontuacaoDAO.inserirPontuacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Pontuação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar pontuação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            PontuacaoModel item = criar(request);
            item.setId_pontuacao(ServletSupport.requiredInt(request, "id_pontuacao"));
            pontuacaoDAO.atualizarPontuacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Pontuação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar pontuação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            pontuacaoDAO.deletarPontuacao(ServletSupport.requiredInt(request, "id_pontuacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Pontuação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover pontuação: " + ServletSupport.message(e));
        }
    }

    private PontuacaoModel criar(HttpServletRequest request) {
        int pontos;
        try {
            pontos = Integer.parseInt(ServletSupport.requiredText(request, "pontos"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'pontos' deve ser inteiro.", e);
        }
        return new PontuacaoModel(
                ServletSupport.requiredInt(request, "id_usuario_b2c"),
                ServletSupport.requiredInt(request, "id_entrega"),
                pontos,
                ServletSupport.requiredText(request, "tp_movimentacao"),
                ServletSupport.requiredText(request, "descricao"),
                ServletSupport.requiredDate(request, "dt_movimentacao")
        );
    }
}
