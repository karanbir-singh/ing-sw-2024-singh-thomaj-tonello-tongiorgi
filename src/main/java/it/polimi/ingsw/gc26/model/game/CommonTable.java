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
     * This attribute represents the two resource cards that are visible on the table
     */
    private final ArrayList<Card> resourceCards;
    /**
     * This attribute represents the two gold cards that are visible on the table
     */
    private final ArrayList<Card> goldCards;
    /**
     * Coordinate X of the selected card on the table
     */
    private int selectedX;
    /**
     * Coordinate Y of the selected card on the table
     */
    private int selectedY;

    /**
     * Initializes the common table with the decks
     *
     * @param resourceDeck resources cards deck
     * @param goldDeck     gold cards deck
     * @param starterDeck  initial cards deck
     * @param missionDeck  mission cards deck
     */
    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck starterDeck, Deck missionDeck) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.starterDeck = starterDeck;
        this.missionDeck = missionDeck;
        selectedX = -1;
        selectedY = -1;
    }

    /**
     * Sets the attribute selectedX and selectedY of the chosen card to select
     *
     * @param selectedX coordinate X of the selected card
     * @param selectedY coordinate Y of the selected card
     */
    public void selectCard(int selectedX, int selectedY) {
        // Check if the selectedX and selectedY are correct
        if (selectedY >= 0 && selectedY < 2) {
            if (selectedX >= 0 && selectedX < 3) {
                this.selectedX = selectedX;
                this.selectedY = selectedY;
            } else {
                // TODO gestire quando la posizione X non è corretta
            }
        } else {
            // TODO gestire quando la posizione Y non è corretta
        }
    }

    /**
     * Adds the card to the given list and given index
     *
     * @param card  new card to be added
     * @param list  list where the card will be added
     * @param index position where the card will be added
     */
    public void addCard(Card card, ArrayList<Card> list, int index) {
        list.add(index, card);
    }

    /**
     * Removes the card on the given index from the list and replaced by a card from the given deck
     *
     * @param list  list of cards
     * @param index position of the card to remove
     * @param deck  deck containing the card that replaces the old one
     * @return removed card
     */
    private Card removeFromTable(ArrayList<Card> list, int index, Deck deck) {
        Card toRemove = null;
        if (list.get(index) != null) {
            if (!deck.getCards().isEmpty())
                toRemove = list.set(index, deck.removeCard());
            else
                toRemove = list.set(index, null);
        } else {
            // TODO gestire quando la posizione selezionata non contiene una carta
        }
        return toRemove;
    }

    /**
     * Removes the selected card from the table and returns it
     *
     * @return removed card
     */
    public Card removeSelectedCard() {
        if(getSelectedCard().isPresent()){
            Card toRemove = null;
            if (selectedY == 0) {
                if (selectedX == 2) {
                    if (!resourceDeck.getCards().isEmpty())
                        toRemove = resourceDeck.removeCard();
                    else {
                        // TODO gestire quando il mazzo è finito
                    }
                } else {
                    toRemove = removeFromTable(resourceCards, selectedX, resourceDeck);
                }
            } else if (selectedY == 1) {
                if (selectedX == 2) {
                    if (!goldDeck.getCards().isEmpty())
                        toRemove = goldDeck.removeCard();
                    else {
                        // TODO gestire quando il mazzo è finito
                    }
                } else {
                    toRemove = removeFromTable(goldCards, selectedX, goldDeck);
                }
            }
            selectedX = -1;
            selectedY = -1;
            return toRemove;
        }else{
            // TODO notify view
            return null;
        }

    }

    /**
     * Returns the card selected by the player
     *
     * @return selected card
     */
    public Optional<Card> getSelectedCard() {
        Card selectedCard = null;
        if (selectedY == 0) {
            if (selectedX == 0) {
                selectedCard = resourceCards.getFirst();
            } else if (selectedX == 1) {
                selectedCard = resourceCards.get(1);
            } else if (selectedX == 2) {
                selectedCard = resourceDeck.getTopCard();
            }
        } else if (selectedY == 1) {
            if (selectedX == 0) {
                selectedCard = goldCards.getFirst();
            } else if (selectedX == 1) {
                selectedCard = goldCards.get(1);
            } else if (selectedX == 2) {
                selectedCard = goldDeck.getTopCard();
            }
        }
        return Optional.ofNullable(selectedCard);
    }

    /**
     * Returns the Resources cards visible
     *
     * @return resource cards visible
     */
    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    /**
     * Returns the Gold cards visible
     *
     * @return gold cards visible
     */
    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    /**
     * Return the common missions
     *
     * @return common mission
     */
    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    /**
     * Returns the resource cards deck
     *
     * @return resource cards deck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Returns the gold cards deck
     *
     * @return gold cards deck
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * Returns the initial cards deck
     *
     * @return initial cards deck
     */
    public Deck getStarterDeck() {
        return starterDeck;
    }

    /**
     * Returns the mission cards deck
     *
     * @return mission cards deck
     */
    public Deck getMissionDeck() {
        return missionDeck;
    }

    /**
     * Returns the selected card's X coordinate
     *
     * @return selectedX
     */
    public int getSelectedX() {
        return selectedX;
    }

    /**
     * Returns the selected card's Y coordinate
     *
     * @return selectedY
     */
    public int getSelectedY() {
        return selectedY;
    }
}
