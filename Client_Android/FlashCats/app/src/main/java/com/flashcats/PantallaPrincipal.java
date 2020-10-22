package com.flashcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.flashcats.ui.login.LoginActivity;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Get the Intent that started this activity and extract Extras Bundle
        Bundle params = this.getIntent().getExtras();

        if(params !=null){
            // Capture the layout's TextView and set the string as its text
            TextView textView1 = findViewById(R.id.clau_sessio);
            TextView textView2 = findViewById(R.id.tipus_user);
            textView1.setText(params.getString("param1"));
            textView2.setText(params.getString("param2"));
        }
    }
}