package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class GoldDeck extends Deck {
    private final int DIMENSION = 40;
    private ArrayList<GoldCard> cards;

    public void addCard(GoldCard card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public GoldCard drawCard(){
        GoldCard drawnCard;
        if(cards.isEmpty()){
            return null;
        }

        drawnCard = cards.get(cards.size() - 1);
        cards.remove(drawnCard);
        return drawnCard;
    }
}
