package Organizacao.Servlet;

import Organizacao.Dao.EntregaPevDAO;
import Organizacao.Model.EntregaPevModel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static Organizacao.Utilitarios.ServletUtil.*;

@WebServlet(name = "EntregaPevSERVLET", value = "/entregas-pev")
public class EntregaPevSERVLET extends HttpServlet {

    private EntregaPevDAO entregaDAO;

    @Override
    public void init() {
        entregaDAO = new EntregaPevDAO();
    }

    // Lista as entregas (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<EntregaPevModel> lista = entregaDAO.listarEntregas();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                EntregaPevModel e = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_entrega\":").append(e.getIdEntrega())
                        .append(",\"dt_entrega\":").append(json(e.getDtEntrega().toString()))
                        .append(",\"volume_entrega\":").append(e.getVolumeEntrega())
                        .append(",\"id_pev\":").append(e.getIdPev())
                        .append(",\"id_b2c\":").append(e.getIdB2c()).append("}");
            }
            enviar(resp, 200, json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar entregas: " + mensagem(e));
        }
    }

    // Cadastra uma entrega (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            entregaDAO.inserirEntrega(criar(req));
            enviarMensagem(resp, 201, "Entrega cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar entrega: " + mensagem(e));
        }
    }

    // Atualiza uma entrega (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            EntregaPevModel entrega = criar(req);
            entrega.setIdEntrega(inteiro(req, "id_entrega"));
            entregaDAO.atualizarEntrega(entrega);
            enviarMensagem(resp, 200, "Entrega atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar entrega: " + mensagem(e));
        }
    }

    // Remove uma entrega (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            entregaDAO.deletarEntrega(inteiro(req, "id_entrega"));
            enviarMensagem(resp, 200, "Entrega deletada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar entrega: " + mensagem(e));
        }
    }

    // Valida os parâmetros e monta o modelo (data em branco = hoje)
    private EntregaPevModel criar(HttpServletRequest req) {
        return new EntregaPevModel(dataOuHoje(req, "dt_entrega"), decimalPositivo(req, "volume_entrega"),
                inteiro(req, "id_pev"), inteiro(req, "id_b2c"));
    }
}
