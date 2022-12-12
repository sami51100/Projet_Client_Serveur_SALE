import HTTP_Tare.ServeurHttp;
import package_java.*;
import UDP_Marche.*;
import TCP_Ami.*;

public class Lanceur {

    Lanceur() {
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Merci de donner un fichier de configuration json");
            System.exit(0);
        }

        Configuration config = new Configuration(args[0]);

        int portEcouteTare = config.getInt("portEcouteTare");
        int portEnvoiTare = config.getInt("portEnvoiTare");

        int portEcouteUDP = config.getInt("portEcouteUDP");
        int portEnvoiUDP = config.getInt("portEnvoiUDP");

        int portEcouteTCP = config.getInt("portEcouteTCP");

        java.util.ArrayList<Thread> mesServices = new java.util.ArrayList<Thread>();

        mesServices.add(new Thread(new ServeurHttp(portEcouteTare, portEnvoiTare)));
        mesServices.add(
                new Thread(new MarcheUDP(portEcouteUDP, portEnvoiUDP, portEcouteTCP, portEcouteTare, portEnvoiTare)));
        mesServices.add(new Thread(new AmiTCP(portEcouteTCP)));
        mesServices.add(new Thread(new PoneUDP(portEcouteUDP, portEnvoiUDP)));

        java.util.Iterator<Thread> it = mesServices.iterator();

        while (it.hasNext()) {
            Thread thread = it.next();
            thread.start();
        }
    }
}
