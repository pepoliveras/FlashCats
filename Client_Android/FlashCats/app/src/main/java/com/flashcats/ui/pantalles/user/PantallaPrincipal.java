package com.flashcats.ui.pantalles.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.R;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.ui.masterDetail.TemaListActivity;
import com.flashcats.ui.login.LoginActivity;
import com.flashcats.ui.login.LoginViewModel;
import com.flashcats.ui.login.LoginViewModelFactory;
import com.flashcats.ui.pantalles.admin.PantallaConfigUsuaris;
import com.google.android.material.snackbar.Snackbar;

public class PantallaPrincipal extends AppCompatActivity {

    private String clau_sessio;
    private int tipus_usuari = 0;
    private String nom_user;

    private LoginViewModel loginViewModel;
    private TemesRepository temesRepository;

    private Button logout_Button;
    private Button button_temes;
    private Button button_continuar;
    private Button button_config;

    private TextView nom_usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        temesRepository = new TemesRepository();

        nom_usuari = findViewById(R.id.nom_usuari);
        logout_Button = findViewById(R.id.logoutButton);
        button_temes = findViewById(R.id.button_temes);
        button_continuar = findViewById(R.id.button_continuar);
        button_config = findViewById(R.id.button_config);

        //recuperem nom d'usuari clau de sessió i inicialitzem tipus d'usuari segons convingui
        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();
        nom_usuari.setText(nom_user);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        //Inicialitzem la visibilitat del buttons segons el prefil d'usuari loggejat
        if(tipus_usuari == 0) {
            button_config.setVisibility(View.INVISIBLE);
            button_temes.setVisibility(View.VISIBLE);
            button_continuar.setVisibility(View.VISIBLE);
        } else if(tipus_usuari == 1){
            button_continuar.setVisibility(View.INVISIBLE);
            button_config.setVisibility(View.VISIBLE);
            button_temes.setVisibility(View.VISIBLE);
        }

        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fem logout ...
                loginViewModel.logout(clau_sessio);
                segPntalla("logout");
            }
        });

        button_temes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segPntalla("llista_temes");
            }
        });

        button_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segPntalla("config_usuaris");
            }
        });

        button_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Continuem a partir d'on ens hem quedat en la dearrera sessió", Snackbar.LENGTH_LONG)
                        .setAction("Continuar", null).show();
                segPntalla("continuar"); }
        });
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("logout")==0) {
            Intent intent = new Intent(this, LoginActivity.class);
            Toast.makeText(getApplicationContext(), "Usuari desconnectat", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else if (action.compareTo("llista_temes")==0) {
            Intent intent = new Intent(this, TemaListActivity.class);
            Toast.makeText(getApplicationContext(), "Selecció de Temes", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else if (action.compareTo("config_usuaris")==0) {
            Intent intent = new Intent(this, PantallaConfigUsuaris.class);
            Toast.makeText(getApplicationContext(), "Configuració d'usuaris", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else if (action.compareTo("continuar")==0) {
            //Intent intent = new Intent(this, FlashCardListActivity.class);
            //Toast.makeText(getApplicationContext(), "Llista de FlashCards", Toast.LENGTH_LONG).show();
            //startActivity(intent);
        }
    }
}