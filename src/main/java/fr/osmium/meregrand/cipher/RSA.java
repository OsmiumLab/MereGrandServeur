package fr.osmium.meregrand.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSA implements ICipher {

    private final KeyPair keyPair;
    private final Cipher cipher;

    public RSA() {
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] cipher(byte[] bytes) {
        try {
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] decipher(byte[] bytes) {
        // TODO
        return null;
    }
}
