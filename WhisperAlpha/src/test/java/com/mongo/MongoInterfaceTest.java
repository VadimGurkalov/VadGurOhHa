/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vadim
 */
public class MongoInterfaceTest {
        private MongoInterface mongoInterface;
    
    public MongoInterfaceTest() {
                mongoInterface = new MongoInterface("test");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }

    @Test
    public void connectsToRightDataBase(){
        assertEquals(mongoInterface.getDataBase().getName(), "test");
    }
}
