package com.example.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String[] passwordHash(String username, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytesUsername = md.digest(username.getBytes());
        byte[] hashedBytesPassword = md.digest(password.getBytes());

        // Convert byte array to a hexadecimal representation
        StringBuilder sbU = new StringBuilder();
        for (byte b : hashedBytesUsername) {
            sbU.append(String.format("%02x", b));
        }

        StringBuilder sbP = new StringBuilder();
        for (byte b : hashedBytesPassword) {
            sbP.append(String.format("%02x", b));
        }

        String[] hash = new String[2];
        hash[0] = sbU.toString();
        hash[1] = sbP.toString();

        return hash;
    }
}
