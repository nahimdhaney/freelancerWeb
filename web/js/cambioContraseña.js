
//var a = 8;

$(document).ready(function () {

});



function Recupera() {

    var codigoRecuperacion = $("#codigoRecuperacion").val();
    var nombreUsuario = $("#nombreUsuario").val();
    var contraseña = $("#contraseña").val();
    var contraseña2 = $("#contraseña2").val(); // nueva contraseña
    var usuario = new Object();
    usuario.user = nombreUsuario;
    usuario.code = codigoRecuperacion;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',

        'url': "api/usuario/validateCode",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': resultado
    });
}
function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")

    if (validar == true) {
        var codigoRecuperacion = $("#codigoRecuperacion").val();
        var nombreUsuario = $("#nombreUsuario").val();
        var nuevaContraseña = $("#contraseña").val();
        var usuario = new Object();
        usuario.user = nombreUsuario;
        usuario.code = codigoRecuperacion;
        usuario.newPassword = nuevaContraseña;
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',

            'url': "api/usuario/newPassword",
            'data': JSON.stringify(usuario),
            'dataType': 'json',
            'success': resultado2
        });

    } else {
        $("#respuesta").html(resultado.mensaje);
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

