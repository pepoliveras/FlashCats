package com.flashcats.ui.masterDetail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.flashcats.data.FlashCardsRepository;
import com.flashcats.data.LoginRepository;
import com.flashcats.ui.pantalles.admin.PantallaModificarFlashCard;
import com.flashcats.ui.pantalles.admin.PantallaModificarTema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.Toast;

import com.flashcats.R;

/**
 * An activity representing a single FlashCard detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FlashCardListActivity}.
 */
public class FlashCardDetailActivity extends AppCompatActivity {

    private String clau_sessio;
    private int tipus_usuari;
    private String codi_tema = "0";
    private String codi_flashcard = "0";

    private FlashCardsRepository flashCardsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_detail);

        codi_tema = FlashCardsRepository.codi_tema;
        clau_sessio = LoginRepository.getUser().getUserId();

        flashCardsRepository = new FlashCardsRepository();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        FloatingActionButton fab_edit = (FloatingActionButton) findViewById(R.id.float_edit_flashcard);
        FloatingActionButton fab_del = (FloatingActionButton) findViewById(R.id.float_elimina_flashcard);

        if(tipus_usuari == 0){
            fab_edit.setVisibility(View.INVISIBLE);
            fab_del.setVisibility(View.INVISIBLE);
        }

        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set a title for alert dialog
                //builder.setTitle("Eliminar FlashCard");

                // Ask the final question
                builder.setMessage("Segur que voleu eliminar?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        //en teoria un usuari no admin no hauria d'arribar aquí però per si de cas ...
                        if (tipus_usuari == 1) {
                            int result = -1;
                            result = flashCardsRepository.eliminaFlashCard(clau_sessio, codi_flashcard);

                            if (result > -1) {
                                Toast.makeText(getApplicationContext(), "FlashCard Eliminada", Toast.LENGTH_LONG).show();
                            } else if (result == -1) {
                                Toast.makeText(getApplicationContext(), "Error d'usuari o permisos", Toast.LENGTH_LONG).show();
                            } else if (result == -2) {
                                Toast.makeText(getApplicationContext(), "No existeix aquesta FlashCard", Toast.LENGTH_LONG).show();
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
                                "FlashCard No eliminat",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {segPntalla("modifica_flashcard");}
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
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            codi_flashcard = getIntent().getStringExtra(FlashCardDetailFragment.ARG_ITEM_ID);
            arguments.putString(FlashCardDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FlashCardDetailFragment.ARG_ITEM_ID));
            FlashCardDetailFragment fragment = new FlashCardDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flashcard_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, FlashCardListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("modifica_flashcard") == 0) {
            Intent intent = new Intent(this, PantallaModificarFlashCard.class);
            // passem a la següent pantalla codi de la flashcard seleccionada
            extras.putString("param1", codi_flashcard);
            intent.putExtras(extras);
            startActivity(intent);
        } else if (action.compareTo("tornar") == 0) {
            Intent intent = new Intent(this, FlashCardListActivity.class);
            /*extras.putString("param1", codi_tema);
            intent.putExtras(extras);*/
            startActivity(intent);
        }
    }
}