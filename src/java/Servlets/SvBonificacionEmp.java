/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import Logica.Empleado;
import Logica.Reserva;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author abel_
 */
@WebServlet(name = "SvBonificacionEmp", urlPatterns = {"/SvBonificacionEmp"})
public class SvBonificacionEmp extends HttpServlet {

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
        // Get:
        String dniEmp = request.getParameter("dniEmp");
        String fechaEmp = request.getParameter("fechaEmp");
        
        // Control:
        Controladora myController = new Controladora();
        Empleado myEmp = myController.traerEmpleadoPorDni(dniEmp);
        String empleadoName = myEmp.getNombreCompletoEmpleado();
        List<Reserva> myRes = myController.reservasPorFecha(fechaEmp);
        List<Reserva> listaFinal = new ArrayList<>();
        for(Reserva res : myRes){
            if(res.getResUsuario().getUsuEmpleado().getDniEmpleado().equals(dniEmp)){
                listaFinal.add(res);
            }
        }
        
        // Set:
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // ====== ARMAMOS JSP ======
        String datePattern = "dd/MM/yyyy";                                
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        
            if (listaFinal.size() > 0){     
                out.println(
                "<div class='section-title-underline'></div>" +
                "<h2>Resultados</h2>"
                );
                out.println(
                "<h2 style='color: crimson; margin-top: 0rem;'> Empleado: "+ empleadoName + " </h2>"
                );
                out.println(
                "<h2 style='color: crimson; margin-top: 0rem;'> Cantidad Res: "+ listaFinal.size() + " </h2>"
                );
                out.println(
                    "<table style='margin-bottom: 2rem;'>" +
                        "<thead>" +
                            "<tr>" +
                              "<th>N° Res</th>" +
                              "<th>Check-in</th>" +
                              "<th>Check-out</th>" +
                              "<th>Habitacion</th>" +
                              "<th>Huesped</th>" +
                              "<th>Cant Noches</th>" +
                              "<th>Dni Empleado</th>" +
                              "<th>Empleado</th>" +
                            "</tr>" +
                        "</thead>" +                                    
                        "<tbody>" );
                for(Reserva res : listaFinal) {
                    String resIn = dateFormatter.format(res.getFechaDe());
                    String resOut = dateFormatter.format(res.getFechaHasta());
                    out.println(                    
                        "<tr>" +
                            "<td>" + res.getId_reserva() + "</td>" +
                            "<td>" + resIn + "</td>"+
                            "<td>"+ resOut + "</td>"+
                            "<td>" + res.getResHabitacion().getTipo() + "</td>" +
                            "<td>" + res.getResHuesped().getNombreCompletoHuesped() + "</td>" +
                            "<td>" + res.getCantidadNoches() + "</td>" +
                            "<td>" + res.getResUsuario().getUsuEmpleado().getDniEmpleado() + "</td>" +
                            "<td>" + empleadoName + "</td>" +
                        "</tr>"
                    );
                }
                out.println(
                        "</tbody>" +
                    "</table>"
                );
            } else {
                out.println("<h3 class="+"buscador-notFound"+">" + "No se encuentran Reservas para la Fecha elegida." + "</h2>");
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
        processRequest(request, response);
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
