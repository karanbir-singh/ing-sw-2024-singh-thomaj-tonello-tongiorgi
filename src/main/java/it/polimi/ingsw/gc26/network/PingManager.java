package it.polimi.ingsw.gc26.network;

/**
 * This interface contains methods to manage ping action
 */
public interface PingManager extends Runnable{
    /**
     * Resets timer in order to identify when the connection has been interrupted.
     */
    void reset();
}
