
//var a = 8;

$(document).ready(function () {
    if (sessionStorage.getItem("usuarioId") !== null) {
        $("#nombreUsuario").html(sessionStorage.getItem("usuarioId"));        
        
    } else {
        var url = "index.html"; 
        $(location).attr('href',url);
    }
});



function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")

    if(validar == true){
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.mensaje);        
    }

}
function modifica() {
    var antiguaContraseña = $("#lastPass").val();
    var contraseña = $("#contraseña").val();
    var contraseña2 = $("#contraseña2").val();
    var usuario = new Object();
    usuario.user = sessionStorage.getItem("usuarioId");
    usuario.password = antiguaContraseña;
    usuario.newPassword = contraseña;
        jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        
        'url': "api/usuario/changePassword",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': resultado
    });
}

