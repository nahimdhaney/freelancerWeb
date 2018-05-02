package dao;

import conexion.Conexion;
import dto.CodigoRecuperacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodigoRecuperacionDao {

    private static final String ID = "id";
    private static final String CODE = "codigo";
    private static final String DATE = "fecha_expiracion";
    private static final String USED = "utilizado";
    private static final String USER_ID = "usuario_id";
    
    public int insert(CodigoRecuperacion obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        String procedimiento = "call mk_codigo_recuperacion(?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getCode());
        ps.setString(3, obj.getDate());
        ps.setBoolean(4, obj.isUsed());
        ps.setInt(5, obj.getUserId());
        
        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        if (filasAfectadas > 0) {
            String consulta = "select max(id) from codigos_recuperacion";
            
            ResultSet rs = objConexion.ejecutar(consulta);
            
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            }
        }
        
        objConexion.desconectar();
        
        return filasAfectadas;
    }

    public int update(CodigoRecuperacion obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        
        String procedimiento = "call mk_codigo_recuperacion(?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getCode());
        ps.setString(3, obj.getDate());
        ps.setBoolean(4, obj.isUsed());
        ps.setInt(5, obj.getUserId());
        
        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        return filasAfectadas;
    }

    public CodigoRecuperacion get(int id) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_codigo_recuperacion_by_id(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, id);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                CodigoRecuperacion obj = getCodigoRecuperacionDeResultSet(objResultSet);

                return obj;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public CodigoRecuperacion verificar(int usuarioId, String codigo) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call verificar_codigo_recuperacion(?, ?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, usuarioId);
            ps.setString(2, codigo);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                CodigoRecuperacion obj = getCodigoRecuperacionDeResultSet(objResultSet);

                return obj;
            }
        } catch (Exception e) {
        }
        
        return null;
    }

    private CodigoRecuperacion getCodigoRecuperacionDeResultSet(ResultSet objResultSet) {
        try {
            CodigoRecuperacion obj = new CodigoRecuperacion();
            int _id = objResultSet.getInt(CodigoRecuperacionDao.ID);
            obj.setId(_id);
            
            String _codigo = objResultSet.getString(CODE);
            obj.setCode(_codigo);
            
            String _fechaExpiracion = objResultSet.getString(DATE);
            obj.setDate(_fechaExpiracion);
            
            boolean _utilizado = objResultSet.getBoolean(USED);
            obj.setUsed(_utilizado);
            
            int _usuarioId = objResultSet.getInt(USER_ID);
            obj.setUserId(_usuarioId);
            
            return obj;
        } catch (SQLException ex) {
            return null;
        }
    }

}