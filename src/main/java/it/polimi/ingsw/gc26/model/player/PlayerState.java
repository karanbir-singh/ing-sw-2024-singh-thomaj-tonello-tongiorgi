package it.polimi.ingsw.gc26.model.player;

import java.io.Serializable;

/**
 * This enumeration represents the player states.
 */
public enum PlayerState implements Serializable {
    /**
     * This state represents the player has to play its card on the board.
     */
    PLAYING,
    /**
     * This state represents the player has played its card. The next step is to draw a card.
     */
    CARD_PLAYED,
    /**
     * This state represents the client has drawn a card.
     */
    CARD_DRAWN
}
