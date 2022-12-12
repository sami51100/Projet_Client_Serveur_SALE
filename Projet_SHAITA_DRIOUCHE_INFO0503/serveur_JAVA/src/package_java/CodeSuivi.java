package package_java;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import package_java.enums.*;

/**
 * Classe permettant de suivre notre commande à l'aide d'un code de suivi
 * 
 * @author SHAITA Fayssal & DRIOUCHE Sami
 */
public class CodeSuivi implements Serializable {
    // attibuts
    private String codeTypeEnergie; // petrole hydrogene electricite
    private String codeQuantiteDesire;
    private String codeModeExtraction;
    private String codeOrigineGeographique;
    private String codePrixMaxUnite; // prix maximum par l'unité de l'energie
    private String codeIdClient;// l'utilisateur
    private String codeEtat;// l'etat de la commande
    private String codeCommande;

    // constructeur
    public CodeSuivi(String typeEnergie, double prixUnite, double quantite, String modeExtraction,
            String origineGeographique, int id, String etat) {
        this.codeTypeEnergie = encoderTypeEnergie(typeEnergie);
        this.codeOrigineGeographique = encoderOrigineEnergie(origineGeographique);
        this.codeModeExtraction = encoderModeExtraction(modeExtraction);
        this.codeIdClient = encoderIdClient(id);
        this.codeEtat = encoderEtat(etat);
        this.codeQuantiteDesire = encoderQuantite(quantite);
        this.codePrixMaxUnite = encoderPrix(prixUnite);

        this.codeCommande = codeEtat + codeIdClient + codeTypeEnergie + codeOrigineGeographique + codeModeExtraction
                + codeQuantiteDesire + codePrixMaxUnite;
    }

    // getter
    public String getCodeCommande() {
        return codeCommande;
    }

    public String toString() {
        return "Code de suivi: [" + codeCommande + "]";
    }

    public void afficher() {
        System.out.println(this.toString());
    }

    public String encoderTypeEnergie(String typeEnergie) {
        String codetypeEnergie = "";
        // creer un tableau avec toute les information de l'enu
        TypeEnergie[] typeEnergies = TypeEnergie.values();
        // pour convertir le type de l'energie en string
        for (TypeEnergie c : typeEnergies) {
            if (c.toString().equals(typeEnergie)) {
                codetypeEnergie = String.valueOf(c.ordinal());
                // concatene avec un 0
                if (codetypeEnergie.length() == 1) {
                    codetypeEnergie = "0" + codetypeEnergie;
                }
            }
        }
        return codetypeEnergie;
    }

    public String encoderOrigineEnergie(String origineGeographique) {
        String codeorigineGeographique = "";
        // creer un tableau avec toute les information de l'enu
        OrigineEnergie[] origine = OrigineEnergie.values();
        // pour convertir le type de l'origine en string
        for (OrigineEnergie c : origine) {
            if (c.toString().equals(origineGeographique)) {

                codeorigineGeographique = String.valueOf(c.ordinal());
                // concatene avec un 0
                if (codeorigineGeographique.length() == 1) {
                    codeorigineGeographique = "0" + codeorigineGeographique;
                }
            }
        }
        return codeorigineGeographique;
    }

    public String encoderModeExtraction(String modeExtraction) {
        String codemodeExtraction = "";
        // creer un tableau avec toute les information de l'enu
        ModeExtractionEnergie[] mode = ModeExtractionEnergie.values();
        // pour convertir le type de l'origine en string
        for (ModeExtractionEnergie c : mode) {
            if (c.toString().equals(modeExtraction)) {

                codemodeExtraction = String.valueOf(c.ordinal());
                // concatene avec un 0
                if (codemodeExtraction.length() == 1) {
                    codemodeExtraction = "0" + codemodeExtraction;
                }
            }
        }
        return codemodeExtraction;
    }

    public String encoderIdClient(int idClient) {

        // pour convertir le type de l'id en string
        String codeIdClient = String.valueOf(idClient);
        switch (codeIdClient.length()) {
            case 1:
                codeIdClient = "000" + codeIdClient;
                break;
            case 2:
                codeIdClient = "00" + codeIdClient;
                break;
            case 3:
                codeIdClient = "0" + codeIdClient;
                break;
        }
        return codeIdClient;
    }

