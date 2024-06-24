package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.network.ModelObservable;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This class represents a table containing decks every player can interact with
 */
public class CommonTable implements Serializable {
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
     * Observable to notify client
     */
    private final ModelObservable observable;

    /**
     * Initializes the common table with the decks
     *
     * @param resourceDeck resources cards deck
     * @param goldDeck     gold cards deck
     * @param starterDeck  initial cards deck
     * @param missionDeck  mission cards deck
     * @param observable observable to notify client
     */
    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck starterDeck, Deck missionDeck, ModelObservable observable) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.starterDeck = starterDeck;
        this.missionDeck = missionDeck;
        selectedX = -1;
        selectedY = -1;
        this.observable = observable;
    }

    /**
     * Sets the attribute selectedX and selectedY of the chosen card to select
     *
     * @param cardIndex index of the selected card on the common table
     * @param clientID client unique ID
     */
    public void selectCard(int cardIndex, String clientID) {
        // Check if the card index are correct
        if (cardIndex < 0 || cardIndex > 6) {
            this.observable.notifyError("Position not valid!", clientID);
            return;
        }

        // Translate card position into coordinates
        switch (cardIndex) {
            case 0:
                selectedX = 0;
                selectedY = 0;
                break;
            case 1:
                selectedX = 1;
                selectedY = 0;
                break;
            case 2:
                selectedX = 2;
                selectedY = 0;
                break;
            case 3:
                selectedX = 0;
                selectedY = 1;
                break;
            case 4:
                selectedX = 1;
                selectedY = 1;
                break;
            case 5:
                selectedX = 2;
                selectedY = 1;
                break;
            default:
                this.observable.notifyError("Select a position!", clientID);
                return;
        }
        this.observable.notifyUpdateCommonTable(new SimplifiedCommonTable(resourceDeck.getTopCard(), goldDeck.getTopCard(), commonMissions, resourceCards, goldCards, cardIndex), "Card selected on common table", clientID);
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
        this.observable.notifyUpdateCommonTable(new SimplifiedCommonTable(this.resourceDeck.getTopCard(), this.goldDeck.getTopCard(), this.commonMissions, this.resourceCards, this.goldCards, -1), "Common Table updated!");
    }

    /**
     * Removes the card on the given index from the list and replaced by a card from the given deck
     *
     * @param list  list of cards
     * @param index position of the card to remove
     * @param deck  deck containing the card that replaces the old one
     * @return removed card
     */
    private Card removeFromTable(ArrayList<Card> list, int index, Deck deck, String clientID) {
        if (list.get(index) == null) {
            this.observable.notifyError("Position not valid!", clientID);
            return null;
        }
        Card toRemove = null;
        if (!deck.getCards().isEmpty())
            toRemove = list.set(index, deck.removeCard());
        else
            toRemove = list.set(index, null);
        return toRemove;
    }

    /**
     * Removes the selected card from the table and returns it
     *
     * @param clientID client unique ID
     * @return removed card
     */
    public Card removeSelectedCard(String clientID) {
        if (!getSelectedCard().isPresent()) {
            this.observable.notifyError("Select a position first!", clientID);
            return null;
        }
        Card toRemove = null;
        if (selectedY == 0) {
            if (selectedX == 2) {
                if (!resourceDeck.getCards().isEmpty())
                    toRemove = resourceDeck.removeCard();
                else {
                    // TODO gestire quando il mazzo è finito
                }
            } else {
                toRemove = removeFromTable(resourceCards, selectedX, resourceDeck, clientID);
            }
        } else if (selectedY == 1) {
            if (selectedX == 2) {
                if (!goldDeck.getCards().isEmpty())
                    toRemove = goldDeck.removeCard();
                else {
                    // TODO gestire quando il mazzo è finito
                }
            } else {
                toRemove = removeFromTable(goldCards, selectedX, goldDeck, clientID);
            }
        }
        selectedX = -1;
        selectedY = -1;

        this.observable.notifyUpdateCommonTable(
                new SimplifiedCommonTable(
                        resourceDeck.getTopCard(),
                        goldDeck.getTopCard(),
                        commonMissions,
                        resourceCards,
                        goldCards,
                        -1),
                "Card removed from common table"
        );
        return toRemove;
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
                selectedCard = resourceCards.get(0);
            } else if (selectedX == 1) {
                selectedCard = resourceCards.get(1);
            } else if (selectedX == 2) {
                selectedCard = resourceDeck.getTopCard();
            }
        } else if (selectedY == 1) {
            if (selectedX == 0) {
                selectedCard = goldCards.get(0);
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
}
