package it.polimi.ingsw.gc26.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains the methods that can be called from the client in the game controller.
 */
public interface VirtualGameController extends Remote {

    /**
     * Sets the pawn color
     *
     * @param color    chosen color index
     * @param playerID ID of the player who is choosing the color
     * @throws RemoteException if remote object cannot be called
     */
    void choosePawnColor(String color, String playerID) throws RemoteException;

    /**
     * Selects the chosen secret mission card
     *
     * @param cardIndex index of the selected mission card
     * @param playerID  ID of the player who is selecting the secret mission card
     * @throws RemoteException if the remote object cannot be called
     */
    void selectSecretMission(int cardIndex, String playerID) throws RemoteException;

    /**
     * Sets the selected secret mission card on the personal board of the player
     *
     * @param playerID ID of the player who is setting the secret mission card
     * @throws RemoteException if the remote object cannot be called
     */
    void setSecretMission(String playerID) throws RemoteException;

    /**
     * Selects the chosen card in the hand
     *
     * @param cardIndex index of the card in the player's hand
     * @param playerID  ID of the player who is selected the card to play
     * @throws RemoteException if the remote object cannot be called
     */
    void selectCardFromHand(int cardIndex, String playerID) throws RemoteException;

    /**
     * Turns the side of the selected card
     *
     * @param playerID ID of the player who is turning the side of the selected card
     * @throws RemoteException if the remote object cannot be called
     */
    void turnSelectedCardSide(String playerID) throws RemoteException;

    /**
     * Selects a position on the personal board where the player wants to place the selected card
     *
     * @param selectedX coordinate on the X axis of the chosen position on the personal board
     * @param selectedY coordinate on the Y axis of the chosen position on the personal board
     * @param playerID  ID of the player who is selecting the position on the personal board
     * @throws RemoteException if the remote object cannot be called
     */
    void selectPositionOnBoard(int selectedX, int selectedY, String playerID) throws RemoteException;

    /**
     * Places the selected side on the personal board
     *
     * @param playerID ID of the player who is playing the selected side of the card in the hand
     * @throws RemoteException if the remote object cannot be called
     */
    void playCardFromHand(String playerID) throws RemoteException;

    /**
     * Selects the card on the common table that the currentPlayer wants to draw
     *
     * @param cardIndex index of the card on the common table
     * @param playerID  ID of the player who is trying to select the card on the common table
     * @throws RemoteException if the remote object cannot be called
     */
    void selectCardFromCommonTable(int cardIndex, String playerID) throws RemoteException;

    /**
     * Draw a card from the common table and places it on the current player's hand
     *
     * @param playerID ID of the player who is trying to draw the selected card on the common table
     * @throws RemoteException if the remote object cannot be called
     */
    void drawSelectedCard(String playerID) throws RemoteException;

    /**
     * Add a new message into the chat
     *
     * @param message          body of the message
     * @param receiverNickname nickname of the receiver player
     * @param senderID         ID of the sender player
     * @param time             local time of the client
     * @throws RemoteException if the remote object cannot be called
     */
    void addMessage(String message, String receiverNickname, String senderID, String time) throws RemoteException;

    /**
     * Readds the virtual view after the server has gone down, because the connection must be recreated
     *
     * @param view client's view
     * @param clientID client's original ID
     * @throws RemoteException if the remote object cannot be called
     */
    void reAddView(VirtualView view, String clientID) throws RemoteException;
}

