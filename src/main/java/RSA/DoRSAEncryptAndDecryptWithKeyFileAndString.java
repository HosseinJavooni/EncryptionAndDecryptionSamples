package RSA;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DoRSAEncryptAndDecryptWithKeyFileAndString {

        // Decrypt using RSA public key
        public String decryptMessage(String encryptedText, PublicKey publicKey) throws Exception {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));
        }

        // Encrypt using RSA private key
        public String encryptMessage(String plainText, PrivateKey privateKey) throws Exception {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes()));
        }

        public PrivateKey readPrivateKeyFromFile() throws Exception {
            File privateKeyFile = new File("src/main/resources/privateKey.pem");
            String key = new String(Files.readAllBytes(privateKeyFile.toPath()), Charset.defaultCharset());

            // We can give private key as a String here also
            String privateKeyPEM = key
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END RSA PRIVATE KEY-----", "");

            System.out.println("=====================================================");
            System.out.println("Private key is: \n" + privateKeyPEM);
            System.out.println("=====================================================");

            byte[] encoded = Base64.decodeBase64(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);
        }

        public PublicKey readPublicKeyFromFile() throws Exception {
            File privateKeyFile = new File("src/main/resources/publicKey.pem");
            String key = new String(Files.readAllBytes(privateKeyFile.toPath()), Charset.defaultCharset());

            // We can give public key as a String here also
            String publicKeyPEM = key
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END PUBLIC KEY-----", "");
            System.out.println("=====================================================");
            System.out.println("Public key is: \n" + publicKeyPEM);
            System.out.println("=====================================================");

            byte[] encoded = Base64.decodeBase64(publicKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        }
    }