package fr.osmium.meregrand;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
