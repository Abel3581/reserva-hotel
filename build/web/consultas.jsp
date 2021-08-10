<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.js"></script>
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <title>Mi Hotel - Consultas</title>
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
                        <li class="nav-singleLink nav-linkActivo">
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
            <h1 class="section-title">
                Consultas
            </h1>
            
            <!--*** Menu ***-->
            <div class="cons-filterMenu" >
                <ul>
                    <li id="porFecha" class="link cons-active" onclick="activeLink(this);">
                        Res. por Fecha
                    </li>
                    <li id="porEmp" class="link" onclick="activeLink(this);">
                        Res. por Empleado
                    </li>
                    <li id="listaHues" class="link" onclick="activeLink(this);">
                        Lista Huespedes
                    </li>
                    <li id="porHyF" class="link" onclick="activeLink(this);">
                        Res. por Huesped y Fechas
                    </li>
                </ul>
            </div>
            
            <!--*************************-->
            <!--******* POR FECHA *******-->
            <!--*************************-->
            <div class="cons-singleScreen" id="screenResPorFecha">
                <div class="cons-mainScreenContainer"> 
                    <div class="singleScreen-description">
                        <h1>Reservas Por Fecha</h1>
                        <h3>
                            Seleccionar una fecha para mostrar todas las Reservas cargadas dicho dia.
                        </h3>
                    </div>
                    <div class="singleScreen-buscador">
                        <form action="SvConsResPorFecha" method="GET" >
                            <div class="res-factSingleInput">
                                <label for="buscador">Fecha: </label>
                                <input id="datepicker" type="text" required="true" name="buscador" placeholder="Calendario" style="background: none; text-align:center;">
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    <div class="singleScreen-resultsContainer">
                    <% 
                        String datePattern = "dd/MM/yyyy";                                
                        String datePattern2 = "dd-MM-yyyy";                                
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern); 
                        SimpleDateFormat dateFormatter2 = new SimpleDateFormat(datePattern2); 

                        List<Reserva> misReservas = (List) mySess.getAttribute("reservasPorFecha");
