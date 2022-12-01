package fr.osmium.meregrand;

import fr.osmium.meregrand.cipher.ICipher;
import fr.osmium.meregrand.cipher.RSA;

public class MereGrandServer {

    public static void main(String[] args) {

        ICipher cipher = new RSA(2048);
        String encrypted = cipher.cipher("Dit moi que ça marche stoplé");
        System.out.println(encrypted);
        String decrypted = cipher.decipher(encrypted);
        System.out.println(decrypted);

        //ServiceManager.getInstance().run();
    }

}
