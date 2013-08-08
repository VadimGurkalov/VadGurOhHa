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
public class PasswordTest {
    private String password1;
    private String password2;
    private String[] saltAndPass1;
    private String[] saltAndPass2;
    
    public PasswordTest() {
        password1 = "CorrectHorseBatteryStaple";
        password2 = "KlaatuBaradaNikto";
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
    public void passwordMatches() throws Exception {
        String saltedHash = Password.getSaltedHash(password1);
        for (int i = 0; i < 99; i++) {
            if(!Password.check(password1, saltedHash)) {
                fail();
            }
        }
        assertTrue(true);
    }

    @Test
    public void differentPasswordsDoNotMatch() throws Exception {
        for (int i = 0; i < 99; i++) {
            if (Password.check(password1, Password.getSaltedHash(password2))) {
                fail();
            }
        }
        assertTrue(true);
    }
    @Test
    public void samePasswordsHaveDifferentHashValues() throws Exception {
        for (int i = 0; i < 99; i++) {
            saltAndPass1 = Password.getSaltedHash(password1).split("\\$");
            saltAndPass2 = Password.getSaltedHash(password1).split("\\$");
            if (saltAndPass1[1].equals(saltAndPass2[1])) {
                fail();
            }
        }
        assertTrue(true);
    }
    @Test
    public void samePasswordsHaveDifferentSaltValues() throws Exception {
        for (int i = 0; i < 99; i++) {
            saltAndPass1 = Password.getSaltedHash(password1).split("\\$");
            saltAndPass2 = Password.getSaltedHash(password1).split("\\$");
            if (saltAndPass1[0].equals(saltAndPass2[0])) {
                fail();
            }
        }
        assertTrue(true);
    }
    @Test(expected=IllegalArgumentException.class)
    public void creatingSaltedhashWithEmptyPasswordCausesIllegalArgumentException() throws Exception {
        Password.getSaltedHash("");
    }
    @Test(expected=IllegalArgumentException.class)
    public void checkinPasswordWithEmptyPasswordCausesIllegalArgumentException() throws Exception {
        Password.check("", Password.getSaltedHash(password1));
    }
}
