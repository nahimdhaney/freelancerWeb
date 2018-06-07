function resultado(resultado) {
    if (resultado.success) {
        var obj = resultado.response;
        $("#categoria").append(obj.category);
        $("#fecha").append(obj.date);
        $("#presupuesto").append(obj.price.toString() + "  USD");
        $("#titulo").text(obj.name);
        $("#descripcion").text(obj.description);
        var val = getParameterByName('proyecto');


        var val = getParameterByName('proyecto');
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/solicitud/solicitudes/" + val,
            'success': aumetarPostuaciones
        });

//        $("#postularse").attr("href", "../Postulacion/nuevaPostulacion.html?proyecto="+val);

    } else {
        $("#respuesta").html(resultado.message);
    }
}

function aumetarPostuaciones(resultado) {
    if (resultado.success) {
        for (var i in resultado.response) {
            var obj = resultado.response[i];
            var tabla = "                                        <tr>\n" +
                    "                                            <td class=\"filterable-cell\">"+ obj.fullName +"</td>\n" +
                    "                                            <td class=\"filterable-cell\">"+ obj.oferta+" $</td>\n" +
                    "                                        </tr>\n";
            $("#tablaCuerpo").append(tabla);
            var opcion = "<option name="+obj.freelancerId+">"+obj.fullName +" - " + obj.oferta  +"$ </option>"
            
            $("#freelancerSelect").append(opcion);
            
        }
    } else {

    }

}

/*

function verSolicitudes(resultado) {
    if (resultado.success) {
        var usuarioID = sessionStorage.getItem("idUser");
        var pry = getParameterByName('proyecto');
        for (var i in resultado.response) {
            var obj = resultado.response[i];
            if (pry == obj.projectId && obj.freelancerId == usuarioID) {
                $("#botonPostularme").html("Despostularme");
                $("#tit2").text("Confirmar Despostulacion");
                $("#botonPostular").html("Confirmar");
                $("#idOferta").attr('type', 'hidden');

                $("#idPostulacion").val(obj.id);
            }
        }
//        $("#postularse").attr("href", "../Postulacion/nuevaPostulacion.html?proyecto="+val);
    } else {
        //$("#respuesta").html(resultado.message);
    }
}

*/
$(document).ready(function () {
    var val = getParameterByName('proyecto');

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "../api/proyecto/" + val,
        'success': resultado
    });

    if (sessionStorage.getItem("usuarioId") !== null) {
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/solicitud",
            'success': verSolicitudes
        });
    }
});


function postularse() {
        var val = getParameterByName('proyecto');

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "../api/proyecto/" + val,
        'success': aceptaProyecto
    });
              
}

function aceptaProyecto(resultado) {
    var val =  $("#freelancerSelect option:selected").attr('name');// getParameterByName('proyecto');
    if (resultado.success) {
            var proyecto = new Object();
            proyecto.name = resultado.response.name;
            proyecto.description = resultado.response.description;
            proyecto.price = resultado.response.price;
            proyecto.id = resultado.response.id;
        //    proyecto.freelancerId = freelancerId;
            proyecto.freelancerId = val; // hardcodeando 7
            proyecto.date = resultado.response.date;
            proyecto.category = resultado.response.category;
            proyecto.ownerId = resultado.response.ownerId;
            jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/proyecto/actualizar",
            'data': JSON.stringify(proyecto),
            'dataType': 'json',
            'success': postula
        });
//        window.location.reload(false);
//        var url = "index.html";
//        $(location).attr('href', url);

    } else {
        $("#respuesta").html(resultado.message);
    }
}
function postula(resultado) {
    if (resultado.success) {
        window.location.reload(false);
//        var url = "index.html";
//        $(location).attr('href', url);

    } else {
        $("#respuesta").html(resultado.message);
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