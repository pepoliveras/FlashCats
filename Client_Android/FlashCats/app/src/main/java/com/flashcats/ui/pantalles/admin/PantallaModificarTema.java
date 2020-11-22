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
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.ui.TemaListActivity;

public class PantallaModificarTema extends AppCompatActivity {

    private String clau_sessio;
    private String nom_user;
    private int tipus_usuari;
    private String codi_tema_modificar;

    private Button button_tornar;
    private Button button_modificarTema;
    private TextView nom_usuari;
    private TextView codi_tema;
    private EditText txt_nomTema;
    private EditText txt_descripcioTema;

    private TemesRepository temesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_tema);

        temesRepository = new TemesRepository();

        nom_usuari = findViewById(R.id.nom_usuari);
        button_tornar = findViewById(R.id.button_tornar);
        button_modificarTema = findViewById(R.id.button_modificarTema);
        txt_nomTema = findViewById(R.id.txt_nomTema);
        txt_descripcioTema = findViewById(R.id.txt_descripcioTema);
        codi_tema = findViewById(R.id.codiTema);

        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();
        nom_usuari.setText(nom_user);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        // Recuperem l'intent que ens ha cridat i extraiem els paràmetres que ens ha passat
        Bundle params = this.getIntent().getExtras();

        if(params !=null) {
            codi_tema_modificar = params.getString("param1");
            codi_tema.setText("codi del tema: " + codi_tema_modificar);
        }

        button_modificarTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //en teoria un usuari no admin no hauria d'arribar aquí però per si de cas ...
                if(tipus_usuari == 1) {
                    int result = -1;
                    result = temesRepository.modificaTema(clau_sessio,codi_tema_modificar,txt_nomTema.getText().toString(),txt_descripcioTema.getText().toString());

                    if (result > -1) {
                        Toast.makeText(getApplicationContext(), "Tema Modificat", Toast.LENGTH_LONG).show();
                    } else if (result == -1) {
                        Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                    } else if (result == -2) {
                        Toast.makeText(getApplicationContext(), "Aquest nom de tema ja existeix", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(this, TemaListActivity.class);
            startActivity(intent);
        }
    }
}