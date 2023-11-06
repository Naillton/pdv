package com.api.pdv.middleware;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserMiddleware {


    public static Boolean ValidUser(String name, String password) {
        return name.length() >= 3 && password.length() >= 8;
    }

    public static String getHashMd5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEncode64(String value) {
        try {
            return Base64.getEncoder().encodeToString(value.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDecode64(String value) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            return new String(decodedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
