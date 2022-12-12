<?php
include "ressource/views/verif_connexion.php";
?>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Système d'achat de l'énergie</title>
    <link rel="icon" type="image/png" href="img/SALE.png" />
    <link href="ressource/css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>

<body>
    
<!-- page d'accueil -->
<div class="container contenir">

<header>
    <img src="ressource/img/SALE.gif" width="100" height="100"  />
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
            <!-- formulaire de connexion -->
            <form action="index.php" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label
                    ">Adresse email</label>
                    <input type="email" class="form-control" aria-describedby="emailHelp" name="login" placeholder="email">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label">Mot de passe</label>
                    
                    <input type="password" class="form-control" name="password" placeholder="mot de passe">
                </div>
                <div class="checkbox">
                    <label> <input type="checkbox"> se souvenir de moi </label>
                </div>
                <button type="submit" class="btn btn-primary">Connexion</button>
            </form>

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


<?php
if(verifier()){  
    $user1=retournerUtilisateur();
    var_dump($user1);
    $_SESSION['utilisateur'] = $user1;
    $_SESSION['connecte'] = true ;   
    $_SESSION['commande']=null;
    $_SESSION['revendeur']=null ;
    header("location: ressource/views/connexion.php");
    exit();
}


?>