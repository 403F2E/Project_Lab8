<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include_once '../service/EtudiantService.php';
$es = new EtudiantService();
header('Content-Type: application/json');
echo json_encode($es->findAllApi());
?>
