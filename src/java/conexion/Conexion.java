package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    protected String host;
    protected String baseDeDatos;
    protected int puerto;
    protected String usuario;
    protected String password;
    protected Connection con;
    protected static Conexion instancia;

    public static Conexion getOrCreate() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        
        instancia.conectar();

        return (Conexion) instancia;
    }
    
    private Conexion() {
        host = "localhost";
        baseDeDatos = "freelancer";
        puerto = 3306;
        usuario = "root";
        password = "";
    }

    public void conectar() {
        if (this.estaConectado()) {
            return;
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | 
                ClassNotFoundException ex) {
            System.out.println(ex);
        }

        String URL = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos
                + "?useSSL=false";
        
        try {
            con = DriverManager.getConnection(URL, usuario, password);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void desconectar() {
        try {
            if (this.estaConectado()) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean estaConectado() {
        if (this.con == null) {
            return false;
        }
        
        try {
            if (this.con.isClosed()) {
                return false;
            }
        } catch (SQLException ex) {
            this.con = null;
            return false;
        }
        
        return true;
    }

    public void comenzarTransaccion() {
        if (!this.estaConectado()) {
            this.conectar();
        }
        
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void terminarTransaccion() {
        try {
            con.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public PreparedStatement invocarProcedimiento(String procedimiento) {
        try {
            int idGenerado = PreparedStatement.RETURN_GENERATED_KEYS;
            
            return con.prepareStatement(procedimiento, idGenerado);
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet ejecutar(String consulta) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public ResultSet ejecutar(PreparedStatement procedimiento) {
        try {
            return procedimiento.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public int ejecutarSimple(String sentencia) {
        try {
            Statement stmt = con.createStatement();
            int cantidadFilasAfectadas = stmt.executeUpdate(sentencia);
            
            return cantidadFilasAfectadas;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    public int ejecutarSimple(PreparedStatement procedimiento) {
        try {
            procedimiento.execute();
            return 1;
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    public int ejecutarInsert(String sentencia) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sentencia, Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            
            return rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    public int ejecutarInsert(PreparedStatement procedimiento) {
        try {
            ResultSet rs = procedimiento.executeQuery();
            
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                return idGenerado;
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getters">

    /**
     * Devuelve el Host
     * @return 
     */
    public String getHost() {
        return host;
    }

    /** 
     * Devuelve el nombre de la Base de Datos
     * @return 
     */
    public String getBaseDeDatos() {
        return baseDeDatos;
    }

    /**
     * Devuelve el puerto
     * @return 
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * Devuelve el nombre de usuario
     * @return 
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Devuelve el password
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Devuelve la conexion
     * @return 
     */
    public Connection getCon() {
        return con;
    }
    
    // </editor-fold>
    
}