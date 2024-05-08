package com.controle.pagamento.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class CryptoUtil {

    private static final String AES_ALGORITHM = "AES";

    private static final String ENCRYPTION_KEY = "1234567890123456";

    // Método para criptografar uma string usando AES
    public static String encrypt(String valueToEncrypt) throws Exception {
        Key key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(valueToEncrypt.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Método para descriptografar uma string usando AES
    public static String decrypt(String encryptedValue) throws Exception {
        Key key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes);
    }
}