/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.userAccounts;

import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author Martin Konicek
 */
public class Password {
    private static final int iter = 10*1024;
    private static final int saltLen = 32;
    private static final int desideKeyLen = 256;
    
    public static String getSaltedHash (String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        return Base64.encodeBase64String(salt) +  "$" + hash(password, salt);
    }
    
    public static boolean check(String password, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            return false;
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }
    
    private static String hash (String password, byte[] salt) throws Exception {
        if (password == null | password.length() == 0) {
            throw new IllegalArgumentException("The password cannot be empty");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec (password.toCharArray(),
                salt, iter, desideKeyLen));
        return Base64.encodeBase64String(key.getEncoded());
    }
}
