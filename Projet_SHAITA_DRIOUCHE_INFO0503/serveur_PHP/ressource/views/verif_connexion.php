<?php
spl_autoload_register(function ($class_name) {
    include 'package_php/'.$class_name . '.php';
    });
session_start();

// chemin d'accès à votre fichier JSON
define("file" ,'storage/Utilisateur.json' );

// fonction verifier information
function verifierInformationConnexion(): bool {
    // on verifie si les champs ne sont pas vide
    if(empty($_POST['login']) || empty($_POST['password'])){  
        
        return false;
    } 
        return true;
    
}
// fonction pour verifier si le login et le password sont correct
function verifier(): bool {
    // mettre le contenu du fichier dans une variable
$data = file_get_contents(file); 
// décoder le flux JSON
$obj = json_decode($data ); 
    if (verifierInformationConnexion()) {
        foreach ($obj as $key => $value)
        {
            // on verifie les champs correspondent a un utilisateur
            if ($_POST['login'] == $value->login && $_POST['password'] == $value->password) {
                return true;
            } 
        }
       
    } 
        return false;
    
}

function retournerUtilisateur() : Utilisateur{
    // mettre le contenu du fichier dans une variable
    $data = file_get_contents(file); 
    // décoder le flux JSON
    $obj = json_decode($data ); 
    
    foreach ($obj as $key => $value)
    {
        // on verifie les champs correspondent a un utilisateur
        if ($_POST['login'] == $value->login && $_POST['password'] == $value->password) {
            $user = new Utilisateur($value->nom, $value->prenom, $value->adresse, $value->login, $value->password , $value->id);
            return $user;
        } 
    }
    return null;
}


 
?>