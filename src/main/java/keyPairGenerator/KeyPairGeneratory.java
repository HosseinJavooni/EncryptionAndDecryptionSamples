package keyPairGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPairGeneratory {
    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    public void generateKeyPair() throws NoSuchAlgorithmException, IOException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        String privateOutFile = "src/main/resources/privateKey";
        FileWriter fileOutputStream = new FileWriter(privateOutFile + ".pem");
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        String privateKeyInString = encoder.encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("private key: \n" + privateKeyInString);
        fileOutputStream.write(
                "-----BEGIN RSA PRIVATE KEY-----\n" +
                injectEnterCharacterToString(privateKeyInString , 70) + "\n" +
                "-----END RSA PRIVATE KEY-----"
                );
        fileOutputStream.close();
        String publicKeyInString = encoder.encodeToString(keyPair.getPublic().getEncoded());
        System.out.println("public key: : \n" + publicKeyInString);
        String publicOutFile = "src/main/resources/publicKey";
        FileWriter fileOutputStream1 = new FileWriter(publicOutFile + ".pem");
        fileOutputStream1.write(
                "-----BEGIN PUBLIC KEY-----\n" +
                injectEnterCharacterToString(publicKeyInString,70) + "\n" +
                "-----END PUBLIC KEY-----"
        );
        fileOutputStream1.close();
    }
    public String injectEnterCharacterToString(String string, int jumpHowManyCharacter){
        int length = string.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i += jumpHowManyCharacter) {
            if(length > i + jumpHowManyCharacter)
                result.append(string, i, Math.min(length, i + jumpHowManyCharacter)).append("\n");
            else
                result.append(string, i, Math.min(length, i + jumpHowManyCharacter));
        }
        return result.toString();
    }
}
