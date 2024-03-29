package it.polimi.ingsw.gc26.model.card;

import it.polimi.ingsw.gc26.model.card_side.Side;

/**
 * This class creates a GoldCard Object that represents a Gold Card
 */
public class GoldCard extends Card {
    /**
     * Creates a new instance of the Gold Card
     * @param front card's front side
     * @param back card's back side
     */
    public GoldCard(Side front, Side back) {
        super(front, back);
    }
}
