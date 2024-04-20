package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.ClientState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    // This are examples of view updating methods
    void notifyMessage(String message) throws RemoteException;

    void reportMessage(String message) throws RemoteException;

    void reportError(String errorMessage) throws RemoteException;

    void updateState(ClientState clientState) throws RemoteException;
}
