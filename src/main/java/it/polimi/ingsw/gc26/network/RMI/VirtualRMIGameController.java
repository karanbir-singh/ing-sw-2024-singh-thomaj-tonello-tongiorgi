package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualGameController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class VirtualRMIGameController implements VirtualGameController {

    private final GameController gameController;

    public VirtualRMIGameController(GameController gameController) throws RemoteException {
        this.gameController = gameController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void prepareCommonTable() throws RemoteException {
        this.gameController.prepareCommonTable();
    }

    @Override
    public void prepareStarterCards() throws RemoteException {
        this.gameController.prepareStarterCards();
    }

    @Override
    public void preparePlayersHand(String playerID) throws RemoteException {
        this.gameController.preparePlayersHand(playerID);
    }

    @Override
    public void prepareCommonMissions() throws RemoteException {
        this.gameController.prepareCommonMissions();
    }

    @Override
    public void prepareSecretMissions() throws RemoteException {
        this.gameController.prepareSecretMissions();
    }

    @Override
    public void selectSecretMission(int cardIndex, String playerID) throws RemoteException  {
        this.gameController.selectSecretMission(cardIndex, playerID);
    }

    @Override
    public void setSecretMission(String playerID) throws RemoteException  {
        this.gameController.setSecretMission(playerID);
    }

    @Override
    public void setFirstPlayer(String playerID) throws RemoteException  {
        this.gameController.setFirstPlayer(playerID);
    }

    @Override
    public void selectCardFromHand(int cardIndex, String playerID) throws RemoteException  {
        this.gameController.selectCardFromHand(cardIndex, playerID);
    }

    @Override
    public void turnSelectedCardSide(String playerID) throws RemoteException  {
        this.gameController.turnSelectedCardSide(playerID);
    }

    @Override
    public void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException  {
        this.gameController.selectPositionOnBoard(selectedX, selectedY, playerID);
    }

    @Override
    public void playCardFromHand(String playerID) throws RemoteException  {
        this.gameController.playCardFromHand(playerID);
    }

    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) throws RemoteException  {
        this.gameController.selectCardFromCommonTable(cardX, cardY, playerID);
    }

    @Override
    public void drawSelectedCard(String playerID) throws RemoteException  {
        this.gameController.drawSelectedCard(playerID);
    }
}
