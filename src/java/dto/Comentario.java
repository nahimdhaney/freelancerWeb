package dto;

public class Comentario {

    private int id;
    private String mensaje;
    private String fecha;
    private int usuarioId;
    private int solicitudProyectoId;

    public Comentario() {
    }

    public Comentario(int id, String mensaje, String fecha, int usuarioId, int solicitudProyectoId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.solicitudProyectoId = solicitudProyectoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getSolicitudProyectoId() {
        return solicitudProyectoId;
    }

    public void setSolicitudProyectoId(int solicitudProyectoId) {
        this.solicitudProyectoId = solicitudProyectoId;
    }
    
}