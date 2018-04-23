
//var a = 8;

function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")
    var nombreUsuario = $("#nombreDeusuario").val();
    if(validar == true){
        sessionStorage.setItem("usuarioId", nombreUsuario);
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.mensaje);        
    }

}
function registrarse() {
    var nombreUsuario = $("#nombreDeusuario").val();
    var contraseña = $("#contraseña").val();
    var mail = $("#correo").val();
    var fullName = $("#nombreCompleto").val();
    var looking_for = $('input:radio[name=looking_for]:checked').val();
    
    var usuario = new Object();
    usuario.user = nombreUsuario;
    usuario.email = mail;
    usuario.fullName = fullName;
    usuario.password = contraseña;
    usuario.type = looking_for;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        
        'url': "api/usuario/register",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': resultado
    });
}

