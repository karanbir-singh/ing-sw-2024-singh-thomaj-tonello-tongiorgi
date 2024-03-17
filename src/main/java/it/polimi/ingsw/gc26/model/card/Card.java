package it.polimi.ingsw.gc26.model.card;

import it.polimi.ingsw.gc26.model.card_side.Side;

abstract public class Card {
    private Side front;
    private Side back;

    public Card(Side front, Side back) {
        this.front = front;
        this.back = back;
    }

    public Side getFront() {
        return front;
    }

    public void setFront(Side front) {
        this.front = front;
    }

    public Side getBack() {
        return back;
    }

    public void setBack(Side back) {
        this.back = back;
    }

    // TODO serve ridefinire la equals
}
