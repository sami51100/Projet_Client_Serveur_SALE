<?php

class TypeEnergie {
    // Attributs
    public static $PETROLE = null;
    public static $ELECTRICITE = null;
    public static $HYDROGENE = null;
    public static $BIOMETHANE = null;
    public static $GAZNATUREL = null;
    

    private $nom;

    // Constructeur
    public function __construct($nom){
        $this->nom = $nom;
        
    }

    public function toString() : String{ return $this->nom; }


}

TypeEnergie::$PETROLE = new TypeEnergie("Petrole");
TypeEnergie::$ELECTRICITE = new TypeEnergie("Electricite");
TypeEnergie::$HYDROGENE = new TypeEnergie("Hydrogene");
TypeEnergie::$BIOMETHANE = new TypeEnergie("Biomethane");
TypeEnergie::$GAZNATUREL = new TypeEnergie("Gaz naturel");
   

$tabTypeEnergie = array(TypeEnergie::$PETROLE, TypeEnergie::$ELECTRICITE, TypeEnergie::$HYDROGENE, TypeEnergie::$BIOMETHANE, TypeEnergie::$GAZNATUREL);

?>