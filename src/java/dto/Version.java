package dto;

public class Version {

    private int versionMayor;
    private int versionMenor;
    private int parche;

    public Version() {
    }

    public int getVersionMayor() {
        return versionMayor;
    }

    public void setVersionMayor(int versionMayor) {
        this.versionMayor = versionMayor;
    }

    public int getVersionMenor() {
        return versionMenor;
    }

    public void setVersionMenor(int versionMenor) {
        this.versionMenor = versionMenor;
    }

    public int getParche() {
        return parche;
    }

    public void setParche(int parche) {
        this.parche = parche;
    }
    
}