package fr.osmium.meregrand.packet;

import java.util.UUID;

public class TokenSendPacket extends Packet{
    private final UUID token;
    public TokenSendPacket(UUID token) {
        super(PacketType.TOKEN_SEND);
        this.token = token;
    }

    public UUID getToken(){
        return token;
    }

}
