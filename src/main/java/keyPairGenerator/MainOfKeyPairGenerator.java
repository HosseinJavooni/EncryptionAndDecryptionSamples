package keyPairGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainOfKeyPairGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGeneratory keyPairGenerator = new KeyPairGeneratory();
        keyPairGenerator.generateKeyPair();
    }
}
