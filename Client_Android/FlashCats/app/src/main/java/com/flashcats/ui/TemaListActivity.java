package com.flashcats.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.flashcats.R;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.data.model.TemaFlashCard;
import com.flashcats.ui.pantalles.admin.PantallaAfegirTema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * An activity representing a list of Temes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TemaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TemaListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private TemesRepository temesRepository;
    private String clau_sessio;
    private int tipus_usuari = 0;
    private String nom_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        nom_user = LoginRepository.getUser().getDisplayName();
        clau_sessio = LoginRepository.getUser().getUserId();

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        setContentView(R.layout.activity_tema_list);

        temesRepository = new TemesRepository();
        temesRepository.obtenirTemes(clau_sessio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.float_add);

        if (tipus_usuari == 0) {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afegir tema nou
                if (tipus_usuari == 1) {
                    segPntalla("afegir_tema");
                }
                //Snackbar.make(view, "Acció d'usuari Admin", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.tema_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.tema_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("afegir_tema")==0) {
            Intent intent = new Intent(this, PantallaAfegirTema.class);
            Toast.makeText(getApplicationContext(), "Afegir Tema Nou", Toast.LENGTH_LONG).show();
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, TemesRepository.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TemaListActivity mParentActivity;
        private final List<TemaFlashCard> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemaFlashCard item = (TemaFlashCard) view.getTag();

                if (mTwoPane) { // si estem en mode tauleta mostrem panell lateral
                    Bundle arguments = new Bundle();
                    //arguments.putString(TemaDetailFragment.ARG_ITEM_ID, item.getCodi());
                    arguments.putString(TemaDetailFragment.ARG_ITEM_ID, item.getCodi());
                    TemaDetailFragment fragment = new TemaDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.tema_detail_container, fragment)
                            .commit();
                } else { // si estem en mode dispositiu mòbil canvi de pantalla
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TemaDetailActivity.class);
                    intent.putExtra(TemaDetailFragment.ARG_ITEM_ID, item.getCodi());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(TemaListActivity parent,
                                      List<TemaFlashCard> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tema_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getCodi());
            holder.mIdView.setGravity(Gravity.LEFT);
            holder.mContentView.setText(mValues.get(position).getNom());
            holder.mContentView.setGravity(Gravity.LEFT);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}