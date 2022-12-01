package fr.osmium.meregrand.packet;

import java.util.UUID;

public class SendMessagePacket extends Packet{

    private final UUID token;
    private final String message;
    public SendMessagePacket(UUID token, String message) {
        super(PacketType.SEND_MESSAGE);
        this.token = token;
        this.message = message;
    }

    public UUID getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

}
