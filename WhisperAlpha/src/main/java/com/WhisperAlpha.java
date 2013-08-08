/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;
import com.mongo.MongoInterface;
import com.mongo.MongoInterfaceAccounts;
import com.userAccounts.AccountTools;
import com.userAccounts.User;
import com.userAccounts.UserType;

/**
 *
 * @author Vadim
 */
public class WhisperAlpha {
    public static void main (String[] args) {
        MongoInterface test;
        MongoInterfaceAccounts testAccounts;
        testAccounts = new MongoInterfaceAccounts();
        AccountTools tools = new AccountTools();
        String testName;
        String spookName;
        String testHash;
        int testUserType;
        
        try {
            testName = "User";
            testHash = "swordfish"; //It's always swordfish
            spookName = "spook";
            testUserType = 0;
        } catch (Exception e) {
            System.out.println(e);
        }
//        try {
//            testAccounts.removeUser("tester");
//            System.out.println(testAccounts.UserNameExists("tester"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
