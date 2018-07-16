package edson.com.freelancer.Model;

/**
 * Created by Edson on 21/04/2018.
 */

public class Usuario {

    private int id;
    private String fullName;
    private String user;
    private String password;
    private String email;
    private boolean enabled;
    private int type;

    private static Usuario singleton;

    public Usuario(){
    }

    public static Usuario getUsuario() {
        return singleton;
    }

    public static void setUsuario(Usuario usuario) {
        singleton = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
