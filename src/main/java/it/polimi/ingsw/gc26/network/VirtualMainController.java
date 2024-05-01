package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMainController extends Remote {
    void connect(VirtualView client, String nickname) throws RemoteException;

    void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException;

    VirtualGameController getVirtualGameController() throws RemoteException;
}