    public String encoderEtat(String etat) {
        String codeEtat = "";
        switch (etat) {
            case "en cours":
                codeEtat = "0";
                break;
            case "energie achete":
                codeEtat = "1";
                break;
            case "verification":
                codeEtat = "2";
                break;
        }
        return codeEtat;
    }

    public String encoderQuantite(double quantiteDesire) {
        String codeQuantiteDesire = "";
        // pour convertir le type de la quantite en string
        codeQuantiteDesire = String.valueOf(quantiteDesire);
        // compte le nombre de chiffre avant la virgule
        int nbChiffreAvantVirgule = codeQuantiteDesire.indexOf(".");
        // affiche les nombre avant la virgule
        String chiffreAvantVirgule = codeQuantiteDesire.substring(0, nbChiffreAvantVirgule);
        // compte le nombre de chiffre apres la virgule
        int nbChiffreApresVirgule = codeQuantiteDesire.length() - nbChiffreAvantVirgule - 1;
        // affiche les nombre apres la virgule
        String chiffreApresVirgule = codeQuantiteDesire.substring(nbChiffreAvantVirgule + 1);

        codeQuantiteDesire = String.valueOf(nbChiffreAvantVirgule) + String.valueOf(nbChiffreApresVirgule)
                + chiffreAvantVirgule + chiffreApresVirgule;

        return codeQuantiteDesire;
    }

    public String encoderPrix(double prixMaxUnite) {
        String codePrixMaxUnite = "";
        // pour convertir le type du prix en string
        codePrixMaxUnite = String.valueOf(prixMaxUnite);
        // compte le nombre de chiffre avant la virgule
        int nbChiffreAvantVirgule = codePrixMaxUnite.indexOf(".");
        // affiche les nombre avant la virgule
        String chiffreAvantVirgule = codePrixMaxUnite.substring(0, nbChiffreAvantVirgule);
        // compte le nombre de chiffre apres la virgule
        int nbChiffreApresVirgule = codePrixMaxUnite.length() - nbChiffreAvantVirgule - 1;
        // affiche les nombre apres la virgule
        String chiffreApresVirgule = codePrixMaxUnite.substring(nbChiffreAvantVirgule + 1);

        codePrixMaxUnite = String.valueOf(nbChiffreAvantVirgule) + String.valueOf(nbChiffreApresVirgule)
                + chiffreAvantVirgule + chiffreApresVirgule;

        return codePrixMaxUnite;
    }

    public static int decoderIdClient(CodeSuivi codeDeSuivi) {
        int idClient = Integer.parseInt(codeDeSuivi.codeCommande.substring(1, 5));
        return idClient;
    }

    public static String decoderEtat(CodeSuivi codeDeSuivi) {
        String etat = codeDeSuivi.codeCommande.substring(0, 1);
        switch (etat) {
            case "0":
                etat = "en cours";
                break;
            case "1":
                etat = "energie achete";
                break;
            case "2":
                etat = "verification";
                break;
        }
        return etat;
    }

    public static String decoderTypeEnergie(CodeSuivi codeDeSuivi) {
        String codetypeEnergie = codeDeSuivi.codeCommande.substring(5, 7);
        String typeEnergie = "";
        // creer un tableau avec toute les information de l'enu
        TypeEnergie[] typeEnergies = TypeEnergie.values();
        // pour convertir le type de l'energie en string
        for (TypeEnergie c : typeEnergies) {

            if (c.ordinal() == Integer.parseInt(codetypeEnergie)) {

                typeEnergie = c.toString();

            }

        }
        return typeEnergie;
    }

    public static String decoderOrigineGeographique(CodeSuivi codeDeSuivi) {
        String codeorigineGeographique = codeDeSuivi.codeCommande.substring(7, 9);
        String origineGeographique = "";
        // creer un tableau avec toute les information de l'enu
        OrigineEnergie[] origineEnergies = OrigineEnergie.values();
        // pour convertir le type de l'origine en string
        for (OrigineEnergie c : origineEnergies) {
            if (c.ordinal() == Integer.parseInt(codeorigineGeographique)) {
                origineGeographique = c.toString();
            }
        }
        return origineGeographique;
    }

