package fr.osmium.meregrand.cipher;

public interface ICipher {

    byte[] cipher(byte[] bytes);
    byte[] decipher(byte[] bytes);

}
