package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class ResourceDeck extends Deck {
    private final int DIMENSION = 40;
    private ArrayList<ResourceCard> cards;

    public void addCard(ResourceCard card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public ResourceCard drawCard(){
        ResourceCard drawnCard;
        if(cards.isEmpty()){
            return null;
        }

        drawnCard = cards.get(cards.size() - 1);
        cards.remove(drawnCard);
        return drawnCard;
    }
}
