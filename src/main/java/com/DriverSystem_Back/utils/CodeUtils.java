package com.DriverSystem_Back.utils;

import java.security.SecureRandom;

public class CodeUtils {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder codigo = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }
        return codigo.toString();
    }
}
