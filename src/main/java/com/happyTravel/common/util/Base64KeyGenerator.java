package com.happyTravel.common.util;

import java.util.Base64;

public class Base64KeyGenerator {

    public static String generateBase64Key(String plainTextKey) {
        return Base64.getEncoder().encodeToString(plainTextKey.getBytes());
    }

    public static void main(String[] args) {
        String testKey = "theQuickBrownFoxJumpsOverTheLazyDogTheQuickBrownFoxJumpsOverTheLazyDog";
        String base64Key = generateBase64Key(testKey);
        System.out.println("임시 Base64 Key: " + base64Key);

        System.out.println("⚠️ 이 키는 운영 환경에서 사용하면 안 됩니다!");
    }
}
