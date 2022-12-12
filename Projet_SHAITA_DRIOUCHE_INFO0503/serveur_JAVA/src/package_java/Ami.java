package package_java;

import java.io.Serializable;

public class Ami extends Acteur implements Serializable {
    // Attributs

    // Constructeur
    public Ami(String nom, String adresse, int id) {
        super(nom, adresse, id);

    }

    public boolean verifierEnergie(Energie e1) {
        if (e1.getPrixUnite() < 150) {
            return true;
        }
        return false;
    }

    // toString
    @Override
    public String toString() {
        return "Ami" + super.toString();
    }

    // afficher
    public void afficher() {
        System.out.println(this.toString());
    }

}
