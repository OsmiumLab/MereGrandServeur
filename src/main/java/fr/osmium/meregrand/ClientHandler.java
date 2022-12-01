package fr.osmium.meregrand;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private Socket clientSocket;
    private DataOutputStream dOut;
    private DataInputStream dIn;

    public ClientHandler(Socket clientSocket, DataOutputStream dOut, DataInputStream dIn) {
        this.clientSocket = clientSocket;
        this.dOut = dOut;
        this.dIn = dIn;
    }

    @Override
    public void run() {

    }
}
