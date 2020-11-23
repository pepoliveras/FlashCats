package com.flashcats.ui.pantalles.admin;

import androidx.appcompat.app.AppCompatActivity;

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
import com.flashcats.data.model.FlashCard;
import com.flashcats.data.model.TemaFlashCard;
import com.flashcats.ui.masterDetail.FlashCardListActivity;

public class PantallaModificarFlashCard extends AppCompatActivity {

    private String clau_sessio;
    private String nom_user;
    private int tipus_usuari;
    private String codi_tema;
    private String codi_flashcard_modificar;

    private Button button_tornar;
    private Button button_modificar;
    private TextView nom_usuari;
    private TextView nom_tema;
    private TextView camp_codi_flashcard;
    private EditText txt_anvers;
    private EditText txt_revers;

    private FlashCardsRepository flashCardsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_flashcard);

        flashCardsRepository = new FlashCardsRepository();
        codi_tema = flashCardsRepository.codi_tema;

        nom_usuari = findViewById(R.id.nom_usuari_modificar_flashcard);
        nom_tema = findViewById(R.id.nom_tema_modificar_flashcard);
        camp_codi_flashcard = findViewById(R.id.codi_flashcard_modificar_flashcard);
        button_tornar = findViewById(R.id.button_tornar_modificar_flashcard);
        button_modificar = findViewById(R.id.button_modificar_modificar_flashcard);
        txt_anvers = findViewById(R.id.txt_anvers_modificar_flashcard);
        txt_revers = findViewById(R.id.txt_revers_modificar_flashcard);

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

        // Recuperem l'intent que ens ha cridat i extraiem els paràmetres que ens ha passat
        Bundle params = this.getIntent().getExtras();

        if(params !=null) {
            codi_flashcard_modificar = params.getString("param1");
            camp_codi_flashcard.setText("codi de la flashcard: " + codi_flashcard_modificar);
        }

        FlashCard flashCard_modificar = flashCardsRepository.ITEM_MAP.get(codi_flashcard_modificar);

        if(flashCard_modificar != null) {
            txt_anvers.setText(flashCard_modificar.getAnvers_text());
            txt_revers.setText(flashCard_modificar.getRevers_text());
        }

        button_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //en teoria un usuari no admin no hauria d'arribar aquí però per si de cas ...
                if(tipus_usuari == 1) {
                    int result = -1;
                    result = flashCardsRepository.modificaFlashCard(clau_sessio,codi_flashcard_modificar,txt_anvers.getText().toString(),txt_revers.getText().toString());

                    if (result > -1) {
                        Toast.makeText(getApplicationContext(), "FlashCard Modificat", Toast.LENGTH_LONG).show();
                    } else if (result == -1) {
                        Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                    } else if (result == -2) {
                        Toast.makeText(getApplicationContext(), "Aquest nom de Flashcard ja existeix", Toast.LENGTH_LONG).show();
                    } else if (result == -3) {
                        Toast.makeText(getApplicationContext(), "Error de la Base de Dades", Toast.LENGTH_LONG).show();
                    }
                    segPntalla("tornar");
                } else {
                    Toast.makeText(getApplicationContext(), "L'usuari no té permisos", Toast.LENGTH_LONG).show();
                }
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