package fr.osmium.meregrand.Paquet;

import java.util.UUID;

public class SENDMESSAGEPACKET extends Packet{

    private UUID Token;
    private String message;
    public SENDMESSAGEPACKET(PacketType packetType) {
        super(packetType);
    }

    @Override
    public String toString() {
        return Token + "," + message;
    }
}
