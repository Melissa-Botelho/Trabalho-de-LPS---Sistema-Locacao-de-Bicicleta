/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerweb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Bicicleta;
import model.dao.BicicletaDAO;

/**
 *
 * @author melisa
 */
@WebServlet(name = "ControllerBicicleta", urlPatterns = {"/ControllerBicicleta"})
public class ControllerBicicleta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerBicicleta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerBicicleta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String forward="";
        String action = request.getParameter("action");

        forward = "/createBicicleta.jsp";

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        boolean result = this.insert(
                request.getParameter("nome"),  
                request.getParameter("modelo"),
                
                Float.parseFloat(request.getParameter("tamanho")), 
                Float.parseFloat(request.getParameter("tempouso")), 
                request.getParameter("cor"),
                request.getParameter("tipopneu"),
                Float.parseFloat(request.getParameter("valor")) 
        );
        
        String forward = "/createBicicleta.jsp";

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public boolean insert(String nome, String modelo, float tamanho, float tempUs, String cor, String tipoP, float valor){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setNome(nome);
        bicicleta.setModelo(modelo);
        bicicleta.setCor(cor);
        bicicleta.setTipPneu(tipoP);
        bicicleta.setTamanho(tamanho);
        bicicleta.setTempUs(tempUs);
        bicicleta.setVarLocacao(valor);
        BicicletaDAO bicicletaD = new BicicletaDAO();
        return bicicletaD.insert(bicicleta);
    }
    
}
