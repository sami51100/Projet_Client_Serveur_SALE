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
    $revendeur = $_SESSION['revendeur'];
    $revendeur->getCommandes()->setEtat("verification");
    $revendeur->getCommandes()->setIdClient($_SESSION['idPONE']);

   
    $json =$revendeur->envoyerCommande();
    $json1 = json_decode($json);

?>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Certification de l'énergie</title>
    <link rel="icon" type="image/png" href="../img/SALE.png" />
    <link href="../css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>

<body>
    
<!-- page d'accueil -->
<div class="container contenir">

<header>
    <img src="../img/SALE.gif" width="100" height="100"  />
    <br><h1 class="text-center">Système d'achat de l'énergie</h1>
</header>
<br>
<div >
    <p class="text-primary">Bienvenue dans notre site de commande d'énergie en ligne.
        <br>Grace à SALE commandé les énergies de demain en toute sécurité.</p>
</div>
<br>


    <div class="row">
        <div class="col-4">
            
        </div>
        <div class="col-4 alert alert-secondary">
            <h3 class="text-center">Certification de l'énergie</h3>
            <?php
            // var_dump($json1);
            $message = $json1->message;
            $message = explode(" ", $message);
            $certification = $message[2];
            $code = $message[9];
            echo '<p>Etat de la certification de l\'AMI : <span class="badge text-bg-success">'.$certification.'</span></p>';
           
            echo '<p>Le code de suivi de l\'énergie: '.$code.'</p>';


            ?>
        <form action="connexion.php" method="post"><button type="submit" class="btn btn-info" >Retour vers la page d'accueil</button></form>
            

        </div>
        <div class="col-4"></div>
    </div>
</div>


    

<footer class="pied_page">
    <div class="container">
        <span class="text-muted">Copyright &copy; SALE - 2022-2023 - Tous droits réservés.</span>
    </div>

</footer>
</body>

</html>


