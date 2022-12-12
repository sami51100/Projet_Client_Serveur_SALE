package package_java;

import java.io.Serializable;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * classe permettant de reconstruire la commande du client
 * 
 * @author Sami DRIOUCHE & Fayssal SHAITA
 */
public class Commande implements Serializable {

    // Attributs
    private Date date;
    private String typeEnergie;
    private double quantiteDesire;
    private String modeExtraction;
    private String origineGeographique;
    private double prixMaxUnite; // prix maximum par l'unité de l'energie
    private int idClient;// l'utilisateur
    private String etat;// l'etat de la commande
    private CodeSuivi code; // code de suivi de la commande

    // constructeur
    public Commande(String typeEnergie, double quantiteDesire, String modeExtraction, String origineGeographique,
            double prixMaxUnite, int idClient, String etat, Date date) {
        this.typeEnergie = typeEnergie;
        this.quantiteDesire = quantiteDesire;
        this.modeExtraction = modeExtraction;
        this.origineGeographique = origineGeographique;
        this.prixMaxUnite = prixMaxUnite;
        this.idClient = idClient;
        this.etat = etat;
        this.date = date;
        this.code = new CodeSuivi(typeEnergie, prixMaxUnite, quantiteDesire, modeExtraction, origineGeographique,
                idClient, etat);
    }

    // getter
    public Date getDate() {
        return date;
    }

    public String getTypeEnergie() {
        return typeEnergie;
    }

    public double getQuantiteDesire() {
        return quantiteDesire;
    }

    public String getModeExtraction() {
        return modeExtraction;
    }

    public String getOrigineGeographique() {
        return origineGeographique;
    }

    public double getPrixMaxUnite() {
        return prixMaxUnite;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getEtat() {
        return etat;
    }

    public CodeSuivi getCode() {
        return code;
    }

    // setter
    public void setDate(Date date) {
        this.date = date;
    }

    public void setTypeEnergie(String typeEnergie) {
        this.typeEnergie = typeEnergie;
    }

    public void setQuantiteDesire(double quantiteDesire) {
        this.quantiteDesire = quantiteDesire;
    }

    public void setModeExtraction(String modeExtraction) {
        this.modeExtraction = modeExtraction;
    }

    public void setOrigineGeographique(String origineGeographique) {
        this.origineGeographique = origineGeographique;
    }

    public void setPrixMaxUnite(double prixMaxUnite) {
        this.prixMaxUnite = prixMaxUnite;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setCode(CodeSuivi code) {
        this.code = code;
    }

    // toString
    @Override
    public String toString() {
        return "\ntype d'energie :" + typeEnergie + " \nquantitee desire :" + quantiteDesire + " \nmode d'extraction : "
                + modeExtraction
                + "\norigine geographique : " + origineGeographique + "\nprix max par unite : " + prixMaxUnite
                + "\nid client :" + idClient
                + " \netat : " + etat;
    }

    // tojson
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        json.put("date", format.format(date));
        json.put("typeEnergie", typeEnergie);
        json.put("quantiteDesire", quantiteDesire);
        json.put("modeExtraction", modeExtraction);
        json.put("origineGeographique", origineGeographique);
        json.put("prixMaxUnite", prixMaxUnite);
        json.put("idClient", idClient);
        json.put("etat", etat);
        return json;
    }

    // fromjson
    public static Commande fromJSON(String json) {
        JSONObject Objet = new JSONObject(json);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(Objet.getString("date"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Commande commande = new Commande(Objet.getString("typeEnergie"), Objet.getDouble("quantiteDesire"),
                Objet.getString("modeExtraction"), Objet.getString("origineGeographique"),
                Objet.getDouble("prixMaxUnite"),
                Objet.getInt("idClient"), Objet.getString("etat"), date);
        return commande;
    }
}