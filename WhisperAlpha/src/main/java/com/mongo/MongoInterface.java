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
    public DBCursor findSingleField (String field, String matching, DBCollection collection) {
        BasicDBObject query = new BasicDBObject();
        query.put(field, matching);
        return collection.find(query);
    }
    public DBObject getNext (DBCursor cursor) {
        return cursor.next();
    }
    
 //   public List<List<String>> getTablesAsList(String tableName) {
//    }
}
