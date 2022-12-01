package fr.osmium.meregrand;

import com.sun.jdi.OpaqueFrameException;
import fr.osmium.meregrand.packet.AuthPacket;
import fr.osmium.meregrand.packet.Packet;
import fr.osmium.meregrand.packet.SendMessagePacket;

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
                case SEND_MESSAGE -> {
                    SendMessagePacket packetSend = (SendMessagePacket) packet;
                    try{
                        Socket client = new Socket(packetSend.getIpDest(),packetSend.getPortDest());
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        out.writeObject(packetSend);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
