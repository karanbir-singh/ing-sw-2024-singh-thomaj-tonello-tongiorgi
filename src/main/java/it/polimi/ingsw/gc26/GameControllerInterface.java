package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.game.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameControllerInterface extends Remote {
    void prepareCommonTable() throws RemoteException;
    void prepareStarterCards() throws RemoteException;
    void preparePlayersHand() throws RemoteException;
    void prepareCommonMissions() throws RemoteException;
    void prepareSecretMissions()throws RemoteException;
    void selectSecretMission(int cardIndex, String playerID) throws RemoteException;
    void setSecretMission(String playerID) throws RemoteException;
    void setFirstPlayer(String playerID) throws RemoteException;
    void selectCardFromHand(int cardIndex, String playerID)throws RemoteException;
    void turnSelectedCardSide(String playerID) throws RemoteException;
    void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException;
    void playCardFromHand(String playerID) throws RemoteException;
    void selectCardFromCommonTable(int cardX, int cardY, String playerID) throws RemoteException;
    void drawSelectedCard(String playerID) throws RemoteException;
    void changeTurn() throws RemoteException;
    Game getGame() throws RemoteException;
}
