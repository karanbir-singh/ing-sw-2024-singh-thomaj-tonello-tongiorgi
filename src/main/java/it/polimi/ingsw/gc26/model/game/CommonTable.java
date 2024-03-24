package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;

import java.util.*;

public class CommonTable {
    private final Deck resourceDeck;
    private final Deck goldDeck;
    private final Deck initialDeck;
    private final Deck missionDeck;
    private final ArrayList<Card> commonMissions;
    private final ArrayList<Card> resourceCards;
    private final ArrayList<Card> goldCards;
    private Card selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        this.missionDeck = missionDeck;
        selectedCard = null;
    }

    public void selectCard(Card cardSelected) {
        this.selectedCard = cardSelected;
    }

    // TODO controlla se servono veramente il metodo addCard e removeCard (si può passare direttamente dagli array
    public void addCard(Card card, ArrayList<Card> cards, int index) {
        cards.add(index, card);
    }

    public Card removeCard(ArrayList<Card> cards, int index) {
        return cards.remove(index);
    }

    public Optional<Card> getSelectedCard() {
        return Optional.ofNullable(this.selectedCard);
    }

    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    public ArrayList<Card> getCommonMissions() {
        return new ArrayList<>(commonMissions);
    }

    public Deck getResourceDeck() {
        return resourceDeck;
    }


    public Deck getGoldDeck() {
        return goldDeck;
    }

    public Deck getInitialDeck() {
        return initialDeck;
    }

    public Deck getMissionDeck() {
        return missionDeck;
    }
}
