package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @throws RemoteException
     */
    void showMessage(String message) throws RemoteException;

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @throws RemoteException
     */
    void showError(String message) throws RemoteException;

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState
     * @throws RemoteException
     */
    void updateClientState(ClientState clientState) throws RemoteException;

    /**
     * Used only with socket implementation
     *
     * @param clientID
     * @throws RemoteException
     */
    void setClientID(String clientID) throws RemoteException;

    /**
     * Used only with socket implementation
     *
     * @throws RemoteException
     */
    void setGameController() throws RemoteException;

    void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException;

    void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException;

    void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException;

    void updatePersonalBoard(PersonalBoard personalBoard, String message) throws RemoteException;

    void updateOtherPersonalBoard(PersonalBoard otherPersonalBoard, String message) throws RemoteException;

    void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException;

//    void updateOptionsMenu(OptionsMenu optionsMenu, String message) throws RemoteException;

    void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException;

//
//    /**
//     * To notify not the current player but the other that a pawn color has been selected
//     *
//     * @param pawn
//     * @throws RemoteException
//     */
//    void updateChosenPawn(Pawn pawn) throws RemoteException;

//    /**
//     * Update pawn
//     *
//     * @param pawn
//     * @throws RemoteException
//     */
//    void updatePawn(Pawn pawn) throws RemoteException;

//    /**
//     * To notify the current player the successful selection of its mission
//     *
//     * @param clientID
//     * @throws RemoteException
//     */
//    void updateSelectedMission(String clientID) throws RemoteException;

//    /**
//     * Update secret mission
//     *
//     * @throws RemoteException
//     */
//    void updateSecretMission() throws RemoteException;

//    /**
//     * Notifies the current player the successful selection of its card in the hand
//     *
//     * @throws RemoteException
//     */
//    void updateSelectedCardFromHand() throws RemoteException;
//
//    /**
//     * Notifies the current player the successful turned of the selected card
//     *
//     * @param cardIndex
//     * @param clientID
//     * @throws RemoteException
//     */
//    void updateSelectedSide(String cardIndex, String clientID) throws RemoteException;
//
//    /**
//     * Notifies the current player the successful selection of the position on the board
//     *
//     * @param selectedX
//     * @param selectedY
//     * @param playerID
//     * @param success   1 | 0 indicating the success of the operation
//     * @throws RemoteException
//     */
//    void updateSelectedPositionOnBoard(String selectedX, String selectedY, String playerID, String success) throws RemoteException;
//
//    /**
//     * Notifies the current player the successful play of the card
//     *
//     * @param clientID
//     * @param success
//     * @throws RemoteException
//     */
//    void updateCardRemovalFromHand(Card removedCard) throws RemoteException;
//
//    /**
//     * Notifies all the clients the current player's points
//     *
//     * @param clientID
//     * @param points
//     * @throws RemoteException
//     */
//    void updatePoints(String clientID, String points) throws RemoteException;
//
//    /**
//     * Notifies the current player the success selection of the card in the common table
//     *
//     * @param clientID
//     * @param success
//     * @throws RemoteException
//     */
//    void updateSelectedCardFromCommonTable(String clientID, String success) throws RemoteException;
//
//    /**
//     * Prints the card in the client's interface
//     *
//     * @param playerID
//     * @param cardSerialization
//     * @throws RemoteException
//     */
//    void showCard(String playerID, String cardSerialization) throws RemoteException;
//
//    /**
//     * Notifies the clients about a new message in the chat
//     *
//     * @param message
//     * @throws RemoteException
//     */
//    void showChat(String message) throws RemoteException;
//
//    /**
//     * Prints the personal board
//     *
//     * @param clientID
//     * @param ownerNickname
//     * @param personalBoardSerialization
//     * @throws RemoteException
//     */
//    void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) throws RemoteException;
//
//    /**
//     * Notifies all clients who is the first player
//     *
//     * @param nickname
//     * @throws RemoteException
//     */
//    void updateFirstPlayer(String nickname) throws RemoteException;
//
//    /**
//     * Notifies the clients the new game's state
//     *
//     * @param gameState
//     * @throws RemoteException
//     */
//    void updateGameState(String gameState) throws RemoteException;
}
