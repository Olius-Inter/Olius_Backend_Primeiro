package Organizacao.Servlet;

import Organizacao.Dao.CarteiraDAO;
import Organizacao.Model.CarteiraModel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static Organizacao.Utilitarios.ServletUtil.*;

@WebServlet(name = "CarteiraSERVLET", value = "/carteira")
public class CarteiraSERVLET extends HttpServlet {

    private static final Set<String> PATENTES = Set.of("Ouro", "Prata", "Bronze");
    private CarteiraDAO carteiraDAO;

    @Override
    public void init() {
        carteiraDAO = new CarteiraDAO();
    }

    // Lista as carteiras (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            List<CarteiraModel> lista = carteiraDAO.listarCarteiras();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                CarteiraModel c = lista.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id_carteira\":").append(c.getIdCarteira())
                        .append(",\"pontos_totais\":").append(c.getPontosTotais())
                        .append(",\"patente\":").append(json(c.getPatente()))
                        .append(",\"nivel\":").append(c.getNivel())
                        .append(",\"id_b2c\":").append(c.getIdB2c())
                        .append(",\"id_b2b\":").append(c.getIdB2b()).append("}");
            }
            enviar(resp, 200, json.append("]").toString());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao listar carteiras: " + mensagem(e));
        }
    }

    // Cadastra uma carteira (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            carteiraDAO.inserirCarteira(criar(req));
            enviarMensagem(resp, 201, "Carteira cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao cadastrar carteira: " + mensagem(e));
        }
    }

    // Atualiza uma carteira (PUT)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        req.setCharacterEncoding("UTF-8");
        try {
            CarteiraModel carteira = criar(req);
            carteira.setIdCarteira(inteiro(req, "id_carteira"));
            carteiraDAO.atualizarCarteira(carteira);
            enviarMensagem(resp, 200, "Carteira atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao atualizar carteira: " + mensagem(e));
        }
    }

    // Remove uma carteira (DELETE)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        prepararResposta(resp);
        try {
            carteiraDAO.deletarCarteira(inteiro(req, "id_carteira"));
            enviarMensagem(resp, 200, "Carteira deletada com sucesso!");
        } catch (IllegalArgumentException e) {
            enviarErro(resp, 400, e.getMessage());
        } catch (Exception e) {
            enviarErro(resp, 500, "Erro ao deletar carteira: " + mensagem(e));
        }
    }

    // Valida os parâmetros e monta o modelo
    private CarteiraModel criar(HttpServletRequest req) {
        String patente = texto(req, "patente");
        if (!PATENTES.contains(patente)) {
            throw new IllegalArgumentException("Patente inválida. Valores aceitos: Ouro, Prata ou Bronze.");
        }
        Integer idB2c = inteiroOpcional(req, "id_b2c");
        Integer idB2b = inteiroOpcional(req, "id_b2b");
        if ((idB2c == null) == (idB2b == null)) {
            throw new IllegalArgumentException("Informe somente um dos campos: id_b2c (cliente) ou id_b2b (empresa).");
        }
        return new CarteiraModel(inteiroNaoNegativo(req, "pontos_totais"), patente,
                inteiro(req, "nivel"), idB2c, idB2b);
    }
}
