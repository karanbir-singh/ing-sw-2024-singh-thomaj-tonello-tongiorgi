package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;

import java.util.*;

/**
 * This class represents a table containing decks every player can interact with
 */
public class CommonTable {
    /**
     * This attribute represents the resource cards deck
     */
    private final Deck resourceDeck;
    /**
     * This attribute represents the gold cards deck
     */
    private final Deck goldDeck;
    /**
     * This attribute represents the initial cards decl
     */
    private final Deck starterDeck;
    /**
     * This attribute represents the mission cards deck
     */
    private final Deck missionDeck;
    /**
     * This attribute represents the two common mission that every player has
     */
    private final ArrayList<Card> commonMissions;
    /**
     * This attribute represents the two resource cards that are visible in the table
     */
    private final ArrayList<Card> resourceCards;
    /**
     * This attribute represents the two gold cards that are visible in the table
     */
    private final ArrayList<Card> goldCards;
    /**
     * Card selected by the player
     */
    private Card selectedCard;

    /**
     * Initializes the common table with the decks
     * @param resourceDeck resources cards deck
     * @param goldDeck gold cards deck
     * @param starterDeck initial cards deck
     * @param missionDeck mission cards deck
     */
    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck starterDeck, Deck missionDeck) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.starterDeck = starterDeck;
        this.missionDeck = missionDeck;
        selectedCard = null;
    }

    /**
     * Sets the attribute selectedCard to the given paramenter
     * @param cardSelected new card selected
     */
    public void selectCard(Card cardSelected) {
        this.selectedCard = cardSelected;
    }

    // TODO controlla se servono veramente il metodo addCard e removeCard (si può passare direttamente dagli array

    /**
     * Adds the card to the deck given in the position given
     * @param card new card to be added
     * @param deck deck where the card will be added
     * @param index position where the card will be added
     */
    public void addCard(Card card, ArrayList<Card> deck, int index) {
        deck.add(index, card);
    }

    /**
     * Removes a card from a deck and returns the card
     * @param deck deck where the card will be removed
     * @param index position where the card to be removed is currently
     * @return card removed
     */
    public Card removeCard(ArrayList<Card> deck, int index) {
        return deck.remove(index);
    }

    /**
     * Returns the card selected by the player
     * @return selected card
     */
    public Optional<Card> getSelectedCard() {
        return Optional.ofNullable(this.selectedCard);
    }

    /**
     * Returns the Resources cards visible
     * @return resource cards visible
     */
    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    /**
     * Returns the Gold cards visible
     * @return gold cards visible
     */
    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    /**
     * Return the common missions
     * @return common mission
     */
    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    /**
     * Returns the resource cards deck
     * @return resource cards deck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Returns the gold cards deck
     * @return gold cards deck
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * Returns the initial cards deck
     * @return initial cards deck
     */
    public Deck getStarterDeck() {
        return starterDeck;
    }

    /**
     * Returns the mission cards deck
     * @return mission cards deck
     */
    public Deck getMissionDeck() {
        return missionDeck;
    }
}
