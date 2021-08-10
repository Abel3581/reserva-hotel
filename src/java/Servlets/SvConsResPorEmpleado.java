/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import Logica.Reserva;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
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
@WebServlet(name = "SvConsResPorEmpleado", urlPatterns = {"/SvConsResPorEmpleado"})
public class SvConsResPorEmpleado extends HttpServlet {

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
       // Data:
        String dniIngresado =  request.getParameter("buscador");
        // Controladora:
        Controladora myContr = new Controladora();
        List<Reserva> myList = myContr.traerResPorEmpleadoDni(dniIngresado);
        
        // Set:
        HttpSession mySess = request.getSession();
        mySess.setAttribute("reservasPorEmpleado", myList);
        
        // Response:
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // ====== ARMAMOS JSP ======
        String datePattern = "dd/MM/yyyy";                                
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        if(myList != null){
            if (myList.size() > 0){     
                out.println(
                "<div class='section-title-underline'></div>" +
                "<h2>Resultados</h2>"
                );
                out.println(
                    "<table style='margin-bottom: 2rem;'>" +
                        "<thead>" +
                            "<tr>" +
                              "<th>N° Res </th>" +
                              "<th>Check-in</th>" +
                              "<th>Check-out</th>" +
                              "<th>Habitacion</th>" +
                              "<th>N° Huespedes</th>" +
                              "<th>Huesped Dni</th>" +
                              "<th>Huesped</th>" +
                              "<th>Empleado</th>" +
                            "</tr>" +
                        "</thead>" +                                    
                        "<tbody>" );
                for(Reserva res : myList) {
                    String resIn = dateFormatter.format(res.getFechaDe());
                    String resOut = dateFormatter.format(res.getFechaHasta());
                    out.println(                    
                        "<tr>" +
                            "<td>" + res.getId_reserva() + "</td>" +
                            "<td>" + resIn + "</td>"+
                            "<td>"+ resOut + "</td>"+
                            "<td>" + res.getResHabitacion().getTipo() + "</td>" +
                            "<td>" + res.getCantidadPersonas() + "</td>" +
                            "<td>" + res.getResHuesped().getDniHuesped() + "</td>" +
                            "<td>" + res.getResHuesped().getNombreCompletoHuesped() + "</td>" +
                            "<td>" + res.getResUsuario().getUsuEmpleado().getNombreEmpleado() + "</td>" +
                        "</tr>"
                    );
                }
                out.println(
                        "</tbody>" +
                    "</table>"
                );
            } else {
                out.println("<h3 class="+"buscador-notFound"+">" + "No se encuentran Reservas para el Empleado buscado." + "</h2>");
            }           
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
