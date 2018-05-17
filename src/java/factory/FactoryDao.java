package factory;

import dao.CodigoRecuperacionDao;
import dao.ProyectoDao;
import dao.SolicitudProyectoDao;
import dao.UsuarioDao;

public class FactoryDao {

    protected static FactoryDao instancia;
    
    public static FactoryDao getOrCreate() {
        if (instancia == null) {
            instancia = new FactoryDao();
        }
        
        return instancia;
    }

    public FactoryDao() {
    }

    public UsuarioDao newUsuarioDao() {
        return new UsuarioDao();
    }
    
    public CodigoRecuperacionDao newCodigoRecuperacionDao() {
        return new CodigoRecuperacionDao();
    }
    
    public ProyectoDao newProyectoDao() {
        return new ProyectoDao();
    }
    
    public SolicitudProyectoDao newSolicitudProyectoDao() {
        return new SolicitudProyectoDao();
    }
    
}