
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
        $("#respuesta").html("Error al ingresar");        
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

function ejecutarQuery() {
//    var myObject = new Object();
//myObject.name = "John";
//myObject.age = 12;
//myObject.pets = ["cat", "dog"];
    var nombre = $("#nombre").val();
    var apellido = $("#Apellido").val();
    var nombreCompleto = nombre + " " + apellido;
    var nombreUsuario = $("#nombreUsuario").val();
    var tarjeta = $("#Tarjeta").val();
    var direccion = $("#Direccion").val();
    var contraseña = $("#contraseña").val();
    var contraseña2 = $("#contraseña2").val();
    var usuario = new Object();
    usuario.usuarioId = 1;
    usuario.nombreCompleto = nombreCompleto;
    usuario.userName = nombreUsuario;
    usuario.password = contraseña;
    usuario.direccion = direccion;
    usuario.fechaIngreso = "2234";
    usuario.NroTarjeta = tarjeta;
    if (contraseña.length < 3) {
        $("#respuesta").html("Por favor Digite Una contraseña mas larga");
        return;
    }
    if (contraseña != contraseña2) {
        $("#respuesta").html("Las contraseñas no son iguales");
        return;
    }


//var valor = {"qty":999,"name":"iPad 3"};

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "../api/usuario/login",
        'data': JSON.stringify(usuario),
        'dataType': 'json',
        'success': prueba
    });
//   
//$.postJSON = function(url, data, callback) {
//    return jQuery.ajax({
//    headers: { 
//        'Accept': 'application/json',
//        'Content-Type': 'application/json' 
//    },
//    'type': 'POST',
//    'url': url,
//    'data': JSON.stringify(data),
//    'dataType': 'json',
//    'success': callback
//    });
//};

//       $.post(,usuario,prueba, 'json');
//    private String userName;
//    private String password;
//    private String direccion;
//    private String fechaIngreso;
//    private String NroTarjeta;



}
