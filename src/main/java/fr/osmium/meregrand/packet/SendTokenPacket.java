package fr.osmium.meregrand.packet;

public class SendTokenPacket extends Packet {

    private final String publicKey;
    private final String token;
    private final String ip;

    public SendTokenPacket(String token, String publicKey, String ip) {
        super(PacketType.SEND_TOKEN_PACKET);
        this.token = token;
        this.publicKey = publicKey;
        this.ip = ip;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getToken() {
        return token;
    }
}
