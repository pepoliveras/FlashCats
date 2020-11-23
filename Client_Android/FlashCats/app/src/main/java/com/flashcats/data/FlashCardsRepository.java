package com.flashcats.data;

import android.os.AsyncTask;

import com.flashcats.data.model.FlashCard;
import com.flashcats.data.model.TemaFlashCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* *
 * Classe que recull el conjunt de temes i els mètodes per a tractar-los dins de l'aplicació.
 * Crida a Temes DataSource per omplir els continguts dels mateixos.
 * */

public class FlashCardsRepository {

    private FlashCardsDataSource flashCardsDataSource;
    private int num_flashcards = 0;
    public static String codi_tema;

    //Repositori de Temes
    public static List<FlashCard> ITEMS = new ArrayList<FlashCard>();
    public static Map<String, FlashCard> ITEM_MAP = new HashMap<String, FlashCard>();

    //en el constructor inicialitzem flashCardsDataSource
    public FlashCardsRepository(){
        flashCardsDataSource = new FlashCardsDataSource();
    }

    //omplim el repositori de flashcards
    public void obtenirFlashCards(String codi_sessio,String codi_tema){
        try {
            obtenir_numflashcards(codi_sessio,codi_tema);
            new FlashCardsRepository.obtenirFlashCards_async().execute(codi_sessio,codi_tema,Integer.toString(num_flashcards)).get();
            Iterator<FlashCard> llista = ITEMS.iterator();
            while (llista.hasNext()){
                FlashCard flashcard = llista.next();
                ITEM_MAP.put(flashcard.getCodi(), flashcard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reservem possibilitat d'afegir externament una Flashcard a l'array del repositori (utilitat auxiliar)
    private static void addFlashCard(FlashCard item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getCodi(), item);
    }

    // retorna
    // -1 error permisos o petició,
    // -2 error flashcard ja existent,
    // -3 error SQL
    public int altaFlashCard(String codi_sessio,String codi_tema,String anvers_text, String revers_text){
        int result = -1;
        try {
            result = new FlashCardsRepository.altaFlashCard_async().execute(codi_sessio,codi_tema,anvers_text,revers_text).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // retorna
    // -1 error permisos o petició,
    // -3 error SQL
    public int obtenir_numflashcards(String codi_sessio,String codi_tema){
        try {
            this.codi_tema = codi_tema;
            num_flashcards = new FlashCardsRepository.obtenir_numflashcards_async().execute(codi_sessio,codi_tema).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num_flashcards;
    }

    // retorna:
    //  1 s'ha modificat correctament
    // -1 error permisos o petició,
    // -2 error flashcard ja existent,
    // -3 error SQL
    public int modificaFlashCard(String codi_sessio,String codi_flashcard,String anvers_text, String revers_text){
        int result = -1;
        try {
            result = new FlashCardsRepository.modificaFlashCard_async().execute(codi_sessio,codi_flashcard,anvers_text,revers_text).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // retorna:
    //  1 s'ha eliminat correctament
    // -1 error permisos o petició,
    // -2 error flashcard no existeix,
    // -3 error SQL
    public int eliminaFlashCard(String codi_sessio,String codi_flashcard){
        int result = -1;
        try {
            result = new FlashCardsRepository.eliminaFlashCard_async().execute(codi_sessio,codi_flashcard).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /******************************************
    *
    * A poartir d'aquí les crides asíncrones
    *
    *******************************************/

    private class obtenirFlashCards_async extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            ITEMS = flashCardsDataSource.obtenirLlistatFlashCards(params[0],params[1],params[2]);
            return null;
        }
    }

    private class altaFlashCard_async extends AsyncTask<String, Void, Integer > {
        protected Integer doInBackground(String... params) {
            return flashCardsDataSource.altaFlashCard(params[0],params[1],params[2],params[3]);
        }
    }

    private class obtenir_numflashcards_async extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... params) {
            return flashCardsDataSource.numFlashCards(params[0],params[1]);
        }
    }

    private class modificaFlashCard_async extends AsyncTask<String, Void, Integer > {

        protected Integer doInBackground(String... params) {

            return flashCardsDataSource.modificaFlashCard(params[0],params[1],params[2],params[3]);
        }
    }

    private class eliminaFlashCard_async extends AsyncTask<String, Void, Integer > {

        protected Integer doInBackground(String... params) {

            return flashCardsDataSource.eliminaFlashCard(params[0],params[1]);
        }
    }
}
