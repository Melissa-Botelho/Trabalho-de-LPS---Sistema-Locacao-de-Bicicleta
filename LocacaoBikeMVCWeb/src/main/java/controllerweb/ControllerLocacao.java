/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Locacao;
import model.dao.LocacaoDAO;

/**
 *
 * @author melisa
 */
@WebServlet(name = "ControllerLocacao", urlPatterns = {"/ControllerLocacao"})
public class ControllerLocacao extends HttpServlet {

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
            out.println("<title>Servlet ControllerLocacao</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerLocacao at " + request.getContextPath() + "</h1>");
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

        forward = "/createLocacao.jsp";

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
        Date dataLoc = null;
        Date dataDev = null;
        try{
             dataLoc =  new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("datalocacao"));
             dataDev =  new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("datadevolucao"));
        } catch (ParseException ex) {
             Logger.getLogger(ControllerLocacao.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        boolean result = this.insert(
                Integer.parseInt(request.getParameter("idcliente")),
                Integer.parseInt(request.getParameter("idbicicleta")),
                Float.parseFloat(request.getParameter("horariolocacao")),
                dataLoc,
                Float.parseFloat(request.getParameter("horariodevolucao")),
                dataDev,
                Float.parseFloat(request.getParameter("valor"))
                
        );
        
        String forward = "/createLocacao.jsp";

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
    
    public boolean insert(int idC, int idB, float horarioL, Date dataL, float horarioD, Date dataD, float valor){
        LocacaoDAO locacaoD = new LocacaoDAO();
        Locacao locacao = new Locacao();
        locacao.setIdCliente(idC);
        locacao.setIdBicicleta(idB);
        locacao.setHorarioLoc(horarioL);
        locacao.setDataLoc(dataL);
        locacao.setHorarioDev(horarioD);
        locacao.setDataD(dataD);
        locacao.setValorLoc(valor);
        if(locacao.getIdCliente() == -1 || locacao.getIdLocacao() == -1)
            return false;
        return locacaoD.insert(locacao);
    }

}
