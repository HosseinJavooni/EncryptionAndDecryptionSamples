package AES;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String cipherEncryptText = AES256.encrypt("aTextForEncryption");
        System.out.println("AES256.encrypt: \n"+ cipherEncryptText);
        String cipherDecryptText = AES256.decrypt(cipherEncryptText);
        System.out.println("AES256.decrypt: \n"+ cipherDecryptText);
        //        System.out.println("Hello world!");
//       String encrypted =  encrypt("AES", "Hossein", generateSecretKey("SGHuLCpqnjQnLPzFnu5ODoDvsfEa", "NljjeFH5TaeQN6d5AuqbNyBPSNMa"));
//        System.out.println("encrypted:\n" + encrypted);
//        String decrypted = decrypt("", "", generateSecretKey("", ""));
    }

    public static String encrypt(String algorithm, String input, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key,  new IvParameterSpec(key.getEncoded(), 0, 16));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }
    private static SecretKey generateSecretKey(String password, String salt) {
        byte[] saltBytes = salt.getBytes(StandardCharsets.US_ASCII);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 1024, 256);
        SecretKey secretKey = null;
        try {
            secretKey = (factory != null) ? factory.generateSecret(spec) : null;
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
        }
        return secretKey;
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getEncoded(), "AES"), new IvParameterSpec(key.getEncoded(), 0, 16));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
}