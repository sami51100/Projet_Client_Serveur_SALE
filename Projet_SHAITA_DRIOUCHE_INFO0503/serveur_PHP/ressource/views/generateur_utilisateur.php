<?php
//  creer un tableau d'utilisateur
    $user = new Utilisateur("DRIOUCHE", "Sami", "20 allée des Bourguignons", "sami.driouche@etudiant.univ-reims.fr", "toto");
    $user2 = new Utilisateur("SHAITA", "Fayssal", "8 allée des Lorains", "fayssal.shaita@etudiant.univ-reims.fr", "titi");
    $user3 = new Utilisateur("BOISSON", "Jean-charles", "5 allée des Picard", "Jean-Charles.Boisson@univ-reims.fr", "tata");
    $tab = array($user,$user2,$user3);
    $json = json_encode($tab);
    


    //generate json file
    file_put_contents("storage/Utilisateur.json", $json);           

?>