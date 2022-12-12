package package_java;

import org.json.JSONObject;

import package_java.enums.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class MarcheGros {
    // attributs
    private Vector<Energie> energies;

    // constructeur
    public MarcheGros() {
        this.energies = new Vector<Energie>();
    }

    // getter
    public Vector<Energie> getEnergies() {
        return energies;
    }

    // setter
    public void setEnergies(Vector<Energie> energies) {
        this.energies = energies;
    }

    // methodes
    public void ajouterEnergie(Energie energie) {
        this.energies.add(energie);
    }

    public void supprimerEnergie(Energie energie) {
        this.energies.remove(energie);
    }

    public void supprimerEnergie(int index) {
        this.energies.remove(index);
    }

    public void afficherEnergies() {
        System.out.println("Energies du marche: ");
        for (Energie energie : this.energies) {
            System.out.println(energie);
        }
    }

    // toString
    @Override
    public String toString() {
        return "MarcheGros [energies=" + energies + "]";
    }

    // toJSON
    public JSONObject toJSON() {
        JSONObject objet = new JSONObject();

        for (int cle = 0; cle < energies.size(); cle++) {
            objet.put("" + cle, energies.get(cle).toJSON());
        }
        return objet;
    }

    // fromJSON
    public static MarcheGros fromJSON(String json) {
        MarcheGros marche = new MarcheGros();
        JSONObject objet = new JSONObject(json);
        for (int cle = 0; cle < objet.length(); cle++) {
            marche.ajouterEnergie(Energie.fromJSON(objet.getJSONObject("" + cle).toString()));
        }
        return marche;
    }

    public void creerFichier(String nomFicher) {
        try {
            FileWriter file = new FileWriter(nomFicher);
            file.write(this.toJSON().toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MarcheGros recupererFichier(String nomFicher) {
        MarcheGros marche = new MarcheGros();
        try {
            FileReader file = new FileReader(nomFicher);
            BufferedReader br = new BufferedReader(file);

            String ligne;
            while ((ligne = br.readLine()) != null) {
                marche = MarcheGros.fromJSON(ligne.toString());
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marche;
    }

    public Energie comparerCommandeEnergie(Commande c1) {

        // parcours le vector d'energie
        for (Energie energie : this.energies) {
            if ((c1.getModeExtraction().equals(String.valueOf(energie.getModeExtraction()))
                    || c1.getModeExtraction().equals(String.valueOf(ModeExtractionEnergie.AUCUNE)))
                    && c1.getTypeEnergie().equals(String.valueOf(energie.getTypeEnergie()))
                    && (c1.getOrigineGeographique().equals(String.valueOf(energie.getOrigineGeographique()))
                            || c1.getOrigineGeographique().equals(String.valueOf(OrigineEnergie.AUCUNE)))) {
                supprimerEnergie(energie);
                return energie;

            }
        }
        return null;
    }
}