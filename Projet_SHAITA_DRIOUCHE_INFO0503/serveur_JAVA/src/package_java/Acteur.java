package package_java;

import java.io.Serializable;

public class Acteur implements Serializable {
    // Attributs
    private String nom;
    private String adresse;
    private int id;

    // Constructeur
    public Acteur(String nom, String adresse, int id) {
        this.nom = nom;
        this.adresse = adresse;
        this.id = id;
    }

    // Getter
    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getId() {
        return id;
    }

    // Setter
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setId(int id) {
        this.id = id;
    }

    // toString
    @Override
    public String toString() {
        return " [ nom : " + nom + ", adresse : " + adresse + ", id : " + id + ']';
    }

    // afficher
    public void afficher() {
        System.out.println(this.toString());
    }

}
