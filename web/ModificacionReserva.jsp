<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.js"></script>
        <title>Mi Hotel - Modificar Reserva</title>
    </head>
    <body>
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
                Edicion Reserva
            </h1>
            <!--*** Formulario ***-->
            <%
            HttpSession mySess = request.getSession();
            Reserva myRes = (Reserva)mySess.getAttribute("myResModif");
            Date resIn = myRes.getFechaDe();
            Date resOut = myRes.getFechaHasta();
            Date resCarga = myRes.getFechaDeCarga();
            Date huesNac = myRes.getResHuesped().getFechaNacHuesped();
            Date newDate = new Date();
            // Date to String
            String datePattern = "dd-MM-yyyy";                                
            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            String resFechaDe = dateFormatter.format(resIn);
            String resFechaHasta = dateFormatter.format(resOut);
            String resFechaDeCarga = dateFormatter.format(resCarga);
            String huesFechaNac = dateFormatter.format(huesNac);
            String newFecha = dateFormatter.format(newDate);
            %>
            
            <!--*** Formulario ***-->
            <div class="res-formContainer">
                <form action="SvEditRes" method="GET" id="myResForm">
                    <div class="res-formCenter">
                        <%
                            String msg = (String) mySess.getAttribute("noDispoMsg");
                            if(msg != null){
                                if(msg.equals("no")){
                        %>
                        <!--*** Message NO DISPO ***--> 
                        <div style="display: grid; place-items: center; margin-bottom: 2rem;" id="resVer" class="res-noDispoContainer" >
                            <h3 style="color: red; letter-spacing: 1.4px;">
                                Habitacion NO Disponible. Por favor Rellenar el Formulario.
                            </h3>
                        </div>
                        <% 
                                }
                            }
                        %>
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Habitacion 
                                </h3>
                                <p>Seleccionar el Tipo de Habitacion y Cantidad de Huespedes.</p>
                            </div> 
                            <div class="res-formInputsContainer">
                                <div class="res-formInputsTop">  
                                    <div class="res-singleInput">
                                        <label for="res-tipoHabitacion">Habitacion </label>
                                        <select class="res-select" name="res-tipoHabitacion" id="res-habSelect" onchange="getHabitacion();" required="true" >
                                            <option value="Single Room" >Single Room</option>
                                            <option value="Double Room" >Double Room</option>
                                            <option value="Triple Room" >Triple Room</option>
                                            <option value="Multiple Room" >Multiple Room</option>
                                        </select>
                                    </div>                                
                                    <div class="res-singleInput">
                                        <label for="res-cantPersonas">Cantidad de Personas </label>
                                        <select class="res-select" name="res-cantPersonas" required="true" id="res-cantPers">
                                            <option value="1" id="1per">1</option>
                                            <option value="2" id="2per">2</option>
                                            <option value="3" id="3per">3</option>
                                            <option value="4" id="4per">4</option>
                                            <option value="5" id="5per">5</option>
                                            <option value="6" id="6per">6</option>
                                        </select> 
                                    </div>
                                </div>
                                <div class="res-fechasContainer">
                                    <div class="res-formTitle">
                                        <h3>
                                            Fechas 
                                        </h3>
                                        <p>Ingresar fechas de Check-in y Check-out.</p>
                                    </div> 
                                    <div class="datePickerBtnsContainer">
                                        <input id="datepickerDe" type="text" class="datePickerBtn" placeholder="De" name="res-fechaDe" required="true" value="<%= resFechaDe %>">
                                        <input id="datepickerHasta" type="text" class="datePickerBtn" placeholder="Hasta" name="res-fechaHasta" required="true" value="<%= resFechaHasta %>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="res-formSection">
                            <div class="res-formTitle">
                                <h3>
                                    Datos de Facturacion
                                </h3>
                                <p>Ingresar los datos proporcionados por el Huesped.</p>
                            </div> 
                            <div class="res-formInputsContainer res-facturacionContainer">
                                <div class="res-factSingleInput">
                                    <label for="hues-dni">
                                        DNI:
                                    </label>
                                    <input type="text" name="hues-dni" required="true" value="<%= myRes.getResHuesped().getDniHuesped() %>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="hues-nombre">
                                        Nombre:
                                    </label>
                                    <input type="text" name="hues-nombre" required="true" value="<%= myRes.getResHuesped().getNombreHuesped() %>"/>
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="hues-apellido">
                                        Apellido:
                                    </label>
                                    <input type="text" name="hues-apellido" required="true" value="<%= myRes.getResHuesped().getApellidoHuesped() %>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="hues-fechaNac">
                                        Fecha Nac:
                                    </label>
                                    <input type="text" name="hues-fechaNac" id="datepickerFechaNac" required="true" placeholder="Seleccionar Fecha" value="<%= huesFechaNac %>" />
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="hues-direccion">
                                        Direccion:
                                    </label>
                                    <input type="text" name="hues-direccion" required="true" value="<%= myRes.getResHuesped().getDireccionHuesped() %>"/>
                                </div>
                                <div class="res-factSingleInput">
                                    <label for="hues-profesion">
                                        Profesion:
                                    </label>
                                    <input type="text" name="hues-profesion" required="true" value="<%= myRes.getResHuesped().getProfesionHuesped() %>" />
                                </div>
                                <input type="hidden" value="<%= myRes.getId_reserva()%>" name="idResHidden">
                                <input type="hidden" value="<%=newFecha%>" name="fechaCargaResHidden">
                            </div>
                        </div>  
                        <!--*** BTNs Confirmar ***-->
                        <div class="res-formBtnContainer">
                            <input type="submit" value="Modificar" class="formBtn" name="cargarReserva"/>
                            <a href="consultas.jsp" type="button" class="formBtn cancelBtn" onclick="borrarCampos(); selectedToDefault();" >Anular</a>
                        </div>                       
                    </div>
                </form>
            </div>            
        </section>    
                            
