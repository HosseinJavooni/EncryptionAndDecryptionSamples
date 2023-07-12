package JWT_generator_for_request_sign;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class JwtGenerator {
    
    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;

//    public JwtGenerator() throws NoSuchAlgorithmException {
//        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//        keyPair = keyPairGenerator.generateKeyPair();
//    }

    public String generateJwt(Map<String, String> payload) throws Exception {
        Builder tokenBuilder = JWT.create();
        payload.forEach((key, value) -> tokenBuilder.withClaim(key, value));
        return  tokenBuilder.sign(Algorithm.RSA256(((RSAPublicKey) readPublicKeyFromFile()), ((RSAPrivateKey) readPrivateKeyFromFile())));
    }

    public PrivateKey readPrivateKeyFromFile() throws Exception {
        File privateKeyFile = new File("src/main/resources/privateKey.anyExtention");
        String key = new String(Files.readAllBytes(privateKeyFile.toPath()), Charset.defaultCharset());
        // We can give private key as a String here also
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        System.out.println("=====================================================");
        System.out.println("Private key is: \n" + privateKeyPEM);
        System.out.println("=====================================================");
        byte[] encoded = Base64.decodeBase64(privateKeyPEM);
        try {
            return getPrivateKey(encoded);
        } catch (Exception e){
            if(e.getMessage().contains("InvalidKeyException")){
                //BouncyCastle is a Java library that complements the default Java Cryptographic Extension (JCE).
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                return getPrivateKey(encoded);
            } else {
                throw e;
            }
        }
    }

    private PrivateKey getPrivateKey(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey;
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        privateKey =   keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public PublicKey readPublicKeyFromFile() throws Exception {
        File privateKeyFile = new File("src/main/resources/publicKey.anyExtention");
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
