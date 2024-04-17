package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMainController extends Remote {
    String connect(VirtualView client, String nickName) throws RemoteException;

    void createWaitingList(VirtualView client, String clientID, String nickname, int numPlayers) throws RemoteException;

    VirtualGameController getVirtualGameController() throws RemoteException;
}
