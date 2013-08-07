/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userAccounts;
import com.mongo.MongoInterfaceAccounts;
import java.util.Map;
/**
 *
 * @author Vadim
 */
public class AccountTools {
    private MongoInterfaceAccounts mongoInterfaceAccounts;
    
    public AccountTools() {
        this.mongoInterfaceAccounts = new MongoInterfaceAccounts();
    }
    
    public User authenticate(String username, String password) throws Exception {
        if (this.mongoInterfaceAccounts.UserNameExists(username)) {
            if (Password.check(password, this.mongoInterfaceAccounts.getHash(username))){
                Map<String, Object> userData = this.mongoInterfaceAccounts.getUserData(username);
                int userType = Integer.parseInt(userData.get(MongoInterfaceAccounts.FIELD_USERTYPE).toString());
                return new User(username, UserType.values()[userType]);
            }
        }
        throw new IllegalArgumentException("The username or password is incorrect");
    }
    public boolean addGuest(User guest, String password) throws Exception {
        this.mongoInterfaceAccounts.addNewUserToCollection(guest.getUserName(), Password.getSaltedHash(password), guest.getUserType().ordinal());
        return true;
    }
    
    public boolean registerGuestAsUser(User guest) throws Exception {
        this.mongoInterfaceAccounts.changeUserType(guest.getUserName(), UserType.USER.ordinal());
        return true;
    }
    
    public boolean registerUserAsAdmin(User user) throws Exception {
        this.mongoInterfaceAccounts.changeUserType(user.getUserName(), UserType.ADMIN.ordinal());
        return true;
    }
    
    public boolean removeUser(User user) throws Exception {
        this.mongoInterfaceAccounts.removeUser(user.getUserName());
        return true;
    }
}
