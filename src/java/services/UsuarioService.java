package services;

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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("usuario")
public class UsuarioService {
    
    @Path("test")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public Respuesta prueba(Usuario param) {
        Respuesta respuesta = new Respuesta();
        
        respuesta.setMensaje("Hello earth");
        respuesta.setEsOk(true);
        
        return respuesta;
    }
    
    // api/usuario/login
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON) // lo que va a devolver
    @Consumes(MediaType.APPLICATION_JSON) // lo que va a recibir
    public Respuesta login(dto.Usuario param) {
        Respuesta respuesta = new Respuesta();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            dto.Usuario objUsuario = dao.login(param.getUser(), param.getPassword());
            
            if (objUsuario != null) {
                respuesta.setEsOk(true);
                respuesta.setMensaje(Integer.toString(objUsuario.getId()));
            } else {
                respuesta.setMensaje("usuario y/o contraseña incorrectos");
            }
            
        } catch (Exception e) {
            respuesta.setMensaje("Error de autenticacion");
        }
        
        return respuesta;
    }
    
    @Path("register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrarse(dto.Usuario param) {
        Respuesta respuesta = new Respuesta();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            dto.Usuario usuario = dao.getByUserName(param.getUser());
            
            if (usuario != null) {
                respuesta.setMensaje("el nombre de usuario ya está siendo ocupado");
                return respuesta;
            }
            
            int idGenerado = dao.insert(param);
            
            if (idGenerado == 0) {
                respuesta.setMensaje("El nombre de usuario/correo ya está siendo ocupado");
            } else {
                param.setId(idGenerado);
            
                respuesta.setEsOk(true);
                respuesta.setMensaje(Integer.toString(idGenerado));
            }
        } catch (Exception e) {
            respuesta.setMensaje("Error de autenticacion");
        }
        
        return respuesta;
    }
    
    @Path("changePassword")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta cambiarContraseña(UsuarioRecuperacion param) {
        Respuesta respuesta = new Respuesta();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
//            dto.Usuario objUsuario = dao.getByUserName(param.getUser());
            dto.Usuario objUsuario = dao.login(param.getUser(), param.getPassword());

//            if (objUsuario != null
//                    && objUsuario.getPassword().equals(param.getPassword())) {
            if (objUsuario != null) {
                
                objUsuario.setPassword(param.getNewPassword());
                int filasAfectadas = dao.update(objUsuario);
                
                if (filasAfectadas > 0) {
                    respuesta.setEsOk(true);
                    respuesta.setMensaje("Contraseña cambiada correctamente");
                } else {
                    respuesta.setMensaje(("Hubo un error al cambiar la contraseña"));
                }
                
            } else {
                respuesta.setMensaje("contraseña incorrecta");
            }
            
        } catch (Exception e) {
            respuesta.setMensaje("Error de autenticacion");
        }
        
        return respuesta;
    }
    
    @Path("passwordForgotten")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta recuperarContrasenia(UsuarioRecuperacion param) {
        Respuesta respuesta = new Respuesta();
        
        final String username = "kevinduran1997@gmail.com";
        final String password = "subt3nientE";

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
          });

        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao usuarioDao = factory.newUsuarioDao();
            
            dto.Usuario usuario = usuarioDao.getByUserName(param.getUser());
            
            if (usuario != null) {
                CodigoRecuperacion objRecuperacion = new CodigoRecuperacion(0, 
                    getCodigo(), getNext24HrsDate(), false, usuario.getId());
                
                CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
                
                int idGenerado = recuperacionDao.insert(objRecuperacion);
//                
                if (idGenerado == 0) {
                    respuesta.setMensaje("Hubo un error al crear el codigo de recuperacion");
                    return respuesta;
                } else {
                    objRecuperacion.setId(idGenerado);
                    
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("kevinduran1997@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));
    //                        InternetAddress.parse("kevinduran@outlook.com"));
                        message.setSubject("Tu codigo de recuperacion");
                        message.setText("localhost:8080/freelancerWeb/validarRegistro.html?usuario=" + param.getUser() + "&codigoConfirmacion=" + objRecuperacion.getCode());

                        Transport.send(message);

                        respuesta.setEsOk(true);
                        respuesta.setMensaje("se envio un codigo de recuperacion a tu correo");
                        return respuesta;
                    } catch (MessagingException e) {
                        respuesta.setMensaje("Hubo un error al enviar el correo con el codigo de recuperacion "+ e.getMessage());
                        return respuesta;
                    }
                
                }


            } else {
                respuesta.setMensaje("No existe un usuario con ese nombre de usuario registrado");
                return respuesta;
            }

        } catch (Exception e) {
            
        }
        
        return respuesta;
    }
    
    @Path("validateCode")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta validarContrasenia(UsuarioRecuperacion param) {
        Respuesta respuesta = new Respuesta();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao usuarioDao = factory.newUsuarioDao();
            
            dto.Usuario usuario = usuarioDao.getByUserName(param.getUser());
            
            if (usuario != null) {
                CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
                
                CodigoRecuperacion objRecuperacion = 
                        recuperacionDao.verificar(usuario.getId(), param.getCode());
                
                if (objRecuperacion != null) {
                    respuesta.setEsOk(true);
                    respuesta.setMensaje("codigo de verificacion valido");
                    return respuesta;
                } else {
                    respuesta.setMensaje(Integer.toString(usuario.getId()));
                    return respuesta;
                }

            } else {
                respuesta.setMensaje("No existe un usuario con ese nombre de usuario registrado");
                return respuesta;
            }

        } catch (Exception e) {
            
        }
        
        return respuesta;
    }
    
    @Path("newPassword")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta nuevaContrasenia(UsuarioRecuperacion param) {
        Respuesta respuesta = new Respuesta();
        
        try {
            FactoryDao factory = FactoryDao.getOrCreate();
            UsuarioDao dao = factory.newUsuarioDao();
            
            dto.Usuario objUsuario = dao.getByUserName(param.getUser());
            
            if (objUsuario != null) {
                CodigoRecuperacionDao recuperacionDao = factory.newCodigoRecuperacionDao();
                CodigoRecuperacion objRecuperacion = recuperacionDao.verificar(objUsuario.getId(), param.getCode());
                
                if (objRecuperacion != null) {
                    objUsuario.setPassword(param.getNewPassword());
                    int filasAfectadas = dao.update(objUsuario);

                    if (filasAfectadas > 0) {
                        objRecuperacion.setUsed(true);
                        int actualizacionExitosa = recuperacionDao.update(objRecuperacion);
                        
                        respuesta.setEsOk(true);
                        respuesta.setMensaje("Contraseña cambiada correctamente");
                        
                        return respuesta;
                    } else {
                        respuesta.setMensaje(("Hubo un error al cambiar la contraseña"));
                        return respuesta;
                    }
                } else {
                    respuesta.setMensaje("el codigo no es valido o ya fue utilizado");
                    return respuesta;
                }
                
            } else {
                respuesta.setMensaje("no existe un usuario con ese nombre de usuario");
                return respuesta;
            }
            
        } catch (Exception e) {
            respuesta.setMensaje("Error de autenticacion");
        }
        
        return respuesta;
    }
    
    public static final String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private String getCodigo() {
        String codigo = "";
        
        for (int i = 0; i < 8; i++) {
            int posicion = (int) (Math.random() * letras.length());
            codigo += letras.charAt(posicion);
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
        
//        return dt.toString();
    }
    
}