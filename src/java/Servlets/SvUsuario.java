/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author abel_
 */
@WebServlet(name = "SvUsuario", urlPatterns = {"/SvUsuario"})
public class SvUsuario extends HttpServlet {

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
        processRequest(request, response);
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
        // Get Data:
        String empUsername = request.getParameter("empUsername");
        String empPassword = request.getParameter("empPassword");
        String empDni = request.getParameter("empDni");
        String empNombre = request.getParameter("empNombre");
        String empApellido = request.getParameter("empApellido");
        String empFechaNac = request.getParameter("empFechaNac");
        String empDireccion = request.getParameter("empDireccion");
        String empCargo = request.getParameter("empCargo");
        
        // Controller:
        Controladora control = new Controladora();
        String verifUsu = control.verifUsuario(empUsername);
        if(verifUsu.equals("true")){
            final HttpSession mySess = request.getSession(true);
            mySess.setAttribute("errorMsg", verifUsu);
            new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mySess.setAttribute("errorMsg", null);
                    }
                }, 5000L); 
        } else if (verifUsu.equals("false")){
            control.altaUsuario(empUsername, empPassword, empDni, empNombre, empApellido, empFechaNac, empDireccion, empCargo);
            response.sendRedirect("empleados.jsp");
        }
        
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

}
