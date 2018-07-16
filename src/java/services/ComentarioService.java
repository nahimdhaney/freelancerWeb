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

    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String insertar(Comentario param) {
        Response respuesta = new Response();
        
        FactoryDao factory = FactoryDao.getOrCreate();
        ComentarioDao dao = factory.newComentarioDao();
            
        int idGenerado = 0;
        
        try {
            idGenerado = dao.insert(param);
        } catch (Exception ex) {
            respuesta.setMessage("Hubo un error al insertar el comentario");
            return new Gson().toJson(respuesta);
        }
            
        if (idGenerado == 0) {
            respuesta.setMessage("Hubo un error al insertar el comentario");
            return new Gson().toJson(respuesta);
        } 
        
        param.setId(idGenerado);
            
        respuesta.setSuccess(true);
        respuesta.setMessage("Comentario creado");
        respuesta.setResponse(param);
                
        return new Gson().toJson(respuesta);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getComentariosDeSolicitud(@PathParam("id") int id) {
        Response respuesta = new Response();

        FactoryDao factory = FactoryDao.getOrCreate();
        ComentarioDao dao = factory.newComentarioDao();

        List<Comentario> lista = dao.get(id);

        respuesta.setSuccess(true);
        respuesta.setMessage("Comentarios");
        respuesta.setResponse(lista);
        
        return new Gson().toJson(respuesta);
    }
    
}