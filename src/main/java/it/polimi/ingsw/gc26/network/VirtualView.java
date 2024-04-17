package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.StateClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    // This are examples of view updating methods
    void showMessage(String message) throws RemoteException;

    void reportMessage(String message) throws RemoteException;

    void reportError(String errorMessage) throws RemoteException;

    void updateState(StateClient stateClient) throws RemoteException;
    // Then other method for updating...
}
