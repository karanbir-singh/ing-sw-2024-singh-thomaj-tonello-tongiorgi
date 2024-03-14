package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private Card selectedCard;
    private Side selectedSide;


    public Card getSelectedCard(){
        return this.selectedCard;
    }

    /* setSelectedCard might be called by the controller */
    public void setSelectedCard(Card selected){
        this.selectedCard = selected;
    }

    public Side getSelectedSide(){
        return this.selectedSide;
    }
    public void turnSide(Side selectedSide){}
    public Card removeCard(Card selectedCard){}

    public void addCard(Card new){}
}
