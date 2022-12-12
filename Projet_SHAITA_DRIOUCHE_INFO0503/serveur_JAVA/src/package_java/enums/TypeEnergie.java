package package_java.enums;

public enum TypeEnergie {
    PETROLE("Petrole"),
    ELECTRICITE("Electricite"),
    HYDROGENE("Hydrogene"),
    BIOMETHANE("Biomethane"),
    GAZNATUREL("Gaz naturel");

    private String nom;

    TypeEnergie(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }

    public static TypeEnergie getValue(String s) {
        for (TypeEnergie c : TypeEnergie.values()) {
            if (c.nom.equals(s))
                return c;
        }
        return null;
    }

}
