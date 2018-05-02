package dao;

import conexion.Conexion;
import dto.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    private static final String ID = "id";
    private static final String NAME = "nombre_completo";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "contraseña";
    private static final String EMAIL = "correo";
    private static final String ENABLED = "habilitado";
    private static final String TYPE = "tipo";
    
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

    public Usuario getByUserName(String userName) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_user_by_username(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setString(1, userName);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                Usuario usuario = getUsuarioDeResultSet(objResultSet);

                return usuario;
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
                Usuario usuario = getUsuarioDeResultSet(objResultSet);
                
                return usuario;
            }
        } catch (SQLException e) {
        }
        
        return null;
    }

    private Usuario getUsuarioDeResultSet(ResultSet objResultSet) {
        try {
            Usuario obj = new Usuario();
            int _usuarioId = objResultSet.getInt(ID);
            obj.setId(_usuarioId);
            
            String _nombreCompleto = objResultSet.getString(NAME);
            obj.setFullName(_nombreCompleto);
            
            String _userName = objResultSet.getString(USERNAME);
            obj.setUser(_userName);
            
            String _password = objResultSet.getString(PASSWORD);
            obj.setPassword(_password);
            
            String _correo = objResultSet.getString(EMAIL);
            obj.setEmail(_correo);
            
            boolean _habilitado = objResultSet.getBoolean(ENABLED);
            obj.setEnabled(_habilitado);
            
            int _tipo = objResultSet.getInt(TYPE);
            obj.setType(_tipo);
            
            return obj;
        } catch (SQLException ex) {
            return null;
        }
    }
    
}