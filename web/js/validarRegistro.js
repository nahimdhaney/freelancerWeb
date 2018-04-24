

$(document).ready(function () { 
        
//http://freelancer.com/?usuario=ejemploParaNico&codigoConfirmacion=12345
    var usuario = getUrlParameter('usuario');
    var codigoConfirmacion = getUrlParameter('codigoConfirmacion');
    var valor = new Object();
    valor.usuarioNombre = usuario;
    valor.codigoConfirmacion = codigoConfirmacion;

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        
        'url': "api/usuario/validarRegistro",
        'data': JSON.stringify(valor),
        'dataType': 'json',
        'success': resultado
    });
});

function resultado(resultado) {
    var validar = resultado.esOk
//    alert("ENTRA")
    var nombreUsuario = resultado.nombre;

    if(validar == true){
        sessionStorage.setItem("usuarioId", nombreUsuario);
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.mensaje);        
    }
}