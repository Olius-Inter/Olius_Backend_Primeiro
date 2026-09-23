package Organizacao.Servlet;

import Organizacao.Dao.ColetaDAO;
import Organizacao.Model.ColetaModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "ColetaSERVLET", value = "/coleta")
public class ColetaSERVLET extends HttpServlet {
    private ColetaDAO coletaDAO;
    @Override
    public void init() throws ServletException {
        coletaDAO = new ColetaDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ColetaModel> coletas = coletaDAO.listarColetas();
        request.setAttribute("coletas", coletas);
        request.getRequestDispatcher("/WEB-INF/jsp/coleta.jsp").forward(request, response);
    }
}