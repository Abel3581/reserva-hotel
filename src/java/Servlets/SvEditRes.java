/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import Logica.Habitacion;
import Logica.Huesped;
import Logica.Reserva;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "SvEditRes", urlPatterns = {"/SvEditRes"})
public class SvEditRes extends HttpServlet {

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
        try {
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
            String fechaCargaHidden = request.getParameter("fechaCargaResHidden");
            int idRes = Integer.parseInt(request.getParameter("idResHidden"));
            
            // Controladora:
            Controladora myContr = new Controladora();
            Reserva resModif = myContr.traerReservaPorId(idRes);
            Habitacion myHab = myContr.traerHabPorTipo(resTipoHabitacion);
            Huesped huesModif = resModif.getResHuesped();
            
            // String to Date:
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date resIn = formatter.parse(resFechaDe);
            Date resOut = formatter.parse(resFechaHasta);
            Date huesNac = formatter.parse(huesFechaNac);
            Date resCarga = new Date();            
            
            // Set:
            resModif.setCantidadPersonas(Integer.parseInt(resCantPersonas));
            resModif.setFechaDe(resIn);
            resModif.setFechaHasta(resOut);
            resModif.setFechaDeCarga(resCarga);
            resModif.setResHabitacion(myHab);
          
            huesModif.setDniHuesped(huesDni);
            huesModif.setNombreHuesped(huesNombre);
            huesModif.setApellidoHuesped(huesApellido);
            huesModif.setFechaNacHuesped(huesNac);
            huesModif.setDireccionHuesped(huesDireccion);
            huesModif.setProfesionHuesped(huesProfesion);
                        
            // Verif y Modificar:
            String myRes = myContr.verifRes(resTipoHabitacion, resFechaDe, resFechaHasta);            
            if(myRes.equals("no")){
                // Set:
                final HttpSession mySess = request.getSession();
                mySess.setAttribute("noDispoMsg", myRes);
                response.sendRedirect("modificacionReserva.jsp");
                // Timeout:
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mySess.setAttribute("noDispoMsg", null);
                    }
                }, 5000L); 
            } else if (myRes.equals("yes")){                
                // Controller:
                myContr.modificarReserva(resModif, huesModif);
                List<Reserva> myList = myContr.reservasPorFecha(fechaCargaHidden);
                // Set:
                HttpSession mySess = request.getSession();
                mySess.setAttribute("reservasPorFecha", myList);
                // Response:
                response.sendRedirect("consultas.jsp");
            }
        } catch (ParseException ex) {
            Logger.getLogger(SvEditRes.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        int resId = Integer.parseInt(request.getParameter("idRes"));
        
        // Controller:
        Controladora myController = new Controladora();
        Reserva myRes = myController.traerReservaPorId(resId);
        // Set:
        HttpSession mySess = request.getSession();
        mySess.setAttribute("myResModif", myRes);
        
        // Response:
        response.sendRedirect("modificacionReserva.jsp");
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
