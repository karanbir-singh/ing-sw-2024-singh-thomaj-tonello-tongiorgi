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
    public void choosePawnColor(String color, String playerID) throws RemoteException {

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

    @Override
    public void printPersonalBoard(String nickname, String playerID) throws RemoteException {
        this.gameController.printPersonalBoard(nickname, playerID);
    }

    @Override
    public void addMessage(String line, String nicknameReceiver, String senderID, String time) throws RemoteException {
        this.gameController.addMessage(line, nicknameReceiver, senderID, time);
    }
}
