$(document).ready(function () {
    var val = getParameterByName('id');
    if (val !== null) {
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "api/comentario/" + val,
            'success': procesarComentarios
        });
    }else{
        
    }
});

function procesarComentarios(resultado) {
    if (resultado.success) {
        var html = "";
        
        $.each(resultado.response, function(i, obj) {
            if (obj.usuarioId == sessionStorage.getItem("idUser")) {
                html += "<div style='float: right; clear: both'>";
                html += obj.mensaje;
                html += "</div>";
            } else {
                html += "<div style='float: left; clear: both'>";
                html += obj.mensaje;
                html += "</div>";
            }
        });
        
        $("#comentarios").html(html);
    }
}

function procesarNuevoComentario(resultado) {
//    if (resultado.success) {
//        var contenido = $("#comentarios").html;
//        
//        var html = "";
//        
//        if (contenido.usuarioId == sessionStorage.getItem("idUser")) {
//            html += "<div style='float: right; clear: both'>";
//            html += contenido.mensaje;
//            html += "</div>";
//        } else {
//            html += "<div style='float: left; clear: both'>";
//            html += contenido.mensaje;
//            html += "</div>";
//        }
//        
//        contenido += html;
//        $("#comentarios").html(contenido);
//    }
    $("#input_chat").val("");
    var val = getParameterByName('id');
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "api/comentario/" + val,
        'success': procesarComentarios
    });
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

function pressenter(e, inp) {
    var tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 13) {
        var valor = $(inp).val();
        
        if (valor.length > 0) {
            var solicitudProyectoId = getParameterByName('id');
            
            var objChat = {
                mensaje: valor,
                usuarioId: sessionStorage.getItem("idUser"),
                solicitudProyectoId: solicitudProyectoId
            }
            
            jQuery.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'type': 'POST',
                'data': JSON.stringify(objChat),
                'url': "api/comentario/insertar",
                'success': procesarNuevoComentario
            });
        }
    }
}
