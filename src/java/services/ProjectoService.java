package services;

import com.google.gson.Gson;
import dao.ProyectoDao;
import dto.Proyecto;
import factory.FactoryDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("proyecto")
public class ProjectoService {

    // api/proyecto/insertar
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String insertar(Proyecto param) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            int idGenerado = dao.insert(param);

            if (idGenerado == 0) {
                respuesta.setMessage("Hubo un error al insertar el proyecto");
            } else {
                Proyecto objProyecto = new Proyecto();
                objProyecto.setId(idGenerado);
                objProyecto.setName(param.getName());
                objProyecto.setDescription(param.getDescription());
                objProyecto.setCategory(param.getCategory());
                objProyecto.setPrice(param.getPrice());
                objProyecto.setDate(param.getDate());
                objProyecto.setStart("");
                objProyecto.setEnd("");
                objProyecto.setOwnerId(param.getOwnerId());
                objProyecto.setFreelancerId(0);

                respuesta.setSuccess(true);
                respuesta.setMessage("Proyecto creado");
                respuesta.setResponse(objProyecto);
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/actualizar
    @Path("actualizar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8") // lo que va a recibir
    public String actualizar(Proyecto param) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            int filasAfectadas = dao.update(param);

            if (filasAfectadas == 0) {
                respuesta.setMessage("Hubo un error al actualizar los datos del proyecto");
            } else {
                respuesta.setSuccess(true);
                respuesta.setMessage("Proyecto actualizado");
                respuesta.setResponse("");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/eliminar
    @Path("eliminar")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public String eliminar(Proyecto param) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            int filasAfectadas = dao.delete(param.getId());

            if (filasAfectadas == 0) {
                respuesta.setMessage("Hubo un error al eliminar el proyecto");
            } else {
                respuesta.setSuccess(true);
                respuesta.setMessage("Proyecto eliminado");
                respuesta.setResponse("");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/categoria/ejemplo
    @Path("/categoria/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyectoConCategoria(@PathParam("id") String id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            List<Proyecto> proyectos = dao.get();
            for (Proyecto proyecto : proyectos) {
                if(!proyecto.getCategory().equals(id)){
                    proyectos.remove(proyecto);
                }
            }
            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }    // api/proyecto/categoria/ejemplo
    
    
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyectos() {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();
            
            List<Proyecto> proyectos = dao.get();
            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }
    // api/proyecto/proyectos_contratista/

    
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyecto(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            Proyecto objProyecto = dao.get(id);

            respuesta.setSuccess(true);
            respuesta.setMessage("Proyecto");
            respuesta.setResponse(objProyecto);
        } catch (Exception e) {
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/proyectos_contratista/
    @Path("proyectos_contratista/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyectosDeContratista(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            List<Proyecto> proyectos = dao.getProjectsOfOwner(id);

            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/proyectos_categoria/
    @Path("proyectos_categoria/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyectosCategoria(@PathParam("id") String id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            List<Proyecto> proyectos = dao.get();
            for (Proyecto proyecto : proyectos) {
                if(!proyecto.getCategory().equals(id)){
                    proyectos.remove(proyecto);
                }
            }
            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de " + e.getMessage());
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/proyectos_freelancer/
    @Path("proyectos_freelancer/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyectosDeFrelancer(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            List<Proyecto> proyectos = dao.getProjectsOfFreelancer(id);

            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

    // api/proyecto/buscar/
    @Path("buscar/{valor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscar(@PathParam("valor") String valor) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            ProyectoDao dao = factory.newProyectoDao();

            List<Proyecto> proyectos = dao.search(valor);

            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de proyectos");
            respuesta.setResponse(proyectos);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }

}
