<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="Logica.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
         <link rel="stylesheet" href="estilos.css" type="text/css" />
        <title>Mi Hotel - Dashboard</title>
    </head>
    <body class="fondo2">
    <%
        HttpSession mySess = request.getSession();
        String myUsu = (String)mySess.getAttribute("usuUsername");
        if(myUsu == null){
            response.sendRedirect("index.jsp");
        } else {
    %>
        <!-- *** Navigation *** -->
        <nav class="navigationContainer colorNav">
            <div class="navigationCenter colorNav">                
                <div class="nav-logoContainer">
                    <h1>Hotel</h1>
                </div>
                <div class="nav-linksContainer">
                    <ul class="nav-links">
                        <li class="nav-singleLink nav-linkActivo">
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
            
            <!--*** Welcome Section ***-->
            <div class="hab-welcomeSect">
                <h1>
                    Bienvenido: <span>  <%= myUsu %> </span>
                </h1>
            </div>
            <div class="section-title-underline"></div>
            
            <!--*** Titulo ***-->
            <h1 class="section-title">
                Habitaciones
            </h1>            
            
            <!--*** Cards ***-->
            <div class="hab-cardsContainer">                
                <!--*** Single Room ***-->
                <article class="hab-cardContainer">
                    <div class="hab-cardLeft">
                        <img src="assets/Images/single.jpg">
                    </div>
                    <div class="hab-cardRight fondoCard">
                        <div class="hab-cardRightCenter">
                            <div class="hab-cardRight-title">
                                <p>
                                    #001
                                </p>
                                <h3>
                                    Habitacion Simple
                                </h3>
                                <h6>
                                    Piso: 01
                                </h6>
                            </div>
                            <div class="hab-cardRight-info">
                                <p>
                                    Tipo: Single Room
                                </p>
                                <span>
                                    $150 / Noche
                                </span>
                            </div>
                         </div>
                    </div>
                </article>
                
                 <!--*** Double Room ***-->
                <article class="hab-cardContainer">
                    <div class="hab-cardLeft">
                        <img src="assets/Images/double.jpg">
                    </div>
                    <div class="hab-cardRight fondoCard2">
                        <div class="hab-cardRightCenter">
                            <div class="hab-cardRight-title">
                                <p>
                                    #002
                                </p>
                                <h3>
                                    Habitacion Doble
                                </h3>
                                <h6>
                                    Piso: 02
                                </h6>
                            </div>
                            <div class="hab-cardRight-info">
                                <p>
                                    Tipo: Double Room
                                </p>
                                <span>
                                    $250 / Noche
                                </span>
                            </div>
                         </div>
                    </div>
                </article>
                 
                 <!--*** Triple Room ***-->
                <article class="hab-cardContainer">
                    <div class="hab-cardLeft">
                        <img src="assets/Images/triple.jpeg">
                    </div>
                    <div class="hab-cardRight fondoCard3">
                        <div class="hab-cardRightCenter">
                            <div class="hab-cardRight-title">
                                <p>
                                    #003
                                </p>
                                <h3>
                                    Habitacion Triple
                                </h3>
                                <h6>
                                    Piso: 02
                                </h6>
                            </div>
                            <div class="hab-cardRight-info">
                                <p>
                                    Tipo: Triple Room
                                </p>
                                <span>
                                    $300 / Noche
                                </span>
                            </div>
                         </div>
                    </div>
                </article>
                 
                 <!--*** Multiple Room ***-->
                <article class="hab-cardContainer">
                    <div class="hab-cardLeft">
                        <img src="assets/Images/multiple.jpg">
                    </div>
                    <div class="hab-cardRight fondoCard4">
                        <div class="hab-cardRightCenter">
                            <div class="hab-cardRight-title">
                                <p>
                                    #004
                                </p>
                                <h3>
                                    Habitacion Multiple
                                </h3>
                                <h6>
                                    Piso: 03
                                </h6>
                            </div>
                            <div class="hab-cardRight-info">
                                <p>
                                    Tipo: Multiple Room
                                </p>
                                <span>
                                    $450 / Noche
                                </span>
                            </div>
                         </div>
                    </div>
                </article>
            </div>
        </section>
    <% } %>
    </body>
</html>
