package dao;

import conexion.Conexion;
import dto.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    private static final String table = "usuarios";
    private static final String id = "id";
    private static final String name = "nombre_completo";
    private static final String username = "usuario";
    private static final String password = "contraseña";
    private static final String email = "correo";
    private static final String type = "tipo";
    
    public int insert(Usuario obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        String procedimiento = "call mk_usuario(?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getFullName());
        ps.setString(3, obj.getUser());
        ps.setString(4, obj.getPassword());
        ps.setString(5, obj.getEmail());
        ps.setInt(6, obj.getType());
        
        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        if (filasAfectadas > 0) {
            String consulta = "select max(id) from usuarios";
            
            ResultSet rs = objConexion.ejecutar(consulta);
            
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            }
        }
        
        objConexion.desconectar();
        
        return filasAfectadas;
    }

    public int update(Usuario obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        
        String procedimiento = "call mk_usuario(?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getFullName());
        ps.setString(3, obj.getUser());
        ps.setString(4, obj.getPassword());
        ps.setString(5, obj.getEmail());
        ps.setInt(6, obj.getType());
        
        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        return filasAfectadas;
    }

//    public void delete(int id) {
//        Conexion objConexion = Conexion.getOrCreate();
//        
//        String procedimiento = "select delete_usuario(?)";
//        
//        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
//        try {
//            ps.setInt(1, id);
//        } catch (SQLException ex) {
//            System.out.println("hubo un error al eliminar el usuario");
//        }
//        
//        objConexion.ejecutarSimple(ps);
//        
////        Conexion objConexion = Conexion.getOrCreate();
////        StringBuffer query = new StringBuffer("DELETE FROM " + table);
////        query.append("WHERE "  + UsuarioDaoPostgreSQL.id + " = " + id);
////        objConexion.ejecutarSimple(query.toString());
////        objConexion.desconectar();
//    }
//
//    public Usuario get(int id) {
//        try {
//            Conexion objConexion = Conexion.getOrCreate();
//            
//            String procedimiento = "select * from get_usuario(?)";
//            
//            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
//            ps.setInt(1, id);
//            
//            ResultSet objResultSet = objConexion.ejecutar(ps);
//            if (objResultSet.next()) {
//                Usuario obj = new Usuario();
//                int _usuarioId = objResultSet.getInt(UsuarioDaoPostgreSQL.id);
//                obj.setId(_usuarioId);
//
//                String _nombreCompleto = objResultSet.getString(name);
//                obj.setIn_nombreCompleto(_nombreCompleto);
//
//                String _userName = objResultSet.getString(username);
//                obj.setIn_usuario(_userName);
//
//                String _password = objResultSet.getString(password);
//                obj.setIn_password(_password);
//                
//                String _correo = objResultSet.getString(email);
//                obj.setIn_correo(_correo);
//                
//                int _tipo = objResultSet.getInt(type);
//                obj.setIn_tipo(_tipo);
//
//                return obj;
//            }
//        } catch (Exception e) {
//        }
//        
//        return null;
////        try {
////            Conexion objConexion = Conexion.getOrCreate();
////            String query = "SELECT * FROM " + table + " WHERE " 
////                    + UsuarioDaoPostgreSQL.id + " = " + id;
////            ResultSet objResultSet = objConexion.ejecutar(query);
////            if (objResultSet.next()) {
////                Usuario obj = new Usuario();
////                int _usuarioId = objResultSet.getInt(UsuarioDaoPostgreSQL.id);
////                obj.setId(_usuarioId);
////
////                String _nombreCompleto = objResultSet.getString(name);
////                obj.setNombreCompleto(_nombreCompleto);
////
////                String _userName = objResultSet.getString(username);
////                obj.setUsuario(_userName);
////
////                String _password = objResultSet.getString(password);
////                obj.setPassword(_password);
////                
////                String _correo = objResultSet.getString(email);
////                obj.setCorreo(_correo);
////                
////                String _tipo = objResultSet.getString(type);
////                obj.setTipo(_tipo);
////
////                return obj;
////            }
////        } catch (Exception ex) {
////            
////        }
////        return null;
//    }
//
//    public ArrayList<Usuario> getList() {
//        ArrayList<Usuario> registros = new ArrayList<Usuario>();
//        try {
//            Conexion objConexion = Conexion.getOrCreate();
//            String query = "SELECT * FROM " + table;
//            ResultSet objResultSet = objConexion.ejecutar(query);
//            while (objResultSet.next()) {
//                Usuario obj = new Usuario();
//                int _usuarioId = objResultSet.getInt(UsuarioDaoPostgreSQL.id);
//                obj.setId(_usuarioId);
//
//                String _nombreCompleto = objResultSet.getString(name);
//                obj.setIn_nombreCompleto(_nombreCompleto);
//
//                String _userName = objResultSet.getString(username);
//                obj.setIn_usuario(_userName);
//
//                String _password = objResultSet.getString(password);
//                obj.setIn_password(_password);
//                
//                String _correo = objResultSet.getString(email);
//                obj.setIn_correo(_correo);
//                
//                int _tipo = objResultSet.getInt(type);
//                obj.setIn_tipo(_tipo);
//
//                registros.add(obj);
//            }
//        } catch (Exception ex) {
//            
//        }
//        return registros;
//    }
//
    public Usuario getByUserName(String userName) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_user_by_username(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setString(1, userName);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                Usuario obj = new Usuario();
                int _usuarioId = objResultSet.getInt(id);
                obj.setId(_usuarioId);

                String _nombreCompleto = objResultSet.getString(name);
                obj.setFullName(_nombreCompleto);

                String _userName = objResultSet.getString(username);
                obj.setUser(_userName);

                String _password = objResultSet.getString(password);
                obj.setPassword(_password);
                
                String _correo = objResultSet.getString(email);
                obj.setEmail(_correo);
                
                int _tipo = objResultSet.getInt(type);
                obj.setType(_tipo);

                return obj;
            }
        } catch (SQLException e) {
        }
        
        return null;
    }

    public Usuario login(String userName, String contraseña) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call login_usuario(?, ?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setString(1, userName);
            ps.setString(2, contraseña);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                Usuario obj = new Usuario();
                int _usuarioId = objResultSet.getInt(id);
                obj.setId(_usuarioId);

                String _nombreCompleto = objResultSet.getString(name);
                obj.setFullName(_nombreCompleto);

                String _userName = objResultSet.getString(username);
                obj.setUser(_userName);

                String _password = objResultSet.getString(password);
                obj.setPassword(_password);
                
                String _correo = objResultSet.getString(email);
                obj.setEmail(_correo);
                
                int _tipo = objResultSet.getInt(type);
                obj.setType(_tipo);

                return obj;
            }
        } catch (SQLException e) {
        }
        
        return null;
    }

}