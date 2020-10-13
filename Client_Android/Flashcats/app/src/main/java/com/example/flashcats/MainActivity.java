package com.example.flashcats;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private int port_server = 6066;
    private String missatge_prova = "Hola server :)";
    private TextView text_mostrar;
    private String addr_server = "jlzorita.ddns.net";
    private Socket socket;

    // métodes Webservice Server:
    // login(usuari,password) retorna String[] amb [0,0] si falla o [clau_sessió, tipus_user] si èxit
    // tipus:user: 0 --> usuari normal, 1 --> usuari admin
    // logout(clau) no retorna res

    // usuaris (demo, demo) o (root, root) per fer proves


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_mostrar = (TextView) findViewById(R.id.misatge_retorn);
    }

    public void provar_connexio(View view) {

        text_mostrar.setText(missatge_prova);
        sendMessage(missatge_prova);

    }

    private void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    socket = new Socket(addr_server, port_server);

                    OutputStream out = socket.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(msg);
                    output.flush();

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String st = input.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = text_mostrar.getText().toString();
                            if (st.trim().length() != 0)
                                text_mostrar.setText(s + "\nFrom Server : " + st);
                        }
                    });

                    output.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


}