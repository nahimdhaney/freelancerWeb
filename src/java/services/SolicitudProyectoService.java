package services;

import com.google.gson.Gson;
import dao.SolicitudProyectoDao;
import dto.SolicitudProyecto;
import factory.FactoryDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("solicitud")
public class SolicitudProyectoService {
    
    // api/solicitud/insertar
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String insertar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
            int idGenerado = dao.insert(param);
            
            if (idGenerado == 0) {
                respuesta.setMessage("Hubo un error al insertar la solicitud de proyecto");
            } else {
                SolicitudProyecto objSolicitud = new SolicitudProyecto();
                objSolicitud.setId(idGenerado);
                objSolicitud.setProjectId(param.getProjectId());
                objSolicitud.setFreelancerId(0);
                objSolicitud.setState("");
            
                respuesta.setSuccess(true);
                respuesta.setMessage("Solicitud creada");
                respuesta.setResponse(objSolicitud);
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    // api/solicitud/actualizar
    @Path("actualizar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String actualizar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
            int filasAfectadas = dao.update(param);
            
            if (filasAfectadas == 0) {
                respuesta.setMessage("Hubo un error al actualizar la solicitud de proyecto");
            } else {
                respuesta.setSuccess(true);
                respuesta.setMessage("Solicitud de proyecto actualizada");
                respuesta.setResponse("");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    // api/solicitud/eliminar
    @Path("eliminar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String eliminar(SolicitudProyecto param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();
            
            int filasAfectadas = dao.delete(param.getId());
            
            if (filasAfectadas == 0) {
                respuesta.setMessage("Hubo un error al eliminar la solicitud de proyecto");
            } else {
                respuesta.setSuccess(true);
                respuesta.setMessage("Solicitud eliminada");
                respuesta.setResponse("");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    // api/solicitud/
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitudes() {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

            List<SolicitudProyecto> solicitudes = dao.get();
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de solicitudes de proyectos");
            respuesta.setResponse(solicitudes);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSolicitud(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            SolicitudProyectoDao dao = factory.newSolicitudProyectoDao();

            SolicitudProyecto objProyecto = dao.get(id);
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Solicitud de proyecto");
            respuesta.setResponse(objProyecto);
        } catch (Exception e) {
        }
        
        return new Gson().toJson(respuesta);
    }

}