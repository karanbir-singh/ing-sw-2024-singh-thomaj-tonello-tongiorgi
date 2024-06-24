package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a simplified version of a common table.
 * It provides methods to access the attributes.
 */
public class SimplifiedCommonTable implements Serializable {
    /**
     * This attribute represents the resource cards deck
     */
    private final Card resourceDeck;
    /**
     * This attribute represents the gold cards deck
     */
    private final Card goldDeck;
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
    private ArrayList<Card> goldCards;
    /**
     * Index of selected card in the common table
     */
    private int selectedIndex;

    /**
     * Constructs a SimplifiedCommonTable object with the specified parameters.
     *
     * @param resourceDeck   the resource deck card in the common table.
     * @param goldDeck       the gold deck card in the common table.
     * @param commonMissions the list of common mission cards in the common table.
     * @param resourceCards  the list of resource cards in the common table.
     * @param goldCards      the list of gold cards in the common table.
     * @param selectedIndex  the selected index value associated with the common table.
     */
    public SimplifiedCommonTable(Card resourceDeck, Card goldDeck, ArrayList<Card> commonMissions,
                                 ArrayList<Card> resourceCards, ArrayList<Card> goldCards, int selectedIndex) {
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.commonMissions = commonMissions;
        this.resourceCards = resourceCards;
        this.goldCards = goldCards;
        this.selectedIndex = selectedIndex;
    }

    /**
     * Returns the resource deck card in the common table.
     *
     * @return the resource deck card.
     */
    public Card getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Returns the gold deck card in the common table.
     *
     * @return the gold deck card.
     */
    public Card getGoldDeck() {
        return goldDeck;
    }

    /**
     * Returns the list of common mission cards in the common table.
     *
     * @return the list of common mission cards.
     */
    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    /**
     * Returns the list of resource cards in the common table.
     *
     * @return the list of resource cards.
     */
    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    /**
     * Returns the list of gold cards in the common table.
     *
     * @return the list of gold cards.
     */
    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    /**
     * Returns the selected index value associated with the common table.
     *
     * @return the selected index value.
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
}
