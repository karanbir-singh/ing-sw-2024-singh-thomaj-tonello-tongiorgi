package it.polimi.ingsw.gc26.model.card;
import it.polimi.ingsw.gc26.model.card_side.Side;

/**
 * This class creates a Mission Card Object that represents a Mission Card
 */
public class MissionCard extends Card {
    /**
     * Create a new instance of a Mission Card
     * @param front card's front side
     * @param back card's back side
     */
    public MissionCard(Side front, Side back) {
        super(front, back);
    }
}
