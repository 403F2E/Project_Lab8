package com.example.projectlab8;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectlab8.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 403f2e had created the ListEtudiantActivity class to display and manage students
public class ListEtudiantActivity extends AppCompatActivity {

    // 403f2e had declared UI components and data structures
    private ListView listView;
    private RequestQueue requestQueue;
    private List<Etudiant> etudiants = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<String> displayList = new ArrayList<>();

    // 403f2e had defined the PHP endpoints for CRUD operations
    private static final String LOAD_URL = "http://10.0.2.2/Projet_Lab5/ws/loadEtudiant.php";
    private static final String DELETE_URL = "http://10.0.2.2/Projet_Lab5/ws/deleteEtudiant.php";
    private static final String UPDATE_URL = "http://10.0.2.2/Projet_Lab5/ws/updateEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        // 403f2e had initialized the ListView and Volley request queue
        listView = findViewById(R.id.listView);
        requestQueue = Volley.newRequestQueue(this);

        // 403f2e had set up the ArrayAdapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        // 403f2e had triggered the initial loading of students
        chargerEtudiants();

        // 403f2e had implemented click listener to show options when a student is selected
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Etudiant e = etudiants.get(position);
            showOptionsDialog(e);
        });
    }

    // 403f2e had created the method to load all students from the server
    private void chargerEtudiants() {
        // 403f2e had implemented a GET request to fetch student data
        StringRequest request = new StringRequest(Request.Method.GET, LOAD_URL,
                response -> {
                    // 403f2e had used Gson to parse JSON response into Etudiant objects
                    Type type = new TypeToken<List<Etudiant>>(){}.getType();
                    etudiants = new Gson().fromJson(response, type);
                    // 403f2e had updated the display list with formatted student information
                    displayList.clear();
                    for (Etudiant e : etudiants) {
                        displayList.add(e.getNom() + " " + e.getPrenom() + " - " + e.getVille());
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Erreur chargement", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    // 403f2e had created a dialog showing available options (Modify/Delete) for a student
    private void showOptionsDialog(Etudiant e) {
        String[] options = {"Modifier", "Supprimer"};
        new AlertDialog.Builder(this)
                .setTitle(e.getNom() + " " + e.getPrenom())
                .setItems(options, (dialog, which) -> {
                    if (which == 0) showModifierDialog(e);
                    else showConfirmSupprimer(e);
                }).show();
    }

    // 403f2e had created a confirmation dialog before deleting a student
    private void showConfirmSupprimer(Etudiant e) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Supprimer " + e.getNom() + " ?")
                .setPositiveButton("Oui", (dialog, which) -> supprimerEtudiant(e))
                .setNegativeButton("Non", null)
                .show();
    }

    // 403f2e had implemented the DELETE operation
    private void supprimerEtudiant(Etudiant e) {
        // 403f2e had created a POST request with the student ID to delete
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_URL,
                response -> {
                    Toast.makeText(this, "Supprimé !", Toast.LENGTH_SHORT).show();
                    // 403f2e had refreshed the list after successful deletion
                    chargerEtudiants();
                },
                error -> Toast.makeText(this, "Erreur suppression", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                // 403f2e had prepared parameters with the student ID
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(e.getId()));
                return params;
            }
        };
        requestQueue.add(request);
    }

    // 403f2e had created a dialog for modifying a student's name
    private void showModifierDialog(Etudiant e) {
        android.widget.EditText input = new android.widget.EditText(this);
        input.setText(e.getNom());
        new AlertDialog.Builder(this)
                .setTitle("Modifier le nom")
                .setView(input)
                .setPositiveButton("Modifier", (dialog, which) -> {
                    String nouveauNom = input.getText().toString();
                    modifierEtudiant(e, nouveauNom);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    // 403f2e had implemented the UPDATE operation
    private void modifierEtudiant(Etudiant e, String nouveauNom) {
        // 403f2e had created a POST request with student ID and new name
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_URL,
                response -> {
                    Toast.makeText(this, "Modifié !", Toast.LENGTH_SHORT).show();
                    // 403f2e had refreshed the list after successful update
                    chargerEtudiants();
                },
                error -> Toast.makeText(this, "Erreur modification", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                // 403f2e had prepared parameters with student ID and new name
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(e.getId()));
                params.put("nom", nouveauNom);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
