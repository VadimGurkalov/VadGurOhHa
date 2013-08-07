/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Vadim
 */
public class MongoInterfaceAccounts extends MongoInterface {
    private static final String NAME_DATABASE = "UserAccounts";
    private static final String NAME_COLLECTION = "useraccounts";
    public static final String FIELD_USERNAME = "userName";
    public static final String FIELD_USERTYPE = "userType";
    public static final String FIELD_SALTEDHASH = "saltedHash";
    private final DBCollection collection;
    private DBCursor currentCursor;
    
    public MongoInterfaceAccounts () {
        super(NAME_DATABASE);
        this.collection = super.getDataBase().getCollection(NAME_COLLECTION);
    }
    
    public DBCollection getCollection() {
        return collection;
    }
    
    public boolean UserNameExists(String userName) {
        this.currentCursor = super.getCursorFromSingleField(FIELD_USERNAME, userName, this.collection);
        return currentCursor.hasNext();
    }
    
    public String getHash (String userName) {
        this.currentCursor = super.getCursorFromSingleField(FIELD_USERNAME, userName, collection);
        if ( this.currentCursor.hasNext()) {
            return super.getNext(currentCursor).get(FIELD_SALTEDHASH).toString();
        } 
        throw new IllegalArgumentException("Username not found");
    }
    
    public Map<String, Object> getUserData(String userName) {
        return super.getCursorFromSingleField(FIELD_USERNAME, userName, collection).next().toMap();
    }
    
    public void addNewUserToCollection(String userName, String saltedHash, int userType) throws Exception{
        if(this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username exists in database. This should have been caught sooner!");
        }
        Map<String, Object> newUser = new LinkedHashMap<String, Object>();
        newUser.put(FIELD_USERNAME, userName);
        newUser.put(FIELD_SALTEDHASH, saltedHash);
        newUser.put(FIELD_USERTYPE, userType);
        super.addNewMapToCollection(newUser, collection);
    }

    public void changeUserType(String userName, int userType) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(FIELD_USERNAME, userName);
        Map<String, Object> update = new HashMap<String, Object>();
        update.put(FIELD_USERTYPE, userType);
        super.updateEntriesInCollection(query, update, collection);
    }
    
    public void changeHash(String userName, String saltedHash) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(FIELD_USERNAME, userName);
        Map<String, Object> update = new HashMap<String, Object>();
        update.put(FIELD_SALTEDHASH, saltedHash);
        super.updateEntriesInCollection(query, update, collection);
    }
    
    public void removeUser (String userName) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(FIELD_USERNAME, userName);
        super.deleteEntryInCollection(query, collection);
    }
}
