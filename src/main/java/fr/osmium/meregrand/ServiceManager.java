package fr.osmium.meregrand;

import fr.osmium.meregrand.cipher.RSA;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServiceManager {


    private RSA rsa;
    public final static Logger LOGGER = Logger.getLogger("MereGrandServer");

    private static final int PORT = 6969;

    private boolean isRunning = false;
    private ServerSocket serverSocket;

    private static ServiceManager instance = null;

    final ExecutorService executorService = Executors.newFixedThreadPool(32);

    private ServiceManager() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static ServiceManager getInstance() {
        if (instance == null) instance = new ServiceManager();
        return instance;
    }

    public RSA getRsa(){
        return rsa;
    }

    public void shutdown() {
        isRunning = false;
    }

    public void run() {
        if (isRunning) return;
        isRunning = true;
        LOGGER.info("Server is running...");
        while (isRunning) {
            try {
                final Socket currentSocketClient = serverSocket.accept();
                handleClientRequest(currentSocketClient);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleClientRequest(Socket socketClient){
        try {
            final ObjectOutputStream out = new ObjectOutputStream(socketClient.getOutputStream());
            final ObjectInputStream in = new ObjectInputStream(socketClient.getInputStream());
            final ClientHandler c = new ClientHandler(socketClient, out, in);
            executorService.execute(c);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
