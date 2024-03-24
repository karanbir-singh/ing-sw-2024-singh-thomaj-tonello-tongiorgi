package it.polimi.ingsw.gc26.model.deck;

import it.polimi.ingsw.gc26.model.card.Card;

import java.util.*;

public class Deck {

    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        return cards.getLast();
    }

    public Card removeCard() {
        return cards.removeLast();
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {//da togliere serve per prova
        return this.cards;
    }
}