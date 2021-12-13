package com.test.pricesearch.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesUtil {
    final private static String key="123456789abcdefg";
    private static byte[] encryptToBytes(String strToEncrypt) throws Exception  {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
    }

    public static String encrypt(String strToEncrypt) throws Exception  {
        String encryptedStr = Base64.getEncoder().encodeToString(encryptToBytes(strToEncrypt));
        return encryptedStr;
    }

    private static byte[] decryptToBytes(byte[] bytesToDecrypt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return  cipher.doFinal(bytesToDecrypt);
    }

    public static String decrypt(String strToDecrypt) throws Exception {
        byte[] bytesToDecrypt = Base64.getDecoder().decode(strToDecrypt);
        String decreptedStr = new String(decryptToBytes(bytesToDecrypt));
        return decreptedStr;
    }
}
