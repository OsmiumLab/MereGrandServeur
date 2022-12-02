package fr.osmium.meregrand;

import fr.osmium.meregrand.cipher.ICipher;
import fr.osmium.meregrand.cipher.RSA;
import fr.osmium.meregrand.packet.ExchangePacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final ICipher cipher;

    private String clientPublicKey;

    public ClientHandler(Socket clientSocket, ObjectOutputStream out, ObjectInputStream in) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
        this.cipher = new RSA(2048);
    }

    @Override
    public void run() {
        try {
            ServiceManager.LOGGER.info("Client connected to server");
            swapPublicKeys();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void swapPublicKeys() throws IOException, ClassNotFoundException {
        out.writeObject(new ExchangePacket(cipher.getPublicKey()));
        ExchangePacket exchangePacket = (ExchangePacket) in.readObject();
        clientPublicKey = exchangePacket.getPublicKey();
    }

}
