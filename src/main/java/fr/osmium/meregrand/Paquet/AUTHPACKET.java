package fr.osmium.meregrand.Paquet;

public class AUTHPACKET extends Packet{


    private String mail;
    private String mdp;

    public AUTHPACKET(PacketType packetType,String mdp,String mail){
        super(packetType);
        this.mail = mail;
        this.mdp = mdp;

    }

    @Override
    public String toString() {
        return mail + "," + mdp;
    }
}
