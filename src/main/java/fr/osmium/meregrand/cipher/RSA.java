package fr.osmium.meregrand.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSA implements ICipher {

    private final KeyPair keyPair;
    private final Cipher cipher;

    public RSA() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            final SecureRandom secureRandom = new SecureRandom();
            keyPairGenerator.initialize(2048,secureRandom);
            keyPair = keyPairGenerator.generateKeyPair();
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] cipher(byte[] bytes) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] decipher(byte[] bytes) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
