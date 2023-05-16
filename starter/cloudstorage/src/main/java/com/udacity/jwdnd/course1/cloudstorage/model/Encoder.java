package com.udacity.jwdnd.course1.cloudstorage.model;
import java.security.SecureRandom;
import java.util.Base64;


public class Encoder {
    public static String getEncoded() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String randomstr=Base64.getEncoder().encodeToString(salt);
        return randomstr;
    }
}
