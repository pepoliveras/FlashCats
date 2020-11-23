package com.flashcats.ui.masterDetail;

import android.app.Activity;
import android.os.Bundle;

import com.flashcats.R;
import com.flashcats.data.TemesRepository;
import com.flashcats.data.model.TemaFlashCard;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Tema detail screen.
 * This fragment is either contained in a {@link TemaListActivity}
 * in two-pane mode (on tablets) or a {@link TemaDetailActivity}
 * on handsets.
 */
public class TemaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static String ARG_ITEM_ID = "id_tema";

    /**
     * element Tema a mostrar
     */
    private TemaFlashCard mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TemaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = TemesRepository.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getNom());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tema_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.tema_detail)).setText(mItem.getDescripcio());
        }

        return rootView;
    }
}