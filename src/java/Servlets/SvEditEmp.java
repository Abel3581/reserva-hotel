/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.Controladora;
import Logica.Empleado;
import Logica.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "SvEditEmp", urlPatterns = {"/SvEditEmp"})
public class SvEditEmp extends HttpServlet {

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
            String empUsername = request.getParameter("empUsername");
            String empPassword = request.getParameter("empPassword");
            String empDni = request.getParameter("empDni");
            String empNombre = request.getParameter("empNombre");
            String empApellido = request.getParameter("empApellido");
            String empFechaNac = request.getParameter("empFechaNac");
            String empDireccion = request.getParameter("empDireccion");
            String empCargo = request.getParameter("empCargo");            
            int idUsu = Integer.parseInt(request.getParameter("idUsuario"));
            
            // Controladora GET:
            Controladora myController = new Controladora();
            Usuario myUsuModif = myController.traerUsuarioPorId(idUsu);
            Empleado myEmpModif = myUsuModif.getUsuEmpleado();
            
            // String to Date:
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date empDate = formatter.parse(empFechaNac);
            
            //Set:    
            myUsuModif.setUsername(empUsername);
            myUsuModif.setPassword(empPassword);
            myEmpModif.setDniEmpleado(empDni);
            myEmpModif.setNombreEmpleado(empNombre);
            myEmpModif.setApellidoEmpleado(empApellido);
            myEmpModif.setFechaNacEmpleado(empDate);
            myEmpModif.setDireccionEmpleado(empDireccion);
            myEmpModif.setCargoEmpleado(empCargo);
            
            // Controladora SET:
            myController.modificarEmpleado(myUsuModif, myEmpModif);
            
            // Response:
            response.sendRedirect("empleados.jsp");               
            
            
        } catch (ParseException ex) {
            Logger.getLogger(SvEditEmp.class.getName()).log(Level.SEVERE, null, ex);
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
        int empId = Integer.parseInt(request.getParameter("idEmp"));
        
        // Controller:
        Controladora myController = new Controladora();
        Usuario myUsu = myController.traerUsuarioPorId(empId);
        
        // Set:
        HttpSession mySess = request.getSession();
        mySess.setAttribute("myEmpModif", myUsu);
        
        // Response:
        response.sendRedirect("modificacionEmpleado.jsp");
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
