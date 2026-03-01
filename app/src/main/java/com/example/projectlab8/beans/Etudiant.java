package com.example.projectlab8.beans;

// 403f2e had created the Etudiant bean class
public class Etudiant {
    // 403f2e had defined the private attributes for the student entity
    private int id;
    private String nom, prenom, ville, sexe;

    // 403f2e had implemented the default constructor
    public Etudiant() {}

    // 403f2e had created getter methods for all attributes
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getVille() { return ville; }
    public String getSexe() { return sexe; }

    // 403f2e had overridden toString() for proper object representation
    @Override
    public String toString() {
        return "Etudiant{id=" + id + ", nom='" + nom + "', prenom='" + prenom +
                "', ville='" + ville + "', sexe='" + sexe + "'}";
    }
}
