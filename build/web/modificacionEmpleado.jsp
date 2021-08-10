<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.js"></script>
        <title>Hotel - Modificar Empleado</title>
    </head>
    <body>
        <!-- *** Navigation *** -->
        <nav class="navigationContainer">
            <div class="navigationCenter">                
                <div class="nav-logoContainer">
                    <h1>Hotel</h1>
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
                Edicion Empleado
            </h1>
            <!--*** Formulario ***-->
            <%
            HttpSession mySess = request.getSession();
            Usuario myUsu = (Usuario)mySess.getAttribute("myEmpModif");
            Date empleDate = myUsu.getUsuEmpleado().getFechaNacEmpleado();
            // Date to String
            String datePattern = "dd-MM-yyyy";                                
            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            String fechaEmple = dateFormatter.format(empleDate);
            %>
            <div class="res-formContainer">
                <form class="emp-addEmpForm" action="SvEditEmp" method="GET" id="EditEmpleForm">
                    <div class="res-formCenter">
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Editar Empleado
                                </h3>
                                <p>Se modificaran los datos del empleado.</p>
                            </div> 
                            <div class="res-formInputsContainer res-facturacionContainer">
                                <div class="res-factSingleInput">
                                    <label for="empUsername">
                                        Usuario:
                                    </label>
                                    <input type="text" name="empUsername" required="true" value="<%=myUsu.getUsername()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empPassword">
                                        Contraseña:
                                    </label>
                                    <input type="text" name="empPassword" required="true" value="<%=myUsu.getPassword()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empDni">
                                        DNI:
                                    </label>
                                    <input type="text" name="empDni" required="true" value="<%=myUsu.getUsuEmpleado().getDniEmpleado()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empNombre">
                                        Nombre:
                                    </label>
                                    <input type="text" name="empNombre" required="true" value="<%=myUsu.getUsuEmpleado().getNombreEmpleado()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empApellido">
                                        Apellido:
                                    </label>
                                    <input type="text" name="empApellido" required="true" value="<%=myUsu.getUsuEmpleado().getApellidoEmpleado()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empFechaNac">
                                        Fecha Nac:
                                    </label>
                                    <input type="text" name="empFechaNac" required="true" id="datepickerEmpFechaNac2" value="<%=fechaEmple%>" placeholder="Seleccionar Fecha" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empDireccion">
                                        Direccion:
                                    </label>
                                    <input type="text" name="empDireccion" required="true" value="<%=myUsu.getUsuEmpleado().getDireccionEmpleado()%>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="empCargo">
                                        Cargo:
                                    </label>
                                    <input type="text" name="empCargo" required="true" value="<%=myUsu.getUsuEmpleado().getCargoEmpleado()%>" />
                                </div>
                                <input type="hidden" name="idUsuario" value="<%=myUsu.getId_usuario()%>">
                            </div>
                        </div>
                        <div class="emp-addEmpFormBtns">
                            <input type="submit" value="Modificar" class="formBtn" />
                            <a href="empleados.jsp" type="button" class="formBtn cancelBtn" onclick="borrarCampos();" >Anular</a>
                        </div>
                    </div>
                </form>
            </div>
        </section>    
                            
    <!--*** JAVASCRIPT ***-->
    <script src="./assets/JS/main.js"></script>
    <script>
        //  Date Picker EMPFECHANAC:
        const picker5 = MCDatepicker.create({
                el: '#datepickerEmpFechaNac2',
                dateFormat: 'dd-mm-yyyy',
                customWeekDays: ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
                customMonths: [
                  'Enero',
                  'Febrero',
                  'Marzo',
                  'Abril',
                  'Mayo',
                  'Junio',
                  'Julio',
                  'Agosto',
                  'Sept.',
                  'Oct.',
                  'Nov.',
                  'Dic.',
                ],
                customClearBTN: "Borrar",
                customCancelBTN: "Anular",
            });
    </script>
    </body>
</html>
