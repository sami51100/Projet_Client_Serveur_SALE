<?php
spl_autoload_register(function ($class_name) {
    include '../../package_php/'.$class_name . '.php';
    });
?>