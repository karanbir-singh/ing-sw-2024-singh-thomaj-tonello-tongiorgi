package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.ClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIView implements VirtualView {
    /**
     * This attribute represents the client controller on which execute the called actions
     */
    private ClientController clientController;

    public VirtualRMIView(ClientController clientController) throws RemoteException {
        this.clientController = clientController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {
        this.clientController.setClientID(clientID);
    }

    @Override
    public void setGameController() throws RemoteException {
    }

    /**
     * To notify not the current player but the other that a pawn color has been selected
     *
     * @param pawnColor
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateChosenPawn(String pawnColor, String clientID) throws RemoteException {
        this.clientController.updateChosenPawn(pawnColor, clientID);
    }

    /**
     * To notify the current player the successful selection of its mission
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedMission(String clientID) throws RemoteException {
        this.clientController.updateSelectedMission(clientID);
    }

    /**
     * Notifies the current player the successful selection of its card in the hand
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedCardFromHand(String clientID) throws RemoteException {
        this.clientController.updateSelectedCardFromHand(clientID);
    }

    /**
     * Notifies the current player the successful turned of the selected card
     *
     * @param cardIndex
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedSide(String cardIndex, String clientID) throws RemoteException {
        this.clientController.updateSelectedSide(cardIndex, clientID);
    }

    /**
     * Notifies the current player the successful selection of the position on the board
     *
     * @param selectedX
     * @param selectedY
     * @param playerID
     * @param success   1 | 0 indicating the success of the operation
     * @throws RemoteException
     */
    @Override
    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String playerID, String success) throws RemoteException {
        this.clientController.updateSelectedPositionOnBoard(selectedX, selectedY, playerID, success);
    }

    /**
     * Notifies the current player the successful play of the card
     *
     * @param clientID
     * @param success
     * @throws RemoteException
     */
    @Override
    public void updatePlayedCardFromHand(String clientID, String success) throws RemoteException {
        this.clientController.updatePlayedCardFromHand(clientID, success);
    }

    /**
     * Notifies all the clients the current player's points
     *
     * @param clientID
     * @param points
     * @throws RemoteException
     */
    @Override
    public void updatePoints(String clientID, String points) throws RemoteException {
        this.clientController.updatePoints(clientID, points);
    }

    /**
     * Notifies the current player the success selection of the card in the common table
     *
     * @param clientID
     * @param success
     * @throws RemoteException
     */
    @Override
    public void updateSelectedCardFromCommonTable(String clientID, String success) throws RemoteException {
        this.clientController.updateSelectedCardFromCommonTable(clientID, success);
    }

    /**
     * Prints the card in the client's interface
     *
     * @param playerID
     * @param cardSerialization
     * @throws RemoteException
     */
    @Override
    public void showCard(String playerID, String cardSerialization) throws RemoteException {
        this.clientController.showCard(playerID, cardSerialization);
    }

    /**
     * Notifies the clients about a new message in the chat
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showChat(String message) throws RemoteException {
        this.clientController.showChat(message);
    }

    /**
     * Prints the personal board
     *
     * @param clientID
     * @param ownerNickname
     * @param personalBoardSerialization
     * @throws RemoteException
     */
    @Override
    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) throws RemoteException {
        this.clientController.showPersonalBoard(clientID, ownerNickname, personalBoardSerialization);
    }

    /**
     * Notifies all clients who is the first player
     *
     * @param nickname
     * @throws RemoteException
     */
    @Override
    public void updateFirstPlayer(String nickname) throws RemoteException {
        this.clientController.updateFirstPlayer(nickname);
    }

    /**
     * Notifies the clients the new game's state
     *
     * @param gameState
     * @throws RemoteException
     */
    @Override
    public void updateGameState(String gameState) throws RemoteException {
        this.clientController.updateGameState(gameState);
    }

    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void showMessage(String message, String clientID) throws RemoteException {
        this.clientController.showMessage(message, clientID);
    }

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void showError(String message, String clientID) throws RemoteException {
        this.clientController.showError(message, clientID);
    }

    @Override
    public void updateState(ClientState clientState) throws RemoteException {
        this.clientController.updateClientState(clientState);
    }
}
