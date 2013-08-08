/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vadim
 */
public class MongoInterfaceAccountsTest {

    private MongoInterfaceAccounts mongoInterfaceAccounts;
    private String testName;
    private String spookName;
    private String testHash;
    private int testUserType;

    public MongoInterfaceAccountsTest() {
        mongoInterfaceAccounts = new MongoInterfaceAccounts();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        testName = "User";
        testHash = "swordfish"; //It's always swordfish
        spookName = "spook";
        testUserType = 0;
        mongoInterfaceAccounts.addNewUserToCollection(testName, testHash, testUserType);
    }

    @After
    public void tearDown() {
        try {
            mongoInterfaceAccounts.removeUser(testName);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void DatabaseIsCorrect() {
        assertEquals("UserAccounts", mongoInterfaceAccounts.getDataBase().toString());
    }

    @Test
    public void CollectionIsCorrect() {
        assertEquals("useraccounts", mongoInterfaceAccounts.getCollection().toString());
    }

    @Test
    public void AddingNewUserWorks() {
        assertTrue(mongoInterfaceAccounts.UserNameExists(testName));
    }

    @Test
    public void PasswordIsCorrect() {
        assertEquals(testHash, mongoInterfaceAccounts.getHash(testName));
    }

    @Test
    public void UserTypeIsCorrect() {
        assertEquals(testUserType, mongoInterfaceAccounts.getUserData(testName).get(MongoInterfaceAccounts.FIELD_USERTYPE));
    }

    @Test
    public void ChangingUserTypeWorks() {
        testUserType = 1;
        mongoInterfaceAccounts.changeUserType(testName, testUserType);
        assertEquals(testUserType, mongoInterfaceAccounts.getUserData(testName).get(MongoInterfaceAccounts.FIELD_USERTYPE));
    }

    @Test
    public void ChangingHashWorks() {
        testHash = "El Psy Congroo";
        mongoInterfaceAccounts.changeHash(testName, testHash);
        assertEquals(testHash, mongoInterfaceAccounts.getHash(testName));
    }

    @Test
    public void NotexistantUsernameIsNotExistant() {
        assertFalse(mongoInterfaceAccounts.UserNameExists(spookName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ChangingNotexistantHashCausesException() {
        mongoInterfaceAccounts.changeHash(spookName, testHash);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ChangingNotexistantUsertypeCausesException() {
        mongoInterfaceAccounts.changeUserType(spookName, testUserType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddingSameUsernameCausesException() throws Exception {
        mongoInterfaceAccounts.addNewUserToCollection(testName, testHash, testUserType);
    }
}
