package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;

public class SimplifiedCommonTable implements Serializable {
    /**
     * This attribute represents the resource cards deck
     */
    private Card resourceDeck;
    /**
     * This attribute represents the gold cards deck
     */
    private Card goldDeck;
    /**
     * This attribute represents the two common mission that every player has
     */
    private ArrayList<Card> commonMissions;
    /**
     * This attribute represents the two resource cards that are visible on the table
     */
    private ArrayList<Card> resourceCards;
    /**
     * This attribute represents the two gold cards that are visible on the table
     */
    private ArrayList<Card> goldCards;
    private int selectedIndex;

    public SimplifiedCommonTable(Card resourceDeck, Card goldDeck, ArrayList<Card> commonMissions, ArrayList<Card> resourceCards, ArrayList<Card> goldCards, int selectedIndex) {
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.commonMissions = commonMissions;
        this.resourceCards = resourceCards;
        this.goldCards = goldCards;
        this.selectedIndex = selectedIndex;
    }

    public Card getResourceDeck() {
        return resourceDeck;
    }

    public Card getGoldDeck() {
        return goldDeck;
    }

    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    public int getSelectedIndex() { return selectedIndex;}

}
