package fr.osmium.meregrand;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import fr.osmium.meregrand.cipher.ICipher;
import fr.osmium.meregrand.cipher.RSA;
import fr.osmium.meregrand.database.DBManager;
import fr.osmium.meregrand.database.DBVerif;
import fr.osmium.meregrand.packet.AuthPacket;
import fr.osmium.meregrand.packet.ExchangePacket;
import fr.osmium.meregrand.packet.Packet;
import fr.osmium.meregrand.packet.TokenSend;
import org.sqlite.core.DB;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.interfaces.RSAPublicKey;

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
            Packet packet = (Packet) in.readObject();
            switch (packet.getType()){
                case EXCHANGE_PACKET -> {

                }
                case AUTH_PACKET -> {
                    if(login((AuthPacket) packet)){

                    }
                    else{
                        //message d'erreur
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void createToken(AuthPacket authPacket){
        Algorithm algorithm = Algorithm.RSA256(ServiceManager.getInstance().getRsa().getPublicKey(),ServiceManager.getInstance().getRsa().getPrivateKey());
        String token = JWT.create().withIssuer(authPacket.getTargetMail()).withClaim("publicKey",DBManager.getInstance().getPublicKey(authPacket.getEmail())).withClaim("Email",authPacket.getEmail()).withClaim("hash",authPacket.getMessage()).sign(algorithm);
        String publicKeyC2 = DBManager.getInstance().getPublicKey(authPacket.getTargetMail());
        TokenSend tokenSend = new TokenSend(token,publicKeyC2);
        try{
            out.writeObject(tokenSend);
        }catch(IOException e){
            e.printStackTrace();
        }

    }



    private void swapPublicKeys(ExchangePacket ex) throws IOException, ClassNotFoundException {
        out.writeObject(new ExchangePacket(cipher.getPublicKey()));
        ExchangePacket exchangePacket = (ExchangePacket) in.readObject();
        clientPublicKey = exchangePacket.getPublicKey();
    }

    /* check le login du client et renvois vrai ou faux */
    private boolean login(AuthPacket authPacket){
        String mail = ServiceManager.getInstance().getRsa().decipher(authPacket.getEmail());
        String password = ServiceManager.getInstance().getRsa().decipher(authPacket.getPassword());
        return DBVerif.getInstance().pwdVerif(mail,password);
    }


}
