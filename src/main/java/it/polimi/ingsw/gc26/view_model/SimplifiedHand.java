package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a simplified version of a hand.
 * It provides methods to access the attributes.
 */
public class SimplifiedHand implements Serializable {
    /**
     * This attribute represent the current cards in the hand
     */
    private final ArrayList<Card> cards;
    /**
     * This attribute represents the selected card to be played
     */
    private final Card selectedCard;
    /**
     * This attribute represent the selected side of the selected card to be played
     */
    private final Side selectedSide;

    /**
     * Initializes the hand for the player
     *
     * @param cards new cards in hand
     */
    public SimplifiedHand(ArrayList<Card> cards, Card selectedCard, Side selectedSide) {
        this.cards = cards;
        this.selectedCard = selectedCard;
        this.selectedSide = selectedSide;
    }

    /**
     * Returns the list of cards available for selection.
     *
     * @return the list of cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Returns the currently selected card.
     *
     * @return the selected card.
     */
    public Card getSelectedCard() {
        return selectedCard;
    }

    /**
     * Returns the selected side of the card.
     *
     * @return the selected side of the card.
     */
    public Side getSelectedSide() {
        return selectedSide;
    }

}
