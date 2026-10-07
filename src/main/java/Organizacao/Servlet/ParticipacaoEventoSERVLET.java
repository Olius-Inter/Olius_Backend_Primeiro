package Organizacao.Servlet;

import Organizacao.Dao.ParticipacaoEventoDAO;
import Organizacao.Model.ParticipacaoEventoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ParticipacaoEventoSERVLET", value = "/participacoes-evento")
public class ParticipacaoEventoSERVLET extends HttpServlet {

    private ParticipacaoEventoDAO participacaoDAO;

    @Override
    public void init() throws ServletException {
        participacaoDAO = new ParticipacaoEventoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<ParticipacaoEventoModel> items = participacaoDAO.listarParticipacoes();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                ParticipacaoEventoModel item = items.get(i);
                json.append("{\"id_participacao\":").append(item.getId_participacao())
                        .append(",\"status\":").append(ServletSupport.json(item.getStatus()))
                        .append(",\"id_b2c\":").append(item.getId_b2c())
                        .append(",\"id_evento\":").append(item.getId_evento()).append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar participações: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            participacaoDAO.inserirParticipacao(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Participação cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar participação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            ParticipacaoEventoModel item = criar(request);
            item.setId_participacao(ServletSupport.requiredInt(request, "id_participacao"));
            participacaoDAO.atualizarParticipacao(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Participação atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar participação: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            participacaoDAO.deletarParticipacao(
                    ServletSupport.requiredInt(request, "id_participacao"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Participação removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover participação: " + ServletSupport.message(e));
        }
    }

    private ParticipacaoEventoModel criar(HttpServletRequest request) {
        return new ParticipacaoEventoModel(
                ServletSupport.requiredText(request, "status"),
                ServletSupport.requiredInt(request, "id_b2c"),
                ServletSupport.requiredInt(request, "id_evento"));
    }
}
