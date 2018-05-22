function resultado(resultado) {
    if (resultado.success) {
        var obj = resultado.response;
        $("#categoria").append(obj.category);
        $("#fecha").append(obj.date);
        $("#presupuesto").append(obj.price.toString() + "  USD");
 /*    "response": {
        "id": 1,
        "name": "PriceWaterHouse",
        "description": "Desarrollo de pagina Web comercial",
        "price": 8000,
        "date": "2018-05-16 08:32:16.0",
        "ownerId": 13,
        "freelancerId": 0
    }*/
        
    } else {
        $("#respuesta").html(resultado.message);
    }
}

$(document).ready(function () {
        var val = getParameterByName('proyecto');
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "../api/proyecto/" + val,
        'success': resultado
    });
});


function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}