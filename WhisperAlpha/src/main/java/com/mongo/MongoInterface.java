/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Map;

/**
 *
 * @author Vadim
 */
public class MongoInterface {
    private MongoClient mongo;
    private DB dataBase;

    public MongoInterface (String databaseName) {
        try {
            this.mongo = new MongoClient("localhost", 27017);
        } catch (UnknownHostException ex) {
            System.out.println("Error: MongoS not available");
            System.out.println(ex);
        }
        this.dataBase = mongo.getDB(databaseName);
    }
    
    public DB getDataBase() {
        return dataBase;
    }
    public DBCursor getCursorFromSingleField (String field, Object matching, DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put(field, matching);
        return collection.find(query);
    }
    public DBObject getNext (DBCursor cursor) {
        return cursor.next();
    }
    public void addNewMapToCollection(Map<String, Object> map, DBCollection collection) throws Exception {
        collection.insert(new BasicDBObject(map));
    }
    public void updateEntriesInCollection(Map<String, Object> query, Map<String, Object> data, DBCollection collection) {
        collection.update(new BasicDBObject(query), new BasicDBObject("$set", new BasicDBObject(data)));
    }
    
    public void deleteEntryInCollection(Map<String, Object> query, DBCollection collection) {
        BasicDBObject bdbo = new BasicDBObject(query);
        collection.remove(bdbo);
    }
 //   public List<List<String>> getTablesAsList(String tableName) {
//    }
}
