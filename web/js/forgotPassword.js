
//var a = 8;

function resultado(valor) { // 1 bien 2 no bien
    // TODO validar y llevarme a la pagina correcta
//    alert("TODO Si no es correcto")
//    var nume = valor.nro
//    if (nume === 1) {
//        $("#respuesta").html("Todo Ok");
//        var url = "http://localhost:9090/LuxuryCars/index/index.html";
//        $(location).attr('href', url);
//    } else {
//        alert("TODO Si no es correcto")
//        $("#respuesta").html("Por favor Utilice otro Nombre de Usuario");
//    }
}
function enviarMailRecuperacion() {
    var email = $("#mailUsuario").val();
    var mail = new Object();
    mail.email = email;
//    alert(JSON.stringify(mail));
//    debugger;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': 'http://httpbin.org/post',
        'data': JSON.stringify(email),
        'dataType': 'json',
        'success': resultado
    });
    // modificar por el cambio de contraseña
    var url = "cambioContraseña.html";
    $(location).attr('href', url);

}
