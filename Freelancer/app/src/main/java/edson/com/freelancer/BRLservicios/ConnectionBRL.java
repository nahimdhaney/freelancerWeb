package edson.com.freelancer.BRLservicios;

/**
 * Created by Edson on 22/04/2018.
 */

public class ConnectionBRL {

    String url;

    //casa de elmer
    //String url = "http://192.168.1.4:8080/RedSocialWeb/ServletRegistro";

    public String cx(){
        url = "http://192.168.43.77:8080/RedSocialWeb/ServletRegistro" ;
        return url;
    }
}
