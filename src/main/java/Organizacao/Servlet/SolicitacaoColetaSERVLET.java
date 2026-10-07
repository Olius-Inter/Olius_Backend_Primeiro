package Organizacao.Servlet;

import Organizacao.Dao.SolicitacaoColetaDAO;
import Organizacao.Model.SolicitacaoColetaModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SolicitacaoColetaSERVLET", value = "/solicitacoes-coleta")
public class SolicitacaoColetaSERVLET extends HttpServlet {

    private SolicitacaoColetaDAO solicitacaoDAO;

    @Override
    public void init() throws ServletException {
        solicitacaoDAO = new SolicitacaoColetaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<SolicitacaoColetaModel> items = solicitacaoDAO.listarSolicitacoes();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                SolicitacaoColetaModel item = items.get(i);
                json.append("{\"id_solicitacao\":").append(item.getId_solicitacao())
                        .append(",\"litros_estimados\":").append(item.getLitros_estimados())
                        .append(",\"dt_solicitacao\":").append(ServletSupport.json(item.getDt_solicitacao()))
                        .append(",\"status\":").append(ServletSupport.json(item.getStatus()))
                        .append(",\"id_b2b\":").append(item.getId_b2b())
                        .append(",\"id_pev\":").append(item.getId_pev()).append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar solicitações: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            solicitacaoDAO.inserirSolicitacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Solicitação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar solicitação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            SolicitacaoColetaModel item = criar(request);
            item.setId_solicitacao(ServletSupport.requiredInt(request, "id_solicitacao"));
            solicitacaoDAO.atualizarSolicitacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Solicitação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar solicitação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            solicitacaoDAO.deletarSolicitacao(
                    ServletSupport.requiredInt(request, "id_solicitacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Solicitação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover solicitação: " + ServletSupport.message(e));
        }
    }

    private SolicitacaoColetaModel criar(HttpServletRequest request) {
        String date = ServletSupport.requiredDate(request, "dt_solicitacao").toString();
        return new SolicitacaoColetaModel(
                ServletSupport.requiredNonNegativeDouble(request, "litros_estimados"),
                date,
                ServletSupport.requiredText(request, "status"),
                ServletSupport.requiredInt(request, "id_b2b"),
                ServletSupport.requiredInt(request, "id_pev"));
    }
}
