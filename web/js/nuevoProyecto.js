/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$(document).ready(function (){
    if (sessionStorage.getItem("usuarioId") !== null) {
        
    }else{
        
        var url = "../ingresar.html"; 
        $(location).attr('href',url);        
        
    }    
});

function resultado(resultado) {
    if(resultado.success){
        var url = "index.html"; 
        $(location).attr('href',url);
    }else{
        $("#respuesta").html(resultado.message);        
    }
}

function ingresar() {
    
    
/*    {
"id": 0,
    "name": "Canchas",
    "description": "Para hacer reservas de canchas en Santa Cruz",
    "price": 800,
    "date": "2018-05-16 08:32:16",
    "start": null,
    "end": null,
    "ownerId": 6,
    "freelancerId": 0
}*/
    
    var name = $("#name").val();
    var description = $("#descripcion").val();
    var price = $("#price").val();
    var date = $("#fecha").val();
    var id = 0;
    var freelancerId = 0;
    var OwnerId = sessionStorage.getItem("idUser");

    
    var proyecto = new Object();
    proyecto.name = name;
    proyecto.description = description;
    proyecto.price = price;
    proyecto.id = id;
    proyecto.freelancerId = freelancerId;
    proyecto.date = date;
    proyecto.category = $("#categoria option:selected").text();
    proyecto.ownerId = OwnerId;
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': "../api/proyecto/insertar",
        'data': JSON.stringify(proyecto),
        'dataType': 'json',
        'success': resultado
    });
}





