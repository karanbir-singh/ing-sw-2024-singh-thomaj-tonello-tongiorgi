package it.polimi.ingsw.gc26.model.game;

import java.io.Serializable;

/**
 * This is an enumeration of the possible game's states
 */
public enum GameState implements Serializable {
    COMMON_TABLE_PREPARATION,
    STARTER_CARDS_DISTRIBUTION,
    WAITING_STARTER_CARD_PLACEMENT,
    WAITING_PAWNS_SELECTION,
    HAND_PREPARATION,
    COMMON_MISSION_PREPARATION,
    SECRET_MISSION_DISTRIBUTION,
    WAITING_SECRET_MISSION_CHOICE,
    FIRST_PLAYER_EXTRACTION,
    GAME_STARTED,
    END_STAGE
}
