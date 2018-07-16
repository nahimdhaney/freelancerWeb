/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Comentario;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Usuario
 */
public class ComentarioDaoTest {
    
    public ComentarioDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of insert method, of class ComentarioDao.
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("insert");
        Comentario obj = null;
        ComentarioDao instance = new ComentarioDao();
        int expResult = 0;
        int result = instance.insert(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class ComentarioDao.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int projectRquestId = 0;
        ComentarioDao instance = new ComentarioDao();
        List<Comentario> expResult = null;
        List<Comentario> result = instance.get(projectRquestId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
