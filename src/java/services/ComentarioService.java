package services;

import com.google.gson.Gson;
import dao.ComentarioDao;
import dto.Comentario;
import factory.FactoryDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("comentario")
public class ComentarioService {

    // api/solicitud/insertar
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String insertar(Comentario param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ComentarioDao dao = factory.newComentarioDao();
            
            int idGenerado = dao.insert(param);
            
            if (idGenerado == 0) {
                respuesta.setMessage("Hubo un error al insertar el comentario");
            } else {
                param.setId(idGenerado);
            
                respuesta.setSuccess(true);
                respuesta.setMessage("Comentario creado");
                respuesta.setResponse(param);
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getComentariosDeSolicitud(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ComentarioDao dao = factory.newComentarioDao();

            List<Comentario> lista = dao.get(id);
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Comentarios");
            respuesta.setResponse(lista);
        } catch (Exception e) {
        }
        
        return new Gson().toJson(respuesta);
    }
    
}