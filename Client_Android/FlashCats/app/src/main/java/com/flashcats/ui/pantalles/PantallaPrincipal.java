package com.flashcats.ui.pantalles;

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
import com.flashcats.ui.TemaListActivity;
import com.flashcats.ui.login.LoginActivity;
import com.flashcats.ui.login.LoginViewModel;
import com.flashcats.ui.login.LoginViewModelFactory;

public class PantallaPrincipal extends AppCompatActivity {

    private String clau_sessio;
    private int tipus_usuari = 0;
    private String nom_user;

    private LoginViewModel loginViewModel;
    private TemesRepository temesViewModel;

    private Button logout_Button;
    private Button button_temes;
    private Button button_continuar;
    private Button button_config;
    private Button button_flashcards;

    private TextView nom_usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        temesViewModel = new TemesRepository();

        nom_usuari = findViewById(R.id.nom_usuari);
        logout_Button = findViewById(R.id.logoutButton);
        button_temes = findViewById(R.id.button_temes);
        button_continuar = findViewById(R.id.button_continuar);
        button_config = findViewById(R.id.button_config);
        button_flashcards = findViewById(R.id.button_flashcards);

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
            button_flashcards.setVisibility(View.INVISIBLE);
            button_temes.setVisibility(View.VISIBLE);
            button_continuar.setVisibility(View.VISIBLE);
        } else if(tipus_usuari == 1){
            button_continuar.setVisibility(View.INVISIBLE);
            button_config.setVisibility(View.VISIBLE);
            button_flashcards.setVisibility(View.VISIBLE);
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
                segPntalla("config_admin");
            }
        });

        button_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temesViewModel.numTemes(clau_sessio);
                temesViewModel.obtenirTemes(clau_sessio);
                segPntalla("continuar");
            }
        });

        button_flashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temesViewModel.numTemes(clau_sessio);
                temesViewModel.obtenirTemes(clau_sessio);
                segPntalla("llista_flashcards");
            }
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
        } else if (action.compareTo("config_admin")==0) {
            //Intent intent = new Intent(this, PantallaAdminTemes.class);
            Toast.makeText(getApplicationContext(), "Configuració d'usuaris", Toast.LENGTH_LONG).show();
            //startActivity(intent);
        } else if (action.compareTo("llista_flashcards")==0) {
            //Intent intent = new Intent(this, PantallaAdminTemes.class);
            Toast.makeText(getApplicationContext(), "Llista de FlashCards", Toast.LENGTH_LONG).show();
            //startActivity(intent);
        }
    }
}