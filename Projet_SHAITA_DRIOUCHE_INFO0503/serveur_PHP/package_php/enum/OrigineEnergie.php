<?php

class OrigineEnergie {
    // Attributs
    public static $AUCUNE = null;
    public static $FRANCE = null;
    public static $ALLEMAGNE = null;
    public static $ESPAGNE = null;
    public static $CHINE = null;
    public static $ITALIE = null;
    public static $ROYAUMEUNI = null;
    public static $PAYSBAS = null;
    public static $PORTUGAL = null;
    public static $SUEDE = null;
    public static $FINLANDE = null;
    public static $ALGERIE = null;
    public static $MAROC = null;
    public static $TUNISIE = null;
    public static $TURQUIE = null;

    private $nom;

    // Constructeur
    public function __construct($nom){
        $this->nom = $nom;
    }

    public function toString() : String{ return $this->nom; }

    
}

OrigineEnergie::$AUCUNE = new OrigineEnergie("Aucune");
OrigineEnergie::$FRANCE = new OrigineEnergie("France");
OrigineEnergie::$ALLEMAGNE = new OrigineEnergie("Allemagne");
OrigineEnergie::$ESPAGNE = new OrigineEnergie("Espagne");
OrigineEnergie::$CHINE = new OrigineEnergie("Chine");
OrigineEnergie::$ITALIE = new OrigineEnergie("Italie");
OrigineEnergie::$ROYAUMEUNI = new OrigineEnergie("Royaume-Uni");
OrigineEnergie::$PAYSBAS = new OrigineEnergie("Pays-Bas");
OrigineEnergie::$PORTUGAL = new OrigineEnergie("Portugal");
OrigineEnergie::$SUEDE = new OrigineEnergie("Suede");
OrigineEnergie::$FINLANDE = new OrigineEnergie("Finlande");
OrigineEnergie::$ALGERIE = new OrigineEnergie("Algerie");
OrigineEnergie::$MAROC = new OrigineEnergie("Maroc");
OrigineEnergie::$TUNISIE = new OrigineEnergie("Tunisie");
OrigineEnergie::$TURQUIE = new OrigineEnergie("Turquie");


$tabOrigineEnergie = array(OrigineEnergie::$AUCUNE,OrigineEnergie::$FRANCE, OrigineEnergie::$ALLEMAGNE, OrigineEnergie::$ESPAGNE, OrigineEnergie::$CHINE, OrigineEnergie::$ITALIE, OrigineEnergie::$ROYAUMEUNI, OrigineEnergie::$PAYSBAS, OrigineEnergie::$PORTUGAL, OrigineEnergie::$SUEDE, OrigineEnergie::$FINLANDE, OrigineEnergie::$ALGERIE, OrigineEnergie::$MAROC, OrigineEnergie::$TUNISIE, OrigineEnergie::$TURQUIE);

?>