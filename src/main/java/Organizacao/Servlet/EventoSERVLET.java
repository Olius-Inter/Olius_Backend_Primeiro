package Organizacao.Servlet;

import Organizacao.Dao.EventoDAO;
import Organizacao.Model.EventoModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "EventoSERVLET", value = "/eventos")
public class EventoSERVLET extends HttpServlet {

    private EventoDAO eventoDAO;

    @Override
    public void init() throws ServletException {
        eventoDAO = new EventoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<EventoModel> items = eventoDAO.listarEventos();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                EventoModel item = items.get(i);
                json.append("{\"id_evento\":").append(item.getId_evento())
                        .append(",\"nome\":").append(ServletSupport.json(item.getNome()))
                        .append(",\"descricao\":").append(ServletSupport.json(item.getDescricao()))
                        .append(",\"dt_finalizacao\":").append(ServletSupport.json(item.getDt_finalizacao()))
                        .append(",\"dt_inicio\":").append(ServletSupport.json(item.getDt_inicio()))
                        .append(",\"id_b2b\":").append(item.getId_b2b()).append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar eventos: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            eventoDAO.inserirEvento(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Evento cadastrado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar evento: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            EventoModel item = criar(request);
            item.setId_evento(ServletSupport.requiredInt(request, "id_evento"));
            eventoDAO.atualizarEvento(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Evento atualizado com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar evento: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            eventoDAO.deletarEvento(ServletSupport.requiredInt(request, "id_evento"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Evento removido com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover evento: " + ServletSupport.message(e));
        }
    }

    private EventoModel criar(HttpServletRequest request) {
        String inicio = ServletSupport.requiredDate(request, "dt_inicio").toString();
        String finalizacao = ServletSupport.requiredDate(request, "dt_finalizacao").toString();
        if (LocalDate.parse(finalizacao).isBefore(LocalDate.parse(inicio))) {
            throw new IllegalArgumentException("A data de finalização não pode anteceder a data de início.");
        }
        return new EventoModel(
                ServletSupport.requiredText(request, "nome"),
                ServletSupport.requiredText(request, "descricao"),
                finalizacao,
                inicio,
                ServletSupport.requiredInt(request, "id_b2b"));
    }
}
