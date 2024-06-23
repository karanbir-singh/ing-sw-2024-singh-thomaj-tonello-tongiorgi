package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.MainClient;

import java.rmi.RemoteException;

/**
 * This interface contains methods to be called regarding the interface chose by the client.
 * These are used to connect the client to the server and run the game
 */
public interface UIInterface {
    /**
     * Initializes the application with the specified network type.
     *
     * @param networkType The type of network to initialize.
     */
    void init(MainClient.NetworkType networkType);
    /**
     * Connects the client to the server and sets game controller
     *
     * @throws RemoteException if the network is now working
     */
    void runConnection() throws RemoteException;
    /**
     * Start with game interface
     */
    void runGame() throws RemoteException;
}
