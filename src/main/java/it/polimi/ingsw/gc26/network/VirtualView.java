package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.client.ClientState;
import it.polimi.ingsw.gc26.view_model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @throws RemoteException
     */
    void showMessage(String message) throws RemoteException;

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @throws RemoteException
     */
    void showError(String message) throws RemoteException;

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState
     * @throws RemoteException
     */
    void updateClientState(ClientState clientState) throws RemoteException;

    /**
     * Used only with socket implementation
     *
     * @param clientID
     * @throws RemoteException
     */
    void setClientID(String clientID) throws RemoteException;

    /**
     * Used only with socket implementation
     *
     * @throws RemoteException
     */
    void setGameController() throws RemoteException;

    void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException;

    void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException;

    void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException;

    void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException;

    void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException;

    void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException;

//    void updateOptionsMenu(OptionsMenu optionsMenu, String message) throws RemoteException;

    void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException;

    void updateIDGame(int id) throws RemoteException;

    void isClientAlive() throws RemoteException;

    void killProcess() throws RemoteException;
}
