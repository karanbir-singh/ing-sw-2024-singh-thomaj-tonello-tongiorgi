package it.polimi.ingsw.gc26.model.card;
import it.polimi.ingsw.gc26.model.card_side.Side;

/**
 * This class represents a generic card, containing two sides.
 */
abstract public class Card {
    /**
     * This attribute is the Card's front side (generic)
     */
    private Side front;
    /**
     * This attribute is the Card's back side (generic)
     */
    private Side back;

    /**
     * Initializes card with front and back.
     * @param front card's front side.
     * @param back card's back side.
     */
    public Card(Side front, Side back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Returns the card's front reference.
     * @return Card.front.
     */
    public Side getFront() {
        return front;
    }

    /**
     * Sets a new front side of the card instance.
     * @param front new Side object representing card's front.
     */
    public void setFront(Side front) {
        this.front = front;
    }

    /**
     * Returs the card's back reference.
     * @return card.back
     */
    public Side getBack() {
        return back;
    }

    /**
     * Sets a new back for the card.
     * @param back new Side Object representing the card's back
     */
    public void setBack(Side back) {
        this.back = back;
    }
}
