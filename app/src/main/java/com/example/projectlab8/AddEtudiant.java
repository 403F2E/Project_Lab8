package com.example.projectlab8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectlab8.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 403f2e had created the AddEtudiant activity class for adding new students
public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    // 403f2e had declared UI components and request queue
    private EditText nom, prenom;
    private Spinner ville;
    private RadioButton m, f;
    private Button add;
    private RequestQueue requestQueue;

    // 403f2e had defined the PHP endpoint URL for adding students
    private static final String insertUrl = "http://10.0.2.2/Projet_Lab5/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);
        
        // 403f2e had set up the "View List" button to navigate to ListEtudiantActivity
        Button btnVoirListe = findViewById(R.id.btnVoirListe);
        btnVoirListe.setOnClickListener(v -> {
            startActivity(new Intent(this, ListEtudiantActivity.class));
        });
        
        // 403f2e had initialized all UI components using findViewById
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        m = findViewById(R.id.m);
        f = findViewById(R.id.f);
        add = findViewById(R.id.add);

        // 403f2e had initialized the Volley request queue
        requestQueue = Volley.newRequestQueue(this);
        // 403f2e had set the click listener for the add button
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 403f2e had implemented click handling for the add button
        if (v == add) {
            envoyerEtudiant();
        }
    }

    // 403f2e had created the method to send student data to the server
    private void envoyerEtudiant() {
        // 403f2e had created a Volley StringRequest for POST operation
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                // 403f2e had implemented the response listener with Gson parsing
                response -> {
                    Log.d("RESPONSE", response);
                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                    for (Etudiant e : etudiants) {
                        Log.d("ETUDIANT", e.toString());
                    }
                },
                // 403f2e had implemented the error listener
                error -> Log.e("VOLLEY", "Erreur : " + error.getMessage())) {

            // 403f2e had overridden getParams to prepare POST parameters
            @Override
            protected Map<String, String> getParams() {
                // 403f2e had determined the selected gender value
                String sexe = m.isChecked() ? "homme" : "femme";
                // 403f2e had created a map with all form data
                Map<String, String> params = new HashMap<>();
                params.put("nom", nom.getText().toString());
                params.put("prenom", prenom.getText().toString());
                params.put("ville", ville.getSelectedItem().toString());
                params.put("sexe", sexe);
                return params;
            }

        };
        // 403f2e had added the request to the queue for execution
        requestQueue.add(request);
    }
}
