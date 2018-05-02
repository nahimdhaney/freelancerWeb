function resultado(valor) { 
    if(valor.success){
        var url = "index.html";
        $(location).attr('href', url);        
    }
//    } else {
//        alert("TODO Si no es correcto")
//        $("#respuesta").html("Por favor Utilice otro Nombre de Usuario");
//    }
}
function enviarMailRecuperacion() {
    var email = $("#usuario").val();
    var mail = new Object();
    mail.user = email;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': 'api/usuario/passwordForgotten',
        'data': JSON.stringify(mail),
        'dataType': 'json',
        'success': resultado
    });
    // modificar por el cambio de contraseña
}
