package it.polimi.ingsw.gc26.model.card;

import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;

/**
 * This class creates a ResourceCard Object that represents a Resource Card
 */
public class ResourceCard extends Card implements Serializable {
    /**
     * Creates a new instance of the Resource Card
     *
     * @param front card's front side
     * @param back  card's back side
     */
    public ResourceCard(Side front, Side back) {
        super(front, back);
    }
}
