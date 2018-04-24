

$(document).ready(function () { 
        
//http://freelancer.com/?usuario=ejemploParaNico&codigoConfirmacion=12345
    var usuario = getUrlParameter('usuario');
    var codigoConfirmacion = getUrlParameter('codigoConfirmacion');
    var valor = new Object();
    valor.user = usuario;
    valor.code = codigoConfirmacion;

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        
        'url': "api/usuario/validateCode",
        'data': JSON.stringify(valor),
        'dataType': 'json',
        'success': resultado
    });
});

function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")
//    var idUsuario = resultado.mensaje;

    if(validar == true){
        // ok, el codigo es valido, ahora procedo a que el usuario cambie su contraseña
        var usuario = getUrlParameter('usuario');
        var codigoConfirmacion = getUrlParameter('codigoConfirmacion');
        
//        sessionStorage.setItem("usuarioId", idUsuario);
        var url = "cambioContraseña.html?usuario=" + usuario + "&codigoConfirmacion=" + codigoConfirmacion;
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.mensaje);        
    }
}


/**
 * Usage
 * Calling getUrlParameter("id") - would return "1".
 * Calling getUrlParameter("image") - would return "awesome.jpg".
 * @param {type} variable
 * @return {getUrlParameter.pair, Boolean}
 */
function getUrlParameter(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }

    return(false);
}
