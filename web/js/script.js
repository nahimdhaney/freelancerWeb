

$(document).ready(function () {
    if (sessionStorage.getItem("usuarioId") !== null) {
        $(".ingresar").text(sessionStorage.getItem("usuarioId"));
        $(".ingresar").attr("href", "perfil.html");
        $(".registrarse").text("Salir");
        $(".registrarse").attr("href", "");
        $("#cambcon").attr("href", "modificaContraseña.html");
        $("#cambcon").text("Modificar Contraseña");
        $("#indice").append('<li class="nav-item"><a class="nav-link mis-proyectos" href="misProyectos.html">Mis Proyectos</a></li>');



        
        
        $(".registrarse").click(function () {
            sessionStorage.removeItem("usuarioId");
            var url = "index.html";
            $(location).attr('href', url);
        });

//        alert(sessionStorage.getItem("usuarioId"))
//    $(".sesion").html();
    } else {
//        $(".sesion").html("");
    }
//    alert("Creando Local")
//    if (!localStorage.getItem("carrito"))
//        localStorage.setItem("carrito", "{}");
});




function Realizar() {
    var valor1 = document.getElementById("valor1").value;
    var seleccionado = document.getElementById("funcion");
    //document.getElementById("msg-1").innerHTML = seleccionado.options[seleccionado.selectedIndex].value;
    var escogido = seleccionado.options[seleccionado.selectedIndex].value;
    var vlrResultado;
    if (escogido == 1) {
        valor1 = parseInt(document.getElementById("valor1").value);
        var valor2 = parseInt(document.getElementById("valor2").value);
        var valor3 = parseInt(document.getElementById("valor3").value);
//		document.getElementById("respuesta").innerHTML = (valor1+valor2+valor3);
        vlrResultado = nroMayor(valor1, valor2, valor3);
    }
    if (escogido == 2) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = serie(valor1);
    }
    if (escogido == 3) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = fibonacci(valor1);
    }
    if (escogido == 4) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = primo(valor1);
        if (vlrResultado == false)
            vlrResultado = "FALSO"
    }
    if (escogido == 5) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = Nprimo(valor1);
    }
    if (escogido == 6) {
        vlrResultado = Invertir(valor1);
    }
    if (escogido == 7) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = factorial(valor1);
    }
    if (escogido == 8) {
        valor1 = parseInt(document.getElementById("valor1").value);
        vlrResultado = Binario(valor1);
    }

    if (typeof vlrResultado == "undefined") {
        vlrResultado = "Error en la forma tipeada, por favor ingrese los datos valores correctamente!"
    }
    if (vlrResultado) {
        document.getElementById("respuesta").innerHTML = vlrResultado;
    } else {
        document.getElementById("respuesta").innerHTML = "Error en la forma tipeada, por favor ingrese los datos valores correctamente!";
    }


//	return nroMayor(valor1,valor2,valor3);
}


function pressenter(e,inp){
    var tecla=(document.all) ? e.keyCode : e.which;
    if(tecla==13){
        var valor = $(inp).val();
        if(valor.length>0){
            window.location="Proyectos/index.html?val="+valor;
        }
    }
}

