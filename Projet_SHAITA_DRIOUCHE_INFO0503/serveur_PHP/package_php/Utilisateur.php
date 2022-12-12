<?php

class Utilisateur implements JsonSerializable{
    
        // Attributs
        private $nom;
        private $prenom;
        private $adresse;
        private $login;
        private $password;
        private int $id;
    
        // Constructeur
        public function __construct($nom, $prenom, $adresse, $login, $password , $id){
            $this->nom = $nom;
            $this->prenom = $prenom;
            $this->adresse = $adresse;
            $this->login = $login;
            $this->password = $password;
            $this->id = $id;
        }

        // Getters
        public function getNom() {
            return $this->nom;
        }

        public function getPrenom() {
            return $this->prenom;
        }

        public function getAdresse() {
            return $this->adresse;
        }

        public function getLogin() {
            return $this->login;
        }

        public function getPassword() {
            return $this->password;
        }

        public function getId() {
            return $this->id;
        }

        // Setters
        public function setNom($nom) {
            $this->nom = $nom;
        }

        public function setPrenom($prenom) {
            $this->prenom = $prenom;
        }

        public function setAdresse($adresse) {
            $this->adresse = $adresse;
        }

        public function setLogin($login) {
            $this->login = $login;
        }

        public function setPassword($password) {
            $this->password = $password;
        }

        public function setId($id) {
            $this->id = $id;
        }
        
        // tojson
        public function jsonSerialize() : array{
            return [
                'nom' => $this->nom,
                'prenom' => $this->prenom,
                'adresse' => $this->adresse,
                'login' => $this->login,
                'password' => $this->password,
                'id' => $this->id
            ];
        }

        // fromjson
        public static function fromJson($json) : Utilisateur {
            $data = json_decode($json, true);
            return new Utilisateur($data['nom'], $data['prenom'], $data['adresse'], $data['login'], $data['password'], $data['id']);
        }
}