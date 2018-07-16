/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import dto.Usuario;
import java.util.ArrayList;
import java.util.List;
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
public class UsuarioDaoTest {
    
    public UsuarioDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Conexion con = Conexion.getOrCreate();
        
        con.setBaseDeDatos("freelancerTest");
        con.desconectar();
        
        con.conectar();
        // con.ejecutarSimple("delete from usuarios");
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
     * Test of insert method, of class UsuarioDao.
     */
    @Test
    public void testA1Insert() throws Exception {
        System.out.println("insert");
        
        Usuario obj = new Usuario();
        obj.setFullName("Nicolas Duran");
        obj.setUser("admin");
        obj.setPassword("admin");
        obj.setEmail("kevinduran@outlook.com");
        obj.setType(1);
        
        UsuarioDao instance = new UsuarioDao();
        int expResult = 0;
        int result = instance.insert(obj);
        
        assertNotEquals(expResult, result);
    }

    /**
     * Test of update method, of class UsuarioDao.
     */
    @Test
    public void testBUpdate() throws Exception {
        System.out.println("update");
        
        UsuarioDao instance = new UsuarioDao();
        
        Usuario obj = instance.login("admin", "admin");
        obj.setFullName("Kevin Duran");
        
        int expResult = 1;
        int result = instance.update(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of getByUserName method, of class UsuarioDao.
     */
    @Test
    public void testDGetByUserName() {
        System.out.println("getByUserName");
        
        String userName = "admin";
        UsuarioDao instance = new UsuarioDao();
        Usuario result = instance.getByUserName(userName);
        
        assertNotNull(result);
    }

    /**
     * Test of login method, of class UsuarioDao.
     */
    @Test
    public void testCLogin() {
        System.out.println("login");
        
        String userName = "admin";
        String contraseña = "admin";
        
        UsuarioDao instance = new UsuarioDao();
        
        Usuario result = instance.login(userName, contraseña);
        
        assertNotNull(result);
    }

    /**
     * Test of get method, of class UsuarioDao.
     */
    @Test
    public void testEGet_int() {
        System.out.println("get");
        
        
        UsuarioDao instance = new UsuarioDao();
        Usuario obj = instance.getByUserName("admin");
        
        int usuarioId = obj.getId();
        
        Usuario result = instance.get(usuarioId);
        
        assertEquals(usuarioId, result.getId());
    }

    /**
     * Test of get method, of class UsuarioDao.
     */
    @Test
    public void testA0Get_0args() {
        System.out.println("get all");
        
        UsuarioDao instance = new UsuarioDao();
        List<Usuario> expResult = new ArrayList<>();
        List<Usuario> result = instance.get();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getFreelancers method, of class UsuarioDao.
     */
    @Test
    public void testFGetFreelancers() {
        System.out.println("getFreelancers");
        
        UsuarioDao instance = new UsuarioDao();
        List<Usuario> expResult = new ArrayList<>();
        List<Usuario> result = instance.getFreelancers();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getContratistas method, of class UsuarioDao.
     */
    @Test
    public void testGGetContratistas() {
        System.out.println("getContratistas");
        
        UsuarioDao instance = new UsuarioDao();
        List<Usuario> result = instance.getContratistas();
        
        assertFalse(result.isEmpty());
    }
    
}
