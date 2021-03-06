package com.flashcats.ui.pantalles.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.R;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.ui.masterDetail.FlashCardListActivity;
import com.flashcats.ui.masterDetail.TemaListActivity;

public class PantallaAfegirTema extends AppCompatActivity {

    private String clau_sessio;
    private String nom_user;
    private int tipus_usuari;

    private Button button_tornar;
    private Button button_afegirTema;
    private TextView nom_usuari;
    private EditText txt_nomTema;
    private EditText txt_descripcioTema;

    private TemesRepository temesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_afegir_tema);

        temesRepository = new TemesRepository();

        nom_usuari = findViewById(R.id.nom_usuari);
        button_tornar = findViewById(R.id.button_tornar);
        button_afegirTema = findViewById(R.id.button_afegirTema);
        txt_nomTema = findViewById(R.id.txt_nomTema);
        txt_descripcioTema = findViewById(R.id.txt_descripcioTema);

        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();
        nom_usuari.setText(nom_user);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        /*// Mostrem fletxa de retorn a la action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // I definim que al clicar tornem al llistat de FlashCards
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TemaListActivity.class));
            }
        });*/

        button_afegirTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = -1;
                result = temesRepository.altaTema(clau_sessio,txt_nomTema.getText().toString(),txt_descripcioTema.getText().toString());

                if (result > -1) {
                    Toast.makeText(getApplicationContext(), "Tema afegit", Toast.LENGTH_LONG).show();
                } else if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                } else if (result == -2) {
                    Toast.makeText(getApplicationContext(), "Aquest tema ja existeix", Toast.LENGTH_LONG).show();
                } else if (result == -3) {
                    Toast.makeText(getApplicationContext(), "Error de la Base de Dades", Toast.LENGTH_LONG).show();
                }

                segPntalla("tornar");

            }
        });

        button_tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                segPntalla("tornar");
            }
        });
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("tornar") == 0) {
            Intent intent = new Intent(this, TemaListActivity.class);
            startActivity(intent);
        }
    }
}