package com.flashcats.data.model;

import java.util.ArrayList;

/* *
* Classe que representa i emmagatzema la informació dels Temes de l'aplicació
*
* */
public class TemaFlashCard {

    private String codi;
    private String nom;
    private String descripcio;

    private ArrayList<FlashCard> llistatFlashCards;

    public TemaFlashCard(String codi, String nom, String descripcio) {
        this.codi = codi;
        this.nom = nom;
        this.descripcio = descripcio;
        llistatFlashCards = new ArrayList<FlashCard>();
    }

    @Override
    public String toString() {
        return "TemaFlashCard{" +
                "codi=" + codi +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", llistatFlashCards=" + llistatFlashCards +
                '}';
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public ArrayList<FlashCard> getLlistatFlashCards() {
        return llistatFlashCards;
    }

    public void setLlistatFlashCards(ArrayList<FlashCard> llistatFlashCards) {
        this.llistatFlashCards = llistatFlashCards;
    }

}
