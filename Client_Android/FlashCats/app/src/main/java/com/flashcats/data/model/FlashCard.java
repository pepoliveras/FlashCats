package com.flashcats.data.model;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/* *
 * Classe que representa i emmagatzema la informació de els FlashCards de l'aplicació
 *
 * */
public class FlashCard {

    private String codi;
    private String codi_tema;
    private String anvers_text;
    private String revers_text;
    private String anvers_multimedia;
    private String tipus_arxiu_multimedia;

    private ArrayList<String> tags;

    @Override
    public String toString() {
        return "FlashCard{" +
                "codi=" + codi +
                ", codi_tema=" + codi_tema +
                ", anvers_text='" + anvers_text + '\'' +
                ", revers_text='" + revers_text + '\'' +
                '}';
    }

    // Constructor de la Classe FlashCard amb inicialització de paràmetres
    public FlashCard(String codi_param, String codi_tema_param, String anvers_text_param, String revers_text_param) {
        this.codi = codi_param;
        this.codi_tema = codi_tema_param;
        this.anvers_text = anvers_text_param;
        this.revers_text = revers_text_param;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getCodi_tema() {
        return codi_tema;
    }

    public void setCodi_tema(String codi_tema) {
        this.codi_tema = codi_tema;
    }

    public String getAnvers_text() {
        return anvers_text;
    }

    public void setAnvers_text(String anvers_text) {
        this.anvers_text = anvers_text;
    }

    public String getRevers_text() {
        return revers_text;
    }

    public void setRevers_text(String revers_text) {
        this.revers_text = revers_text;
    }

    public String getAnvers_multimedia() {
        return anvers_multimedia;
    }

    public void setAnvers_multimedia(String anvers_multimedia) { this.anvers_multimedia = anvers_multimedia; }

    public String getTipus_arxiu_multimedia() { return tipus_arxiu_multimedia; }

    public void setTipus_arxiu_multimedia(String tipus_arxiu_multimedia) { this.tipus_arxiu_multimedia = tipus_arxiu_multimedia; }

    public ArrayList<String> getTags() { return tags; }

    public void setTags(ArrayList<String> tags) { this.tags = tags; }
}
