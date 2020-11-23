package com.flashcats.ui.masterDetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flashcats.R;
import com.flashcats.data.FlashCardsRepository;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.ui.pantalles.admin.PantallaModificarTema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * An activity representing a single Tema detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TemaListActivity}.
 */
public class TemaDetailActivity extends AppCompatActivity {

    //coi del tema del qual es mostren els detalls
    private String codi_tema;
    private String nom_user;
    private String clau_sessio;
    private int tipus_usuari;

    private TemesRepository temesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();

        temesRepository = new TemesRepository();

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        setContentView(R.layout.activity_tema_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton float_delete = (FloatingActionButton) findViewById(R.id.float_delete_tema);
        FloatingActionButton float_edit = (FloatingActionButton) findViewById(R.id.float_edit_tema);
        FloatingActionButton float_start = (FloatingActionButton) findViewById(R.id.float_start_tema);

        //si l'usuari no és admin no deixem modificar ni eliminar
        if (tipus_usuari == 0){
            float_delete.setVisibility(View.INVISIBLE);
            float_edit.setVisibility(View.INVISIBLE);
            float_start.setVisibility(View.VISIBLE);
        // i si és admin no deixem iniciar el tema
        } else if (tipus_usuari == 1){
            float_delete.setVisibility(View.VISIBLE);
            float_edit.setVisibility(View.VISIBLE);
            float_start.setVisibility(View.VISIBLE);
        }

        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        float_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set a title for alert dialog
                //builder.setTitle("Eliminar Tema");

                // Ask the final question
                builder.setMessage("Segur que el voleu eliminar?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        //en teoria un usuari no admin no hauria d'arribar aquí però per si de cas ...
                        if(tipus_usuari == 1) {
                            int result = -1;
                            result = temesRepository.eliminaTema(clau_sessio,codi_tema);

                            if (result > -1) {
                                Toast.makeText(getApplicationContext(), "Tema Eliminat", Toast.LENGTH_LONG).show();
                            } else if (result == -1) {
                                Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                            } else if (result == -2) {
                                Toast.makeText(getApplicationContext(), "No existeix aquest tema", Toast.LENGTH_LONG).show();
                            } else if (result == -3) {
                                Toast.makeText(getApplicationContext(), "Error de la Base de Dades", Toast.LENGTH_LONG).show();
                            }

                            segPntalla("tornar");
                        } else {
                            Toast.makeText(getApplicationContext(), "L'usuari no té permisos", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        Toast.makeText(getApplicationContext(),
                                "Tema No eliminat",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        float_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segPntalla("modifica_tema");
            }
        });

        float_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segPntalla("llista_flashcards");
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.
        // For more information, see the Fragments API guide at:
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            codi_tema = getIntent().getStringExtra(TemaDetailFragment.ARG_ITEM_ID);
            arguments.putString(TemaDetailFragment.ARG_ITEM_ID,codi_tema);
            TemaDetailFragment fragment = new TemaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tema_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, TemaListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("modifica_tema") == 0) {
            Intent intent = new Intent(this, PantallaModificarTema.class);
            // passem a la següent pantalla codi del tema seleccionat
            extras.putString("param1", codi_tema);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (action.compareTo("llista_flashcards")==0) {
            Intent intent = new Intent(this, FlashCardListActivity.class);
            // passem a la següent pantalla codi del tema seleccionat
            extras.putString("param1", codi_tema);
            intent.putExtras(extras);
            Toast.makeText(getApplicationContext(), "Llista de FlashCards", Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else if (action.compareTo("tornar") == 0) {
            Intent intent = new Intent(this, TemaListActivity.class);
            startActivity(intent);
        }
    }
}
