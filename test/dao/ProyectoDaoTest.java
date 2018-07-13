/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import dto.Proyecto;
import dto.Usuario;
import dto.VistaProyectosFreelancer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Usuario
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProyectoDaoTest {
    
    public ProyectoDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Conexion con = Conexion.getOrCreate();
        
        con.setBaseDeDatos("freelancerTest");
        con.desconectar();
        
        con.conectar();
        con.ejecutarSimple("delete from usuarios");
        con.ejecutarSimple("delete from proyectos");
        
        con.ejecutarInsert("insert into usuarios (nombre_completo, usuario, contraseña, correo, tipo) "
                + "values('Nahim Terrazas', 'nahimcho', '1234', 'nahimdhaney@gmail.com', 1)");
        
        con.ejecutarInsert("insert into usuarios (nombre_completo, usuario, contraseña, correo, tipo) "
                + "values('Ricardo Paz', 'rpaz', '1234', 'rickypazd@icloud.com', 2)");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class ProyectoDao.
     */
    @Test
    public void testCInsert() throws Exception {
        System.out.println("insert");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("nahimcho");
        
        Proyecto obj = new Proyecto();
        obj.setName("Mencargo");
        obj.setDescription("Administracion de condominios");
        obj.setCategory("TI");
        obj.setPrice(5000);
        obj.setDate("2018-07-03");
        obj.setOwnerId(user.getId());
        
        ProyectoDao instance = new ProyectoDao();
        int expResult = 0;
        int projectId = instance.insert(obj);
        
        assertNotEquals(expResult, projectId);
    }

    /**
     * Test of update method, of class ProyectoDao.
     */
    @Test
    public void testEUpdate() throws Exception {
        System.out.println("update");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("nahimcho");
        
        Proyecto obj = new Proyecto();
        obj.setName("Mencargo");
        obj.setDescription("Administracion de condominios");
        obj.setCategory("TI");
        obj.setPrice(5000);
        obj.setDate("2018-07-03");
        obj.setOwnerId(user.getId());
        
        ProyectoDao instance = new ProyectoDao();
        int projectId = instance.insert(obj);
        
        obj = instance.get(projectId);
        obj.setName("Mencargo app");
        
        int expResult = 0;
        int result = instance.update(obj);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of delete method, of class ProyectoDao.
     */
    @Test
    public void testIDelete() throws Exception {
        System.out.println("delete");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("nahimcho");
        
        Proyecto obj = new Proyecto();
        obj.setName("Mencargo");
        obj.setDescription("Administracion de condominios");
        obj.setCategory("TI");
        obj.setPrice(5000);
        obj.setDate("2018-07-03");
        obj.setOwnerId(user.getId());
        
        ProyectoDao instance = new ProyectoDao();
        int projectId = instance.insert(obj);
        
        int expResult = 0;
        int result = instance.delete(projectId);
        
        assertNotEquals(expResult, result);
    }

    /**
     * Test of get method, of class ProyectoDao.
     */
    @Test
    public void testDGet_int() {
        System.out.println("get");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("nahimcho");
        
        Proyecto obj = new Proyecto();
        obj.setName("Mencargo");
        obj.setDescription("Administracion de condominios");
        obj.setCategory("TI");
        obj.setPrice(5000);
        obj.setDate("2018-07-03");
        obj.setOwnerId(user.getId());
        
        ProyectoDao instance = new ProyectoDao();
        try {
            int projectId = instance.insert(obj);
            Proyecto result = instance.get(projectId);

            assertNotNull(result);
        } catch (Exception ex) {
            fail("failed inserting a project");
        }
    }

    /**
     * Test of get method, of class ProyectoDao.
     */
    @Test
    public void testBGet_0args() {
        System.out.println("get all");
        
        ProyectoDao instance = new ProyectoDao();
        List<Proyecto> result = instance.get();
        
        assertTrue(result.isEmpty());
    }

    /**
     * Test of getProjectsOfOwner method, of class ProyectoDao.
     */
    @Test
    public void testFGetProjectsOfOwner() {
        System.out.println("getProjectsOfOwner");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("nahimcho");
        int ownerId = user.getId();
        
        ProyectoDao instance = new ProyectoDao();
        List<Proyecto> result = instance.getProjectsOfOwner(ownerId);
        
        assertFalse(result.isEmpty());
    }

    /**
     * Test of getProjectsOfFreelancer method, of class ProyectoDao.
     */
    @Test
    public void testGetProjectsOfFreelancer() {
        System.out.println("getProjectsOfFreelancer is deprecated");
    }

    /**
     * Test of getProjectsOfFreelancer5 method, of class ProyectoDao.
     */
    @Test
    public void testGGetProjectsOfFreelancer5() {
        System.out.println("getProjectsOfFreelancer5");
        
        UsuarioDao dao = new UsuarioDao();
        Usuario user = dao.getByUserName("rpaz");
        int freelancerId = user.getId();
        
        ProyectoDao instance = new ProyectoDao();
        List<VistaProyectosFreelancer> expResult = new ArrayList<>();
        List<VistaProyectosFreelancer> result = instance.getProjectsOfFreelancer5(freelancerId);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of search method, of class ProyectoDao.
     */
    @Test
    public void testHSearch() {
        System.out.println("search");
        
        String param = "Mencargo";
        ProyectoDao instance = new ProyectoDao();
        List<Proyecto> result = instance.search(param);
        
        assertNotNull(result);
    }
    
}
