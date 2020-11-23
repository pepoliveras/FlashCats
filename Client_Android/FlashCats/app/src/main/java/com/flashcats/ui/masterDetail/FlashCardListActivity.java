package com.flashcats.ui.masterDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.flashcats.data.FlashCardsRepository;
import com.flashcats.data.LoginRepository;
import com.flashcats.data.TemesRepository;
import com.flashcats.data.model.FlashCard;
import com.flashcats.data.model.TemaFlashCard;
import com.flashcats.ui.pantalles.admin.PantallaAfegirFlashCard;
import com.flashcats.ui.pantalles.admin.PantallaAfegirTema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flashcats.R;

import java.util.List;

/**
 * An activity representing a list of FlashCards. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FlashCardDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FlashCardListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private FlashCardsRepository flashCardsRepository;
    private TemesRepository temesRepository;

    private String clau_sessio;
    private int tipus_usuari = 0;
    private String codi_tema = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_list);

        // Recuperem l'intent que ens ha cridat i extraiem els paràmetres que ens ha passat
        Bundle params = this.getIntent().getExtras();
        if(params !=null) {
            codi_tema = params.getString("param1");
        } else {
            //en cas de no trobar paràmetres prenem el darrer accés al repositori com a referència
            codi_tema = FlashCardsRepository.codi_tema;
        }

        clau_sessio = LoginRepository.getUser().getUserId();

        if (clau_sessio.startsWith("0")) {
            tipus_usuari = 0; // perfil usuari
        } else if (clau_sessio.startsWith("1")) {
            tipus_usuari = 1; // perfil admin
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        flashCardsRepository = new FlashCardsRepository();
        flashCardsRepository.obtenirFlashCards(clau_sessio,codi_tema);
        temesRepository = new TemesRepository();

        // Mostrem fletxa de retorn a la action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TemaFlashCard tema_sup = temesRepository.ITEM_MAP.get(codi_tema);
        actionBar.setTitle(tema_sup.getNom());

        // I definim que al clicar tornem al llistat de Temes
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TemaListActivity.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.float_add_flashcard);

        if (tipus_usuari == 0) {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //afegir tema nou
                if (tipus_usuari == 1) {
                    segPntalla("afegir_flashcard");
                }
            }
        });

        if (findViewById(R.id.flashcard_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.flashcard_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void segPntalla(String action) {

        Bundle extras = new Bundle();

        if (action.compareTo("afegir_flashcard")==0) {
            //passar codi_tema
            Toast.makeText(getApplicationContext(), "Afegir Nova Flashcard", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PantallaAfegirFlashCard.class);
            // passem a la següent pantalla codi del tema seleccionat
            extras.putString("param1", codi_tema);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, flashCardsRepository.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final FlashCardListActivity mParentActivity;
        private final List<FlashCard> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlashCard item = (FlashCard) view.getTag();
                if (mTwoPane) { // si estem en mode tauleta mostrem panell lateral
                    Bundle arguments = new Bundle();
                    arguments.putString(FlashCardDetailFragment.ARG_ITEM_ID, item.getCodi());
                    FlashCardDetailFragment fragment = new FlashCardDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flashcard_detail_container, fragment)
                            .commit();
                } else { // si estem en mode dispositiu mòbil fem canvi de pantalla
                    Context context = view.getContext();
                    Intent intent = new Intent(context, FlashCardDetailActivity.class);
                    intent.putExtra(FlashCardDetailFragment.ARG_ITEM_ID, item.getCodi());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(FlashCardListActivity parent,
                                      List<FlashCard> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.flashcard_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getCodi());
            holder.mContentView.setText(mValues.get(position).getAnvers_text());

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