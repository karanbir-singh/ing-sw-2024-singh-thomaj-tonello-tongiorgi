package it.polimi.ingsw.gc26.model.card;

import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;

/**
 * This class represents a generic card, containing two sides.
 */
abstract public class Card implements Serializable {
    /**
     * This attribute is the Card's front side (generic)
     */
    private final Side front;
    /**
     * This attribute is the Card's back side (generic)
     */
    private final Side back;

    /**
     * Initializes card with front and back.
     *
     * @param front card's front side.
     * @param back  card's back side.
     */
    public Card(Side front, Side back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Returns the card's front reference.
     *
     * @return Card.front.
     */
    public Side getFront() {
        return front;
    }

    /**
     * Returns the card's back reference.
     *
     * @return card.back
     */
    public Side getBack() {
        return back;
    }
}
