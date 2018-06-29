package dao;

import conexion.Conexion;
import dto.Proyecto;
import dto.VistaProyectosFreelancer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDao {

    public static final String ID = "id";
    private static final String NAME = "nombre";
    private static final String DESCRIPTION = "descripcion";
    private static final String CATEGORY = "categoria";
    private static final String PRICE = "precio";
    private static final String DATE = "fecha";
    private static final String START = "inicio";
    private static final String END = "fin";
    private static final String OWNER_ID = "contratista_id";
    private static final String FREELANCER_ID = "freelancer_id";
        
    public int insert(Proyecto obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        String procedimiento = "call mk_proyecto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getName());
        ps.setString(3, obj.getDescription());
        ps.setString(4, obj.getCategory());
        ps.setDouble(5, obj.getPrice());
        ps.setString(6, obj.getDate());
        ps.setNull(7, Types.DATE); // start
        ps.setNull(8, Types.DATE); // end
        ps.setInt(9, obj.getOwnerId());
        ps.setNull(10, Types.INTEGER);
        
        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        if (filasAfectadas > 0) {
            String consulta = "select max(id) from proyectos";
            
            ResultSet rs = objConexion.ejecutar(consulta);
            
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            }
        }
        
        objConexion.desconectar();
        
        return filasAfectadas;
    }

    public int update(Proyecto obj) throws Exception {
        Conexion objConexion = Conexion.getOrCreate();
        
        String procedimiento = "call mk_proyecto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, obj.getId());
        ps.setString(2, obj.getName());
        ps.setString(3, obj.getDescription());
        ps.setString(4, obj.getCategory());
        ps.setDouble(5, obj.getPrice());
        ps.setString(6, obj.getDate());
        
        if (obj.getStart() == "") {
            ps.setNull(7, Types.DATE);
        } else {
            ps.setString(7, obj.getStart());
        }
        
        if (obj.getEnd() == "") {
            ps.setNull(8, Types.DATE);
        } else {
            ps.setString(8, obj.getEnd());
        }
        
        ps.setInt(9, obj.getOwnerId());
        
        if (obj.getFreelancerId() == 0) {
            ps.setNull(10, Types.INTEGER);
        } else {
            ps.setInt(10, obj.getFreelancerId());
        }

        int filasAfectadas = objConexion.ejecutarSimple(ps);
        
        return filasAfectadas;
    }

    public int delete(int projectId) throws SQLException {
        Conexion objConexion = Conexion.getOrCreate();

        String procedimiento = "call eliminar_proyecto(?)";

        PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
        ps.setInt(1, projectId);

        int filasAfectadas = objConexion.ejecutarSimple(ps);

        return filasAfectadas;
    }

    public Proyecto get(int projectId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_proyecto(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, projectId);
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            if (objResultSet.next()) {
                Proyecto solicitud = getProyectoDeResultSet(objResultSet);

                return solicitud;
            }
        } catch (SQLException e) {
        }
        
        return null;
    }

    public List<Proyecto> get() {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_proyectos()";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            
            List<Proyecto> lista = new ArrayList<>();
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                Proyecto solicitud = getProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            
            return lista;
        } catch (SQLException e) {
        }
        
        return null;
    }

    public List<Proyecto> getProjectsOfOwner(int ownerId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_proyectos_de_contratista(?)";

            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, ownerId);

            List<Proyecto> lista = new ArrayList<>();
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                Proyecto solicitud = getProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            
            return lista;
        } catch (SQLException e) {
        }
        
        return null;
    }

    public List<Proyecto> getProjectsOfFreelancer(int freelancerId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_proyectos_de_freelancer(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, freelancerId);

            List<Proyecto> lista = new ArrayList<>();
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                Proyecto solicitud = getProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            
            return lista;
        } catch (SQLException e) {
        }
        
        return null;
    }

    public List<VistaProyectosFreelancer> getProjectsOfFreelancer5(int freelancerId) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call get_proyectos_de_freelancer5(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setInt(1, freelancerId);

            List<VistaProyectosFreelancer> lista = new ArrayList<>();
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                VistaProyectosFreelancer obj = new VistaProyectosFreelancer();
                
                int _idSolicitud = objResultSet.getInt("id_solicitud");
                obj.setId_solicitud(_idSolicitud);

                int _idProyecto = objResultSet.getInt("id_proyecto");
                obj.setId_proyecto(_idProyecto);

                String _estado = objResultSet.getString("estado");
                obj.setEstado(_estado);

                String _nombre = objResultSet.getString("nombre");
                obj.setNombre(_nombre);

                String _descripcion = objResultSet.getString("descripcion");
                obj.setDescripcion(_descripcion);
            
                lista.add(obj);
            }
            
            return lista;
        } catch (SQLException e) {
        }
        
        return null;
    }

    public List<Proyecto> search(String param) {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            
            String procedimiento = "call buscar_proyectos(?)";
            
            PreparedStatement ps = objConexion.invocarProcedimiento(procedimiento);
            ps.setString(1, param);

            List<Proyecto> lista = new ArrayList<>();
            
            ResultSet objResultSet = objConexion.ejecutar(ps);
            while (objResultSet.next()) {
                Proyecto solicitud = getProyectoDeResultSet(objResultSet);
                lista.add(solicitud);
            }
            
            return lista;
        } catch (SQLException e) {
        }
        
        return null;
    }

    private Proyecto getProyectoDeResultSet(ResultSet objResultSet) {
        try {
            Proyecto obj = new Proyecto();
            int _id = objResultSet.getInt(ID);
            obj.setId(_id);
            
            String _name = objResultSet.getString(NAME);
            obj.setName(_name);
            
            String _description = objResultSet.getString(DESCRIPTION);
            obj.setDescription(_description);
            
            String _categoria = objResultSet.getString(CATEGORY);
            obj.setCategory(_categoria);
            
            double _price = objResultSet.getDouble(PRICE);
            obj.setPrice(_price);
            
            String _date = objResultSet.getString(DATE);
            obj.setDate(_date);
            
            String _start = objResultSet.getString(START);
            obj.setStart(_start);
            
            String _end = objResultSet.getString(END);
            obj.setEnd(_end);
            
            int _ownerId = objResultSet.getInt(OWNER_ID);
            obj.setOwnerId(_ownerId);
            
            int _freelancerId = objResultSet.getInt(FREELANCER_ID);
            obj.setFreelancerId(_freelancerId);
            
            return obj;
        } catch (SQLException ex) {
            return null;
        }
    }

}