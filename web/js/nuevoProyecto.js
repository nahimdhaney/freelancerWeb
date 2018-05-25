/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function () {
    if (sessionStorage.getItem("usuarioId") !== null) {

    } else {
        var url = "../ingresar.html";
        $(location).attr('href', url);
    }
    var val = getParameterByName('proyecto');
    if (val !== null) { // si es nuevo hacer ajax para traer el proyecto y Editarlo
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/proyecto/" + val,
            'dataType': 'json',
            'success': cargaProyecto
        });
    }

});
function cargaProyecto(resultado) {
    if (resultado.success) {
        $("#name").val(resultado.response.name);
        $("#descripcion").val(resultado.response.description);
        $("#price").val(resultado.response.price);
        $("#categoria").val(resultado.response.category.trim());
        $("#fecha").val(resultado.response.date.substring(0, 10));
        $("#confirmar").text("Editar");
    } else {
        $("#respuesta").html(resultado.message);
    }
}

function resultado(resultado) {
    if (resultado.success) {
        var url = "index.html";
        $(location).attr('href', url);
    } else {
        $("#respuesta").html(resultado.message);
    }
}

function enviar() {
    var name = $("#name").val();
    var description = $("#descripcion").val();
    var price = $("#price").val();
    var date = $("#fecha").val();
    var id = 0;
    var freelancerId = 0;
    var OwnerId = sessionStorage.getItem("idUser");


    var proyecto = new Object();
    proyecto.name = name;
    proyecto.description = description;
    proyecto.price = price;
    proyecto.id = id;
//    proyecto.freelancerId = freelancerId;
    proyecto.freelancerId = 7; // hardcodeando 7
    proyecto.date = date;
    proyecto.category = $("#categoria option:selected").text();
    proyecto.ownerId = OwnerId;
    var val = getParameterByName('proyecto');
    if (val !== null) { //editar
        proyecto.id = parseInt(val);
                jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/proyecto/actualizar",
            'data': JSON.stringify(proyecto),
            'dataType': 'json',
            'success': resultado
        });
    } else { // crear  
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/proyecto/insertar",
            'data': JSON.stringify(proyecto),
            'dataType': 'json',
            'success': resultado
        });
    }
}






function getParameterByName(name, url) {
    if (!url)
        url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
    if (!results)
        return null;
    if (!results[2])
        return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}