package HTTP_Tare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import org.json.JSONObject;

import package_java.*;
import package_java.enums.*;

/**
 * Classe correspondant au handler sur le contexte 'index.html'.
 * 
 * @author DRIOUCHE Sami & SHAITA Fayssal
 */
public class IndexHandler implements HttpHandler {
    public final int portEcouteMarche;
    public final int portEnvoiMarche;
    private final Messenger gestionMessage;

    public IndexHandler(int portEnvoiMarche, int portEcouteMarche) {
        this.portEcouteMarche = portEcouteMarche;
        this.portEnvoiMarche = portEnvoiMarche;
        this.gestionMessage = new Messenger("Tare");
    }

    public void handle(HttpExchange t) {

        String messageMarche;
        // TARE

        String reponse = "";

        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        br = utiliserflux(br, t);

        // Récupération des données en POST
        query = recuperationPost(query, br);

        // Affichage des données
        reponse = affichageDonne(reponse, query);

        Commande commande = Commande.fromJSON(reponse);
        JSONObject objet = commande.toJSON();
        if (commande.getEtat().equals("verification")) {
            gestionMessage.afficheMessage("Commande client a verifier");
            gestionMessage.afficheMessage("Envoye au marche de gros de la commande a verifier ");

        } else {
            gestionMessage.afficheMessage("Commande recu du revendeur " + reponse);
            gestionMessage.afficheMessage("Envoye au marche de gros " + objet.toString());
        }

        // ------------------création socket TARE------------------
        DatagramSocket socket = null;
        socket = creerSocket(socket);

        // Transformation en tableau d'octets
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        transformationTabOctet(commande, baos);

        // Création et envoi du segment UDP
        creerSegmentUDP(baos, socket, portEnvoiMarche);

        // reception du message
        socket = recevoirMessage(socket, portEcouteMarche);

        // Création du message
        byte[] tampon = new byte[1024];
        DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

        // Lecture du message du client
        messageMarche = lectureMessageClient(msg, socket);

        Energie e1 = null;
        if (messageMarche.equals("aucune energie disponible")) {
            gestionMessage.afficheMessage("Lu " + messageMarche);

            Energie e0 = new Energie(TypeEnergie.PETROLE, 0, 0, ModeExtractionEnergie.FORAGEHORIZONTAL,
                    OrigineEnergie.ALGERIE, 0, "");
            JSONObject objet0 = e0.toJSON();
            // Envoi de l'en-tête Http
            envoieEnteteHTTP(t, objet0);

            // Envoi du corps (données HTML)
            envoieCorpsHTTP(t, objet0);

        } else if (commande.getEtat().equals("verification")) {
            // met en json le texte recu
            JSONObject objet1 = new JSONObject();
            objet1.put("message", messageMarche);
            gestionMessage.afficheMessage("Lu " + objet1.getString("message"));
            // Envoi de l'en-tête Http
            envoieEnteteHTTP(t, objet1);

            // Envoi du corps (données HTML)
            envoieCorpsHTTP(t, objet1);

        } else {
            // ACHAT D'UNE ENERGIE
            e1 = Energie.fromJSON(messageMarche);
            Tare t1 = new Tare("RAHIM", "3 rue des lorier", 0, e1);
            // met en json le tare t1
            JSONObject objet3 = t1.toJSON();
            gestionMessage.afficheMessage("Lu " + objet3.toString());

            // creer un fichier json pour l'energie
            String FICHIER_JSON = "src/HTTP_Tare/energie_Tare/energie" + e1.getCode().getCodeCommande() + ".json";
            try {
                FileWriter file = new FileWriter(FICHIER_JSON);
                file.write(t1.toJSON().toString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gestionMessage.afficheMessage("Lu " + e1.toString());
            JSONObject objet2 = e1.toJSON();

            gestionMessage.afficheMessage("Envoye au revendeur ");
            // Envoi de l'en-tête Http
            envoieEnteteHTTP(t, objet2);

            // Envoi du corps (données HTML)
            envoieCorpsHTTP(t, objet2);
        }

        // Fermeture de la socket
        socket.close();
    }

    // Utilisation d'un flux pour lire les données du message Http
    public BufferedReader utiliserflux(BufferedReader br, HttpExchange t) {

        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la recuperation du flux " + e);
            System.exit(0);
        }
        return br;
    }

    // Récupération des données en POST
    public String recuperationPost(String query, BufferedReader br) {

        try {
            query = br.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }
        return query;
    }

    // Affichage des données
    public String affichageDonne(String reponse, String query) {

        if (query == null)
            reponse += "Aucune";
        else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                query = "";
            }
            reponse += query;
        }
        return reponse;
    }

    // Envoi de l'en-tête Http
    public void envoieEnteteHTTP(HttpExchange t, JSONObject objet) {
        try {
            Headers h = t.getResponseHeaders();
            // Content-type: application/x-www-form-urlencoded
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, objet.toString().getBytes().length);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }
    }

    // Envoi du corps (données HTML)
    public void envoieCorpsHTTP(HttpExchange t, JSONObject objet) {
        try {
            OutputStream os = t.getResponseBody();
            os.write(objet.toString().getBytes());
            os.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }

    // ------------------création socket TARE UDP------------------
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
    public void transformationTabOctet(Commande objet, ByteArrayOutputStream baos) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objet);
        } catch (IOException e) {
            System.err.println("Erreur lors de la serialisation : " + e);
            System.exit(0);
        }

    }

    public void creerSegmentUDP(ByteArrayOutputStream baos, DatagramSocket socket, int portEcoute) {
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

    public DatagramSocket recevoirMessage(DatagramSocket socket, int portEnvoi) {
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

        } catch (IOException e) {
            System.err.println("Erreur lors de la reception du message : " + e);
            System.exit(0);
        }

        return texte;
    }
}