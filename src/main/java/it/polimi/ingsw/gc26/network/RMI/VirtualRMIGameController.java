package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.game_request.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class contains the methods that can be called from the client in the game controller.
 * This is the implementation for RMI network connection.
 */
public class VirtualRMIGameController implements VirtualGameController {

    /**
     * This attribute represents the game controller on which execute the called actions
     */
    private final GameController gameController;

    /**
     * Constructor of the virtual controller.
     *
     * @param gameController game controller where the methods will be called.
     * @throws RemoteException if the remote method cannot be called
     */
    public VirtualRMIGameController(GameController gameController) throws RemoteException {
        this.gameController = gameController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Sets the pawn color
     *
     * @param color    chosen color index
     * @param playerID ID of the player who is choosing the color
     * @throws RemoteException if remote object cannot be called
     */
    @Override
    public void choosePawnColor(String color, String playerID) throws RemoteException {
        this.gameController.addRequest(new ChoosePawnColorRequest(color, playerID));

    }

    /**
     * Selects the chosen secret mission card
     *
     * @param cardIndex index of the selected mission card
     * @param playerID  ID of the player who is selecting the secret mission card
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void selectSecretMission(int cardIndex, String playerID) throws RemoteException {
        this.gameController.addRequest(new SelectSecretMissionRequest(cardIndex, playerID));
    }

    /**
     * Sets the selected secret mission card on the personal board of the player
     *
     * @param playerID ID of the player who is setting the secret mission card
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void setSecretMission(String playerID) throws RemoteException {
        this.gameController.addRequest(new SetSecretMissionRequest(playerID));
    }

    /**
     * Selects the chosen card in the hand
     *
     * @param cardIndex index of the card in the player's hand
     * @param playerID  ID of the player who is selected the card to play
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void selectCardFromHand(int cardIndex, String playerID) throws RemoteException {
        this.gameController.addRequest(new SelectCardFromHandRequest(cardIndex, playerID));
    }

    /**
     * Turns the side of the selected card
     *
     * @param playerID ID of the player who is turning the side of the selected card
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void turnSelectedCardSide(String playerID) throws RemoteException {
        this.gameController.addRequest(new TurnSelectedSideRequest(playerID));
    }

    /**
     * Selects a position on the personal board where the player wants to place the selected card
     *
     * @param selectedX coordinate on the X axis of the chosen position on the personal board
     * @param selectedY coordinate on the Y axis of the chosen position on the personal board
     * @param playerID  ID of the player who is selecting the position on the personal board
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException {
        this.gameController.addRequest(new SelectPositionOnBoardRequest(selectedX, selectedY, playerID));
    }

    /**
     * Places the selected side on the personal board
     *
     * @param playerID ID of the player who is playing the selected side of the card in the hand
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void playCardFromHand(String playerID) throws RemoteException {
        this.gameController.addRequest(new PlayCardFromHandRequest(playerID));
    }

    /**
     * Selects the card on the common table that the currentPlayer wants to draw
     *
     * @param cardIndex index of the card on the common table
     * @param playerID  ID of the player who is trying to select the card on the common table
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void selectCardFromCommonTable(int cardIndex, String playerID) throws RemoteException {
        this.gameController.addRequest(new SelectCardFromCommonTableRequest(cardIndex, playerID));
    }

    /**
     * Draw a card from the common table and places it on the current player's hand
     *
     * @param playerID ID of the player who is trying to draw the selected card on the common table
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void drawSelectedCard(String playerID) throws RemoteException {
        this.gameController.addRequest(new DrawSelectedCardRequest(playerID));
    }

    /**
     * Add a new message into the chat
     *
     * @param message          body of the message
     * @param nicknameReceiver nickname of the receiver player
     * @param senderID         ID of the sender player
     * @param time             local time of the client
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void addMessage(String message, String nicknameReceiver, String senderID, String time) throws RemoteException {
        this.gameController.addRequest(new AddMessageRequest(message, nicknameReceiver, senderID, time));
    }

    /**
     * @param view     client's view
     * @param clientID client's original ID
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void reAddView(VirtualView view, String clientID) throws RemoteException {
        this.gameController.addRequest(new ReAddViewRequest(view, clientID));
    }
}
