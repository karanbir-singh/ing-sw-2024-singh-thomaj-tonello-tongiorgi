package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualGameController extends Remote {
    void choosePawnColor(String color, String playerID) throws RemoteException;

    void selectSecretMission(int cardIndex, String playerID) throws RemoteException;

    void setSecretMission(String playerID) throws RemoteException;

    void selectCardFromHand(int cardIndex, String playerID) throws RemoteException;

    void turnSelectedCardSide(String playerID) throws RemoteException;

    void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException;

    void playCardFromHand(String playerID) throws RemoteException;

    void selectCardFromCommonTable(int cardIndex, String playerID) throws RemoteException;

    void drawSelectedCard(String playerID) throws RemoteException;

    void addMessage(String message, String receiverNickname, String senderID, String time) throws RemoteException;

    void printPersonalBoard(String nickname, String playerID) throws RemoteException;
    void reAddView(VirtualView view, String clientID) throws RemoteException;
}

