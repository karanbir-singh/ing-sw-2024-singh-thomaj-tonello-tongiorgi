package it.polimi.ingsw.gc26.model;

import java.util.*;

public class Hand {
    private ArrayList<Card> cards;
    private HandState state;
    private Optional<Card> selectedCard;
    private Optional<Side> selectedSide;

    public void setState(HandState state){
        this.state = state;
    }

    public Card getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Optional<Card> selected){
        this.selectedCard = selected;
        if(selected.isEmpty()){
            this.selectedSide = null;
            return;
        }
        this.selectedSide = getFront(selectedCard);
    }
    public Optional<Side> getSelectedSide(){
        return this.selectedSide;
    }

    public void turnSide(Side selectedSide){
        /* mi servono i metodi di card */
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Side playCard(Side selectedSide, Card selectedCard){
        return state.playCard(selectedSide, selectedCard);
    }

}
