package fr.osmium.meregrand.packet;

public class TokenSend extends Packet{

    private String publicKey;
    private String token;
    public TokenSend(String token,String publicKey) {
        super(PacketType.TOKEN_SEND);
        this.token = token;
        this.publicKey = publicKey;

    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getToken() {
        return token;
    }
}
