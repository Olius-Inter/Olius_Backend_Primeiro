package Organizacao.Servlet;

import Organizacao.Dao.HistoricoDAO;
import Organizacao.Model.HistoricoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HistoricoSERVLET", value = "/historicos")
public class HistoricoSERVLET extends HttpServlet {

    private HistoricoDAO historicoDAO;

    @Override
    public void init() throws ServletException {
        historicoDAO = new HistoricoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<HistoricoModel> items = historicoDAO.listarHistoricos();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                HistoricoModel item = items.get(i);
                json.append("{\"id_historico\":").append(item.getId_historico())
                        .append(",\"id_usuario\":").append(item.getId_usuario())
                        .append(",\"tp_evento\":").append(ServletSupport.json(item.getTp_evento()))
                        .append(",\"descricao\":").append(ServletSupport.json(item.getDescricao()))
                        .append(",\"dt_evento\":").append(ServletSupport.json(
                                item.getDt_evento() == null ? null : item.getDt_evento().toString()))
                        .append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar históricos: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            historicoDAO.inserirHistorico(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Histórico cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar histórico: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            HistoricoModel item = criar(request);
            item.setId_historico(ServletSupport.requiredInt(request, "id_historico"));
            historicoDAO.atualizarHistorico(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Histórico atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar histórico: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            historicoDAO.deletarHistorico(ServletSupport.requiredInt(request, "id_historico"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Histórico removido com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover histórico: " + ServletSupport.message(e));
        }
    }

    private HistoricoModel criar(HttpServletRequest request) {
        return new HistoricoModel(
                ServletSupport.requiredInt(request, "id_usuario"),
                ServletSupport.requiredText(request, "tp_evento"),
                ServletSupport.requiredText(request, "descricao"),
                ServletSupport.requiredDate(request, "dt_evento"));
    }
}
