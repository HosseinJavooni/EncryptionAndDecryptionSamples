package RSA;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAMain {
    public static void main(String[] args) throws Exception {
        String plainText = "Hello RSA Encryption!";
        DoRSAEncryptAndDecryptWithKeyFileAndString rsaEncryptionJava = new DoRSAEncryptAndDecryptWithKeyFileAndString();
        PrivateKey privateKey = rsaEncryptionJava.readPrivateKeyFromFile();
        PublicKey publicKey = rsaEncryptionJava.readPublicKeyFromFile();
        String encryptedText = rsaEncryptionJava.encryptMessage(plainText, privateKey);
        String descryptedText = rsaEncryptionJava.decryptMessage(encryptedText, publicKey);

        System.out.println("input:\n" + plainText);
        System.out.println("encrypted:\n" + encryptedText);
        System.out.println("decrypted:\n" + descryptedText);
    }
}
