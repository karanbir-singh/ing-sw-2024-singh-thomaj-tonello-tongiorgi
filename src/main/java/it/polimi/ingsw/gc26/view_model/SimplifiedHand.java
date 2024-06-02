package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Side getSelectedSide() {
        return selectedSide;
    }

}
