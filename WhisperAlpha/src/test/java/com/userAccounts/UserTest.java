/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userAccounts;

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
public class UserTest {
    private User guest;
    private User user;
    private User admin;
    private User daemon;
    
    public UserTest() {
        guest = new User("guest", UserType.GUEST);
        user = new User("user", UserType.USER);
        admin = new User("admin", UserType.ADMIN);
        daemon = new User("daemon", UserType.DAEMON);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void userNameReturnsCorrectly() {
        assertEquals("guest", guest.getUserName());
    }
    @Test
    public void guestUserTypeReturnsCorrectly() {
        assertEquals(UserType.GUEST, guest.getUserType());
    }
    @Test
    public void userUserTypeReturnsCorrectly() {
        assertEquals(UserType.USER, user.getUserType());
    }
    @Test
    public void adminUserTypeReturnsCorrectly() {
        assertEquals(UserType.ADMIN, admin.getUserType());
    }
    @Test
    public void weHaveDaemonInfestationHereBetterCallExterminator() {
        assertEquals(UserType.DAEMON, daemon.getUserType());
    }
    @Test
    public void guestToStringWorksAsIntended() {
        assertEquals("guest|" + UserType.GUEST, guest.toString());
    }
    @Test
    public void userToStringWorksAsIntended() {
        assertEquals("user|" + UserType.USER, user.toString());
    }
    @Test
    public void adminToStringWorksAsIntended() {
        assertEquals("admin|" + UserType.ADMIN, admin.toString());
    }
    @Test
    public void daemonToStringWorksAsIntended() {
        assertEquals("daemon|" + UserType.DAEMON, daemon.toString());
    }
}
