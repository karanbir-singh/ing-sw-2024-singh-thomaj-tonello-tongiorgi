package it.polimi.ingsw.gc26.model;

import java.util.*;

public class StarterDeck extends Deck {
    private final int DIMENSION = 6;
    private ArrayList<StarterCard> cards;

    public void addCard(StarterCard card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public StarterCard removeCard(){
        StarterCard cardToRemove;
        if(cards.isEmpty()){
            return null;
        }

        cardToRemove = cards.get(cards.size() - 1);
        cards.remove(cardToRemove);
        return cardToRemove;
    }
}
