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
    private Optional<Card> selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        this.missionDeck = missionDeck;
        selectedCard = Optional.empty();
    }

    public void selectCard(Card cardSelected) {
        this.selectedCard = Optional.of(cardSelected);
    }

    // TODO controlla se servono veramente il metodo addCard e removeCard (si può passare direttamente dagli array
    public void addCard(Card card, ArrayList<Card> cards, int index) {
        cards.add(index, card);
    }

    public Card removeCard(ArrayList<Card> cards, int index) {
        return cards.remove(index);
    }

    public Card getSelectedCard() throws NullPointerException {
        return this.selectedCard.orElseThrow(NullPointerException::new);
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
