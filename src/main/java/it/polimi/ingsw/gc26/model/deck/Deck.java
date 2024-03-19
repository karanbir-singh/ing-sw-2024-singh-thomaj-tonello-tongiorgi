package it.polimi.ingsw.gc26.model.deck;

import it.polimi.ingsw.gc26.model.card.Card;

import java.util.*;

public abstract class Deck {

    private ArrayList<Card> cards;

    public Deck(ArrayList<Card> c) {
        this.cards = c;
        this.shuffleDeck();
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

        card = cards.get(cards.size() - 1);
        cards.remove(card);
        return card;
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }
}