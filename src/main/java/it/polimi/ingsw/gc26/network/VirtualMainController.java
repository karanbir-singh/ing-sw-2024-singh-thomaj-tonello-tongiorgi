package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.ClientState;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains the method that can be called from the client.
 * It allows the client to connect to the server and to create a new game.
 * Finally, it delegates the control to a game controller.
 */
public interface VirtualMainController extends Remote {
    /**
     * Joins the new client to an existing game if there's any game with available player,
     * otherwise it set the client's state to creator.
     *
     * @param client Virtual view representing the client
     * @param nickname client's nickname
     * @param clientState client's current state
     * @throws RemoteException if the remote method cannot be called
     */
    void connect(VirtualView client, String nickname, ClientState clientState) throws RemoteException;

    /**
     * Initializes the waiting list of players and updating max numbers of players for the next game
     *
     * @param client     Virtual view representing the client
     * @param numPlayers number of players of the next game
     * @param nickname   nickname of the player who is initializing the waiting list
     * @throws RemoteException if the remote method cannot be called
     */
    void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException;

    /**
     * Returns the game controller if exists.
     *
     * @param id unique identifier for the game
     * @return Returns the right game controller based on the id
     * @throws RemoteException if the remote method cannot be called
     */
    VirtualGameController getVirtualGameController(int id) throws RemoteException;
    void resetServerTimer(String clientID) throws RemoteException;

}
