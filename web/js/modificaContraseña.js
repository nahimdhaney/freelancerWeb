
//var a = 8;

function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")
    var nombreUsuario = $("#nombreUsuario").val();

    if(validar == true){
        sessionStorage.setItem("usuarioId", nombreUsuario);
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.mensaje);        
    }

}
function ingresar() {
    var nombreUsuario = $("#nombreUsuario").val();
    var contraseña = $("#contraseña").val();
    var usuario = new Object();
    usuario.user = nombreUsuario;
    usuario.password = contraseña;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        
        'url': "api/usuario/login",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': resultado
    });
}

