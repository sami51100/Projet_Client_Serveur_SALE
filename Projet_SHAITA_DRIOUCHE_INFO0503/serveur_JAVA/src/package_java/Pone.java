package package_java;

import package_java.enums.*;

import java.io.Serializable;
import java.util.Vector;

public class Pone extends Acteur implements Serializable {
    // attributs
    private Vector<TypeEnergie> energieProduite;
    private Vector<OrigineEnergie> origineProduction;
    private Vector<ModeExtractionEnergie> modeExtraction;

    // constructeur
    public Pone(String nom, String adresse, int id, Vector<TypeEnergie> energieProduite,
            Vector<OrigineEnergie> origineProduction, Vector<ModeExtractionEnergie> modeExtraction) {
        super(nom, adresse, id);
        this.energieProduite = energieProduite;
        this.origineProduction = origineProduction;
        this.modeExtraction = modeExtraction;
    }

    // getter
    public Vector<TypeEnergie> getEnergieProduite() {
        return energieProduite;
    }

    public Vector<OrigineEnergie> getOrigineProduction() {
        return origineProduction;
    }

    public Vector<ModeExtractionEnergie> getModeExtraction() {
        return modeExtraction;
    }

    // setter
    public void setEnergieProduite(Vector<TypeEnergie> energieProduite) {
        this.energieProduite = energieProduite;
    }

    public void setOrigineProduction(Vector<OrigineEnergie> origineProduction) {
        this.origineProduction = origineProduction;
    }

    public void setModeExtraction(Vector<ModeExtractionEnergie> modeExtraction) {
        this.modeExtraction = modeExtraction;
    }

    // methodes
    public void ajouterEnergieProduite(TypeEnergie energie) {
        this.energieProduite.add(energie);
    }

    public void ajouterOrigineProduction(OrigineEnergie origine) {
        this.origineProduction.add(origine);
    }

    public void ajouterModeExtraction(ModeExtractionEnergie mode) {
        this.modeExtraction.add(mode);
    }

    // toString

    @Override
    public String toString() {
        return "Pone" + super.toString() + "\n" +
                "energieProduite : " + energieProduite + "\n" +
                "origineProduction : " + origineProduction + "\n" +
                "modeExtraction : " + modeExtraction;
    }

    // afficher
    public void afficher() {
        System.out.println(this.toString());
    }

}