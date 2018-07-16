package services;

public class Respuesta {
    
    private boolean esOk;
    private String mensaje;

    public Respuesta() {
    }

    public boolean isEsOk() {
        return esOk;
    }

    public void setEsOk(boolean esOk) {
        this.esOk = esOk;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}