//                                    System.out.println(misReservas);
                        if(misReservas != null){
                            if (misReservas.size() > 0){
                    %>
                        <div class="section-title-underline"></div>
                        <h2>Resultados</h2>
                        
                        <!--*** Tabla Como Visto en Clase ***-->
                        <div class="emp-tableContainer">
                            <table style="margin-bottom: 2rem;">
                                <thead>
                                    <tr>
                                      <th>N° Res</th>
                                      <th>Check-in</th>
                                      <th>Check-out</th>
                                      <th>Habitacion</th>
                                      <th>N° Huespedes</th>
                                      <th>Huesped Dni</th>
                                      <th>Huesped</th>
                                      <th>Empleado</th>
                                      <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for(Reserva res : misReservas) {
                                            String resIn = dateFormatter.format(res.getFechaDe());
                                            String resOut = dateFormatter.format(res.getFechaHasta());
                                            String resInDelete = dateFormatter2.format(res.getFechaDeCarga());
                                    %>
                                    <tr>
                                        <td>
                                           <%= res.getId_reserva() %>
                                        </td>
                                        <td>
                                            <%= resIn %>
                                        </td>
                                        <td>
                                            <%= resOut %>
                                        </td>
                                        <td>
                                            <%= res.getResHabitacion().getTipo() %>
                                        </td>
                                        <td>
                                            <%= res.getCantidadPersonas() %>
                                        </td>
                                        <td>
                                            <%= res.getResHuesped().getDniHuesped() %>
                                        </td>
                                        <td>
                                            <%= res.getResHuesped().getNombreCompletoHuesped() %>
                                        </td>
                                        <td>
                                            <% if(res.getResUsuario() == null) {
                                            %>
                                            Usuario Eliminado
                                            <% } else { 
                                            %>
                                            <%=res.getResUsuario().getUsuEmpleado().getNombreEmpleado() %>
                                            <% } %>
                                        </td>
                                        <!--***-->
                                        <td class="emp-tableIconsContainer">
                                            <form action="SvEditRes" method="POST">
                                                <input type="hidden" name="idRes" value="<%= res.getId_reserva() %>">
                                                <button type="submit" style="outline: none; background: none; border: none;">
                                                    <img  class="emp-tableIcon" src="./assets/Icons/editEmp.png" alt="Editar Empleado"/>
                                                </button>
                                            </form>

                                            <!--*** MODAL ***-->
                                            <div class="emp-Modal" id="myModal">
                                                <div class="modal-content">
                                                    <div class="modal-texts">
                                                        <h2>Se eliminará definitivamente la Reserva y su Huesped(De no tener otras cargadas en el sistema).</h2>
                                                        <h3>Desea continuar?</h3>
                                                    </div>
                                                    <div class="modal-btns">
                                                        <div class="modal-singleBtn">
                                                            <form action="SvDeleteEmp" method="GET" class="modal-singleBtn">
                                                                <input type="hidden" name="idRes"  id="inputData">
                                                                <input type="hidden" name="fechaRes"  id="inputData2">
                                                                <button type="submit" style="outline: none; background: none; border: none; background: #d85888 " class="formBtn">
                                                                    Eliminar
                                                                </button>
                                                            </form>
                                                        </div>
                                                        <div class="modal-singleBtn">
                                                            <button type="submit" style="outline: none; background: none; border: none; background: #faeecf" class="formBtn" onclick="closeModal();">
                                                                Anular
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div> 
                                                <button style="outline: none; background: none; border: none;" onclick="openModal('<%=res.getId_reserva()%>', '<%=resInDelete%>')">
                                                    <img  class="emp-tableIcon" src="./assets/Icons/deleteEmp.png" alt="Eliminar Empleado" />
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                    <% } %>                                    
                                    <% } else { %> 
                                    <h3 class="buscador-notFound">No se encuentran Reservas para la Fecha seleccionada.</h2>
                                    <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!--************************-->
            <!--***** POR EMPLEADO *****-->
            <!--************************-->
            <div class="cons-singleScreen" id="screenResPorEmpleado">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Reservas Por Empleado</h1>
                        <h3>
                            Ingresar el DNI de un Empleado para obtener sus Reservas.
                        </h3>
                    </div>
                    <div class="singleScreen-buscador">
                        <form action="SvConsResPorEmpleado" method="GET" id="formEmpleadosID">
                            <div class="res-factSingleInput">
                                <label for="buscador">DNI Empelado: </label>
                                <input type="text" required="true" name="buscador"  style="background: none; text-align:center;">
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div class="emp-tableContainer singleScreen-resultsContainer" id="empContent">
                    </div>
                    
                </div>
            </div>
            
            <!--*************************-->
            <!--**** LISTA HUESPEDES ****-->
            <!--*************************-->
            <div class="cons-singleScreen" id="screenListaHuespedes">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Lista de Huespedes</h1>
                        <h3>
                            Lista de todos los huespedes ingresados en el sistema.
                        </h3>
                    </div>
                    <div class="singleScreen-buscador" >
                        <form action="SvConsListaHuespedes" method="GET" id="formListHosts">                            
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div style="margin-top: -5rem;">
                        <div class="emp-tableContainer singleScreen-resultsContainer" id="huesContent">                           
                        </div>
                    </div>
                    
                </div>
            </div>    

            <!--*************************-->
            <!--**** POR HUESy FECHA ****-->
            <!--*************************-->
            <div class="cons-singleScreen" id="screenResPorHuesyFechas">
                <div class="cons-mainScreenContainer" style="padding-bottom: 0;"> 
                    <div class="singleScreen-description">
                        <h1>Reservas Por Huesped y Fechas</h1>
                        <h3>
                            Ingresar DNI de Huesped y el rango de Fechas deseadas.
                        </h3>
                    </div> 
                    <div class="singleScreen-buscador">
                        <form action="SvConsPorHyF" method="GET" id="formHyF">
                            <div class="res-factSingleInput">
                                <label for="buscador">DNI Huesped </label>
                                <input type="text" required="true" name="hues-dni"  style="background: none; text-align:center;">
                            </div>
                            <div style="margin: 1rem 0rem;" >
                                <div class="res-formTitle">
                                    <h3>
                                        Fechas 
                                    </h3>
                                    <p>Ingresar fechas de Check-in y Check-out.</p>
                                </div> 
                                <div class="datePickerBtnsContainer" style="display: flex; align-items: center; justify-content: space-between; width: 100%;">
                                    <input id="datepickerDeHF" type="text" class="datePickerBtn" placeholder="De" name="res-fechaDe" required="true" style="margin: 0rem 0.7rem;">
                                    <input id="datepickerHastaHF" type="text" class="datePickerBtn" placeholder="Hasta" name="res-fechaHasta" required="true" style="margin: 0rem 0.7rem;">
                                </div>
                            </div>
                            <div class="login-formButtons">
                                <input type="submit" class="formBtn" value="Buscar" />
                            </div>
                        </form>
                    </div>
                    
                    <!--*** Tabla AJAX y armado HTML en Servlet ***-->
                    <div style="margin-top: 5rem;">
                        <div class="emp-tableContainer singleScreen-resultsContainer" id="HFContent">                           
                        </div>                        
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
        <!--*** Empleados ***-->
        <script type="text/javascript">
            var formEmp = $('#formEmpleadosID');
            formEmp.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formEmp.attr('method'),
                url: formEmp.attr('action'),
                data: formEmp.serialize(),
                success: function (data) {
                    var result=data;
                    $('#empContent').html(result);
                    }             
                });
                return false;
            }); 
        </script>        
        <!--*** Huespedes ***-->
        <script type="text/javascript">
            var formHues = $('#formListHosts');
            formHues.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formHues.attr('method'),
                url: formHues.attr('action'),
                data: formHues.serialize(),
                success: function (data) {
                    var result=data;
                    $('#huesContent').html(result);
                    }             
                });
                return false;
            }); 
        </script>        
        <!--*** Hues y Fecha ***-->
        <script type="text/javascript">
            var formHF = $('#formHyF');
            formHF.submit(function (event) {
                event.preventDefault();
                $.ajax({
                type: formHF.attr('method'),
                url: formHF.attr('action'),
                data: formHF.serialize(),
                success: function (data) {
                    var result=data;
                    $('#HFContent').html(result);
                    }             
                });
                return false;
            }); 
        </script>
        
        <!--*** DATE PICKER ***-->
        <script>
            // Modal Delte:
            var modal = document.getElementById("myModal");
            function openModal(el, el2){
                modal.style.display= "grid";
                let eliminarBtn = document.getElementById("inputData");
                let eliminarBtn2 = document.getElementById("inputData2");
                eliminarBtn.setAttribute("value", el);
                eliminarBtn2.setAttribute("value", el2);
                
            }
            function closeModal(){
                modal.style.display = "none";
            }
            window.onclick = function(event) {
            if (event.target === modal) {
              modal.style.display = "none";
                }
            };    
            
            // Date Picker DE: Consulta Por Fecha
            const picker1 = MCDatepicker.create({
                el: '#datepicker',
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
                  'Dic.',
                ],
                customClearBTN: "Borrar",
                customCancelBTN: "Anular",
            });
            
            // Active Menu: NO ME DEJABA IMPORTARLO DE "main.js"
            let myFormFec = document.getElementById("screenResPorFecha");
            let myFormEmp = document.getElementById("screenResPorEmpleado");
            let myFormHues = document.getElementById("screenListaHuespedes");
            let myFormHyF = document.getElementById("screenResPorHuesyFechas");
            myFormEmp.style.display = "none";                         
            myFormHues.style.display = "none";                         
            myFormHyF.style.display = "none"; 
            function activeLink(id){
                const allLinks = document.getElementsByClassName("link");                
                let myLink = document.getElementById(id.id);
                
                for(i = 0 ; i < allLinks.length; i++){
                    if(allLinks[i].id !== myLink.id){
                        allLinks[i].classList.remove("cons-active");
                    } else { 
                        myLink.classList.add("cons-active");

                        if(myLink.id === "porFecha"){
                            myFormEmp.style.display = "none";                         
                            myFormHues.style.display = "none";                         
                            myFormHyF.style.display = "none"; 
                            myFormFec.style.display = "block";
                        }
                        if(myLink.id === "porEmp") {
                            myFormFec.style.display = "none";                         
                            myFormHues.style.display = "none";                         
                            myFormHyF.style.display = "none"; 
                            myFormEmp.style.display = "block";
                        }
                        if(myLink.id === "listaHues") {
                            myFormFec.style.display = "none";                         
                            myFormEmp.style.display = "none";                         
                            myFormHyF.style.display = "none"; 
                            myFormHues.style.display = "block";
                        }
                        if(myLink.id === "porHyF") {
                            myFormFec.style.display = "none";                         
                            myFormHues.style.display = "none";                         
                            myFormEmp.style.display = "none"; 
                            myFormHyF.style.display = "block";
                        }
                    }
                }                
            }
            
            let hastaBtn = document.getElementById("datepickerHastaHF");
            hastaBtn.disabled=true;
            hastaBtn.style.background="lightgray";
            hastaBtn.style.cursor="default";
            // Date Picker DE: Constulca Huesped y Fecha
            let pickerHF1 = MCDatepicker.create({
                el: '#datepickerDeHF',
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
            let pickerHF2;            
            pickerHF1.onSelect((date)=> { 
                // Habilitar HASTA
                hastaBtn.disabled = false;
                hastaBtn.style.background="#96baec"; // ColorBlue
                hastaBtn.style.cursor="pointer";
                
                // Settear Min Date:
                var myMin = new Date(date);
                myMin.setDate(date.getDate() + 1);
                
                // Date Picker HASTA: Constulca Huesped y Fecha
                pickerHF2 = MCDatepicker.create({
                el: '#datepickerHastaHF',
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
                      'Dic.',
                    ],
                customClearBTN: "Borrar",
                customCancelBTN: "Anular",
            })
        });
        </script>
    </body>
</html>
