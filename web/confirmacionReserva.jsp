<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <title>Mi Hotel - Confirmacion Reserva</title>
    </head>
    <body>
    <%
        HttpSession mySess = request.getSession();
        String myUsu = (String)mySess.getAttribute("usuUsername");              
        Controladora control = new Controladora();                                        
        Usuario usuSession = control.usuarioPorSession(myUsu); 
        
        Reserva myRes = usuSession.getUsuLastReserva();
        Habitacion myHab = myRes.getResHabitacion();
        Huesped myHues = myRes.getResHuesped();
        
        String datePattern = "dd/MM/yyyy";                                
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        Date checkIn = myRes.getFechaDe();
        Date checkOut = myRes.getFechaHasta();
        Date fechaNac = myHues.getFechaNacHuesped();
        String resIn = dateFormatter.format(checkIn);
        String resOut = dateFormatter.format(checkOut);
        String huesDate = dateFormatter.format(fechaNac);
        
        if(myUsu == null){
            response.sendRedirect("index.jsp");
        } else {
    %>
        <!-- *** Navigation *** -->
        <nav class="navigationContainer">
            <div class="navigationCenter">                
                <div class="nav-logoContainer">
                    <h1>Mi Hotel</h1>
                </div>
                <div class="nav-linksContainer">
                    <ul class="nav-links">
                        <li class="nav-singleLink">
                            <a href="dashboard.jsp">
                                Inicio
                            </a>
                        </li>
                        <li class="nav-singleLink">
                            <a href="reservas.jsp">
                                Reservas
                            </a>
                        </li>
                        <li class="nav-singleLink">
                            <a href="consultas.jsp">
                                Consultas
                            </a>
                        </li>
                        <li class="nav-singleLink">
                            <a href="empleados.jsp">
                                Empleados
                            </a>
                        </li>
                        <li class="nav-singleLink">
                            <a href="bonus.jsp">
                                Utiles
                            </a>
                        </li>
                        <li class="nav-singleLink singleLink-logout">
                            <a href="SvLogout">
                                Cerrar Sesión
                            </a>
                        </li>
                    </ul>
                </div>                
            </div>
        </nav>
        
        <!-- *** Seccion Principal *** -->
        <section class="section main-sect">
            <h1 class="section-title" style="color: green;">
                Reserva cargada con exito!
            </h1>
            <!--*** Formulario ***-->
            <div class="res-formContainer">
                <form>
                    <div class="res-formCenter">
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Reserva 
                                </h3>
                                <p>Habitacion, Cantidad de Personas y Fechas.</p>
                            </div>
                            <div class="res-formInputsContainer res-confirmacionInputsContainer">
                                <div class="res-confSingleInput">
                                    <label>
                                        Habitacion:
                                    </label>
                                    <span>
                                        <%= myHab.getTipo() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Cant. Personas:
                                    </label>
                                    <span>
                                        <%= myRes.getCantidadPersonas() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Check-In:
                                    </label>
                                    <span>
                                        <%= resIn %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Check-Out:
                                    </label>
                                    <span>
                                        <%= resOut %>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Huesped 
                                </h3>
                                <p>Datos Personales del Huesped.</p>
                            </div>
                            <div class="res-formInputsContainer res-confirmacionInputsContainer">
                                <div class="res-confSingleInput">
                                    <label>
                                        DNI:
                                    </label>
                                    <span>
                                        <%= myHues.getDniHuesped() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Nombre
                                    </label>
                                    <span>
                                        <%= myHues.getNombreHuesped() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Apellido
                                    </label>
                                    <span>
                                        <%= myHues.getApellidoHuesped() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Fecha Nac:
                                    </label>
                                    <span>
                                        <%= huesDate %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Direccion:
                                    </label>
                                    <span>
                                        <%= myHues.getDireccionHuesped() %>
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Profesion:
                                    </label>
                                    <span>
                                        <%= myHues.getProfesionHuesped() %>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Precio 
                                </h3>
                                <p>Cantidad de Noches y Precio Total.</p>
                            </div>
                            <div class="res-formInputsContainer res-confirmacionInputsContainer">
                                <div class="res-confSingleInput">
                                    <label>
                                        Total Noches:
                                    </label>
                                    <span>
                                        <%= myRes.getCantidadNoches() %> Noches
                                    </span>
                                </div>
                                <div class="res-confSingleInput">
                                    <label>
                                        Precio Estadia:
                                    </label>
                                    <span style="letter-spacing: 1px; color: crimson">
                                        $ <%= myRes.getPrecioTotal() %>  
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="res-formSection">
                        <div class="res-formInputsContainer res-confirmacionInputsContainer">
                            <div class="res-confSingleInput" style="justify-content: space-around; width: 50%;">
                                <label>Cargada Por: </label>
                                <span>
                                    <%= usuSession.getUsuEmpleado().getNombreCompletoEmpleado() %>
                                </span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </section>
    <%}%>
    </body>
</html>
