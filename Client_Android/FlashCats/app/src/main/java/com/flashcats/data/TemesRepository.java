package com.flashcats.data;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

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

public class TemesRepository {

    private TemesDataSource temesDataSource;
    private int num_temes = 0;

    //Repositori de Temes
    public static List<TemaFlashCard> ITEMS = new ArrayList<TemaFlashCard>();
    public static Map<String, TemaFlashCard> ITEM_MAP = new HashMap<String, TemaFlashCard>();

    //en el constructor inicialitzem temesDataSource
    public TemesRepository(){
        temesDataSource = new TemesDataSource();
    }

    //omplim el repositori de temes 
    public void obtenirTemes(String codi_sessio){
        try {
            numTemes(codi_sessio);
            new TemesRepository.obtenirTemes_async().execute(codi_sessio,Integer.toString(num_temes)).get();
            Iterator<TemaFlashCard> llista = ITEMS.iterator();
            while (llista.hasNext()){
                TemaFlashCard tema = llista.next();
                ITEM_MAP.put(tema.getCodi(), tema);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTema(TemaFlashCard item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getCodi(), item);
    }

    // retorna
    // -1 error permisos o petició,
    // -2 error tema ja existent,
    // -3 error SQL
    public int altaTema(String codi_sessio,String nomTema, String descTema){
        int result = -1;
        try {
            result = new TemesRepository.altaTema_async().execute(codi_sessio,nomTema,descTema).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // retorna
    // -1 error permisos o petició,
    // -3 error SQL
    public int numTemes(String codi_sessio){
        try {
            num_temes = new TemesRepository.numTemes_async().execute(codi_sessio).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num_temes;
    }

    // retorna:
    //  1 s'ha modificat correctament
    // -1 error permisos o petició,
    // -2 error tema ja existent,
    // -3 error SQL
    public int modificaTema(String codi_sessio,String codiTema, String nomTema, String descTema){
        int result = -1;
        try {
            result = new TemesRepository.modificaTema_async().execute(codi_sessio,codiTema,nomTema,descTema).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // retorna:
    //  1 s'ha eliminat correctament
    // -1 error permisos o petició,
    // -2 error tema no existeix,
    // -3 error SQL
    public int eliminaTema(String codi_sessio,String codiTema){
        int result = -1;
        try {
            result = new TemesRepository.eliminaTema_async().execute(codi_sessio,codiTema).get();
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

    private class obtenirTemes_async extends AsyncTask<String, Void, Void> {

        protected Void doInBackground(String... params) {

            ITEMS = temesDataSource.obtenirLlistatTemes(params[0],params[1]);
            return null;
        }
    }

    private class altaTema_async extends AsyncTask<String, Void, Integer > {

        protected Integer doInBackground(String... params) {

            return temesDataSource.altaTema(params[0],params[1],params[2]);
        }
    }

    private class numTemes_async extends AsyncTask<String, Void, Integer> {

        protected Integer doInBackground(String... params) {

            return temesDataSource.numTemes(params[0]);
        }
    }

    private class modificaTema_async extends AsyncTask<String, Void, Integer > {

        protected Integer doInBackground(String... params) {

            return temesDataSource.modificaTema(params[0],params[1],params[2],params[3]);
        }
    }

    private class eliminaTema_async extends AsyncTask<String, Void, Integer > {

        protected Integer doInBackground(String... params) {

            return temesDataSource.eliminaTema(params[0],params[1]);
        }
    }
}
