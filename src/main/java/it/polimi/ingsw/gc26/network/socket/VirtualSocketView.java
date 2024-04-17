package it.polimi.ingsw.gc26.network.socket;

import it.polimi.ingsw.gc26.StateClient;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;

public class VirtualSocketView implements VirtualView {

    // This are examples of view updating methods
    @Override
    public void showMessage(String message) throws RemoteException {

    }

    @Override
    public void reportMessage(String message) throws RemoteException {

    }

    @Override
    public void reportError(String errorMessage) throws RemoteException {

    }
    @Override
    public void updateState(StateClient stateClient) throws RemoteException{

    }
}
