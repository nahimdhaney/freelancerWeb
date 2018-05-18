function resultado(resultado) {
    var iDUsuario = $("#nombreUsuario").val();
    var id = resultado.response.id;
    
    if(resultado.success){
        sessionStorage.setItem("usuarioId", iDUsuario);
        sessionStorage.setItem("idUser", id);
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.message);        
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
