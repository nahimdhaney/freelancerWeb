package dao;

import conexion.Conexion;
import dto.SolicitudProyecto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudProyectoDao {

    private static final String ID = "id";
    private static final String PROJECT_ID = "proyecto_id";
    private static final String FREELANCER_ID = "freelancer_id";
    private static final String STATE = "estado";
    private static final String OFERTA = "oferta";

    public int insert(SolicitudProyecto obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        String procedimiento = "call mk_solicitud_proyecto(?, ?, ?, ? , ?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setInt(2, obj.getProjectId());
        ps.setInt(3, obj.getFreelancerId());
        ps.setString(4, obj.getState());
        ps.setDouble(5, obj.getOferta());

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        if (filasAfectadas > 0) {
            String consulta = "select max(id) from solicitudes_proyecto";

            ResultSet rs = objConexion.ejecutar(consulta);

            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            }
        }

        objConexion.desconectar();

        return filasAfectadas;
    }

    public int update(SolicitudProyecto obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();

        String procedimiento = "call mk_solicitud_proyecto(?, ?, ?, ?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setInt(2, obj.getProjectId());
        ps.setInt(3, obj.getFreelancerId());
        ps.setString(4, obj.getState());

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        return filasAfectadas;
    }

    public int delete(int projectId) throws SQLException {
        Conexion objConexion = Conexion.getOrCreate();

        String procedimiento = "call eliminar_solicitud_proyecto(?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, projectId);

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        return filasAfectadas;
    }

    public List<SolicitudProyecto> get(int projectId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();

            String procedimiento = "call get_solicitud_proyecto(?)";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, projectId);

            List<SolicitudProyecto> lista = new ArrayList<>();

            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                SolicitudProyecto solicitud = getSolicitudProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            return lista;

        } catch (SQLException e) {
        }
        return null;
    }

    public List<SolicitudProyecto> get() {
        try {
            Conexion objConexion = Conexion.getOrCreate();

            String procedimiento = "call get_solicitudes_proyectos()";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);

            List<SolicitudProyecto> lista = new ArrayList<>();

            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                SolicitudProyecto solicitud = getSolicitudProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }

            return lista;
        } catch (SQLException e) {
        }

        return null;
    }

    private SolicitudProyecto getSolicitudProyectoDeResultSet(ResultSet objResultSet) {
        try {
            SolicitudProyecto obj = new SolicitudProyecto();
            int _id = objResultSet.getInt(ID);
            obj.setId(_id);

            int _projectId = objResultSet.getInt(PROJECT_ID);
            obj.setProjectId(_projectId);

            int _freelancerId = objResultSet.getInt(FREELANCER_ID);
            obj.setFreelancerId(_freelancerId);

            String _state = objResultSet.getString(STATE);
            obj.setState(_state);

            try {
                double _oferta = objResultSet.getDouble(OFERTA);
                obj.setOferta(_oferta);

            } catch (Exception e) {
            }

            return obj;
        } catch (SQLException ex) {
            return null;
        }
    }

}
