function resultado(resultado) {
    if (resultado.success) {
        for (var i in resultado.response) {
        var obj = resultado.response[i];

        var val = "			<div class=\"col-lg-4 col-md-6\">\n"
                + "                            <div class=\"l_news_item\">\n"
                + "                                <!--<div class=\"l_news_img\"><a href=\"#\"><img class=\"img-fluid\" src=\"img/blog/l-news/l-news-1.jpg\" alt=\"\"></a></div>-->\n"
                + "                                <div class=\"l_news_content\">\n"
                + "                                    <a href=verProyecto.html?proyecto=" + obj.id + "><h4>" + obj.name + "</h4></a>\n"
                + "                                    <p> " + obj.description + "\n"
                + "                                    </p>\n"
                + "                                    <a class=\"more_btn\" href=\"#\">" + obj.category+ "</a>\n"
                + "                                    <p >1 postulacion</p>\n"
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

$(document).ready(function () {
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'GET',
        'url': "../api/proyecto",
        'success': resultado
    });
});
function ingresar() {

}
