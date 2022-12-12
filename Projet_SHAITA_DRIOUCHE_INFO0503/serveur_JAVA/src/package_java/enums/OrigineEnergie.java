package package_java.enums;

public enum OrigineEnergie {
    AUCUNE("Aucune"),
    FRANCE("France"),
    ALLEMAGNE("Allemagne"),
    ESPAGNE("Espagne"),
    CHINE("Chine"),
    ITALIE("Italie"),
    ROYAUMEUNI("Royaume-Uni"),
    PAYSBAS("Pays-Bas"),
    PORTUGAL("Portugal"),
    SUEDE("Suede"),
    FINLANDE("Finlande"),
    ALGERIE("Algerie"),
    MAROC("Maroc"),
    TUNISIE("Tunisie"),
    TURQUIE("Turquie");

    private String nom;

    OrigineEnergie(String nom) {
        this.nom = nom;
    }

    public String toString() {
        return nom;
    }

    public static OrigineEnergie getValue(String s) {
        for (OrigineEnergie c : OrigineEnergie.values()) {
            if (c.nom.equals(s))
                return c;
        }
        return null;
    }

}
