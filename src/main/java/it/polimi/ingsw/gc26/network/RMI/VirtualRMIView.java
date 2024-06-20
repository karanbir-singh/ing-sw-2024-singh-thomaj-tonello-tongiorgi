package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.view_request.*;
import it.polimi.ingsw.gc26.view_model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class represents the implementation for methods called from the server using RMI network connection.
 */
public class VirtualRMIView implements VirtualView {
    /**
     * This attribute represents the client controller on which execute the called actions
     */
    private final ViewController viewController;

    /**
     * Constructor for the RMI virtual view.
     *
     * @param viewController controller where to call the method
     * @throws RemoteException if the remote method cannot be called
     */
    public VirtualRMIView(ViewController viewController) throws RemoteException {
        this.viewController = viewController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Not used for RMI. Used only with socket implementation.
     *
     * @param clientID new client's id
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        this.viewController.setClientID(clientID);
    }

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState new client state
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateClientState(ClientState clientState) throws RemoteException {
        this.viewController.updateClientState(clientState);
    }

    /**
     * Not used for RMI. Used only with socket implementation.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void setGameController() throws RemoteException {
    }

    /**
     * Print messages from the server to the client.
     *
     * @param message string to be displayed as information
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void showMessage(String message) throws RemoteException {
        this.viewController.showMessage(message);
    }

    /**
     * Notifies the clients about an error.
     *
     * @param message string to be displayed as an error
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void showError(String message) throws RemoteException {
        this.viewController.showError(message);
    }

    /**
     * Updates the client's common table by giving a simplified version of model's common table.
     * It also displays a message with information about the update.
     *
     * @param simplifiedCommonTable updated common table
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException {
        this.viewController.addRequest(new CommonTableUpdateRequest(simplifiedCommonTable, message));
    }

    /**
     * Updates the client's hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedHand updated hand
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException {
        this.viewController.addRequest(new HandUpdateRequest(simplifiedHand, message));
    }

    /**
     * Updates the client's secret hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedSecretHand updated hand
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException {
        this.viewController.addRequest(new SecretHandUpdateRequest(simplifiedSecretHand, message));
    }

    /**
     * Updates the client's personal board by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param personalBoard updated personal board
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException {
        this.viewController.addRequest(new PersonalBoardUpdateRequest(personalBoard, message));
    }

    /**
     * Updates the other players' personal boards by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param otherPersonalBoard updated personal board
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException {
        this.viewController.addRequest(new OtherPersonalBoardUpdateRequest(otherPersonalBoard, message));
    }

    /**
     * Updates the client's representation of himself by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedPlayer updated player
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException {
        this.viewController.addRequest(new PlayerUpdateRequest(simplifiedPlayer, message));
    }

    /**
     * Updates the client's chat by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedChat updated chat
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException {
        this.viewController.addRequest(new ChatUpdateRequest(simplifiedChat, message));
    }

    /**
     * Updates the client's game id.
     * It also displays a message with the information about the update.
     *
     * @param gameID updated game ID
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateIDGame(int gameID) throws RemoteException{
        this.viewController.setGameID(gameID);
    }

    /**
     * Updates the game by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedGame updated game
     * @param message updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame, String message) throws RemoteException {
        this.viewController.addRequest(new GameUpdateRequest(simplifiedGame, message));
    }

    /**
     * Resets the timer in the client so the client knows the server is up.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void ping() throws RemoteException {
        this.viewController.resetTimer();
    }

    /**
     * Kills the client ending the game.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void killProcess(String nickname) throws RemoteException {
        this.viewController.addRequest(new DestroyClientRequest());
    }
}
