package UDP_Marche;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;
import package_java.*;
import package_java.enums.*;

public class MarcheUDP implements Runnable {
    public final int portEcouteTare;
    public final int portEnvoiTare;
    public final int portEcouteUDP;
    public final int portEnvoiUDP;
    public final int portEcouteTCP;
    private final Messenger gestionMessage;

    public MarcheUDP(int portEcouteUDP, int portEnvoiUDP, int portEcouteTCP, int portEcouteTare, int portEnvoiTare) {
        this.portEcouteTare = portEcouteTare;
        this.portEnvoiTare = portEnvoiTare;
        this.portEcouteUDP = portEcouteUDP;
        this.portEnvoiUDP = portEnvoiUDP;
        this.portEcouteTCP = portEcouteTCP;
        this.gestionMessage = new Messenger("Marche de Gros");
    }

    public void run() {
        boolean vrai = true;
        MarcheGros m1 = MarcheGros.recupererFichier("Energie.json");
        // Création de la socket UDP PONE
        DatagramSocket socket = null;
        socket = creerSocketUDP(socket, portEcouteUDP);
        // Création de la socket UDP TARE
        DatagramSocket socket_TARE = null;
        socket_TARE = creerSocketUDP(socket_TARE, portEcouteTare);
        System.out.println("-----------------Serveur UDP Marche demarre--------------");

        while (vrai) {
            // ----------lecture de la socket pour le TARE-------------

            DatagramPacket msgRecu_TARE = null;
            msgRecu_TARE = lectureMessageUDP(socket_TARE, msgRecu_TARE);

            RSA.GenererClesRSA("privee.bin", "publique.bin");
            // Récupération de l'objet
            Commande c2 = recuperationObjetUDPCommande(msgRecu_TARE);

            if (c2.getEtat().equals("verification")) {
                gestionMessage.afficheMessage("Commande envoyee pour verficication au autorite ");
                Energie e3 = new Energie(TypeEnergie.getValue(c2.getTypeEnergie()), c2.getQuantiteDesire(),
                        c2.getPrixMaxUnite(),
                        ModeExtractionEnergie.getValue(c2.getModeExtraction()),
                        OrigineEnergie.getValue(c2.getOrigineGeographique()), c2.getIdClient(), c2.getEtat());
                // ----------Création de la socket pour l'AMI-------------
                Socket socket_ami = null;
                socket_ami = creerSocketTCP(socket_ami);

                // Association d'un flux d'entrée et de sortie AMI
                BufferedReader input = null;
                PrintWriter output = null;
                input = associationFluxInputTCP(input, socket_ami);
                output = associationFluxOutputTCP(socket_ami, output);

                // envoyer une réponse au client AMI
                JSONObject json = e3.toJSON();
                String reponse = json.toString();

                output.println(RSA.chiffrerRSA("publique.bin", reponse));

                // reception de la réponse du client AMI
                String reponseClient = "";
                reponseClient = receptionMessageTCP(input, reponseClient);
                // Affichage de la réponse du client AMI
                gestionMessage.afficheMessage("Lu : " + reponseClient);

                // ----------envoie de la reponse au tare------------------
                // Création du message
                DatagramPacket msg_TARE = null;
                msg_TARE = creationMessageUDP(msg_TARE, portEnvoiTare, reponseClient);
                // Envoi du message
                envoiMessageUDP(socket_TARE, msg_TARE);
            } else {
                Energie e2 = m1.comparerCommandeEnergie(c2);
                if (e2 == null) {
                    gestionMessage.afficheMessage("Aucune energie ne correspond a la commande sur le marche de gros");
                    gestionMessage.afficheMessage("Envoi de la commande au PONE");
                    // ----------envoie de la commande au pone------------------

                    // Création du message
                    DatagramPacket msg = null;
                    msg = creationMessageUDP(msg, portEnvoiUDP, c2.toJSON().toString());

                    // Envoi du message
                    envoiMessageUDP(socket, msg);

                    DatagramPacket msgRecu = null;
                    msgRecu = lectureMessageUDP(socket, msgRecu);

                    // Récupération de l'objet
                    Energie e1 = recuperationObjetUDP(msgRecu);
                    if (e1 != null) {
                        gestionMessage.afficheMessage(e1.getCode().toString());
                    }
                    m1.ajouterEnergie(e1);

                    // ----------Création de la socket pour l'AMI-------------
                    Socket socket_ami = null;
                    socket_ami = creerSocketTCP(socket_ami);

                    // Association d'un flux d'entrée et de sortie AMI
                    BufferedReader input = null;
                    PrintWriter output = null;
                    input = associationFluxInputTCP(input, socket_ami);
                    output = associationFluxOutputTCP(socket_ami, output);

                    // envoyer une réponse au client AMI
                    JSONObject json = e1.toJSON();
                    String reponse = json.toString();
                    output.println(RSA.chiffrerRSA("publique.bin", reponse));

                    // reception de la réponse du client AMI
                    String reponseClient = "";
                    reponseClient = receptionMessageTCP(input, reponseClient);
                    // Affichage de la réponse du client AMI
                    gestionMessage.afficheMessage("Lu : " + reponseClient);

                    if (reponseClient.equals("prix trop eleve")) {
                        m1.supprimerEnergie(e1);
                        gestionMessage.afficheMessage("Energie supprime");
                    }
                    m1.creerFichier("Energie.json");

                    // compare la comande du client avec les energie du marche
                    e2 = m1.comparerCommandeEnergie(c2);
                } else {
                    gestionMessage
                            .afficheMessage("Energie disponible sur le marche de gros envoie de l'energie au TARE");
                    // creer un fichier json pour l'energie
                    String FICHIER_JSON = "src/UDP_Marche/energie_PONE/energie" + e2.getCode().getCodeCommande()
                            + ".json";
                    try {
                        FileWriter file = new FileWriter(FICHIER_JSON);
                        file.write(e2.toJSON().toString());
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // -----------envoie de l'energie au TARE------------------
                if (e2 != null) {

                    gestionMessage.afficheMessage("Energie vendue au TARE : " + e2.toString());
                    m1.creerFichier("Energie.json");

                    JSONObject objet = e2.toJSON();

                    // Création du message
                    DatagramPacket msg_TARE = null;
                    msg_TARE = creationMessageUDP(msg_TARE, portEnvoiTare, objet.toString());

                    // Envoi du message
                    envoiMessageUDP(socket_TARE, msg_TARE);

                    String reponse = objet.toString();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // ----------Création de la socket pour l'AMI pour valider l'achat-------------
                    // Socket socket_ami = null;
                    // socket_ami = creerSocketTCP(socket_ami);

                    // // Association d'un flux d'entrée et de sortie AMI
                    // BufferedReader input = null;
                    // PrintWriter output = null;
                    // input = associationFluxInputTCP(input, socket_ami);
                    // output = associationFluxOutputTCP(socket_ami, output);

                    // gestionMessage.afficheMessage("envoie de l'energie achete a l'AMI pour
                    // certification " + reponse);
                    // output.println(RSA.chiffrerRSA("publique.bin", reponse));

                } else {

                    // Création du message
                    DatagramPacket msg_TARE = null;
                    String messagePASENERGIE = "aucune energie disponible";
                    msg_TARE = creationMessageUDP(msg_TARE, portEnvoiTare, messagePASENERGIE);
                    // Envoi du message
                    envoiMessageUDP(socket_TARE, msg_TARE);

                    // try {
                    // Thread.sleep(2000);
                    // } catch (InterruptedException e) {
                    // e.printStackTrace();
                    // }
                    // // ----------Création de la socket pour l'AMI pour valider
                    // l'achat-------------
                    // Socket socket_ami = null;
                    // socket_ami = creerSocketTCP(socket_ami);

                    // // Association d'un flux d'entrée et de sortie AMI
                    // BufferedReader input = null;
                    // PrintWriter output = null;
                    // input = associationFluxInputTCP(input, socket_ami);
                    // output = associationFluxOutputTCP(socket_ami, output);
                    // gestionMessage.afficheMessage("Aucune energie disponible a la validation de
                    // l'achat");
                    // output.println(RSA.chiffrerRSA("publique.bin", messagePASENERGIE));
                }
            }
        }
        // Fermeture de la socket
        socket.close();
    }

    // creation de la socket UDP
    public DatagramSocket creerSocketUDP(DatagramSocket socket, int portEcouteUDP) {
        try {
            socket = new DatagramSocket(portEcouteUDP);
        } catch (SocketException e) {
            System.err.println("Erreur lors de la creation de la socket MARCHE: " + e);
            System.exit(0);
        }
        return socket;
    }

    // lecture message client UDP
    public DatagramPacket lectureMessageUDP(DatagramSocket socket, DatagramPacket msgRecu) {
        try {
            byte[] tampon = new byte[1024];
            msgRecu = new DatagramPacket(tampon, tampon.length);
            socket.receive(msgRecu);
        } catch (IOException e) {
            System.err.println("Erreur lors de la reception du message : " + e);
            System.exit(0);
        }
        return msgRecu;
    }

    // recuperation objet UDP energie
    public Energie recuperationObjetUDP(DatagramPacket msgRecu) {
        Energie e1 = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            e1 = (Energie) ois.readObject();

            gestionMessage.afficheMessage("Lu " + e1);
        } catch (ClassNotFoundException e) {
            System.err.println("Objet reçu non reconnu : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la recuperation de l'objet : " + e);
            System.exit(0);
        }

        return e1;
    }

    // recuperation objet UDP energie
    public Commande recuperationObjetUDPCommande(DatagramPacket msgRecu) {
        Commande e1 = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(msgRecu.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            e1 = (Commande) ois.readObject();
            JSONObject objet = e1.toJSON();

            gestionMessage.afficheMessage("Lu " + objet.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("Objet reçu non reconnu : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de la recuperation de l'objet : " + e);
            System.exit(0);
        }

        return e1;
    }

    // creation message UDP
    public DatagramPacket creationMessageUDP(DatagramPacket msg, int portEnvoiUDP, String message) {
        try {
            InetAddress adresse = InetAddress.getByName(null);
            byte[] tmp = message.getBytes();
            msg = new DatagramPacket(tmp, tmp.length, adresse, portEnvoiUDP);

        } catch (UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }
        return msg;
    }

    // envoi message UDP
    public void envoiMessageUDP(DatagramSocket socket, DatagramPacket msg) {
        try {
            socket.send(msg);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
    }

    // --------fonction TCP-----------
    // creation de la socket pour l'ami
    public Socket creerSocketTCP(Socket socket) {
        try {
            socket = new Socket("localhost", portEcouteTCP);
        } catch (UnknownHostException e) {
            System.err.println("Erreur sur l'hôte : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Creation de la socket impossible : " + e);
            System.exit(0);
        }
        return socket;
    }

    // association du flux pour l'ami
    public BufferedReader associationFluxInputTCP(BufferedReader input, Socket socket) {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }
        return input;
    }

    public PrintWriter associationFluxOutputTCP(Socket socket, PrintWriter output) {
        try {
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true);
        } catch (IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }
        return output;
    }

    // reception de la réponse du client AMI
    public String receptionMessageTCP(BufferedReader input, String reponseClient) {
        try {
            reponseClient = input.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la réception de la réponse : " + e);
            System.exit(0);
        }
        return reponseClient;
    }
}
