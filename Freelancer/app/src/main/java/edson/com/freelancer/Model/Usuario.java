package edson.com.freelancer.Model;

/**
 * Created by Edson on 21/04/2018.
 */

public class Usuario {

    private String username;
    private String contraseña;
    private char estado;
    private String correoElectronico;

    public Usuario(){
    }

    public Usuario(String usuario, String contraseña, char estado, String correoElectronico) {
        this.username = usuario;
        this.contraseña = contraseña;
        this.estado = estado;
        this.correoElectronico = correoElectronico;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
