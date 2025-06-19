package com.juaracoding.smartpro_rest_api.security;

public class BCryptImpl {
    private static final BCryptCustom bcrypt = new BCryptCustom(11);

    public static String hash(String plain) {
        return bcrypt.hash(plain);
    }

    public static Boolean verifyHash(String plain, String hashed) {
        return bcrypt.verifyHash(plain, hashed);
    }
}
