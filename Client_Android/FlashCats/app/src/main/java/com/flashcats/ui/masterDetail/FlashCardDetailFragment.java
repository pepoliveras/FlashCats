package com.flashcats.ui.masterDetail;

import android.app.Activity;
import android.os.Bundle;

import com.flashcats.data.FlashCardsRepository;
import com.flashcats.data.model.FlashCard;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flashcats.R;

/**
 * A fragment representing a single FlashCard detail screen.
 * This fragment is either contained in a {@link FlashCardListActivity}
 * in two-pane mode (on tablets) or a {@link FlashCardDetailActivity}
 * on handsets.
 */
public class FlashCardDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private FlashCard mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FlashCardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = FlashCardsRepository.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getAnvers_text());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flashcard_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.flashcard_detail)).setText(mItem.getRevers_text());
            //TODO aquí mostrem també la imatge o vídeo de la Flashcard si s'escau
        }

        return rootView;
    }
}