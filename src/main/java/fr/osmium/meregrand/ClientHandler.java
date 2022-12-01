package fr.osmium.meregrand;

import fr.osmium.meregrand.packet.AuthPacket;
import fr.osmium.meregrand.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

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
                case AUTH_PACKET -> {
                    AuthPacket packetAuth = (AuthPacket) packet;
                    String mail = packetAuth.getMail();
                    String mdp = packetAuth.getPassword();

                    //if(verifPassword()){
                    // Creation du jeton et envois du jeton via JetonPacket
                    //}
                    //else
                    //message d'erreur
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
