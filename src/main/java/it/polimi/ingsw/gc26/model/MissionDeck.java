package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class MissionDeck extends Deck {
    private final int DIMENSION=16;
    private ArrayList<MissionCard> cards;

    public void addCard(MissionCard card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public MissionCard drawCard(){
        MissionCard drawnCard;
        if(cards.isEmpty()){
            return null;
        }

        drawnCard = cards.get(cards.size() - 1);
        cards.remove(drawnCard);
        return drawnCard;
    }
}
