package fr.osmium.meregrand;

import com.sun.jdi.OpaqueFrameException;
import fr.osmium.meregrand.Paquet.AUTHPACKET;
import fr.osmium.meregrand.Paquet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientHandler(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {

            ServiceManager.LOGGER.info("Client connected");
            Packet packet = (Packet) in.readObject();
            switch (packet.getType()){
                case AUTHPACKET -> {
                    AUTHPACKET packetAuth = (AUTHPACKET) packet;
                    String[] str = packetAuth.toString().split(",");
                    String mail = str[0];
                    String mdp = str[1];
                    //if(verifPassword()){
                    // Creation du jeton et envois du jeton via JetonPacket
                    //}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
