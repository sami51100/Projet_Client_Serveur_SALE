<?php
// on détruit la session lorsque l'utilisateur clique sur ce deconnecter 
session_start();
$_SESSION = array();
session_destroy();
header("location: ../../index.php");

?>