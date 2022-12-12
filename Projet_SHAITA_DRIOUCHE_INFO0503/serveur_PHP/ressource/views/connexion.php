<?php
// page lorsque l'utilisateur est connecté
include "../Autoload.php";

session_start();
if($_SESSION['connecte']==false)
    {
    header("location: ../../index.php");
    exit();
    }
define("file2" ,'../../../serveur_JAVA/energie.json' );
    // variable de session pour stocker une commande

?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Système d'achat de l'énergie</title>
    <link rel="icon" type="image/png" href="../img/SALE.png" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link href="../css/style.css" rel="stylesheet">
</head>
<body>
    
<!-- interface  utilisateur -->
<div class="container contenir">
<header>
    <img src="../img/SALE.gif" width="100" height="100"  />
    <br><h1 class="text-center">Système d'achat de l'énergie</h1>
</header>
<br>


    <div class="row ">
        <div class="col-4 alert alert-info scroller">
            <p>consultez les énergies disponible sur le marché </p>
            <?php
            // on recupere d'abord le contenu du fichier json dans un tableau
            $data = file_get_contents(file2);
            $obj = json_decode($data);
        
            foreach ($obj as $key => $value)
                {      
                    $commandeMarche = new Commande($value->typeEnergie, $value->quantite, $value->modeExtraction , $value->origineGeographique ,$value->prixUnite, $value->idPone,$value->etat);                      
                
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>Type d\'énergie  :</td>';
                        echo '<td>'.$commandeMarche->getTypeEnergie().'</td>';         
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Quantité désiré  :</td>';
                        echo '<td>'.$commandeMarche->getQuantiteDesire().'</td>'; 
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Mode d\'extraction :</td>';
                        echo '<td>'.$commandeMarche->getModeExtraction().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Origine géographique :</td>';
                        echo '<td>'.$commandeMarche->getOrigineGeographique().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Prix max par unité :</td>';
                        echo '<td>'.$commandeMarche->getPrixMaxUnite().'</td>';
                echo '</tr>';
                echo '</table>';   
            }
            ?>
        </div>
        <div class="col-4 alert alert-primary scroller ">
            <p>Information utilisateur :</p>
            <?php 

             $user1 = $_SESSION['utilisateur'];
             

                // affiche un tableau avec les informations de l'utilisateur
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>nom :</td>';
                        echo '<td>'.$user1->getNom().'</td>';         
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>prénom :</td>';
                        echo '<td>'.$user1->getPrenom().'</td>'; 
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Adresse :</td>';
                        echo '<td>'.$user1->getAdresse().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Email :</td>';
                        echo '<td>'.$user1->getLogin().'</td>';
                echo '</tr>';
                echo '</table>';                        
            ?>
        </div>
        <div class="col-4 alert alert-info scroller">
            <p>Energie que vous avez acheté : </p>
            <?php

                // mettre le contenu du fichier dans une variable
            $data = file_get_contents("../../storage/Commande.json"); 
            // décoder le flux JSON
            $obj = json_decode($data ); 
            $i = 0;
            foreach ($obj as $key => $value)
            {
                // on verifie les champs correspondent a un utilisateur
                if ($user1->getId() == $value->idClient ) {
                    // on affiche les informations de la commande
                    $commande = new Commande($value->typeEnergie, $value->quantiteDesire, $value->modeExtraction , $value->origineGeographique ,$value->prixMaxUnite, $user1->getId(),$value->etat);
                    echo '<table class="table table-bordered">';   
                    echo '<tr>';    
                            echo '<td>Id :</td>';
                            echo '<td>'.$i.'</td>';         
                    echo '</tr>';
                    echo '<tr>';    
                            echo '<td>Type d\'énergie :</td>';
                            echo '<td>'.$commande->getTypeEnergie().'</td>';         
                    echo '</tr>';
                    echo '<tr>'; 
                            echo '<td>Date d\'envoie:</td>';
                            echo '<td>'.$commande->getDate().'</td>'; 
                    echo '</tr>';
                    echo '<tr>'; 
                            echo '<td>Etat :</td>';
                            echo '<td>'.$commande->getEtat().'</td>';
                    echo '</tr>';
                    echo '</table>';        
                }        
                $i++;
            }     
            ?>
        </div>
    </div>

    <div class="row "  >
        <div class="col-4">
           
        </div>
        <div class="col-4 ">
            <!-- formulaire de deconnexion -->
            <form action="deconnexion.php" method="post">
                <button type="submit" class="btn btn-danger" >Déconnexion</button>
            </form>
        </div>
        <div class="col-4">
            <form action="formulaire.php" method="post">
                <button type="submit" class="btn btn-primary" >Nouvelle commande</button>
            </form>
        </div>
    </div>
</div>


<footer class="pied_page">
    <div class="container">
        <span class="text-muted">Copyright &copy; SALE - 2022-2023 - Tous droits réservés.</span>
    </div>
</footer>

</body>
</html>
