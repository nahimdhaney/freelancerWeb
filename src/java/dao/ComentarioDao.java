package dao;

import conexion.Conexion;
import dto.Comentario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDao {
    
    private static final String ID = "id";
    private static final String MESSAGE = "mensaje";
    private static final String DATE = "fecha";
    private static final String USER_ID = "usuario_id";
    private static final String PROJECT_REQUEST_ID = "solicitud_proyecto_id";
    
    public int insert(Comentario obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        String procedimiento = "call mk_comentario2(?, ?, ? )";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setString(1, obj.getMensaje());
//        ps.setDate(2, new java.sql.Date(322232323));
        ps.setInt(2, obj.getUsuarioId());
        ps.setInt(3, obj.getSolicitudProyectoId());

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        if (filasAfectadas > 0) {
            String consulta = "select max(id) from comentarios";

            ResultSet rs = objConexion.ejecutar(consulta);

            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            }
        }

        objConexion.desconectar();

        return filasAfectadas;
    }

    public List<Comentario> get(int projectRquestId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();

            String procedimiento = "call get_comentarios_de_solicitud(?)";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, projectRquestId);

            List<Comentario> lista = new ArrayList<>();

            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                Comentario comentario = new Comentario();
                
                int _id = objResultSet.getInt(ID);
                comentario.setId(_id);
                
                String _mensaje = objResultSet.getString(MESSAGE);
                comentario.setMensaje(_mensaje);
                
                Time _fecha = objResultSet.getTime(DATE);
                
                int _usuarioId = objResultSet.getInt(USER_ID);
                comentario.setUsuarioId(_usuarioId);
                
                int _solicitudProyectoId = objResultSet.getInt(PROJECT_REQUEST_ID);
                comentario.setSolicitudProyectoId(_solicitudProyectoId);
                
                lista.add(comentario);
            }
            return lista;

        } catch (SQLException e) {
        }
        return null;
    }
    
}