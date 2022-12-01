package fr.osmium.meregrand.Paquet;

import java.util.UUID;

public class TOKENSENDPACKET extends Packet{
    private UUID token;
    public TOKENSENDPACKET(PacketType packetType,UUID token) {
        super(packetType);
        this.token = token;
    }

    public UUID getToken(){
        return token;
    }
    @Override
    public String toString() {
        return null;
    }
}
