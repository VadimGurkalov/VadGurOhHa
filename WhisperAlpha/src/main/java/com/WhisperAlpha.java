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
        System.out.println(testAccounts.getHash("tester"));
        try {
//            System.out.println(tools.addGuest(new User("tester", UserType.GUEST), "swordfish"));
            tools.registerUserAsAdmin(tools.authenticate("tester", "swordfish"));
            System.out.println(tools.authenticate("tester", "swordfish"));
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
