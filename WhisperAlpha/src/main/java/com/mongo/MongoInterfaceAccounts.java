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
    private static final String DATABASENAME = "UserAccounts";
    private static final String COLLECTIONNAME = "useraccounts";
    private static final String USERNAMEFIELD = "userName";
    private static final String USERTYPEFIELD = "userType";
    private static final String SALTEDHASHFIELD = "saltedHash";
    private final DBCollection collection;
    private DBCursor currentCursor;
    
    public MongoInterfaceAccounts () {
        super(DATABASENAME);
        this.collection = super.getDataBase().getCollection(COLLECTIONNAME);
    }
    
    public DBCollection getCollection() {
        return collection;
    }
    
    public boolean UserNameExists(String userName) {
        this.currentCursor = super.getCursorFromSingleField(USERNAMEFIELD, userName, this.collection);
        return currentCursor.hasNext();
    }
    
    public String getHash (String userName) {
        this.currentCursor = super.getCursorFromSingleField(USERNAMEFIELD, userName, collection);
        if ( this.currentCursor.hasNext()) {
            return super.getNext(currentCursor).get(SALTEDHASHFIELD).toString();
        } 
        throw new IllegalArgumentException("Username not found");
    }
    
    public Map<String, Object> getUserData(String userName) {
        return super.getCursorFromSingleField(USERNAMEFIELD, userName, collection).next().toMap();
    }
    
    public void addNewUserToCollection(String userName, String saltedHash, int userType) throws Exception{
        if(this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username exists in database. This should have been caught sooner!");
        }
        Map<String, Object> newUser = new LinkedHashMap<String, Object>();
        newUser.put(USERNAMEFIELD, userName);
        newUser.put(SALTEDHASHFIELD, saltedHash);
        newUser.put(USERTYPEFIELD, userType);
        super.addNewMapToCollection(newUser, collection);
    }

    public void changeUserType(String userName, int userType) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(USERNAMEFIELD, userName);
        Map<String, Object> update = new HashMap<String, Object>();
        update.put(USERTYPEFIELD, userType);
        super.updateEntriesInCollection(query, update, collection);
    }
    
    public void changeHash(String userName, String saltedHash) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(USERNAMEFIELD, userName);
        Map<String, Object> update = new HashMap<String, Object>();
        update.put(SALTEDHASHFIELD, saltedHash);
        super.updateEntriesInCollection(query, update, collection);
    }
    
    public void removeUser (String userName) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        Map<String, Object> query = new HashMap<String, Object>();
        query.put(USERNAMEFIELD, userName);
        super.deleteEntryInCollection(query, collection);
    }
}
