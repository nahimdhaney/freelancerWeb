$(document).ready(function () {
    // aca se corrige la referencia hacia el perfil del usuario
    if (sessionStorage.getItem("usuarioId") !== null) {
        $(".ingresar").attr("href", "../perfil.html");
    }
    
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }

    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("fecha").setAttribute("min", today);


    if (sessionStorage.getItem("usuarioId") !== null) {

    } else {
        var url = "../ingresar.html";
        $(location).attr('href', url);
    }
    
    var val = getParameterByName('proyecto');
    if (val !== null) { // si es nuevo hacer ajax para traer el proyecto y Editarlo
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/proyecto/" + val,
            'dataType': 'json',
            'success': cargaProyecto
        });
    }else{
        $("#btn_ver_solicitudes").css('display',"none")
    }
});

function cargaProyecto(resultado) {
    if (resultado.success) {
        if (resultado.response.freelancerId) {
            $("#btn_ver_solicitudes").hide(); 
        } else {
            $("#btn_interactuar").hide();
        }
        
        $("#freelancer").val(resultado.response.freelancerId);
        $("#name").val(resultado.response.name);
        $("#descripcion").val(resultado.response.description);
        $("#price").val(resultado.response.price);
        $("#categoria").val(resultado.response.category.trim());
        $("#fecha").val(resultado.response.date.substring(0, 10));
        $("#confirmar").text("Editar");
    } else {
        $("#respuesta").html(resultado.message);
    }
}

function resultado(resultado) {
    if (resultado.success) {
        var url = "index.html";
        $(location).attr('href', url);
    } else {
        $("#respuesta").html(resultado.message);
    }
}

function enviar() {
    var name = $("#name").val();
    var description = $("#descripcion").val();
    var price = $("#price").val();
    var date = $("#fecha").val();
    var id = 0;
    var freelancerId = $("#freelancer");
    var OwnerId = sessionStorage.getItem("idUser");

    var proyecto = new Object();
    proyecto.name = name;
    proyecto.description = description;
    proyecto.price = price;
    proyecto.id = id;
    proyecto.freelancerId = freelancerId;
//    proyecto.freelancerId = 7; // hardcodeando 7
    proyecto.date = date;
    proyecto.category = $("#categoria option:selected").text();
    proyecto.ownerId = OwnerId;
    proyecto.start = "";
    proyecto.end = "";
    var val = getParameterByName('proyecto');
    if (val !== null) { //editar
        proyecto.id = parseInt(val);
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/proyecto/actualizar",
            'data': JSON.stringify(proyecto),
            'dataType': 'json',
            'success': resultado
        });
    } else { // crear  
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=utf-8'
            },
            'type': 'POST',
            'url': "../api/proyecto/insertar",
            'data': JSON.stringify(proyecto),
            'dataType': 'json',
            'success': resultado
        });
    }
}

function getParameterByName(name, url) {
    if (!url)
        url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
    if (!results)
        return null;
    if (!results[2])
        return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function pressenter(e,inp){
    var tecla=(document.all) ? e.keyCode : e.which;
    
    if(tecla==13){
        var valor = $(inp).val();
        if(valor.length>0){
            window.location="index.html?val="+valor;
        }
    }
}

function ver_solicitudes(){
      var val = getParameterByName('proyecto');
        if (val !== null) { // si es nuevo hacer ajax para traer el proyecto y Editarlo
            window.location.href="verSolicitudes.html?proyecto="+val;
    }
}

function ver_mensajes() {
    var projectId = getParameterByName('proyecto');
    var freelancerId = $("#freelancer").val;
    
    var obj = {
        projectId: projectId,
        freelancerId: freelancerId
    }
    
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "../api/solicitud/getSolicitudEntreFreelancerYproyecto",
        'data': JSON.stringify(obj),
        'dataType': 'json',
        'success': procesarSolicitud
    });
        
//    window.location.href="chat?proyecto="+val;
}

function procesarSolicitud(resultado) {
    if (resultado.success) {
        var primeroEnLaLista = resultado.response[0];
        var solicitud = primeroEnLaLista.id;
        
        window.location.href="../chat.html?id="+solicitud;
//        jQuery.ajax({
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            },
//            'type': 'GET',
//            'url': "../api/comentario/" + solicitud,
//            'success': procesarComentarios
//        });
    }
}

function procesarComentarios(resultado) {
//    if (resultado.success) {
//        window.location.href="chat?id="+val;
//    }
    
}
