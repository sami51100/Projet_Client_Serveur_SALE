<?php
spl_autoload_register(function ($class_name) {
    include 'package_php/'.$class_name . '.php';
    });

// chemin d'accès à votre fichier JSON
define("file" ,'../../storage/Commande.json' );


// fonction verifier information
function verifierInformationCommande(): bool {
    // on verifie si les champs ne sont pas vide
    if(empty($_POST['typeEnergie']) || empty($_POST['quantiteDesire']) || empty($_POST['modeExtraction']) || empty($_POST['origineGeographique']) || empty($_POST['prixMax'])){  
        
        return false;
    } 
        return true;
    
}

// ajoute la commande dans un fichier json
function ajouterCommande(Commande $commande){
    // on recupere d'abord le contenu du fichier json dans un tableau
    $data = file_get_contents(file);
    $obj = json_decode($data);

    // on ajoute la commande dans le fichier json
    $obj[] = $commande;
    // on encode le fichier json
    $json = json_encode($obj);
    // on ecrit dans le fichier json
    file_put_contents(file, $json);
}


 
?>