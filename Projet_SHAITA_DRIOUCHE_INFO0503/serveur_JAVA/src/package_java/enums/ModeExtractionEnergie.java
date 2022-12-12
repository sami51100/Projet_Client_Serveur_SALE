package package_java.enums;

public enum ModeExtractionEnergie {

    AUCUNE("Aucune"),
    EOLIEN("Eolien"),
    NUCLEAIRE("Nucleaire"),
    FORAGEVERTICAL("Forage Vertical"),
    FORAGEHORIZONTAL("Forage Horizontal"),
    SOLAIRE("Solaire");

    private String nom;

    ModeExtractionEnergie(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }

    public static ModeExtractionEnergie getValue(String s) {
        for (ModeExtractionEnergie c : ModeExtractionEnergie.values()) {
            if (c.nom.equals(s))
                return c;
        }
        return null;
    }

}
