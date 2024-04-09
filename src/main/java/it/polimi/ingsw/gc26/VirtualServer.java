package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    //waiting phase

    String connect(VirtualView client) throws RemoteException;//TODO to manage with a try and catch not with a throws, we dont want to block the game!!
    boolean existsWaitingGame() throws RemoteException;
    void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException;
    void joinWaitingList(String playerID, String playerNickname) throws RemoteException;
}

