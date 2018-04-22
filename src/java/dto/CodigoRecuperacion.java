package dto;

import java.text.SimpleDateFormat;

public class CodigoRecuperacion {

    private int id;
    private String code;
    private String date;
    private boolean used;
    private int userId;

    public CodigoRecuperacion() {
    }

    public CodigoRecuperacion(int id, String code, String date, boolean used, int userId) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.used = used;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String fechaFormateada() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaFormateada = formato.format(date);
        return fechaFormateada;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}