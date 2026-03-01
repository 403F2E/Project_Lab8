<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

class Connexion {
    private $connexion;

    public function __construct() {
        try {
            $this->connexion = new PDO(
                "mysql:host=localhost;dbname=school1;charset=utf8",
                "root",
                ""
            );
            $this->connexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            die("Erreur connexion : " . $e->getMessage());
        }
    }

    public function getConnexion() {
        return $this->connexion;
    }
}
?>