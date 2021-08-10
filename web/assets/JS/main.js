//*********************
//  GLOBALS
//*********************

// === Required Input Message ===
document.addEventListener("DOMContentLoaded", function() {
    var elements = document.getElementsByTagName("INPUT");
    for (var i = 0; i < elements.length; i++) {
        elements[i].oninvalid = function(e) {
            e.target.setCustomValidity("");
            if (!e.target.validity.valid) {
                e.target.setCustomValidity("Por favor completar este campo");
            }
        };
        elements[i].oninput = function(e) {
            e.target.setCustomValidity("");
        };
    }
  });
            

//*********************
//  EMPLEADOS JSP
//*********************
const displayAddEmpForm = document.getElementById("displayAddEmpForm");
const hideAddEmpForm = document.getElementById("hideAddEmpForm");
const empForm = document.getElementById("empForm");

let formElements;
if(document.getElementById("myEmpForm")){
   formElements = document.getElementById("myEmpForm").elements;
}
if(document.getElementById("myResForm")){
   formElements = document.getElementById("myResForm").elements;
}

// Toggle Form:
function showForm() {
    empForm.style.display = "grid";                
}            
function hideForm() {
    empForm.style.display = "none";
}

// Borrar Campos al ANULAR:
var tipoDeCampo = "";
function borrarCampos(){
    for (i=0; i<formElements.length; i++) {
        tipoDeCampo = formElements[i].type.toLowerCase();
        if(tipoDeCampo === "text"){
            formElements[i].value = "";
        }
    }
}   
function selectedToDefault(){
    const myParentElements = document.querySelectorAll("select");
    myParentElements[0].firstElementChild.setAttribute("selected","selected");
    myParentElements[1].firstElementChild.setAttribute("selected","selected");
}

//*********************
//  RESERVAS JSP
//*********************

