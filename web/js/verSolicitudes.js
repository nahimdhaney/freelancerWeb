/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    var id_proyecto = getParameterByName("proyecto");
    if (id_proyecto != null) {
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'GET',
            'url': "../api/solicitud/solicitudes5/" + id_proyecto,
            'success': resultado
        });
    }
});

function resultado(resp) {
    if (resp.success) {
        // id_solicitud, id_freelancer, freelancer, oferta
        var json=$.parseJSON(JSON.stringify(resp)) 
        var arr = json.response;
        var html = "";
        $.each(arr, function (i, obj) {
            html += "<tr>";
            html += "  <td>" + obj.freelancer + "</td>";
            html += "  <td>" + obj.oferta + "</td>";
            html += "  <td><button onclick='aceptarSolicitud(" + obj.id_solicitud + ");'><i class='fas fa-check-circle'></i>Aceptar</button></td>";
            html += "</tr>";
        });
        $("#table_versol").html(html);
    }
}
function aceptarSolicitud(id) {
    var obj = new Object();
    obj.id = id;
    
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "../api/solicitud/aceptar5",
            'data': JSON.stringify(obj),
            'dataType': 'json',
            'success': resp
        });
}


function resp(resp){
//    if(resp.success){
//        alert(resp.success);
//    }
    
    
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
