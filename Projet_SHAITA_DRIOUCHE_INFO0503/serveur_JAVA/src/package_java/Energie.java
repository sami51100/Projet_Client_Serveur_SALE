package package_java;

import java.io.Serializable;

import org.json.JSONObject;
import package_java.enums.*;

public class Energie implements Serializable {
    // Attributs
    private TypeEnergie typeEnergie;
    private double prixUnite;
    private double quantite;
    private ModeExtractionEnergie modeExtraction;
    private OrigineEnergie origineGeographique;
    private int idPone;// l'utilisateur
    private String etat;// l'etat de l'energie
    private CodeSuivi code; // code de suivi de l'energie

    // constructeur
    public Energie(TypeEnergie typeEnergie, double prixUnite, double quantite, ModeExtractionEnergie modeExtraction,
            OrigineEnergie origineGeographique, int idPone, String etat) {
        this.typeEnergie = typeEnergie;
        this.prixUnite = prixUnite;
        this.quantite = quantite;
        this.modeExtraction = modeExtraction;
        this.origineGeographique = origineGeographique;
        this.idPone = idPone;
        this.etat = etat;
        this.code = new CodeSuivi(String.valueOf(typeEnergie), prixUnite, quantite, String.valueOf(modeExtraction),
                String.valueOf(origineGeographique), idPone, etat);
    }

    // getter
    public TypeEnergie getTypeEnergie() {
        return typeEnergie;
    }

    public double getPrixUnite() {
        return prixUnite;
    }

    public double getQuantite() {
        return quantite;
    }

    public ModeExtractionEnergie getModeExtraction() {
        return modeExtraction;
    }

    public OrigineEnergie getOrigineGeographique() {
        return origineGeographique;
    }

    public int getIdPone() {
        return idPone;
    }

    public String getEtat() {
        return etat;
    }

    public CodeSuivi getCode() {
        return code;
    }

    // setter
    public void setTypeEnergie(TypeEnergie typeEnergie) {
        this.typeEnergie = typeEnergie;
    }

    public void setPrixUnite(double prixUnite) {
        this.prixUnite = prixUnite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public void setModeExtraction(ModeExtractionEnergie modeExtraction) {
        this.modeExtraction = modeExtraction;
    }

    public void setOrigineGeographique(OrigineEnergie origineGeographique) {
        this.origineGeographique = origineGeographique;
    }

    public void setIdPone(int idPone) {
        this.idPone = idPone;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // toString
    @Override
    public String toString() {
        return "Energie [etat=" + etat + ", idPone=" + idPone + ", modeExtraction=" + modeExtraction
                + ", origineGeographique="
                + origineGeographique + ", prixUnite=" + prixUnite + ", quantite=" + quantite + ", typeEnergie="
                + typeEnergie
                + "]";
    }

    // afficher
    public void afficher() {
        System.out.println(this.toString());
    }

    // toJSONObject
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("typeEnergie", typeEnergie);
        json.put("prixUnite", prixUnite);
        json.put("quantite", quantite);
        json.put("modeExtraction", modeExtraction);
        json.put("origineGeographique", origineGeographique);
        json.put("idPone", idPone);
        json.put("etat", etat);
        return json;
    }

    // fromJSONObject
    public static Energie fromJSON(String json) {
        JSONObject jsonObject = new JSONObject(json);
        TypeEnergie typeEnergie = TypeEnergie.valueOf(jsonObject.getString("typeEnergie"));
        double prixUnite = jsonObject.getDouble("prixUnite");
        double quantite = jsonObject.getDouble("quantite");
        ModeExtractionEnergie modeExtraction = ModeExtractionEnergie.valueOf(jsonObject.getString("modeExtraction"));
        OrigineEnergie origineGeographique = OrigineEnergie.valueOf(jsonObject.getString("origineGeographique"));
        int idPone = jsonObject.getInt("idPone");
        String etat = jsonObject.getString("etat");
        return new Energie(typeEnergie, prixUnite, quantite, modeExtraction, origineGeographique, idPone, etat);
    }

}