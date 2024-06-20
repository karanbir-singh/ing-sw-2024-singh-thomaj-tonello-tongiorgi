package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.view_model.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains the methods that can be called from the server to be executed on the client.
 */
public interface VirtualView extends Remote {
    /**
     * Print messages from the server to the client.
     *
     * @param message string to be displayed as information
     * @throws RemoteException if the remote method cannot be called
     */
    void showMessage(String message) throws RemoteException;

    /**
     * Notifies the clients about an error.
     *
     * @param message string to be displayed as an error
     * @throws RemoteException if the remote method cannot be called
     */
    void showError(String message) throws RemoteException;

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState new client state
     * @throws RemoteException if the remote method cannot be called
     */
    void updateClientState(ClientState clientState) throws RemoteException;

    /**
     * Sets the client's id. Used only with socket implementation.
     *
     * @param clientID new client's id
     * @throws RemoteException if the remote method cannot be called
     */
    void setClientID(String clientID) throws RemoteException;

    /**
     * Sets the game controller. Used only with socket implementation.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    void setGameController() throws RemoteException;

    /**
     * Updates the client's common table by giving a simplified version of model's common table.
     * It also displays a message with information about the update.
     *
     * @param simplifiedCommonTable updated common table
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException;

    /**
     * Updates the client's hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedHand updated hand
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException;

    /**
     * Updates the client's secret hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedSecretHand updated hand
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException;

    /**
     * Updates the client's personal board by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param personalBoard updated personal board
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException;

    /**
     * Updates the other players' personal boards by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param otherPersonalBoard updated personal board
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException;

    /**
     * Updates the client's representation of himself by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedPlayer updated player
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException;

    /**
     * Updates the game by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedGame updated game
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateGame(SimplifiedGame simplifiedGame, String message) throws RemoteException;

    /**
     * Updates the client's chat by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedChat updated chat
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException;

    /**
     * Updates the client's game id.
     * It also displays a message with the information about the update.
     *
     * @param id updated game ID
     * @throws RemoteException if the remote method cannot be called
     */
    void updateIDGame(int id) throws RemoteException;

    /**
     * Resets the timer in the client so the client knows the server is up.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    void ping() throws RemoteException;

    /**
     * Kills the client ending the game.
     *
     * @param nickname
     * @throws RemoteException if the remote method cannot be called
     */
    void killProcess(String nickname) throws RemoteException;
}
