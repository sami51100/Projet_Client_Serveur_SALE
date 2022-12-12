package UDP_Marche;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

import java.util.Vector;

import package_java.*;
import package_java.enums.*;

public class PoneUDP implements Runnable {
    public final int portEcoute;
    public final int portEnvoi;
    private final Messenger gestionMessage;

    public PoneUDP(int portEcoute, int portEnvoi) {
        this.portEcoute = portEcoute;
        this.portEnvoi = portEnvoi;
        this.gestionMessage = new Messenger("Pone");
    }

    public void run() {
        boolean vrai = true;
        // creation du pone
        Vector<TypeEnergie> energieProduite = new Vector<TypeEnergie>();
        energieProduite.add(TypeEnergie.ELECTRICITE);
        Vector<OrigineEnergie> origineProduction = new Vector<OrigineEnergie>();
        origineProduction.add(OrigineEnergie.ALGERIE);
        origineProduction.add(OrigineEnergie.FRANCE);
        Vector<ModeExtractionEnergie> modeExtraction = new Vector<ModeExtractionEnergie>();
        modeExtraction.add(ModeExtractionEnergie.SOLAIRE);
        Pone p1 = new Pone("SOUHAIL", "3 rue des lorier", 0, energieProduite, origineProduction, modeExtraction);

        DatagramSocket socket = null;

        while (vrai) {
            // ---------Création de la socket---------
            socket = creerSocket(socket);
            // --------reception de l'energie du marche---------

            // reception du message
            socket = recevoirMessage(socket);

            // Création du message
            byte[] tampon = new byte[1024];
            DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

            // Lecture du message du client
            String commandeclient = lectureMessageClient(msg, socket);

            Commande c1 = Commande.fromJSON(commandeclient);

            // energie produite du pone
            Energie e1 = creerEnergie(c1, p1);
            if (e1 != null) {
                // creer un fichier json pour l'energie
                String FICHIER_JSON = "src/UDP_Marche/energie_PONE/energie" + e1.getCode().getCodeCommande() + ".json";
                try {
                    FileWriter file = new FileWriter(FICHIER_JSON);
                    file.write(e1.toJSON().toString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            gestionMessage.afficheMessage("Energie produite par le pone : " + e1);

            // Transformation en tableau d'octets
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transformationTabOctet(e1, baos);

            // Création et envoi du segment UDP
            creerSegmentUDP(baos, socket);

            // Fermeture de la socket
            socket.close();
        }

    }

    // creation de la socket
    public DatagramSocket creerSocket(DatagramSocket socket) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Erreur lors de la creation du socket : " + e);
            System.exit(0);
        }
        return socket;
    }

    // Transformation en tableau d'octets d'un objet energie
    public void transformationTabOctet(Energie objet, ByteArrayOutputStream baos) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objet);
        } catch (IOException e) {
            System.err.println("Erreur lors de la serialisation : " + e);
            System.exit(0);
        }

    }

    public void creerSegmentUDP(ByteArrayOutputStream baos, DatagramSocket socket) {
        try {
            byte[] donnees = baos.toByteArray();
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length,
                    adresse, portEcoute);
            socket.send(msg);
        } catch (UnknownHostException e) {
            System.err.println("Erreur lors de la creation de l'adresse : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
    }

    public DatagramSocket recevoirMessage(DatagramSocket socket) {
        try {
            socket = new DatagramSocket(portEnvoi);
        } catch (SocketException e) {
            System.err.println("Erreur lors de la creation de la socket : " + e);
            System.exit(0);
        }
        return socket;
    }

    public String lectureMessageClient(DatagramPacket msg, DatagramSocket socket) {
        String texte = "";
        try {
            socket.receive(msg);
            texte = new String(msg.getData(), 0, msg.getLength());

            gestionMessage.afficheMessage("Lu " + texte);
        } catch (IOException e) {
            System.err.println("Erreur lors de la reception du message : " + e);
            System.exit(0);
        }
        return texte;
    }

    public Energie creerEnergie(Commande c1, Pone p1) {
        Energie e1 = null;
        if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.ELECTRICITE))
                && c1.getModeExtraction().equals(String.valueOf(ModeExtractionEnergie.SOLAIRE))
                && c1.getOrigineGeographique().equals(String.valueOf(OrigineEnergie.FRANCE))) {
            // SCENARIO 2 : Energie bientot disponible en cours d'acheminement
            gestionMessage
                    .afficheMessage("Energie bientot disponible en cours d'acheminement : " + c1.getTypeEnergie());

            // met le programme en attente pendant 5 secondes
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            e1 = new Energie(TypeEnergie.getValue(c1.getTypeEnergie()), c1.getPrixMaxUnite(), c1.getQuantiteDesire(),
                    ModeExtractionEnergie.SOLAIRE,
                    OrigineEnergie.FRANCE, p1.getId(), "en cours");
            return e1;
        } else if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.PETROLE))) {
            // SCENARIO 1 : Energie disponible
            e1 = new Energie(TypeEnergie.PETROLE, 10.9, 400, ModeExtractionEnergie.FORAGEHORIZONTAL,
                    OrigineEnergie.ALGERIE,
                    p1.getId(), "en cours");
            return e1;
        } else if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.GAZNATUREL))) {
            e1 = new Energie(TypeEnergie.GAZNATUREL, 5.7, 300, ModeExtractionEnergie.FORAGEVERTICAL,
                    OrigineEnergie.CHINE,
                    p1.getId(), "en cours");
            return e1;
        } else if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.BIOMETHANE))) {
            e1 = new Energie(TypeEnergie.BIOMETHANE, 13.34, 200, ModeExtractionEnergie.FORAGEVERTICAL,
                    OrigineEnergie.ALLEMAGNE,
                    p1.getId(), "en cours");
            return e1;
        } else if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.HYDROGENE))) {
            e1 = new Energie(TypeEnergie.HYDROGENE, 11.4, 100, ModeExtractionEnergie.FORAGEVERTICAL,
                    OrigineEnergie.PAYSBAS,
                    p1.getId(), "en cours");
            return e1;
        } else if (c1.getTypeEnergie().equals(String.valueOf(TypeEnergie.ELECTRICITE))) {
            e1 = new Energie(TypeEnergie.ELECTRICITE, 11.4, 100, ModeExtractionEnergie.NUCLEAIRE,
                    OrigineEnergie.TURQUIE,
                    p1.getId(), "en cours");
            return e1;
        }
        e1 = new Energie(TypeEnergie.HYDROGENE, 35.3, 200, ModeExtractionEnergie.FORAGEVERTICAL,
                OrigineEnergie.ESPAGNE,
                p1.getId(), "en cours");

        return e1;
    }
}