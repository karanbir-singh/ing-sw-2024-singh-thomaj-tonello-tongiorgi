package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    //waiting phase

    String connect(VirtualView client) throws RemoteException;//TODO to manage with a try and catch not with a throws, we dont want to block the game!!
    public int login(String nickname, String id) throws RemoteException ;
    public void createGame(int numPlayers,String id) throws Exception;
    public void joinGame(String id) throws RemoteException;



    //Only when game is started
    public void selectCardFromHand(int x, String id) throws RemoteException;
    public void turnSelectedCardSide(String id) throws RemoteException;
    public void selectPositionOnBoard(int x, int y, String id) throws RemoteException;

    public void playCardFromHand(String id) throws RemoteException;
    public void selectCardFromCommonTable(int x, int y, String id) throws RemoteException;

    public void drawSelectedCard(String id) throws RemoteException;

    public void selectSecretMission(int x,String id) throws RemoteException;
    public void setSecretMission(String id) throws RemoteException;

}