<!--*** JAVASCRIPT ***-->
        <script src="./assets/JS/main.js"></script>
        <script>           
            
            // Cant Personas:
            var cantPers = document.getElementById("res-cantPers").value='<%=myRes.getCantidadPersonas()%>';            
            // Habitacion:
            var habEleg = document.getElementById("res-habSelect").value='<%=myRes.getResHabitacion().getTipo()%>';
            
            function getHabitacion(){    
                let habitacionElegida = document.getElementById("res-habSelect").value;                
                var editPers = document.getElementById("res-cantPers");                
                if(habitacionElegida === "Single Room") {
                    editPers.value="1";
                    document.getElementById("2per").style.display = "none";
                    document.getElementById("3per").style.display = "none";
                    document.getElementById("4per").style.display = "none";
                    document.getElementById("5per").style.display = "none";
                    document.getElementById("6per").style.display = "none";

                } else if (habitacionElegida === "Double Room"){
                    editPers.value="2";
                    document.getElementById("2per").style.display = "block";
                    document.getElementById("3per").style.display = "none";
                    document.getElementById("4per").style.display = "none";
                    document.getElementById("5per").style.display = "none";
                    document.getElementById("6per").style.display = "none";

                } else if (habitacionElegida === "Triple Room") {
                    editPers.value="3";
                    document.getElementById("2per").style.display = "block";
                    document.getElementById("3per").style.display = "block";
                    document.getElementById("4per").style.display = "none";
                    document.getElementById("5per").style.display = "none";
                    document.getElementById("6per").style.display = "none";

                } else if (habitacionElegida === "Multiple Room") {
                    editPers.value="4";
                    document.getElementById("2per").style.display = "block";
                    document.getElementById("3per").style.display = "block";
                    document.getElementById("4per").style.display = "block";
                    document.getElementById("5per").style.display = "block";
                    document.getElementById("6per").style.display = "block";
                }
            };
//            getHabitacion();
            
            // Date Picker DE:
            const picker1 = MCDatepicker.create({
                el: '#datepickerDe',
                dateFormat: 'dd-mm-yyyy',
                minDate: new Date(),
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
            
            // Date Picker HASTA:                     
            var myMin = new Date();
            const picker2 = MCDatepicker.create({
            el: '#datepickerHasta',
            minDate: myMin,
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
                  'Sep.',
                  'Oct.',
                  'Nov.',
                  'Dic.'
                ],
            customClearBTN: "Borrar",
            customCancelBTN: "Anular"
        });        
        //  Date Picker: Fecha Nacimiento Huesped
        const picker3 = MCDatepicker.create({
                el: '#datepickerFechaNac',
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
