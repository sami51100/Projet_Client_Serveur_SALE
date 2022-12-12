package TCP_Ami;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import package_java.*;

public class AmiTCP implements Runnable {
    public final int portEcoute;
    private final Messenger gestionMessage;

    public AmiTCP(int portEcoute) {
        this.portEcoute = portEcoute;
        this.gestionMessage = new Messenger("AMI");
    }

    public void run() {
        boolean vrai = true;
        Ami a1 = new Ami("AMADOU", "3 rue des Pamplemousse", 0);
        System.out.println("-----------------Serveur TCP Ami demarre-----------------");

        while (vrai) {
            // Création de la socket serveur
            ServerSocket socketServeur = null;
            socketServeur = creerSocketTCP(socketServeur);

            // Attente d'une connexion d'un client
            Socket socketClient = null;
            socketClient = attenteConnexionClientTCP(socketClient, socketServeur);

            // Association d'un flux d'entrée et de sortie
            BufferedReader input = null;
            PrintWriter output = null;
            input = associationFluxInputTCP(input, socketClient);
            output = associationFluxOutputTCP(socketClient, output);

            // Lecture de l'energie
            String message = lectureEnergie(input);

            // dechiffrement RSA
            message = RSA.dechiffrerRSA("privee.bin", message);

            Energie e1 = Energie.fromJSON(message);
            gestionMessage.afficheMessage("Lu : " + e1.toString());
            String FICHIER_CRADO = "src/TCP_AMI/signature/CRADO" + e1.getCode().getCodeCommande() + ".bin";
            String FICHIER_JSON = "src/UDP_Marche/energie_PONE/energie" + e1.getCode().getCodeCommande() + ".json";
            Boolean certifie;
            if (e1.getEtat().equals("verification")) {
                Energie e2 = new Energie(e1.getTypeEnergie(), e1.getQuantite(), e1.getPrixUnite(),
                        e1.getModeExtraction(), e1.getOrigineGeographique(), e1.getIdPone(), "en cours");
                gestionMessage.afficheMessage(e2.getCode().getCodeCommande());
                FICHIER_CRADO = "src/TCP_AMI/signature/CRADO" + e2.getCode().getCodeCommande() + ".bin";
                FICHIER_JSON = "src/UDP_Marche/energie_PONE/energie" + e2.getCode().getCodeCommande() + ".json";
                // sauvegarde de l'energie
                RSA.SignerFichierRSA("privee.bin", FICHIER_JSON, FICHIER_CRADO);
                certifie = RSA.VerifierSignatureRSA(FICHIER_JSON, FICHIER_CRADO, "publique.bin");

                if (certifie) {
                    gestionMessage.afficheMessage("L'energie est certifiee");
                    output.println(
                            "L'energie est certifiee par l'ami : " + e2.getCode().toString());
                } else {
                    gestionMessage.afficheMessage("L'energie n'est pas certifiee");
                    output.println("L'energie n'est pas certifiee");
                }
            } else {

                if (a1.verifierEnergie(e1)) {
                    // envoie du message
                    output.println("prix valide");

                    // ---------certification de l'energie---------
                    RSA.SignerFichierRSA("privee.bin", FICHIER_JSON, FICHIER_CRADO);
                    certifie = RSA.VerifierSignatureRSA(FICHIER_JSON, FICHIER_CRADO, "publique.bin");

                    if (certifie) {
                        gestionMessage.afficheMessage("L'energie est certifiee");
                    } else {
                        gestionMessage.afficheMessage("L'energie n'est pas certifiee");
                    }

                } else {
                    output.println("prix trop eleve");
                }
            }

            // ---------------CRACHA----------------
            // // Attente d'une connexion d'un client
            // socketClient = attenteConnexionClientTCP(socketClient, socketServeur);
            // input = associationFluxInputTCP(input, socketClient);
            // output = associationFluxOutputTCP(socketClient, output);
            // // Lecture de l'energie
            // String energieAchete = lectureEnergie(input);
            // // dechiffrement RSA
            // message = RSA.dechiffrerRSA("privee.bin", energieAchete);
            // gestionMessage.afficheMessage("Energie achete par le TARE a certifier " +
            // message);

            // // ---------------CERTIFICATION----------------
            // Energie e4 = Energie.fromJSON(message);
            // Energie e2 = new Energie(e4.getTypeEnergie(), e4.getQuantite(),
            // e4.getPrixUnite(),
            // e4.getModeExtraction(), e4.getOrigineGeographique(), e4.getIdPone(), "en
            // cours");

            // gestionMessage.afficheMessage("ENERGIE " + e2.toString() + " COODE" +
            // e2.getCode().getCodeCommande());
            // String FICHIER_CRACHA = "src/TCP_AMI/signature/CRACHA" +
            // e2.getCode().getCodeCommande() + ".bin";
            // FICHIER_JSON = "src/HTTP_Tare/energie_Tare/energie" +
            // e2.getCode().getCodeCommande() + ".json";
            // // sauvegarde de l'energie
            // RSA.SignerFichierRSA("privee.bin", FICHIER_JSON, FICHIER_CRACHA);
            // certifie = RSA.VerifierSignatureRSA(FICHIER_JSON, FICHIER_CRACHA,
            // "publique.bin");

            // if (certifie) {
            // gestionMessage.afficheMessage("L'Achat est certifiee");
            // } else {
            // gestionMessage.afficheMessage("L'ACHAT n'est pas certifiee");
            // }
            // Fermeture des flux et des sockets
            fermetureFluxSocket(input, output, socketClient, socketServeur);
        }
    }

    // creation de la socket TCP
    public ServerSocket creerSocketTCP(ServerSocket socketServeur) {
        try {
            socketServeur = new ServerSocket(portEcoute);
        } catch (IOException e) {
            System.err.println("Creation de la socket impossible : " + e);
            System.exit(0);
        }
        return socketServeur;
    }

    // attente connexion client
    public Socket attenteConnexionClientTCP(Socket socketClient, ServerSocket socketServeur) {
        try {
            socketClient = socketServeur.accept();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }
        return socketClient;
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

    // lecture de l'energie
    public String lectureEnergie(BufferedReader input) {
        String message = "";
        try {
            message = input.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        return message;
    }

    // fermeture des flux et socket
    public void fermetureFluxSocket(BufferedReader input, PrintWriter output, Socket socketClient,
            ServerSocket socketServeur) {
        try {
            input.close();
            output.close();
            socketClient.close();
            socketServeur.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
            System.exit(0);
        }
    }

}