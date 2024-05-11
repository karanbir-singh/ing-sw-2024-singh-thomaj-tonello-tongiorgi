package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.ClientState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMainController extends Remote {
    void connect(VirtualView client, String nickname, ClientState clientState) throws RemoteException;

    void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException;

    VirtualGameController getVirtualGameController(int id) throws RemoteException;

    void amAlive() throws RemoteException;

}
