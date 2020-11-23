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
import com.flashcats.data.FlashCardsRepository;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.data.model.TemaFlashCard;
import com.flashcats.ui.masterDetail.FlashCardListActivity;
import com.flashcats.ui.masterDetail.TemaListActivity;

public class PantallaAfegirFlashCard extends AppCompatActivity {

    private String clau_sessio;
    private String nom_user;
    private int tipus_usuari;
    private String codi_tema;

    private Button button_tornar;
    private Button button_afegir;
    private TextView nom_usuari;
    private TextView nom_tema;
    private EditText txt_anvers;
    private EditText txt_revers;

    private FlashCardsRepository flashCardsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_afegir_flashcard);

        flashCardsRepository = new FlashCardsRepository();
        codi_tema = flashCardsRepository.codi_tema;

        nom_usuari = findViewById(R.id.nom_usuari_afegir_flashcard);
        nom_tema = findViewById(R.id.nom_tema_afegir_flashcard);
        button_tornar = findViewById(R.id.button_tornar_afegir_flashcard);
        button_afegir = findViewById(R.id.button_afegir_afegir_flashcard);
        txt_anvers = findViewById(R.id.txt_anvers_afegir_flashcard);
        txt_revers = findViewById(R.id.txt_revers_afegir_flashcard);

        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();
        nom_usuari.setText(nom_user);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        TemaFlashCard tema_sup = TemesRepository.ITEM_MAP.get(codi_tema);
        nom_tema.setText("Tema: " + tema_sup.getNom());

        /*// Mostrem fletxa de retorn a la action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // I definim que al clicar tornem al llistat de FlashCards
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FlashCardListActivity.class));
            }
        });*/

        button_afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = -1;
                result = flashCardsRepository.altaFlashCard(clau_sessio,codi_tema,txt_anvers.getText().toString(),txt_revers.getText().toString());

                if (result > -1) {
                    Toast.makeText(getApplicationContext(), "FlashCard afegit", Toast.LENGTH_LONG).show();
                } else if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                } else if (result == -2) {
                    Toast.makeText(getApplicationContext(), "Aquest FlashCard ja existeix", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(this, FlashCardListActivity.class);
            startActivity(intent);
        }
    }
}