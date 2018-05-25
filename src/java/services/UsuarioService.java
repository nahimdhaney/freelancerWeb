package services;

import com.google.gson.Gson;
import dao.CodigoRecuperacionDao;
import dao.UsuarioDao;
import dto.CodigoRecuperacion;
import dto.Usuario;
import factory.FactoryDao;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("usuario")
public class UsuarioService {
    
    @Path("test")
    @GET
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    public String prueba() {
        Response respuesta = new Response();
        
        respuesta.setSuccess(true);
        respuesta.setMessage("Hello earth!");
        
        return new Gson().toJson(respuesta);
    }
    
    // api/usuario/login
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8") // lo que va a recibir
    public String login(Usuario param) {
        Response respuesta = new Response();
         
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            Usuario objUsuario = dao.login(param.getUser(), param.getPassword());
            
            if (objUsuario != null) {
                objUsuario.setPassword("");
                objUsuario.setEnabled(true);
                
                respuesta.setSuccess(true);
                respuesta.setMessage("Ingreso correcto");
                respuesta.setResponse(objUsuario);
            } else {
                respuesta.setMessage("Usuario y/o contraseña incorrectos w " + param.getUser());
            }
            
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String registrarse(Usuario param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            Usuario usuario = dao.getByUserName(param.getUser());
            
            if (usuario != null) {
                respuesta.setMessage("El nombre de usuario ya está siendo ocupado");
                return new Gson().toJson(respuesta);
            }
            
            int idGenerado = dao.insert(param);
            
            if (idGenerado == 0) {
                respuesta.setMessage("El nombre de usuario/correo ya está siendo ocupado");
            } else {
                Usuario objUsuario = new Usuario();
                objUsuario.setId(idGenerado);
                objUsuario.setEnabled(true);
                objUsuario.setType(param.getType());
                objUsuario.setDescription(param.getDescription());
            
                respuesta.setSuccess(true);
                respuesta.setMessage("Registro exitoso");
                respuesta.setResponse(objUsuario);
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("changePassword")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String cambiarContraseña(UsuarioRecuperacion param) {
        Response respuesta = new Response();
       
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            Usuario objUsuario = dao.login(param.getUser(), param.getPassword());

            if (objUsuario != null) {
                objUsuario.setPassword(param.getNewPassword());
                int filasAfectadas = dao.update(objUsuario);
                
                if (filasAfectadas > 0) {
                    respuesta.setSuccess(true);
                    respuesta.setMessage("Contraseña cambiada correctamente");
                } else {
                    respuesta.setMessage(("Hubo un error al cambiar la contraseña"));
                }
                
            } else {
                respuesta.setMessage("Contraseña incorrecta");
            }
            
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("passwordForgotten")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String recuperarContrasenia(UsuarioRecuperacion param) {
        Response respuesta = new Response();
        
        final String username = "kevinduran1997@gmail.com";
        final String password = "subt3nientE";

//        para poder enviar correos desde gmail
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao usuarioDao = factory.newUsuarioDao();
            CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
            
            Usuario usuario = usuarioDao.getByUserName(param.getUser());
            
            if (usuario != null) {
                CodigoRecuperacion objRecuperacion = new CodigoRecuperacion(0, 
                    getCodigo(), getNext24HrsDate(), false, usuario.getId());
                
                int idGenerado = recuperacionDao.insert(objRecuperacion);
                
                if (idGenerado == 0) {
                    respuesta.setMessage("Hubo un error al crear el código de recuperacion");
                } else {
                    objRecuperacion.setId(idGenerado);
                    
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("kevinduran1997@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, 
                                InternetAddress.parse(usuario.getEmail()));
                        message.setSubject("Tu código de recuperación");
                        message.setText("localhost:8080/freelancerWeb"
                                + "/validarRegistro.html?usuario=" 
                                + param.getUser() + "&codigoConfirmacion=" 
                                + objRecuperacion.getCode());

                        Transport.send(message);

                        respuesta.setSuccess(true);
                        respuesta.setMessage("Se envió un código de recuperación a tu correo");
                    } catch (MessagingException e) {
                        respuesta.setMessage("Hubo un error al enviar el correo con el código de recuperación "+ e.getMessage());
                    }
                }

            } else {
                respuesta.setMessage("No existe un usuario con ese nombre de usuario registrado");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("validateCode")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String validarContrasenia(UsuarioRecuperacion param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao usuarioDao = factory.newUsuarioDao();
            CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
            
            Usuario usuario = usuarioDao.getByUserName(param.getUser());
            
            if (usuario != null) {
                CodigoRecuperacion objRecuperacion = 
                        recuperacionDao.verificar(usuario.getId(), param.getCode());
                
                if (objRecuperacion != null) {
                    respuesta.setSuccess(true);
                    respuesta.setMessage("Código de verificación válido");
                } else {
                    respuesta.setMessage("El código es inválido o ya fué utilizado");
                }

            } else {
                respuesta.setMessage("No existe un usuario con ese nombre de usuario registrado");
            }

        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    @Path("newPassword")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String nuevaContrasenia(UsuarioRecuperacion param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
            
            Usuario objUsuario = dao.getByUserName(param.getUser());
            
            if (objUsuario != null) {
                CodigoRecuperacion objRecuperacion = 
                        recuperacionDao.verificar(objUsuario.getId(), param.getCode());
                
                if (objRecuperacion != null) {
                    objUsuario.setPassword(param.getNewPassword());
                    int filasAfectadas = dao.update(objUsuario);

                    if (filasAfectadas > 0) {
                        objRecuperacion.setUsed(true);
                        int actualizacionExitosa = recuperacionDao.update(objRecuperacion);
                        
                        respuesta.setSuccess(true);
                        respuesta.setMessage("Contraseña cambiada correctamente");
                    } else {
                        respuesta.setMessage("Hubo un error al cambiar la contraseña");
                    }
                } else {
                    respuesta.setMessage("El código no es válido o ya fué utilizado");
                }
                
            } else {
                respuesta.setMessage("No existe un usuario con ese nombre de usuario");
            }
            
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
    public static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private String getCodigo() {
        String codigo = "";
        
        for (int i = 0; i < 8; i++) {
            int posicion = (int) (Math.random() * LETRAS.length());
            codigo += LETRAS.charAt(posicion);
        }
        
        return codigo;
    }
    
    private String getNext24HrsDate() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(dt); 
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        
        // 2018-04-23 12:50:01
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = formato.format(dt);
        return fechaFormateada;
    }
    
    // api/usuario/
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsuarios() {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();

            List<Usuario> usuarios = dao.get();
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Lista de usuarios");
            respuesta.setResponse(usuarios);
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }

        return new Gson().toJson(respuesta);
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProyecto(@PathParam("id") int id) {
        Response respuesta = new Response();

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();

            Usuario objUsuario = dao.get(id);
            
            respuesta.setSuccess(true);
            respuesta.setMessage("Usuario");
            respuesta.setResponse(objUsuario);
        } catch (Exception e) {
        }
        
        return new Gson().toJson(respuesta);
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String actualizar(Usuario param) {
        Response respuesta = new Response();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            Usuario usuario = dao.getByUserName(param.getUser());
            
            param.setId(usuario.getId());
            
            int filasAfectadas = dao.update(param);
            
            if (filasAfectadas == 0) {
                respuesta.setMessage("Hubo un error al actualizar los datos del usuario");
            } else {
                respuesta.setSuccess(true);
                respuesta.setMessage("Usuario actualizado");
                respuesta.setResponse("");
            }
        } catch (Exception e) {
            respuesta.setMessage("Error de autenticación");
        }
        
        return new Gson().toJson(respuesta);
    }
    
}