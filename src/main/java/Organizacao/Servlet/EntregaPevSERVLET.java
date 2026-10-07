package Organizacao.Servlet;

import Organizacao.Dao.EntregaPevDAO;
import Organizacao.Model.EntregaPevModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EntregaPevSERVLET", value = "/entregas-pev")
public class EntregaPevSERVLET extends HttpServlet {

    private EntregaPevDAO entregaDAO;

    @Override
    public void init() throws ServletException {
        entregaDAO = new EntregaPevDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            List<EntregaPevModel> items = entregaDAO.listarEntregasPev();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < items.size(); i++) {
                EntregaPevModel item = items.get(i);
                json.append("{\"id_entrega\":").append(item.getId_entrega())
                        .append(",\"id_usuario_b2c\":").append(item.getId_usuario_b2c())
                        .append(",\"id_pev\":").append(item.getId_pev())
                        .append(",\"qtd_litros\":").append(item.getQtd_litros())
                        .append(",\"pontos_gerados\":").append(item.getPontos_gerados())
                        .append(",\"dt_entrega\":").append(ServletSupport.json(
                                item.getDt_entrega() == null ? null : item.getDt_entrega().toString()))
                        .append("}");
                if (i < items.size() - 1) json.append(',');
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json.append(']').toString());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao listar entregas PEV: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            entregaDAO.inserirEntregaPev(criar(request));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV cadastrada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao cadastrar entrega PEV: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        ServletSupport.prepareRequest(request);
        try {
            EntregaPevModel item = criar(request);
            item.setId_entrega(ServletSupport.requiredInt(request, "id_entrega"));
            entregaDAO.atualizarEntregaPev(item);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV atualizada com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar entrega PEV: " + ServletSupport.message(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletSupport.prepare(response);
        try {
            entregaDAO.deletarEntregaPev(ServletSupport.requiredInt(request, "id_entrega"));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensagem\":\"Entrega PEV removida com sucesso!\"}");
        } catch (IllegalArgumentException e) {
            ServletSupport.error(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            ServletSupport.error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao remover entrega PEV: " + ServletSupport.message(e));
        }
    }

    private EntregaPevModel criar(HttpServletRequest request) {
        int points;
        try {
            points = Integer.parseInt(ServletSupport.requiredText(request, "pontos_gerados"));
            if (points < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O parâmetro 'pontos_gerados' deve ser inteiro não negativo.", e);
        }
        return new EntregaPevModel(
                ServletSupport.requiredInt(request, "id_usuario_b2c"),
                ServletSupport.requiredInt(request, "id_pev"),
                ServletSupport.requiredNonNegativeDouble(request, "qtd_litros"),
                points,
                ServletSupport.requiredDate(request, "dt_entrega"));
    }
}
