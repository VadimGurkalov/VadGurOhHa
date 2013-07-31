/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import com.mongodb.DB;
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
}
