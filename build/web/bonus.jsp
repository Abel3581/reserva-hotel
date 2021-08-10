<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.js"></script>
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <title>Hotel - Utilidades</title>
    </head>
    <body>
    <%
        HttpSession mySess = request.getSession();
        String myUsu = (String)mySess.getAttribute("usuUsername");
        if(myUsu == null){
            response.sendRedirect("index.jsp");
        }
    %>
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
                        <li class="nav-singleLink  nav-linkActivo">
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
            <h1 class="section-title">
                Consultas
            </h1>
            
            <!--*** Menu ***-->
            <div class="cons-filterMenu" >
                <ul>
                    <li id="porEmpFecha" class="link cons-active" onclick="activeLink(this);">
                        Res. por Empleado y Fecha
                    </li>
                    <li id="ganDiarias" class="link" onclick="activeLink(this);">
                        Ganancias Diarias
                    </li>
                    <li id="ganMensuales" class="link" onclick="activeLink(this);">
                        Ganancias Mensuales
                    </li>
                </ul>
            </div>
            
            <!--*************************-->
            <!--**** POR Emp y Fecha ****-->
            <!--*************************-->
            <div class="cons-singleScreen" id="screenPorEmpleadoFecha">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Reservas Diarias por Empleado</h1>
                        <h3>
                            Ingresar DNI de Empleado y el la Fecha deseada.
                        </h3>
                    </div> 
                    <div class="singleScreen-buscador">
                        <form action="SvBonificacionEmp" method="GET" id="formBonifEmp">
                            <div class="res-factSingleInput">
                                <label for="dniEmp">DNI Empleado </label>
                                <input type="text" required="true" name="dniEmp"  style="background: none; text-align:center;">
                            </div>
                            <div style="margin: 1rem 0rem; margin-bottom: 2rem;" >
                                <div class="res-factSingleInput">
                                    <label for="buscador">Fecha: </label>
                                    <input id="datepickerEmpFecha" type="text" required="true" name="fechaEmp"  style="background: none; text-align:center;">
                                </div>
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div style="margin-top: 5rem;">
                        <div class="emp-tableContainer singleScreen-resultsContainer" id="responseBonifEmp">                           
                        </div>                        
                    </div>
                    
                </div>
            </div>

            <!--************************-->
            <!--*** Ganancias Diarias ***-->
            <!--************************-->
            <div class="cons-singleScreen" id="screenGanDiarias">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Ganancias Diarias</h1>
                        <h3>
                            Seleccionar Fecha para ver un Resumen de ganancias.
                        </h3>
                    </div>
                    <div class="singleScreen-buscador">
                        <form action="SvBonusGanancias" method="GET" id="formGanDiarias">
                            <div class="res-factSingleInput">
                                <label for="fechaDiarias">Fecha: </label>
                                <input id="datepickerGanDiarias" type="text" required="true" name="fechaDiarias"  style="background: none; text-align:center;">
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar"  />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div class="emp-tableContainer singleScreen-resultsContainer" id="responseGanDiarias">
                    </div>
                    
                </div>
            </div>
            
            <!--*************************-->
            <!--*** Ganancias Mensuales ***-->
            <!--*************************-->
                 <div class="cons-singleScreen" id="screenGanMensuales">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Ganancias Mensuales</h1>
                        <h3>
                            Seleccionar CUALQUIER fecha del Mes deseado ver un Resumen de ganancias.
                            <br>
                            <br>
                            Ejemplo: 02-02-2020 Para ver ganancias de "Febrero 2020".
                        </h3>
                    </div>
                    <div class="singleScreen-buscador">
                        <form action="SvBonusGanancias" method="POST" id="formGanMensuales">
                            <div class="res-factSingleInput">
                                <label for="buscador">Mes: </label>
                                <input id="datepickerGanMensuales" type="text" required="true" name="fechaMensual"  style="background: none; text-align:center;">
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div class="emp-tableContainer singleScreen-resultsContainer" id="responseGanMensuales">
                    </div>
                    
                </div>
            </div>
            
        </section>                              
         
        <!--*** JAVASCRIPT ***-->
        <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous">            
        </script>        
        <!--*** Emp y Fecha ***-->
        <script type="text/javascript">
            var formBonifEmp = $('#formBonifEmp');
            formBonifEmp.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formBonifEmp.attr('method'),
                url: formBonifEmp.attr('action'),
                data: formBonifEmp.serialize(),
                success: function (data) {
                    var result=data;
                    $('#responseBonifEmp').html(result);
                    }             
                });
                return false;
            }); 
        </script>        
        <!--*** Ganancias Diarias ***-->
        <script type="text/javascript">
            var formGanDiarias = $('#formGanDiarias');
            formGanDiarias.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formGanDiarias.attr('method'),
                url: formGanDiarias.attr('action'),
                data: formGanDiarias.serialize(),
                success: function (data) {
                    var result=data;
                    $('#responseGanDiarias').html(result);
                    }             
                });
                return false;
            }); 
        </script>        
        <!--*** Ganancias Mensuales ***-->
        <script type="text/javascript">
            var formGanMensuales = $('#formGanMensuales');
            formGanMensuales.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formGanMensuales.attr('method'),
                url: formGanMensuales.attr('action'),
                data: formGanMensuales.serialize(),
                success: function (data) {
                    var result=data;
                    $('#responseGanMensuales').html(result);
                    }             
                });
                return false;
            }); 
        </script>
        
        <!--*** DATE PICKER ***-->
        <script>
            // Date Picker: Empleado Fecha
            const pickerEmpFecha = MCDatepicker.create({
                el: '#datepickerEmpFecha',
                dateFormat: 'dd-MM-yyyy',
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
                  'Dic.'
                ],
                customClearBTN: "Borrar",
                customCancelBTN: "Anular"
            });
            
            // Active Menu: NO ME DEJABA IMPORTARLO DE "main.js"
            let myFormEmpFecha = document.getElementById("screenPorEmpleadoFecha");
            let myFormGanDiarias = document.getElementById("screenGanDiarias");
            let myFormGanMensuales = document.getElementById("screenGanMensuales");
            myFormGanDiarias.style.display = "none";                         
            myFormGanMensuales.style.display = "none";       
            function activeLink(id){
                const allLinks = document.getElementsByClassName("link");                
                let myLink = document.getElementById(id.id);
                
                for(i = 0 ; i < allLinks.length; i++){
                    if(allLinks[i].id !== myLink.id){
                        allLinks[i].classList.remove("cons-active");
                    } else { 
                        myLink.classList.add("cons-active");

                        if(myLink.id === "porEmpFecha"){
                            myFormGanDiarias.style.display = "none";                         
                            myFormGanMensuales.style.display = "none";      
                            myFormEmpFecha.style.display = "block";
                        }
                        if(myLink.id === "ganDiarias") {
                           myFormEmpFecha.style.display = "none";                         
                            myFormGanMensuales.style.display = "none";      
                            myFormGanDiarias.style.display = "block";
                        }
                        if(myLink.id === "ganMensuales") {
                            myFormEmpFecha.style.display = "none";                         
                            myFormGanDiarias.style.display = "none";      
                            myFormGanMensuales.style.display = "block";
                        }
                    }
                }                
            };
            
            // Date Picker: Ganancias Diarias
            let pickerGanDiarias = MCDatepicker.create({
                el: '#datepickerGanDiarias',
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
                  'Dic.'
                ],
                customClearBTN: "Borrar",
                customCancelBTN: "Anular"                    
        });
        // Date Picker: Ganancias Mensuales
        let pickerGanMensuales = MCDatepicker.create({
            el: '#datepickerGanMensuales',
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
              'Dic.'
            ],
            customClearBTN: "Borrar",
            customCancelBTN: "Anular"                      
        });
        </script>
    </body>
</html>
