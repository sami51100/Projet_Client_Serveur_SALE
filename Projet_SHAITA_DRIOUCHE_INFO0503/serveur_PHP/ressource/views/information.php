<?php 
// page lorsque le client finalise la commande
include "../Autoload.php";
session_start();
if($_SESSION['connecte']==false)
{
    header("location: ../../index.php");
    exit();
}
define("file" ,'../../storage/Commande.json' );
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
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Information de la commande d'énergie</title>
    <link rel="icon" type="image/png" href="../img/SALE.png" />
    <link href="../css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body>
        
<!-- page d'information de la commande -->
<div class="container contenir">
<header>
    <img src="../img/SALE.gif" width="100" height="100"  />
    <br><h1 class="text-center">Système d'achat de l'énergie</h1>
    <?php
                $commande = $_SESSION['commande'];
                $revendeur = new Revendeur("AIT-ERRAMI", "Walid", "3 rue des Moulins", "walid.ait-errami@etudiant.univ-reims.fr", "tutu", 4, $commande);
                $_SESSION['revendeur'] = $revendeur;
                $user = $_SESSION['utilisateur'];
                $json =$revendeur->envoyerCommande();

               
                $json1 = json_decode($json);
                if($json1== null){
                        echo '<p>La commande n\'a pas été envoyé , aucun serveur n\'est démarré</p>';
                        echo '<a href="formulaire.php" class="btn btn-primary">Retour</a>';
                        exit();
                }
                $_SESSION['idPONE'] = $json1->idPone;
                $commande2 = new Commande($json1->typeEnergie, $json1->quantite, $json1->modeExtraction , $json1->origineGeographique ,$json1->prixUnite, $user->getId(),$json1->etat);                      

        ?>
</header>
<br>

<div class="row">
        <div class="col-4 alert alert-info" >  
        <p>Information de la commande envoyé au revendeur:</p>
            <?php 
                // affiche un tableau avec les informations de la commande du client
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>Type d\'énergie  :</td>';
                        echo '<td>'.$commande->getTypeEnergie().'</td>';         
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Quantité désiré  :</td>';
                        echo '<td>'.$commande->getQuantiteDesire().'</td>'; 
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Mode d\'extraction :</td>';
                        echo '<td>'.$commande->getModeExtraction().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Origine géographique :</td>';
                        echo '<td>'.$commande->getOrigineGeographique().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Prix max par unité :</td>';
                        echo '<td>'.$commande->getPrixMaxUnite().'</td>';
                echo '</tr>';
                echo '</table>';                        
            ?>    
                  
        </div>
        <div class="col-4 alert alert-primary">
        <p>Information du revendeur :</p>
           <?php
           // affiche un tableau avec les informations de l'utilisateur
           echo '<table class="table table-bordered">'; 
           echo '<tr>';    
                   echo '<td>nom :</td>';
                   echo '<td>'.$revendeur->getNom().'</td>';         
           echo '</tr>';
           echo '<tr>'; 
                   echo '<td>prénom :</td>';
                   echo '<td>'.$revendeur->getPrenom().'</td>'; 
           echo '</tr>';
           echo '<tr>'; 
                   echo '<td>Adresse :</td>';
                   echo '<td>'.$revendeur->getAdresse().'</td>';
           echo '</tr>';
           echo '<tr>'; 
                   echo '<td>Email :</td>';
                   echo '<td>'.$revendeur->getLogin().'</td>';
           echo '</tr>';
           echo '</table>';       
           ?>
    </div>
    <div class="col-4 alert alert-info" >
        <p>Information de l'énergie du marché que le revendeur à acheté:</p>      
           <?php 
                if($commande2->getPrixMaxUnite() != 0 && $commande2->getQuantiteDesire() != 0){
                        $commande2->setEtat("energie achete");
                        ajouterCommande($commande2); 
                        $revendeur->setCommandes($commande2);
                        $_SESSION['revendeur'] = $revendeur;
                        echo '<table class="table table-bordered">'; 
                        echo '<tr>';    
                                echo '<td>Type d\'énergie  :</td>';
                                echo '<td>'.$commande2->getTypeEnergie().'</td>';         
                        echo '</tr>';
                        echo '<tr>'; 
                                echo '<td>Quantité :</td>';
                                echo '<td>'.$commande2->getQuantiteDesire().'</td>'; 
                        echo '</tr>';
                        echo '<tr>'; 
                                echo '<td>Mode d\'extraction :</td>';
                                echo '<td>'.$commande2->getModeExtraction().'</td>';
                        echo '</tr>';
                        echo '<tr>'; 
                                echo '<td>Origine géographique :</td>';
                                echo '<td>'.$commande2->getOrigineGeographique().'</td>';
                        echo '</tr>';
                        echo '<tr>'; 
                                echo '<td>Prix par unité :</td>';
                                echo '<td>'.$commande2->getPrixMaxUnite().'</td>';
                        echo '</tr>';
                        echo '</table>';  
                        
                        echo '<form action="verification.php" method="post" ><button type="submit" class="btn btn-success" name="monBoutonVerif" >Vérifier l\'énergie</button></form>';
                }else
                {
                        echo '<p>Le revendeur n\'a pas acheté d\'énergie. <br>aucune énergie ne correspond à la commande</p>';

                }  
                
               
            ?>  
            
        </div>
        <div class="row "  >
        <div class="col-4">
           
        </div>
        <div class="col-4 ">
            <!-- formulaire de deconnexion -->
            <a href="connexion.php" class="btn btn-primary">Retour vers la page d'accueil</a>
        
        </div>
        <div class="col-4">
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