    public static String decoderModeExtraction(CodeSuivi codeDeSuivi) {
        String codemodeExtraction = codeDeSuivi.codeCommande.substring(9, 11);
        String modeExtraction = "";
        // creer un tableau avec toute les information de l'enu
        ModeExtractionEnergie[] modeExtractions = ModeExtractionEnergie.values();
        // pour convertir le type de l'origine en string
        for (ModeExtractionEnergie c : modeExtractions) {
            if (c.ordinal() == Integer.parseInt(codemodeExtraction)) {
                modeExtraction = c.toString();
            }
        }
        return modeExtraction;
    }

    public static double decoderQuantiteDesire(CodeSuivi codeDeSuivi) {
        String quantiteDesire = codeDeSuivi.codeCommande.substring(11, 13);
        int nbChiffreAvantVirgule = Integer.parseInt(quantiteDesire.substring(0, 1));
        int nbChiffreApresVirgule = Integer.parseInt(quantiteDesire.substring(1, 2));

        String chiffreAvantVirgule = codeDeSuivi.codeCommande.substring(13, 13 + nbChiffreAvantVirgule);
        String chiffreApresVirgule = codeDeSuivi.codeCommande.substring(13 + nbChiffreAvantVirgule,
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule);
        quantiteDesire = chiffreAvantVirgule + "." + chiffreApresVirgule;
        double quantiteDesireDouble = Double.parseDouble(quantiteDesire);
        return quantiteDesireDouble;
    }

    public static double decoderPrixMaxUnite(CodeSuivi codeDeSuivi) {
        String quantiteDesire = codeDeSuivi.codeCommande.substring(11, 13);
        int nbChiffreAvantVirgule = Integer.parseInt(quantiteDesire.substring(0, 1));
        int nbChiffreApresVirgule = Integer.parseInt(quantiteDesire.substring(1, 2));

        String prixMaxUnite = codeDeSuivi.codeCommande.substring(13 + nbChiffreAvantVirgule + nbChiffreApresVirgule,
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule + 2);
        int nbChiffreAvantVirgule2 = Integer.parseInt(prixMaxUnite.substring(0, 1));
        int nbChiffreApresVirgule2 = Integer.parseInt(prixMaxUnite.substring(1, 2));
        String chiffreAvantVirgule = codeDeSuivi.codeCommande.substring(
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule + 2,
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule + 2 + nbChiffreAvantVirgule2);
        String chiffreApresVirgule = codeDeSuivi.codeCommande.substring(
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule + 2 + nbChiffreAvantVirgule2,
                13 + nbChiffreAvantVirgule + nbChiffreApresVirgule + 2 + nbChiffreAvantVirgule2
                        + nbChiffreApresVirgule2);
        prixMaxUnite = chiffreAvantVirgule + "." + chiffreApresVirgule;
        double prixMaxUniteDouble = Double.parseDouble(prixMaxUnite);
        return prixMaxUniteDouble;
    }

    public static Energie decoderCodesuivi(CodeSuivi codeDeSuivi) {
        String etat = CodeSuivi.decoderEtat(codeDeSuivi);

        int idClient = CodeSuivi.decoderIdClient(codeDeSuivi);

        String typeEnergie = CodeSuivi.decoderTypeEnergie(codeDeSuivi);
        String origineGeographique = CodeSuivi.decoderOrigineGeographique(codeDeSuivi);
        String modeExtraction = CodeSuivi.decoderModeExtraction(codeDeSuivi);
        double quantiteDesire = CodeSuivi.decoderQuantiteDesire(codeDeSuivi);
        double prixMaxUnite = CodeSuivi.decoderPrixMaxUnite(codeDeSuivi);

        Energie e1 = new Energie(TypeEnergie.getValue(typeEnergie), prixMaxUnite, quantiteDesire,
                ModeExtractionEnergie.getValue(modeExtraction), OrigineEnergie.getValue(origineGeographique),
                idClient, etat);
        return e1;
    }

}
