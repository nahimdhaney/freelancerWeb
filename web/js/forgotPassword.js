
//var a = 8;

function resultado(valor) { 
    var validar = valor.esOk
//    alert("ENTRA")

    if(validar == true){
//        $("#respuesta").html("Todo Ok");
        alert(valor.mensaje)
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
//    alert(JSON.stringify(mail));
//    debugger;
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
