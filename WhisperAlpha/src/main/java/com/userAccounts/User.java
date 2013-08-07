/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userAccounts;

/**
 *
 * @author Vadim
 */
public class User {
    private String userName;
    private UserType userType;

    public User(String userName, UserType userType) {
        this.userName = userName;
        this.userType = userType;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public UserType getUserType() {
        return userType;
    }
    @Override
    public String toString() {
        return userName + userType;
    }
}
