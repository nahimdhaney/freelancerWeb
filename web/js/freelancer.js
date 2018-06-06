
function resultado(resultado) {
    if (resultado.success) {
        for (var i in resultado.response) {
            var obj = resultado.response[i];
            var text = obj.description;
            if (text == null) {
                text = " ";
            }
            if (text.length > 120) {
                var text = text.substr(0, text.lastIndexOf(' ', 117)) + '...';
            }
//            var text = 'pequeña descripcion del usuario';
            var val = "			<div class=\"col-lg-4 col-md-6\">\n"
                    + "                            <div class=\"l_news_item\">\n"
                    + "                                <!--<div class=\"l_news_img\"><a href=\"#\"><img class=\"img-fluid\" src=\"img/blog/l-news/l-news-1.jpg\" alt=\"\"></a></div>-->\n"
                    + "                                <div class=\"l_news_content\">\n"
                    + "                                    <a href=verFreelancer.html?freelancer=" + obj.id + "><h4>" + obj.fullName + "</h4></a>\n"
                    + "                                    <p> " + text + "\n"
                    + "                                    </p>\n"
                    + "                                    <a class=\"more_btn\" href=\"#\">" + obj.email + "</a>\n"
                    + "                                    <p class=\"text-danger\" id='postulacion" + obj.id + "'  ></p>\n"
//            if (obj.precio == '0.0') {
                +"                                    <p class=\"text-sm-right collor text-success\">" + obj.precio + " $ x hora</p>\n"
//            } else {
//                +"                                    <p class=\"text-sm-right collor text-success\">" + obj.precio + " $ x hora</p>\n"
//            }
            +"                                </div>\n"
                    + "                            </div>\n"
                    + "                        </div>";
            $("#proyectos").append(val);
        }
        /* jQuery.ajax({
         headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
         },
         'type': 'GET',
         'url': "../api/solicitud",
         'success': aumetarPostuaciones
         });*/
    } else {
        $("#respuesta").html(resultado.message);
    }
}
/*
 function aumetarPostuaciones(resultado) {
 if (resultado.success) {
 var array_elements = [];
 for (var i in resultado.response) {
 var obj = resultado.response[i];
 array_elements.push(obj.projectId);
 }
 array_elements.sort();
 
 var current = null;
 var cnt = 0;
 for (var i = 0; i < array_elements.length; i++) {
 if (array_elements[i] != current) {
 if (cnt > 0) {
 if (cnt > 1) {
 $("#postulacion" + current).text(cnt + " postulaciones");
 } else {
 $("#postulacion" + current).text(cnt + " postulacion");
 }
 //                    console.log(current + ' comes --> ' + cnt + ' times<br>');
 }
 current = array_elements[i];
 cnt = 1;
 } else {
 cnt++;
 }
 }
 if (cnt > 0) {
 if (cnt > 1) {
 $("#postulacion" + current).text(cnt + " postulaciones");
 } else {
 $("#postulacion" + current).text(cnt + " postulacion");
 }
 }
 
 
 }
 }
 */


$(document).ready(function () {
    // aca se corrige la referencia hacia el perfil del usuario, y "mis proyectos"
    if (sessionStorage.getItem("usuarioId") !== null) {
        $(".ingresar").attr("href", "../perfil.html");
        $(".mis-proyectos").attr("href", "../Proyectos/misProyectos.html");
    }


    var tipo = getParameterByName("val");
    if (tipo) {
        $.get("../api/proyecto/buscar", {valor: tipo}, function (resp) {
            resultado(resp);
        });
    } else {
        var val = getParameterByName('categoria');

        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/usuario/contratistas",
            'success': resultado
        });

    }

});
function ingresar() {

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

function pressenter(e, inp) {
    var tecla = (document.all) ? e.keyCode : e.which;
    if (tecla == 13) {
        var valor = $(inp).val();
        if (valor.length > 0) {
            window.location = "../Proyectos/index.html?val=" + valor;
        }
    }
}
