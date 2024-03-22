package it.polimi.ingsw.gc26.model.deck;

import it.polimi.ingsw.gc26.model.card.Card;

import java.util.*;

public class Deck {

    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    /* the last card of the Deck is saved in drawnCard, then removed from the Deck and return to the caller */
    public Card getTopCard(){
        if(cards.isEmpty()){
            return null;
        }
        return cards.get(cards.size() - 1);
    }
    public Card removeCard(){
        Card card;
        if(cards.isEmpty()){
            return null;
        }
        return cards.removeLast();
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }
}