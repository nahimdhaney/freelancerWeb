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
