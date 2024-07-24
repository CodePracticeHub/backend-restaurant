package com.restaurantmanagement.utility;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class GenerateBase64Secret {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();
        String base64EncodedSecret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Base64 Encoded Secret: " + base64EncodedSecret);
    }
}
