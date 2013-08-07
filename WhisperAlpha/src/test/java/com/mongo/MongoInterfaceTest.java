/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
    // in database "test" there exists collection "test"
        private MongoInterface mongoInterface;
        private DBCollection collection;
        private String field = "password";
        private String password = "swordfish";
        private Map<String, Object> multipleField;
    
    public MongoInterfaceTest() {
        mongoInterface = new MongoInterface("test");
        collection = mongoInterface.getDataBase().getCollection("test");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        Map<String, Object> singleField = new HashMap<String, Object>();
        singleField.put(field, password);   //It's always swordfish
        mongoInterface.addNewMapToCollection(singleField, collection);
        multipleField = new LinkedHashMap<String, Object>();
        String[] array = {"to", "go", "party!" };
        multipleField.put("_id", 22); // random number
        multipleField.put("foo", 5);
        multipleField.put("baz", (double)1024.512);
        multipleField.put("time", array);
        mongoInterface.addNewMapToCollection(multipleField, collection);
    }
    
    @After
    public void tearDown() {
        collection.drop();
    }

    @Test
    public void connectsToRightDataBase(){
        assertEquals("test", mongoInterface.getDataBase().getName());
    }
    
    @Test
    public void addsNewSingleFieldEntrySuccessfully() throws Exception{
        assertTrue(mongoInterface.getCursorFromSingleField(field, password, collection).hasNext());
    }
    
    @Test
    public void addsNewMultipleFieldEntrySuccessfully() throws Exception{
        mongoInterface.getCursorFromSingleField("_id", 22, collection);
        assertEquals(new BasicDBObject(multipleField).toString(), 
                mongoInterface.getCursorFromSingleField("_id", 22, collection).next().toString());
    }
    @Test(expected = Exception.class)
    public void shouldNotLetAddEntryWithSameIdField() throws Exception {
        mongoInterface.addNewMapToCollection((Map) new HashMap<String, Object>().put("_id", 22), collection);
    }
    @Test
    public void changeEntrySuccessfully() throws Exception{
        Map<String, Object> query = new LinkedHashMap<String, Object>();
        query.put(field, password);
        Map<String, Object> entry = new LinkedHashMap<String, Object>();
        password = "ElPsyCongroo";
        entry.put(field, password);
        mongoInterface.updateEntriesInCollection(query, entry, collection);
    }
    @Test
    public void deleteExistingEntrySuccessfully() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(field, password);
        mongoInterface.deleteEntryInCollection(query, collection);
        assertFalse(mongoInterface.getCursorFromSingleField(field, password, collection).hasNext());
    }
    @Test
    public void deleteExistingEntryWithMultipleFieldsSuccessfully(){
        mongoInterface.deleteEntryInCollection(multipleField, collection);
        assertFalse(mongoInterface.getCursorFromSingleField("_id", 5, collection).hasNext());
    }
}
