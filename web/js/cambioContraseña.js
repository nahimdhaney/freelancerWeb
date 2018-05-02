$(document).ready(function () {
    // le llega: usuario y codigoConfirmacion
    var usuario = getUrlParameter('usuario');
    var codigoConfirmacion = getUrlParameter('codigoConfirmacion');
    
    $("#nombreUsuario").val(usuario);
    $("#codigoRecuperacion").val(codigoConfirmacion);
});

function Recupera() {
 
    var codigoRecuperacion = $("#codigoRecuperacion").val();
    var nombreUsuario = $("#nombreUsuario").val();
    var contraseña = $("#contraseña").val();
    var contraseña2 = $("#contraseña2").val(); // nueva contraseña
    
    if (contraseña != contraseña2) {
        alert("las contraseñas no coinciden");
        return;
    }
    
    var usuario = new Object();
    usuario.user = nombreUsuario;
    usuario.code = codigoRecuperacion;
    usuario.newPassword = contraseña;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',

        'url': "api/usuario/newPassword",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': resultado
    });
}
function resultado(resultado) {
    if (resultado.success) {
        alert(resultado.message);
        
        var url = "index.html";
        $(location).attr('href',url);
//        var codigoRecuperacion = $("#codigoRecuperacion").val();
//        var nombreUsuario = $("#nombreUsuario").val();
//        var nuevaContraseña = $("#contraseña").val();
//        
//        var usuario = new Object();
//        usuario.user = nombreUsuario;
//        usuario.code = codigoRecuperacion;
//        usuario.newPassword = nuevaContraseña;
//        jQuery.ajax({
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            },
//            'type': 'POST',
//
//            'url': "api/usuario/newPassword",
//            'data': JSON.stringify(usuario),
//            'dataType': 'json',
//            'success': resultado2
//        });

    } else {
//        $("#respuesta").html(resultado.mensaje);
    }
}

function resultado2(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")

    var nombreUsuario = $("#nombreUsuario").val();
    if (validar == true) {
        sessionStorage.setItem("usuarioId", nombreUsuario);
        var url = "index.html"; 
        $(location).attr('href',url);

    } else {
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

