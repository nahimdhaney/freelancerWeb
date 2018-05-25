/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    if (sessionStorage.getItem("usrLog")) {
        var usr_log = sessionStorage.getItem("usrLog");
        $("#correo").val(usr_log.email);
        $("#nombreUsuario").val(usr_log.user);
        $("#nombreCompleto").val(usr_log.fullName);
        $("#biblografia").val(usr_log.description);
        get_proyectos();
    }
});

function aceptar_modifi() {
    var nombreCompleto = $("#nombreCompleto").val();
    var biblio = $("#biblografia").val();
    var correcto = true;
    if (nombreCompleto == null) {
        $("#nombreCompleto").css("background-color", "#d6554d");
        correcto = false;
    }
    if (biblio == null) {
        $("#biblografia").css("background-color", "#d6554d");
        correcto = false;
    }
    if (correcto) {

        var usuario = new Object();
        usuario.id = sessionStorage.getItem("usuarioId");
        usuario.fullName = nombreCompleto;
        usuario.description = biblio;
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
}
function resultado(resultado) {

    if (resultado.success) {
        var url = "index.html";
        $(location).attr('href', url);
    } else {
        alert(resultado.mensaje);
    }
}

function get_proyectos() {
         
        var id = sessionStorage.getItem("usuarioId");
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': "api/proyecto/proyectos_contratista/"+id,
            'dataType': 'json',
            'success': cargar_proyectos
        });
    }
function cargar_proyectos(resultado){
      if (resultado.success) {
      var html="";
      $.each(resultado.response,function(i,obj){
          html+="<tr><td>"+obj.name+"</td><td>"+obj.category+"</td><td><i class='fas fa-edit btn_icon' onclick='editar_proyecto("+obj.id+");'></i><i class='far fa-list-alt btn_icon' onclick='ver_proyecto("+obj.id+");'></i></td></tr>";
      });
      $("#table_mis_proyectos").html(html);
    } else {
        alert(resultado.mensaje);
    }
}

function editar_proyecto(id){
    window.location="";
}
function ver_proyecto(id){
  window.location="Proyectos/verProyecto.html?proyecto="+id;
}