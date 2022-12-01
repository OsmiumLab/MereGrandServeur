package fr.osmium.meregrand.packet;

import java.util.UUID;

public class SendMessagePacket extends Packet{

    private final UUID token;
    private final String message;

    private final String ipDest;
    private final int portDest;
    public SendMessagePacket(UUID token, String message, String ipDest, int portDest) {
        super(PacketType.SEND_MESSAGE);
        this.token = token;
        this.message = message;
        this.ipDest = ipDest;
        this.portDest = portDest;
    }

    public int getPortDest() {
        return portDest;
    }

    public UUID getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getIpDest() {
        return ipDest;
    }
}
