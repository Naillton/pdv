package com.api.pdv.util;

public class UserMiddleware {

    public static Boolean ValidUser(String name, String password) {
        return name.length() >= 3 && password.length() >= 8;
    }
}
