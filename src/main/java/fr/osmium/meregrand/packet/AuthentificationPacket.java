package fr.osmium.meregrand.packet;

import java.math.BigInteger;
import java.security.PublicKey;

public class AuthentificationPacket extends Packet{
    private String mail;
    private String password;
    private String hashMessage;

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return hashMessage;
    }

    public AuthentificationPacket(String mail,String password,String hashMessage) {
        super(PacketType.AUTHENTIFICATION_PACKET);
        this.mail = mail;
        this.password = password;
        this.hashMessage = hashMessage;

    }
}
