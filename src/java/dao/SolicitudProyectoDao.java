package dao;

import conexion.Conexion;
import dto.SolicitudFreelancer;
import dto.SolicitudProyecto;
import dto.VistaSolicitudes;
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
    private static final String NAME = "nombre_completo";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "contraseña";
    private static final String EMAIL = "correo";
    private static final String TYPE = "tipo";
    private static final String PRECIO = "precio";
    
    
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
    
    public List<SolicitudFreelancer> getSolicitudes(int projectId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();

            String procedimiento = "call get_solicitudes_proyecto(?)";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, projectId);

            List<SolicitudFreelancer> lista = new ArrayList<>();

            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                SolicitudFreelancer solicitud = getSolicitudFreelancerDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            return lista;

        } catch (SQLException e) {
        }
        return null;
    }

    public List<VistaSolicitudes> getSolicitudes5(int projectId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();

            String procedimiento = "call ver_solicitudes_5(?)";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, projectId);

            List<VistaSolicitudes> lista = new ArrayList<>();

            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                VistaSolicitudes obj = new VistaSolicitudes();
                int _id_solicitud = objResultSet.getInt("id_solicitud");
                obj.setId_solicitud(_id_solicitud);

                int _id_freelancer = objResultSet.getInt("id_freelancer");
                obj.setId_freelancer(_id_freelancer);
                
                String _freelancer = objResultSet.getString("nombre_completo");
                obj.setFreelancer(_freelancer);
                
                double _oferta = objResultSet.getDouble("oferta");
                obj.setOferta(_oferta);
                
                lista.add(obj);
            }
            return lista;

        } catch (SQLException e) {
        }
        return null;
    }

    public int aceptar5(int id) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();

        String procedimiento = "call aceptar5(?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, id);

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        return filasAfectadas;
    }

    public int confirmar5(int id) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();

        String procedimiento = "call confirmar5(?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, id);

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        return filasAfectadas;
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
    private SolicitudFreelancer getSolicitudFreelancerDeResultSet(ResultSet objResultSet) {
        try {
            SolicitudFreelancer obj = new SolicitudFreelancer();
            int _id = objResultSet.getInt(ID);
            obj.setId(_id);

            int _projectId = objResultSet.getInt(PROJECT_ID);
            obj.setProjectId(_projectId);

            int _freelancerId = objResultSet.getInt(FREELANCER_ID);
            obj.setFreelancerId(_freelancerId);

            String _state = objResultSet.getString(STATE);
            obj.setState(_state);
            
            
                        String _nombreCompleto = objResultSet.getString(NAME);
            obj.setFullName(_nombreCompleto);

            String _userName = objResultSet.getString(USERNAME);
            obj.setUser(_userName);

            String _password = objResultSet.getString(PASSWORD);
            obj.setPassword(_password);

            String _correo = objResultSet.getString(EMAIL);
            obj.setEmail(_correo);

//            boolean _habilitado = objResultSet.getBoolean(ENABLED);
//            obj.setEnabled(_habilitado);
            int _tipo = objResultSet.getInt(TYPE);
            obj.setType(_tipo);
            try {
                double _precio = objResultSet.getDouble(PRECIO);                
                obj.setPrecio(_precio);
            } catch (Exception e) {
                obj.setPrecio(222);
            }

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
