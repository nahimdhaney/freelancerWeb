package services;

import com.google.gson.Gson;
import dao.SolicitudProyectoDao;
import dto.SolicitudFreelancer;
import dto.SolicitudProyecto;
import dto.VistaSolicitudes;
import factory.FactoryDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("solicitud")
public class SolicitudProyectoService {
    
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String insertar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        int idGenerado = 0;
        
        try {
            idGenerado = dao.insert(param);
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al insertar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }

        if (idGenerado == 0) {
            respuesta.setMessage("Hubo un error al insertar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        } 
        
        param.setId(idGenerado);

        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud creada");
        respuesta.setResponse(param);
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("actualizar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String actualizar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        int filasAfectadas;
        
        try {
            filasAfectadas = dao.update(param);
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al actualizar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
            
        if (filasAfectadas == 0) {
            respuesta.setMessage("Hubo un error al actualizar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        } 
        
        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud de proyecto actualizada");
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("eliminar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String eliminar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        int filasAfectadas;
        try {
            filasAfectadas = dao.delete(param.getId());
        } catch (SQLException ex) {
            respuesta.setMessage("Hubo un error al eliminar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }

        if (filasAfectadas == 0) {
            respuesta.setMessage("Hubo un error al eliminar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
        
        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud eliminada");
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitudes() {
        Response respuesta = new Response();

        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        List<SolicitudProyecto> solicitudes = dao.get();

        respuesta.setSuccess(true);
        respuesta.setMessage("Lista de solicitudes de proyectos");
        respuesta.setResponse(solicitudes);

        return new Gson().toJson(respuesta);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitud(@PathParam("id") int id) {
        Response respuesta = new Response();

        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        List<SolicitudProyecto> objProyecto = dao.get(id);

        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud de proyecto");
        respuesta.setResponse(objProyecto);
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("/solicitudes/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitudes(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

            List<SolicitudFreelancer> objProyecto = dao.getSolicitudes(id);
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Solicitud de proyecto");
            respuesta.setResponse(objProyecto);
        } catch (Exception e) {
            
        }
        
        return new Gson().toJson(respuesta);
    }

    
    @Path("/solicitudes5/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitudes5(@PathParam("id") int id) {
        Response respuesta = new Response();

        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

        List<VistaSolicitudes> lista = dao.getSolicitudes5(id);

        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitudes de proyecto");
        respuesta.setResponse(lista);
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("aceptar5")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String aceptar5(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
        int filasAfectadas;
        
        try {
            filasAfectadas = dao.aceptar5(param.getId());
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al aceptar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
            
        if (filasAfectadas == 0) {
            respuesta.setMessage("Hubo un error al aceptar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
        
        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud de proyecto aceptada");
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("confirmar5")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String confirmar5(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
        int filasAfectadas;
        
        try {
            filasAfectadas = dao.confirmar5(param.getId());
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al confirmar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
            
        if (filasAfectadas == 0) {
            respuesta.setMessage("Hubo un error al confirmar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
            
        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud de proyecto confirmada");
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("getSolicitudEntreFreelancerYproyecto")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String getSolicitudEntreFreelancerYproyecto(SolicitudFreelancer param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
        int filasAfectadas;
        try {
            filasAfectadas = dao.confirmar5(param.getId());
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al confirmar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
        
        List<SolicitudProyecto> lista = dao.getSolicitudEntreFrelancerYproyecto(
                param.getFreelancerId(), 
                param.getProjectId()
        );
            
        if (filasAfectadas == 0) {
            respuesta.setMessage("Hubo un error al confirmar la solicitud de proyecto");
            return new Gson().toJson(respuesta);
        }
        
        respuesta.setSuccess(true);
        respuesta.setMessage("Solicitud de proyecto");
        respuesta.setResponse(lista);
        
        return new Gson().toJson(respuesta);
    }
    
}
