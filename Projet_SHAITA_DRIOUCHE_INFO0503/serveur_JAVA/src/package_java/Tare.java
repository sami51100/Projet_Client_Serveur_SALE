package package_java;

import package_java.enums.TypeEnergie;

import java.io.Serializable;
import java.util.Vector;
import org.json.JSONObject;

public class Tare extends Acteur implements Serializable {
    // Attributs
    private Energie energie;

    // Constructeur
    public Tare(String nom, String adresse, int id, Energie energie) {
        super(nom, adresse, id);
        this.energie = energie;

    }

    // toString
    @Override
    public String toString() {
        return "Tare" + super.toString();
    }

    // afficher
    public void afficher() {
        System.out.println(this.toString());
    }

    // getter
    public Energie getEnergie() {
        return energie;
    }

    // setter
    public void setEnergie(Energie energie) {
        this.energie = energie;
    }

    // toJSON
    public JSONObject toJSON() {
        JSONObject objet = new JSONObject();
        objet.put("nom", this.getNom());
        objet.put("adresse", this.getAdresse());
        objet.put("id", this.getId());
        objet.put("energie", this.energie.toJSON());
        return objet;
    }

    // fromJSON
    public static Tare fromJSON(JSONObject objet) {
        JSONObject json = new JSONObject(objet);

        String nom = json.getString("nom");
        String adresse = json.getString("adresse");
        int id = json.getInt("id");
        Energie energie = Energie.fromJSON(json.getJSONObject("energie").toString());

        return new Tare(nom, adresse, id, energie);
    }

}
