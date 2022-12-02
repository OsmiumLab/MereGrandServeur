package fr.osmium.meregrand;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.osmium.meregrand.cipher.ICipher;
import fr.osmium.meregrand.cipher.RSA;
import fr.osmium.meregrand.database.DBManager;
import fr.osmium.meregrand.database.DatabaseVerifier;
import fr.osmium.meregrand.packet.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final ICipher cipher;

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
            final Object object = in.readObject();
            if (object instanceof RequestServerKeyPacket) {
                out.writeObject(new ExchangeKeyPacket(cipher.getPublicKey()));
            } else if (object instanceof AuthPacket authPacket) {
                if (login(authPacket))
                    createToken(authPacket);
                else
                    out.writeObject(new ErrorPacket("Wrong email or password"));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createToken(AuthPacket authPacket){
        try {
            final Algorithm algorithm = Algorithm.RSA256(cipher.getPublicKey(), cipher.getPrivateKey());

            final String publicKeyClientString = DBManager.getInstance().getPublicKey(authPacket.getEmail());
            final String token = JWT.create()
                    .withIssuer(authPacket.getTargetMail())
                    .withClaim("publicKey", publicKeyClientString)
                    .withClaim("email", authPacket.getEmail())
                    .withClaim("hash", authPacket.getMessage())
                    .sign(algorithm);
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final String publicKeyC2 = DBManager.getInstance().getPublicKey(authPacket.getTargetMail());
            final EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyC2.getBytes()); // 100% CA MARCHE PAS
            final RSAPublicKey rsaPublicKeyC2 = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            final String ipTarget = DBManager.getInstance().getIp(authPacket.getTargetMail());
            final SendTokenPacket sendTokenPacket = new SendTokenPacket(rsaPublicKeyC2, token, ipTarget);
            out.writeObject(sendTokenPacket);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private boolean login(AuthPacket authPacket ) {
        String mail = authPacket.getEmail();
        String password = new String(cipher.decipher(authPacket.getPassword()));
        return DatabaseVerifier.getInstance().checkPassword(mail, password);
    }

}
