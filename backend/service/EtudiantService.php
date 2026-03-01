<?php
include_once 'C:/Xamp/htdocs/Projet_Lab5/classes/Etudiant.php';
include_once 'C:/Xamp/htdocs/Projet_Lab5/connexion/Connexion.php';
include_once 'C:/Xamp/htdocs/Projet_Lab5/dao/IDao.php';

class EtudiantService implements IDao {
    private $connexion;

    function __construct() {
        $this->connexion = new Connexion();
    }

    public function create($o) {
        $query = "INSERT INTO etudiant (nom, prenom, ville, sexe) VALUES (:nom, :prenom, :ville, :sexe)";
        $stmt = $this->connexion->getConnexion()->prepare($query);
        $stmt->execute([
            ':nom'    => $o->getNom(),
            ':prenom' => $o->getPrenom(),
            ':ville'  => $o->getVille(),
            ':sexe'   => $o->getSexe()
        ]);
    }
    public function deleteById($id) {
    $stmt = $this->connexion->getConnexion()->prepare("DELETE FROM etudiant WHERE id = :id");
    $stmt->execute([':id' => $id]);
}

public function updateNom($id, $nom) {
    $stmt = $this->connexion->getConnexion()->prepare("UPDATE etudiant SET nom = :nom WHERE id = :id");
    $stmt->execute([':nom' => $nom, ':id' => $id]);
}

    public function delete($o) {}
    public function update($o) {}
    public function findAll() {}
    public function findById($id) {}

    public function findAllApi() {
        $req = $this->connexion->getConnexion()->query("SELECT * FROM etudiant");
        return $req->fetchAll(PDO::FETCH_ASSOC);
    }
}
?>