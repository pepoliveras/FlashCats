package com.flashcats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.ui.login.LoggedInUserView;
import com.flashcats.ui.login.LoginActivity;
import com.flashcats.ui.login.LoginViewModel;
import com.flashcats.ui.login.LoginViewModelFactory;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PantallaPrincipal extends AppCompatActivity {

    private String clau_session;
    private LoginViewModel loginViewModel;
    private Button logout_Button;
    private TextView textView1;
    private TextView tipus_user;
    private TextView nom_usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        // Get the Intent that started this activity and extract Extras Bundle
        Bundle params = this.getIntent().getExtras();

        if(params !=null){
            // Capture the layout's TextView and set the string as its text
            textView1 = findViewById(R.id.clau_sessio);
            tipus_user = findViewById(R.id.tipus_user);
            nom_usuari = findViewById(R.id.nom_usuari);
            logout_Button = findViewById(R.id.logoutButton);

            clau_session = params.getString("param1");
            textView1.setText(clau_session);
            nom_usuari.setText(params.getString("param2"));

            if (clau_session.startsWith("0")) {
                tipus_user.setText("perfil usuari");
            } else if (clau_session.startsWith("1")) {
                tipus_user.setText("perfil ADMIN");
            }
        }

        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // fem logout ...
                loginViewModel.logout(clau_session);
                segPntalla("logout");

            }
        });
    }

    private void segPntalla(String action) {

        if (action.compareTo("logout")==0) {

            Intent intent = new Intent(this, LoginActivity.class);

            Toast.makeText(getApplicationContext(), "Usuari desconnectat", Toast.LENGTH_LONG).show();

            startActivity(intent);
        }
    }
}