package com.flashcats.ui.pantalles.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.R;
import com.flashcats.data.LoginRepository;
import com.flashcats.ui.pantalles.user.PantallaPrincipal;

public class PantallaConfigUsuaris extends AppCompatActivity {

    private String clau_sessio;
    private int tipus_usuari = 0;
    private String nom_user;

    private Button button_afegir_usuari;
    private Button button_modif_usuari;
    private Button button_eliminar_usuari;
    private Button button_tornar_usuari;


    private TextView nom_usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_config_usuaris);

        nom_usuari = findViewById(R.id.nom_usuari_pantalla_config_usuari);
        button_afegir_usuari = findViewById(R.id.button_afegir_usuari);
        button_modif_usuari = findViewById(R.id.button_modif_usuari);
        button_eliminar_usuari = findViewById(R.id.button_eliminar_usuari);
        button_tornar_usuari = findViewById(R.id.button_tornar_usuari);

        //recuperem nom d'usuari clau de sessió i inicialitzem tipus d'usuari segons convingui
        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();
        nom_usuari.setText(nom_user);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        button_afegir_usuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Afegir Usuari", Toast.LENGTH_LONG).show();
            }
        });

        button_modif_usuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Modificar Usuari", Toast.LENGTH_LONG).show();
            }
        });

        button_eliminar_usuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Eliminar Usuari", Toast.LENGTH_LONG).show();
            }
        });

        button_tornar_usuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segPntalla("torna");
            }
        });
    }

    private void segPntalla(String action) {

        if (action.compareTo("torna")==0) {
            Intent intent = new Intent(this, PantallaPrincipal.class);
            startActivity(intent);
        }
    }
}