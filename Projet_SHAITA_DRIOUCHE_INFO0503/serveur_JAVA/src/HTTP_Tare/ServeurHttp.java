package HTTP_Tare;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

/**
 * @author DRIOUCHE Sami & SHAITA Fayssal
 */
public class ServeurHttp implements Runnable {
    public final int portEnvoiMarche;
    public final int portEcouteMarche;

    public ServeurHttp(int portEnvoiMarche, int portEcouteMarche) {
        this.portEnvoiMarche = portEnvoiMarche;
        this.portEcouteMarche = portEcouteMarche;
    }

    public void run() {
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            System.err.println("-----------------Erreur lors de la creation du serveur----------------- " + e);
            System.exit(0);
        }
        serveur.createContext("/index",
                new IndexHandler(portEnvoiMarche, portEcouteMarche));
        serveur.setExecutor(null);
        serveur.start();
        System.out.println("-----------------Serveur Http Tare demarre---------------");
    }
}