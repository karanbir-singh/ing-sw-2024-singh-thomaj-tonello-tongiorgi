package it.polimi.ingsw.gc26.model.card;
import it.polimi.ingsw.gc26.model.card_side.Side;

/**
 * This class creates a StarterCard Object that represents a Starter Card
 */
public class StarterCard extends Card {
    /**
     * Creates a new instance of the Starter Card
     * @param front card's front side
     * @param back card's back side
     */
    public StarterCard(Side front, Side back) {
        super(front, back);
    }
}
