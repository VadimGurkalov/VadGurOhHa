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
public class AccountToolsTest {

    private AccountTools toolbox;
    private String username;
    private String password;
    private User guest;

    public AccountToolsTest() {
        toolbox = new AccountTools();
        username = "testuser";
        password = "Peekaboo"; //Good for space station security chief, good for me
        guest = new User(username, UserType.GUEST);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        toolbox.addGuest(guest, password);
    }

    @After
    public void tearDown() throws Exception {
        toolbox.removeUser(guest);
    }

    @Test
    public void accomplishAddingNewUser() throws Exception {
        User testUser = new User("guest", UserType.GUEST);
        assertTrue(toolbox.addGuest(testUser, password));
        toolbox.removeUser(testUser);
    }

    @Test
    public void accomplishRemovingOldUser() throws Exception {
        User testUser = new User("guest", UserType.GUEST);
        toolbox.addGuest(testUser, password);
        assertTrue(toolbox.removeUser(testUser));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingDuplicateUsernameCausesIllegalArgumentException() throws Exception {
        toolbox.addGuest(guest, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removingNonExistantUserCausesIllegalArgumentException() throws Exception {
        User newUser = new User("guest", UserType.GUEST);
        toolbox.removeUser(newUser);
    }

    @Test
    public void authenticateUser() throws Exception {
        assertEquals(guest.toString(), toolbox.authenticate(username, password).toString());
    }

    @Test
    public void registerGuestAsUserSuccesfully() throws Exception {
        toolbox.registerGuestAsUser(guest);
        User user = new User(username, UserType.USER);
        assertEquals(user.toString(), toolbox.authenticate(username, password).toString());
    }

    @Test
    public void registerUserAsAdminSuccesfully() throws Exception {
        toolbox.registerUserAsAdmin(guest);
        User admin = new User(username, UserType.ADMIN);
        assertEquals(admin.toString(), toolbox.authenticate(username, password).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registeringNonexistantGuestThrowsIllegalArgumentException() throws Exception {
        toolbox.registerGuestAsUser(new User("edgecase", UserType.GUEST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registeringNonexistantUserThrowsIllegalArgumentException() throws Exception {
        toolbox.registerUserAsAdmin(new User("edgecase", UserType.GUEST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryingToAuthenticateWithEmptyPasswordCausesIllegalArgumentException() throws Exception {
        toolbox.authenticate(username, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongPasswordCausesIllegalArgumentException() throws Exception {
        toolbox.authenticate(username, "reindeerflotilla");
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryingToRegisterWithEmptyPasswordCausesIllegalArgumentException() throws Exception {
        toolbox.addGuest(guest, "");
    }
    @Test
    public void changeUserPassword() throws Exception {
        assertTrue(toolbox.changePassword(guest, "reindeerflotilla"));
    }
    @Test
    public void authenticateWithNewPassword() throws Exception {
        password = "reindeerflotilla";
        toolbox.changePassword(guest, password);
        assertEquals(guest.toString(), toolbox.authenticate(username, password).toString());
    }
    @Test(expected = IllegalArgumentException.class)
    public void tryingToChangeIntoEmptyPasswordCausesIllegalArgumentException() throws Exception {
        toolbox.changePassword(guest, "");
    }
    @Test(expected = IllegalArgumentException.class)
    public void tryingToChangePasswordOfNonexistantUserCausesIllegalArgumentException() throws Exception {
        toolbox.changePassword(new User("edgecase", UserType.GUEST), password);
    }
}
