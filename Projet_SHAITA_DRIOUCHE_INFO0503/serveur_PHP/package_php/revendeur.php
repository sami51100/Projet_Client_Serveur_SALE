<?php

class revendeur extends Utilisateur implements JsonSerializable {

    private int $id;
    private $commandes;

    
    public function __construct($nom, $prenom, $adresse, $login, $password, $id, $commandes) {
        parent::__construct($nom, $prenom, $adresse, $login, $password , $id);
        $this->id = $id;
        $this->commandes = $commandes;
    }

    public function getId() {
        return $this->id;
    }

    public function getCommandes() {
        return $this->commandes;
    }

    public function setId($id) {
        $this->id = $id;
    }

    public function setCommandes($commandes) {
        $this->commandes = $commandes;
    }
    
    public function envoyerCommande(){
        $data = urlencode(json_encode($this->commandes));
        
        $options = [
            'http' =>
            [
                'method'  => 'POST',
                'header'  => 'Content-type: application/x-www-form-urlencoded',
                'content' => $data
            ]
        ];
        // Envoi de la requête et lecture du JSON reçu
        $URL = "http://localhost:8080/index.html";
         $contexte  = stream_context_create($options);
        $jsonTexte = @file_get_contents($URL, false, $contexte);

        return $jsonTexte;
    }

 
    public function jsonSerialize() : array {
        $json=parent::jsonSerialize();
        $json['id'] = $this->id;
        $json['commandes'] = $this->commandes;
        return $json;
    }

    public static function fromJson($json) : revendeur {     
        $data = json_decode($json , true);  
        $json1 = json_encode($data['commandes']);
        $commandes= Commande::fromJson($json1); 
        $revendeur = new revendeur($data['nom'], $data['prenom'], $data['adresse'], $data['login'], $data['password'], $data['id'], $commandes);
        return $revendeur;
    }
}


