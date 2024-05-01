package it.polimi.ingsw.gc26.model.card;

import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;

/**
 * This class creates a GoldCard Object that represents a Gold Card
 */
public class GoldCard extends Card implements Serializable {
    /**
     * Creates a new instance of the Gold Card
     * @param front card's front side
     * @param back card's back side
     */
    public GoldCard(Side front, Side back) {
        super(front, back);
    }
}
