package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.game_request.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIGameController implements VirtualGameController {

    /**
     * This attribute represents the game controller on which execute the called actions
     */
    private final GameController gameController;

    public VirtualRMIGameController(GameController gameController) throws RemoteException {
        this.gameController = gameController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void choosePawnColor(String color, String playerID) throws RemoteException {
        this.gameController.addRequest(new ChoosePawnColorRequest(color, playerID));

    }

    @Override
    public void selectSecretMission(int cardIndex, String playerID) throws RemoteException  {
        this.gameController.addRequest(new SelectSecretMissionRequest(cardIndex, playerID));
    }

    @Override
    public void setSecretMission(String playerID) throws RemoteException  {
        this.gameController.addRequest(new SetSecretMissionRequest(playerID));
    }

    @Override
    public void selectCardFromHand(int cardIndex, String playerID) throws RemoteException  {
        this.gameController.addRequest(new SelectCardFromHandRequest(cardIndex, playerID));
    }

    @Override
    public void turnSelectedCardSide(String playerID) throws RemoteException  {
        this.gameController.addRequest(new TurnSelectedSideRequest(playerID));
    }

    @Override
    public void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException  {
        this.gameController.addRequest(new SelectPositionOnBoardRequest(selectedX, selectedY, playerID));
    }

    @Override
    public void playCardFromHand(String playerID) throws RemoteException{
        this.gameController.addRequest(new PlayCardFromHandRequest(playerID));
    }

    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) throws RemoteException  {
        this.gameController.addRequest(new SelectCardFromCommonTableRequest(cardX, cardY,playerID));
    }

    @Override
    public void drawSelectedCard(String playerID) throws RemoteException  {
        this.gameController.addRequest(new DrawSelectedCardRequest(playerID));
    }

    @Override
    public void printPersonalBoard(String nickname, String playerID) throws RemoteException {
        this.gameController.addRequest(new PrintPersonalBoardRequest(nickname,playerID));
    }

    @Override
    public void addMessage(String line, String nicknameReceiver, String senderID, String time) throws RemoteException {
        this.gameController.addRequest(new AddMessageRequest(line,nicknameReceiver,senderID,time));
    }

    @Override
    public void reAddView(VirtualView view, String clientID) throws RemoteException{
        this.gameController.addRequest(new ReAddViewRequest(view,clientID));
    }
}
