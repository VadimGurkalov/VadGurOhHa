/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 *
 * @author Vadim
 */
public class MongoInterfaceAccounts extends MongoInterface {
    private DBCollection collection;
    private DBCursor currentCursor;
    public MongoInterfaceAccounts () {
        super("UserAccounts");
        this.collection = super.getDataBase().getCollection("useraccounts");
    }
    
    public boolean UserNameExists(String userName) {
        this.currentCursor = super.findSingleField("userName", userName, this.collection);
        return currentCursor.hasNext();
    }
    
    public String getHash (String userName) {
        this.currentCursor = super.findSingleField("userName", userName, collection);
        if ( this.currentCursor.hasNext()) {
            return this.currentCursor.next().get("hash").toString();
        } 
        throw new IllegalArgumentException("Username not found");
    }
    
    public int getUserType() {
        return Integer.parseInt(this.currentCursor.next().get("userType").toString());
    }
    
    public void addNewUserToCollection(String userName, String saltedHash, int userType) {
        if(this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username exists in database. This should have been caught sooner!");
        }
        BasicDBObject newUser = new BasicDBObject();
        newUser.put("userName", userName);
        newUser.put("hash", saltedHash);
        newUser.put("userType", userType);
        this.collection.insert(newUser);
    }
    public void changeUserType(String userName, int userType) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        BasicDBObject newUserType = new BasicDBObject();
        newUserType.append("$set", new BasicDBObject().append("userType", userType));
        BasicDBObject searchQuery = new BasicDBObject().append("userName", userName);
        this.collection.update(searchQuery, newUserType);
    }
    
    public void removeUser (String userName) {
        if(!this.UserNameExists(userName)) {
            throw new IllegalArgumentException("The username does not exist in database. This should have been caught sooner!");
        }
        this.collection.remove(new BasicDBObject().append("userName", userName));
    }
}
