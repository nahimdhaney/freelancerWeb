
function resultado(resultado) {
    var objUser = sessionStorage.getItem("usrLog");
    objUser = JSON.parse(objUser);
            
    if (resultado.success) {
        for (var i in resultado.response) {
            var obj = resultado.response[i];
            
            // SI ES DE TIPO FREELANCER, y el proyecto YA TIENE UN FREELANCER, no lo muestro
            if (obj.freelancerId != 0 && (objUser && objUser.type == 2)) {
                continue;
            }
            
            var text = obj.description;
              if (text.length > 120) {
                var text = text.substr(0, text.lastIndexOf(' ', 117)) + '...';
              }
            var val = "			<div class=\"col-lg-4 col-md-6\">\n"
                    + "                            <div class=\"l_news_item\">\n"
                    + "                                <!--<div class=\"l_news_img\"><a href=\"#\"><img class=\"img-fluid\" src=\"img/blog/l-news/l-news-1.jpg\" alt=\"\"></a></div>-->\n"
                    + "                                <div class=\"l_news_content\">\n"
                    + "                                    <a href=verProyecto.html?proyecto=" + obj.id + "><h4>" + obj.name + "</h4></a>\n"
                    + "                                    <p> " + text + "\n"
                    + "                                    </p>\n"
                    + "                                    <a class=\"more_btn\" href=\"#\">" + obj.category + "</a>\n"
                    + "                                    <p class=\"text-danger\" id='postulacion" + obj.id + "'  ></p>\n"
                    + "                                    <p class=\"text-sm-right collor text-success\">" + obj.price + " $</p>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                        </div>";
            $("#proyectos").append(val);
        }
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/solicitud",
            'success': aumetarPostuaciones
        });
    } else {
        $("#respuesta").html(resultado.message);
    }
}

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



$(document).ready(function () {
    // aca se corrige la referencia hacia el perfil del usuario
    if (sessionStorage.getItem("usuarioId") !== null) {
        $(".ingresar").attr("href", "../perfil.html");
    }
    
    var tipo = getParameterByName("val");
    if(tipo){
        $.get("../api/proyecto/buscar",{valor:tipo},function(resp){
            resultado(resp);
        });
    }else{
         var val = getParameterByName('categoria');

    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "../api/proyecto",
        'success': resultado
    });
        
    }
    
    var usr = sessionStorage.getItem("usrLog");
    usr = JSON.parse(usr);
    
    if (usr.type == 2) {
        $("#botonCrearProyecto").hide();
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

function pressenter(e,inp){
    var tecla=(document.all) ? e.keyCode : e.which;
    if(tecla==13){
        var valor = $(inp).val();
        if(valor.length>0){
            window.location="index.html?val="+valor;
        }
    }
}
