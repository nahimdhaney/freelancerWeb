package dto;

public class VistaSolicitudes {

    private int id_solicitud;
    private int id_freelancer;
    private String freelancer;
    private double oferta;

    public VistaSolicitudes() {
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String freelancer) {
        this.freelancer = freelancer;
    }

    public double getOferta() {
        return oferta;
    }

    public void setOferta(double oferta) {
        this.oferta = oferta;
    }
    
    
}