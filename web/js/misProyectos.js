function resultado(resultado) {
    if (resultado.success) {
        for (var i in resultado.response) {
        var obj = resultado.response[i];
            var text = obj.description;
              if (text.length > 120) {
                var text = text.substr(0, text.lastIndexOf(' ', 117)) + '...';
              }
        var val = "			<div class=\"col-lg-4 col-md-6\">\n"
                + "                            <div class=\"l_news_item\">\n"
                + "                                <!--<div class=\"l_news_img\"><a href=\"#\"><img class=\"img-fluid\" src=\"img/blog/l-news/l-news-1.jpg\" alt=\"\"></a></div>-->\n"
                + "                                <div class=\"l_news_content\">\n"
                + "                                    <a href=nuevoProyecto.html?proyecto=" + obj.id + "><h4>" + obj.name + "</h4></a>\n"
                    + "                                    <p> " + text + "\n"
                + "                                    </p>\n"
                + "                                    <a class=\"more_btn\" href=\"#\">" + obj.category+ "</a>\n"
//                + "                                    <p >1 postulacion</p>\n"
                + "                                    <a href=GestionarProyecto.html?proyecto=" + obj.id + "><p >Gestionar</p></a>\n"
                + "                                    <p class=\"text-sm-right collor text-success\">" + obj.price + " $</p>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>";
            $("#proyectos").append(val);
        }
    } else {
        $("#respuesta").html(resultado.message);
    }
}

function procesarProyectosFreelancer(resp) {
    if (resp.success) {
        var json = $.parseJSON(JSON.stringify(resp));
        var arr = json.response;
        
        var html = "";
        
        $.each(arr, function(i, obj) {
            if (obj.estado == "") {
                return true;
            }
            
            html += "<tr>";
            html += "  <td>" + obj.nombre + "</td>";
            
            if (obj.estado == "pendiente") {
                html += "  <td>Solicitud en espera de confirmacion</td>";
            } else {
                html += "  <td>" + obj.estado + "</td>";
            }
                  
            if (obj.estado == "pendiente") {
                html += "  <td><button onclick='confirmarSolicitud(" + obj.id_solicitud + ");'><i class='fas fa-check-circle'></i>Aceptar</button></td>";
            }
            
            html += "</tr>";
        });
        
        $("#table_versol").html(html);
        
    } else {
        alert(resp.message);
    }
}

function procesarConfirmarSolicitud(resp) {
    alert(resp.message);
}

function confirmarSolicitud(id) {
    var obj = new Object();
    obj.id = id;
    
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/solicitud/confirmar5",
            'data': JSON.stringify(obj),
            'dataType': 'json',
            'success': procesarConfirmarSolicitud
        });
}

$(document).ready(function () {
    // aca se corrige la referencia hacia el perfil del usuario
    if (sessionStorage.getItem("usuarioId") !== null) {
        $(".ingresar").attr("href", "../perfil.html");
    }else{
        var url = "../ingresar.html"; 
        $(location).attr('href',url);        
    }    

    var objUser = sessionStorage.getItem("usrLog");
    
    if (!objUser) return;
    
    objUser = JSON.parse(objUser);
    
    // SI ES DE TIPO FREELANCER, traigo todos los proyectos
    if (objUser.type == 2) {
        jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
            'type': 'GET',
            'url': "../api/proyecto/proyectos_freelancer5/" + sessionStorage.getItem("idUser"),
            'success': procesarProyectosFreelancer
        });
    } else { // SI ES DE TIPO CONTRATISTA
        jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
            'type': 'GET',
            'url': "../api/proyecto/proyectos_contratista/" + sessionStorage.getItem("idUser"),
            'success': resultado
        });
        
        $("#tabla-proyectos").hide();
    }
    
    
    
//    jQuery.ajax({
//        headers: {
//            'Accept': 'application/json',
//            'Content-Type': 'application/json'
//        },
//        'type': 'GET',
//        'url': "../api/proyecto/proyectos_contratista/" + sessionStorage.getItem("idUser"),
//        'success': resultado
//    });
});
function ingresar() {

}



function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
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
