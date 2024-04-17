package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualMainController extends Remote {
    String connect(VirtualView client, String nickname) throws RemoteException;

    boolean existsWaitingGame() throws RemoteException;

    void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException;

    void joinWaitingList(String playerID, String playerNickname) throws RemoteException;

    // This is for testing
    VirtualGameController getVirtualGameController() throws RemoteException;
}
