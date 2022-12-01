package fr.osmium.meregrand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceManager {

    private boolean isRunning = false;
    private ServerSocket serverSocket;
    private Socket currentSocketClient;
    private int port = 6969;
    private static ServiceManager instance = null;


    ExecutorService executorService = Executors.newFixedThreadPool(32);

    private ServiceManager(){
        try{
            serverSocket = new ServerSocket(6969);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public ServiceManager getInstance() {
        if(instance == null){
            instance = new ServiceManager();
        }
        return instance;
    }


    public void shutdown(){
        isRunning = false;
    }

    public void run(){
        if(isRunning){
            return;
        }
        isRunning = true;
        while(isRunning){
            try{
                currentSocketClient = serverSocket.accept();
            }catch(IOException e){
                e.printStackTrace();
            }

            if(currentSocketClient != null){

            }
        }
    }
    private void handleClientRequest(Socket socketClient){
        try {

            DataInputStream din = new DataInputStream(socketClient.getInputStream());
            DataOutputStream dout = new DataOutputStream(socketClient.getOutputStream());
            ClientHandler c = new ClientHandler(socketClient,dout,din);
            executorService.execute(c);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
