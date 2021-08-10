/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import Logica.Huesped;
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
@WebServlet(name = "SvConsListaHuespedes", urlPatterns = {"/SvConsListaHuespedes"})
public class SvConsListaHuespedes extends HttpServlet {

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
         // Controladora:
        Controladora myContr = new Controladora();
        List<Huesped> myList = myContr.traerHuespedes();
        
        // Set:
        HttpSession mySess = request.getSession();
        mySess.setAttribute("listaHuespedes", myList);
        
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
                              "<th>DNI</th>" +
                              "<th>Nombre</th>" +
                              "<th>Apellido</th>" +
                              "<th>Fecha Nac</th>" +
                              "<th>Direccion</th>" +
                              "<th>Profesion</th>" +
                            "</tr>" +
                        "</thead>" +                                    
                        "<tbody>" );
                for(Huesped hues : myList) {
                    String huesNac = dateFormatter.format(hues.getFechaNacHuesped());
                    out.println(                    
                        "<tr>" +
                            "<td>" + hues.getDniHuesped() + "</td>" +
                            "<td>" + hues.getNombreHuesped() + "</td>"+
                            "<td>" + hues.getApellidoHuesped() + "</td>" +
                            "<td>" + huesNac + "</td>" +
                            "<td>" + hues.getDireccionHuesped() + "</td>" +
                            "<td>" + hues.getProfesionHuesped() + "</td>" +
                        "</tr>"
                    );
                }
                out.println(
                        "</tbody>" +
                    "</table>"
                );
            } else {
                out.println("<h3 class="+"buscador-notFound"+">" + "No se encuentran Huespedes." + "</h2>");
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
