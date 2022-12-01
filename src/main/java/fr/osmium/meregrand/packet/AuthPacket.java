package fr.osmium.meregrand.packet;

public class AuthPacket extends Packet {

    private final String mail;
    private final String password;

    public AuthPacket(String mail, String password) {
        super(PacketType.AUTH_PACKET);
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
