package it.polimi.ingsw.gc26.model.game;

import java.io.Serializable;

/**
 * This is an enumeration of the possible game's states
 */
public enum GameState implements Serializable {
    /**
     * State that indicate the common table is in preparation. Card will be loaded in the table.
     */
    COMMON_TABLE_PREPARATION,
    /**
     * State that indicate the starter cards are being distributed.
     */
    STARTER_CARDS_DISTRIBUTION,
    /**
     * State that indicates the server is waiting for the client to choose a starter card.
     */
    WAITING_STARTER_CARD_PLACEMENT,
    /**
     * State that indicates the server is waiting for the client to choose a pawn color.
     */
    WAITING_PAWNS_SELECTION,
    /**
     * State that indicate the hand is being prepared. Cards will be loaded in the hand.
     */
    HAND_PREPARATION,
    /**
     * State that indicate the common missions are being distributed.
     */
    COMMON_MISSION_PREPARATION,
    /**
     * State that indicates the secret missions are being distributed.
     */
    SECRET_MISSION_DISTRIBUTION,
    /**
     * State that indicate the server is waiting for the client to choose a secret mission.
     */
    WAITING_SECRET_MISSION_CHOICE,
    /**
     * State that indicates the first player is being extracted.
     */
    FIRST_PLAYER_EXTRACTION,
    /**
     * State that indicates the game has been started and player can play their cards.
     */
    GAME_STARTED,
    /**
     * State that indicates a player has reached 20 points. The last turn is being played.
     */
    END_STAGE,
    /**
     * State that indicates the game has ended. It shows to the client who the winners are.
     */
    WINNER
}
