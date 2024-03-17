package it.polimi.ingsw.gc26.model;

import java.util.*;

public class MissionDeck extends Deck {
    private final int DIMENSION=16;
    private ArrayList<MissionCard> cards;

    public void addCard(MissionCard card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public MissionCard removeCard(){
        MissionCard cardToRemove;
        if(cards.isEmpty()){
            return null;
        }

        cardToRemove = cards.get(cards.size() - 1);
        cards.remove(cardToRemove);
        return cardToRemove;
    }
}
