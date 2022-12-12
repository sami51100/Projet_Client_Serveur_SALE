<?php

// fait une enumeration pour les mode d'extraction d'energie
class ModeExtractionEnergie {
    // attributs
    public static $AUCUNE = null;
    public static $EOLIEN = null;
    public static $NUCLEAIRE = null;
    public static $FORAGEVERTICAL = null;
    public static $FORAGEHORIZONTAL = null;
    public static $SOLAIRE = null;


    private $nom;

    // constructeur
    public function __construct($nom){
        $this->nom = $nom;
        
    }

    public function toString() : String{ return $this->nom; }

    // public static function getValue(String $value){
    //     // $value=strtoupper(Display::removeAccents($value));
    //     return self::$$value;
    // }

   
}

ModeExtractionEnergie::$AUCUNE = new ModeExtractionEnergie("Aucune");
ModeExtractionEnergie::$EOLIEN = new ModeExtractionEnergie("Eolien");
ModeExtractionEnergie::$NUCLEAIRE = new ModeExtractionEnergie("Nucleaire");
ModeExtractionEnergie::$FORAGEVERTICAL = new ModeExtractionEnergie("Forage Vertical");
ModeExtractionEnergie::$FORAGEHORIZONTAL = new ModeExtractionEnergie("Forage Horizontal");
ModeExtractionEnergie::$SOLAIRE = new ModeExtractionEnergie("Solaire");


$tabModeExtraction = array(ModeExtractionEnergie::$AUCUNE,ModeExtractionEnergie::$EOLIEN , ModeExtractionEnergie::$NUCLEAIRE , ModeExtractionEnergie::$FORAGEVERTICAL , ModeExtractionEnergie::$FORAGEHORIZONTAL , ModeExtractionEnergie::$SOLAIRE);





?>