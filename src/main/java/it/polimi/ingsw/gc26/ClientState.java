package it.polimi.ingsw.gc26;

/**
 * Enumeration representing different states of a client in the game.
 * These states indicate various stages or conditions that a client can be in during the game.
 */
public enum ClientState {
    /**
     * Initial state when the client is establishing a connection.
     */
    CONNECTION,
    /**
     * State when the client is waiting for a game to start or for other players to join.
     */
    WAITING,
    /**
     * State when the client is the creator of the game.
     */
    CREATOR,
    /**
     * State when the game has begun.
     */
    BEGIN,
    /**
     * State indicating an invalid nickname provided by the client.
     */
    INVALID_NICKNAME,
    /**
     * State indicating an invalid number of players specified by the client.
     */
    INVALID_NUMBER_OF_PLAYER,
    /**
     * State indicating an invalid pawn selected by the client.
     */
    INVALID_PAWN
}
