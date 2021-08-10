<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Logica.*, java.util.*, java.text.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/CSS/styles.css" type="text/css" />
        <link href="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/mc-datepicker/dist/mc-calendar.min.js"></script>
        <title>Mi Hotel - Empleados</title>
    </head>
    <body>
    <%
        HttpSession mySess = request.getSession();
        String myUsu = (String)mySess.getAttribute("usuUsername");
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
                        <li class="nav-singleLink nav-linkActivo">
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
                Lista de Empleados
            </h1>
            <div class="emp-mainCenter">                
                <div class="section-title-underline"></div>

                <!--*** Add Empleado ***-->
                <div class="emp-currentSession">
                    <h1>
                        Usuario Actual: <span> <%= myUsu %> </span>
                    </h1>
                </div>
                <div class="emp-addEmpContainer">
                    <input type="submit" class="emp-addEmp formBtn" value="Nuevo Empleado" id="displayAddEmpForm"  onclick="showForm();" />
                </div>
                
                <%
                String errorMsg = (String) mySess.getAttribute("errorMsg");
                if(errorMsg != null){
                    if(errorMsg.equals("true")){
                %>
                <div class="buscador-notFound">
                    <h2>El Username esta en uso. Por favor elegir otro Username.</h2>
                </div>
                <% }} %>
                    
                <!--*** Add Empleado ***-->
                <div class="emp-addEmpHiddenForm" id="empForm">
                    <form class="emp-addEmpForm" action="SvUsuario" method="POST" id="myEmpForm" >
                        <div class="res-formCenter">
                            <div class="res-formSection">
                                <div class="res-formTitle">
                                    <h3>
                                        Nuevo Empleado
                                    </h3>
                                    <p>Ingresar los datos del nuevo empleado.</p>
                                </div> 
                                <div class="res-formInputsContainer res-facturacionContainer">
                                    <div class="res-factSingleInput">
                                        <label for="empUsername">
                                            Usuario:
                                        </label>
                                        <input type="text" name="empUsername" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empPassword">
                                            Contraseña:
                                        </label>
                                        <input type="text" name="empPassword" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empDni">
                                            DNI:
                                        </label>
                                        <input type="text" name="empDni" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empNombre">
                                            Nombre:
                                        </label>
                                        <input type="text" name="empNombre" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empApellido">
                                            Apellido:
                                        </label>
                                        <input type="text" name="empApellido" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empFechaNac">
                                            Fecha Nac:
                                        </label>
                                        <input type="text" name="empFechaNac" required="true" id="datepickerEmpFechaNac" placeholder="Seleccionar Fecha" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empDireccion">
                                            Direccion:
                                        </label>
                                        <input type="text" name="empDireccion" required="true" />
                                    </div>
                                    <div class="res-factSingleInput">
                                        <label for="empCargo">
                                            Cargo:
                                        </label>
                                        <input type="text" name="empCargo" required="true" />
                                    </div>
                                </div>
                            </div>
                            <div class="emp-addEmpFormBtns">
                                <input type="submit" value="Agregar" class="formBtn" />
                                <button type="button" class="formBtn cancelBtn" onclick="hideForm(); borrarCampos();" >Anular</button>
                            </div>
                        </div>
                    </form>
                </div>
                    
                <!--*** Table ***-->
                <div class="emp-tableContainer">
                    <table>
                        <thead>
                            <tr>
                              <th>Usuario</th>
                              <th>Contraseña</th>
                              <th>DNI</th>
                              <th>Nombre</th>
                              <th>Apellido</th>
                              <th>Fecha de Nac.</th>
                              <th>Direccion</th>
                              <th>Cargo</th>
                              <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!--*** CONSULTA EMPLEADOS ***-->
                            <% 
                            Controladora myContr = new Controladora();                                
                            String datePattern = "dd/MM/yyyy";                                
                            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);                                 
                            List<Usuario> usuLista = myContr.traerUsuarios();
                            for(Usuario usu : usuLista) {
                                Date usuDateBefore = usu.getUsuEmpleado().getFechaNacEmpleado();
                                String usuDate = dateFormatter.format(usuDateBefore);
                                if(usu.getUsername().equals("AdminHotel")){                                        
                            %>
                            <tr style="background-color: #DCE9F9">
                                <td>
                                    <%= usu.getUsername() %>
                                </td>
                                <td>
                                    <%= usu.getPassword() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getDniEmpleado() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getNombreEmpleado() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getApellidoEmpleado() %>
                                </td>
                                <td>
                                    <%= usuDate %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getDireccionEmpleado() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getCargoEmpleado() %>
                                </td>
                                <td class="emp-tableIconsContainer">
                                    <div>
                                        <div></div>
                                    </div>
                                    <div>
                                          <div></div>
                                    </div>
                                </td>
                            </tr>
                            <% } else {%>
                            <!--*** Tabla Empleados ***-->
                            <tr>
                                <td>
                                    <%= usu.getUsername() %>
                                </td>
                                <td>
                                    <%= usu.getPassword() %>
                                </td>
                                <td><%= usu.getUsuEmpleado().getDniEmpleado() %></td>
                                <td>
                                    <%= usu.getUsuEmpleado().getNombreEmpleado() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getApellidoEmpleado() %>
                                </td>
                                <td>
                                    <%= usuDate %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getDireccionEmpleado() %>
                                </td>
                                <td>
                                    <%= usu.getUsuEmpleado().getCargoEmpleado() %>
                                </td>
                                <td class="emp-tableIconsContainer">
                                    <form action="SvEditEmp" method="POST">
                                        <input type="hidden" name="idEmp" value="<%= usu.getId_usuario() %>">
                                        <button type="submit" style="outline: none; background: none; border: none;">
                                            <img  class="emp-tableIcon" src="./assets/Icons/editEmp.png" alt="Editar Empleado"/>
                                        </button>
                                    </form>
                                                
                                    <!--*** MODAL ***-->
                                    <div class="emp-Modal" id="myModal">
                                        <div class="modal-content">
                                            <div class="modal-texts">
                                                <h2>Se eliminará definitivamente al Empleado y su Usuario.</h2>
                                                <h3>Desea continuar?</h3>
                                            </div>
                                            <div class="modal-btns">
                                                <div class="modal-singleBtn">
                                                    <form action="SvDeleteEmp" method="POST" class="modal-singleBtn">
                                                        <input type="hidden" name="idEmp"  id="inputData">
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
                                        <button style="outline: none; background: none; border: none;" onclick="openModal(<%=usu.getId_usuario()%>);" data-usuId="123" >
                                            <img  class="emp-tableIcon" src="./assets/Icons/deleteEmp.png" alt="Eliminar Empleado" />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
                        
    <!--*** JAVASCRIPT ***-->
    <script src="./assets/JS/main.js"></script>
    <script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous">            
    </script>
    <script>
        // Modal Delte:
        var modal = document.getElementById("myModal");
        function openModal(el){
            modal.style.display= "grid";
            let eliminarBtn = document.getElementById("inputData");
            eliminarBtn.setAttribute("value", el);
        }
        function closeModal(){
            modal.style.display = "none";
        }
        window.onclick = function(event) {
        if (event.target === modal) {
          modal.style.display = "none";
            }
        };    
        //  Date Picker: Empleado Fecha Nacimiento:
        const picker3 = MCDatepicker.create({
                el: '#datepickerEmpFechaNac',
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
    <%}%>
    </body>
</html>
