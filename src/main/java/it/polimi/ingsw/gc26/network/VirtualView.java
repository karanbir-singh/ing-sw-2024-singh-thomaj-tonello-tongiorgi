package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    // This are examples of view updating methods
    void showMessage(String message) throws RemoteException;

    void reportMessage(String message) throws RemoteException;

    void reportError(String errorMessage) throws RemoteException;

    // Then other method for updating...
}
