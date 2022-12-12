<?php
// page lorsque l'utilisateur realise une commande
include "../Autoload.php";
include "../../package_php/enum/TypeEnergie.php";
include "../../package_php/enum/ModeExtractionEnergie.php";
include "../../package_php/enum/OrigineEnergie.php";
include "verif_commande.php";
session_start();
if($_SESSION['connecte']==false)
    {
    header("location: ../../index.php");
    exit();
    }
    
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


    <div class="row">
        <div class="col-4 alert alert-info" >
        <p>Information utilisateur :</p>
           <?php
           $user = $_SESSION['utilisateur'];          

                // affiche un tableau avec les informations de l'utilisateur
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>nom :</td>';
                        echo '<td>'.$user->getNom().'</td>';         
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>prénom :</td>';
                        echo '<td>'.$user->getPrenom().'</td>'; 
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Adresse :</td>';
                        echo '<td>'.$user->getAdresse().'</td>';
                echo '</tr>';
                echo '<tr>'; 
                        echo '<td>Email :</td>';
                        echo '<td>'.$user->getLogin().'</td>';
                echo '</tr>';
                echo '</table>';                        
            ?>
          <form action="connexion.php" method="post"><button type="submit" class="btn btn-info" >Retour vers la page d'accueil</button></form>
        </div>
        <div class="col-4 alert alert-primary">
        <p>Commande d'énergie</p>
        <div class="row ">         
                <form action="formulaire.php" method="post">
                    <div class="mb-3">
                        <label for="typeEnergie" class="form-label">Type de l'énergie</label>
                        <select name="typeEnergie" class="form-control" >
                            <?php
                            foreach ($tabTypeEnergie as $key => $value) {
                                echo '<option value="'.$value->toString().'">'.$value->toString().'</option>';
                            }
                            ?>       
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="quantite" class="form-label" >Quantité désiré</label>
                        <input type="number" class="form-control" name="quantiteDesire" min="0" max="100000" step="0.01" placeholder="187.32">
                    </div>
                    <div class="mb-3">
                        <label for="modeExtract" class="form-label">Mode d'extraction souhaité</label>
                        <select name="modeExtraction" class="form-control" >
                            <?php
                            foreach ($tabModeExtraction as $key => $value) {
                                echo '<option value="'.$value->toString().'">'.$value->toString().'</option>';
                            }
                            ?>       
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="Origine" class="form-label">Origine Géographique souhaité</label>
                        <select name="origineGeographique" class="form-control" >
                            <?php 
                            foreach ($tabOrigineEnergie as $key => $value) {
                                echo '<option value="'.$value->toString().'">'.$value->toString().'</option>';
                            }
                            ?> 
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="prixMax" class="form-label">Prix maximum par unité (en euros €)</label>
                        <input type="number" class="form-control" name="prixMax" min="0" max="100000" placeholder="0.015" step="0.001">
                    </div>
                    <button type="submit" class="btn btn-primary" name="monBouton">Envoyer</button>
                </form>
        </div>
        
    </div>
    <div class="col-4 alert alert-info" >
           <p>Information pour les différentes unités de mesure des énergies :<br></p>
              <ul>
                <li>L'électricité se mesure en kilowattheure (kWh)</li>
                <li>Le pétrole se mesure en baril (= 159 litres) </li>
                <li>Les gaz se mesurent en mètres cubes (m3)</li>
                </ul>
                <br>
                <p>Vous n'êtes pas obligé de spécifié un mode d'extraction et une origine géographique si cela vous importe.</p>
        </div>
</div>
<div class="row">
    <div class="col-4 " >
       
    </div>
                            
        <?php   
        
        if(isset($_POST['monBouton']) ){
        echo '<div class="col-4 alert alert-warning " role="alert" <p>Tout les champs sont obligatoires !</p></div>';
        }
        ?>
    
    <div class="col-4 " >
        
    </div>
</div>


<footer class="pied_page">
    <div class="container">
        <span class="text-muted">Copyright &copy; SALE - 2022-2023 - Tous droits réservés.</span>
    </div>
</footer>

</body>
</html>

<?php
if(verifierInformationCommande()){   
    
    $client = $_SESSION['utilisateur'];
    $commande = new Commande($_POST['typeEnergie'], $_POST['quantiteDesire'], $_POST['modeExtraction']  , $_POST['origineGeographique'] ,$_POST['prixMax'] , $client->getId(),"en cours");
    $_SESSION['commande']=$commande;  
   
    // redirige vers la page d'accueil
    echo '<script language="Javascript">
           <!--
                 document.location.replace("information.php");
           // -->
     </script>';

}


?>