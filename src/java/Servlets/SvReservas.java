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
@WebServlet(name = "SvReservas", urlPatterns = {"/SvReservas"})
public class SvReservas extends HttpServlet {

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
            String resTipoHabitacion = request.getParameter("res-tipoHabitacion");
            String resCantPersonas = request.getParameter("res-cantPersonas");
            String resFechaDe = request.getParameter("res-fechaDe");
            String resFechaHasta = request.getParameter("res-fechaHasta");
            String huesDni = request.getParameter("hues-dni");
            String huesNombre = request.getParameter("hues-nombre");
            String huesApellido = request.getParameter("hues-apellido");
            String huesFechaNac = request.getParameter("hues-fechaNac");
            String huesDireccion = request.getParameter("hues-direccion");
            String huesProfesion = request.getParameter("hues-profesion");
            String usuUsername = (String) request.getSession().getAttribute("usuUsername");

            // Controladora:
            Controladora myContr = new Controladora();
            String myRes = myContr.verifRes(resTipoHabitacion, resFechaDe, resFechaHasta);
            if(myRes.equals("no")){
                // Set:
                final HttpSession mySess = request.getSession();
                mySess.setAttribute("noDispoMsg", myRes);
                response.sendRedirect("reservas.jsp");
                // Timeout:
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mySess.setAttribute("noDispoMsg", null);
                    }
                }, 5000L); 
            } else if (myRes.equals("yes")){
            myContr.crearReserva(resTipoHabitacion, resCantPersonas, resFechaDe, resFechaHasta, huesDni, huesNombre, huesApellido, huesFechaNac, huesDireccion, huesProfesion, usuUsername);
            // Response:
            response.sendRedirect("confirmacionReserva.jsp");
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
