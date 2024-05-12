package it.polimi.ingsw.gc26.model.deck;

import it.polimi.ingsw.gc26.model.card.Card;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a deck of cards
 */
public class Deck implements Serializable {
    /**
     * This attribute represents the deck implemented as an array list
     */
    private final ArrayList<Card> cards;

    /**
     * Initializes a deck
     */
    public Deck() {
        this.cards = new ArrayList<>();
    }

    /**
     * Adds a card in the deck
     * @param card new card to be added in the deck
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the top card in the deck without removing it
     * @return the top card in the deck
     */
    public Card getTopCard() {
        return cards.getLast();
    }

    /**
     * Returns the last card of the deck and removes the card from it
     * @return top card
     */
    public Card removeCard() {
        return cards.removeLast();
    }

    /**
     * Shuffles the deck
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Return the complete deck
     * @return deck of cards
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public String[][] printableDeck(){
        return this.getTopCard().getBack().printableSide();
    }

}