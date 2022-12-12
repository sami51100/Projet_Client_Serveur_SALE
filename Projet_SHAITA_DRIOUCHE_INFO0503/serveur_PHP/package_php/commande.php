<?php

class Commande implements JsonSerializable{

    // Attributs
    private $date;   
    private string $typeEnergie; //petrole hydrogene electricite
    private float $quantiteDesire;
    private string $modeExtraction;
    private string $origineGeographique;
    private float $prixMaxUnite; // prix maximum par l'unité de l'energie
    private int $idClient ;//l'utilisateur
    private string $etat ;//l'etat de la commande

    // Constructeur
    public function __construct( string $typeEnergie,float $quantiteDesire ,string $modeExtraction  ,string $origineGeographique  ,float $prixMaxUnite, int $idClient, string $etat ){
        $this->date = date("d-m-Y");      
        $this->typeEnergie = $typeEnergie;
        $this->quantiteDesire = $quantiteDesire;
        $this->modeExtraction = $modeExtraction;
        $this->origineGeographique = $origineGeographique;
        $this->prixMaxUnite = $prixMaxUnite;
        $this->idClient = $idClient;
        $this->etat = $etat;
        $this->verifTypeEnergie();
        $this->verifOrigineGeographique();
        $this->verifModeExtraction();
        
    }

    // Getters
    public function getDate() {
        return $this->date;
    }

    public function getTypeEnergie() {
        return $this->typeEnergie;
    }

    public function getQuantiteDesire() {
        return $this->quantiteDesire;
    }

    public function getModeExtraction() {
        return $this->modeExtraction;
    }

    public function getOrigineGeographique() {
        return $this->origineGeographique;
    }

    public function getPrixMaxUnite() {
        return $this->prixMaxUnite;
    }

    public function getIdClient() {
        return $this->idClient;
    }

    public function getEtat() {
        return $this->etat;
    }

    // Setters
    public function setDate($date) {
        $this->date = $date;
    }

    public function setTypeEnergie($typeEnergie) {
        $this->typeEnergie = $typeEnergie;
    }

    public function setQuantiteDesire($quantiteDesire) {
        $this->quantiteDesire = $quantiteDesire;
    }

    public function setModeExtraction($modeExtraction) {
        $this->modeExtraction = $modeExtraction;
    }

    public function setOrigineGeographique($origineGeographique) {
        $this->origineGeographique = $origineGeographique;
    }

    public function setPrixMaxUnite($prixMaxUnite) {
        $this->prixMaxUnite = $prixMaxUnite;
    }

    public function setIdClient($idClient) {
        $this->idClient = $idClient;
    }

    public function setEtat($etat) {
        $this->etat = $etat;
    }

    

    public function jsonSerialize() : array {
        $json = array();
        $json['date'] = $this->date;
        $json['typeEnergie'] = $this->typeEnergie;
        $json['quantiteDesire'] = $this->quantiteDesire;
        $json['modeExtraction'] = $this->modeExtraction;
        $json['origineGeographique'] = $this->origineGeographique;
        $json['prixMaxUnite'] = $this->prixMaxUnite;
        $json['idClient'] = $this->idClient;
        $json['etat'] = $this->etat;
        return $json;
    }

    public static function fromJson(string $json) : Commande {     
        $data = json_decode($json, true);
        $commande = new Commande($data['typeEnergie'], $data['quantiteDesire'], $data['modeExtraction'], $data['origineGeographique'], $data['prixMaxUnite'], $data['idClient'],$data['etat']);
        return $commande;
    }


    public function verifTypeEnergie(){
       switch($this->typeEnergie){
           case "PETROLE":
            $this->typeEnergie = "Petrole";
            break;
            case "HYDROGENE":
            $this->typeEnergie = "Hydrogene";
            break;
            case "ELECTRICITE":
            $this->typeEnergie = "Electricite";
            break;
            case "BIOMETHANE":  
            $this->typeEnergie = "Biomethane";
            break;
            case "GAZNATUREL":
            $this->typeEnergie = "Gaz naturel";
            break;
       }
    }

    public function verifModeExtraction(){
        switch($this->modeExtraction){
            case "EOLIEN":
             $this->modeExtraction = "Eolien";
             break;
             case "NUCLEAIRE":
             $this->modeExtraction = "Nucleaire";
             break;
             case "FORAGEVERTICAL":
             $this->modeExtraction = "Forage Vertical";
             break;
             case "FORAGEHORIZONTAL":  
             $this->modeExtraction = "Forage Horizontal";
                break;
             case "SOLAIRE":
             $this->modeExtraction = "Solaire";
                break;
            
        }
    }

    public function verifOrigineGeographique(){
        switch($this->origineGeographique){
            case "FRANCE":
                $this->origineGeographique = "France";
                break;
            case "ESPAGNE":
                $this->origineGeographique = "Espagne";
                break;
            case "ALLEMAGNE":
                $this->origineGeographique = "Allemagne";
                break;
            case "ITALIE":  
                $this->origineGeographique = "Italie";
                break;
            case "ROYAUMEUNI":
                $this->origineGeographique = "Royaume-Uni";
                break;
            case "CHINE": 
                $this->origineGeographique = "Chine";
                break;
            case "PAYSBAS": 
                $this->origineGeographique = "Pays-Bas";
                break;
            case "PORTUGAL": 
                $this->origineGeographique = "Portugal";
                break;
            case "SUEDE": 
                $this->origineGeographique = "Suede";
                break;
            case "FINLANDE": 
                $this->origineGeographique = "Finlande";
                break;
            case "ALGERIE": 
                $this->origineGeographique = "Algerie";
                break;
            case "MAROC":
                $this->origineGeographique = "Maroc";
                break;
            case "TUNISIE":
                $this->origineGeographique = "Tunisie";
                break;
            case "TURQUIE":
                $this->origineGeographique = "Turquie";
                break;

            
        }
    }
}

?>