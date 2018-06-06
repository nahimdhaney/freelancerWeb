function resultado(resultado) {
    if (resultado.success) {
        var obj = resultado.response;
        $("#categoria").append(obj.category);
        $("#fecha").append(obj.date);
        $("#presupuesto").append(obj.price.toString() + "  USD");
        $("#titulo").text(obj.name);
        $("#descripcion").text(obj.description);
        var val = getParameterByName('proyecto');
//        $("#postularse").attr("href", "../Postulacion/nuevaPostulacion.html?proyecto="+val);

    } else {
        $("#respuesta").html(resultado.message);
    }
}
function verSolicitudes(resultado) {
    if (resultado.success) {
        var usuarioID = sessionStorage.getItem("idUser");
        var pry = getParameterByName('proyecto');
        for (var i in resultado.response) {
            var obj = resultado.response[i];
            if (pry == obj.projectId && obj.freelancerId == usuarioID) {
                $("#botonPostularme").html("Despostularme");
                $("#idPostulacion").val(obj.id);
            }
        }
//        $("#postularse").attr("href", "../Postulacion/nuevaPostulacion.html?proyecto="+val);
    } else {
        //$("#respuesta").html(resultado.message);
    }
}


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
    if (sessionStorage.getItem("usuarioId") !== null) {
        if ($("#idPostulacion").val() == 0) {
            var proyecto = getParameterByName('proyecto');
            var usuarioID = sessionStorage.getItem("idUser");
            var id = 0;
            var state = "";
            var solicitud = new Object();
            solicitud.projectId = proyecto;
            solicitud.freelancerId = usuarioID;
            solicitud.id = id;
            solicitud.state = state;
            jQuery.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'type': 'POST',
                'url': "../api/solicitud/insertar",
                'data': JSON.stringify(solicitud),
                'dataType': 'json',
                'success': postula
            });
        } else {
            var solicitud = new Object();
            solicitud = new Object();
            solicitud.id = $("#idPostulacion").val();

            jQuery.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'type': 'POST',
                'url': "../api/solicitud/eliminar",
                'data': JSON.stringify(solicitud),
                'dataType': 'json',
                'success': postula
            });
        }
    } else {
        var url = "../ingresar.html";
        $(location).attr('href', url);
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