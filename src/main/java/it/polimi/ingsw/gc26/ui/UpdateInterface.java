package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.view_model.*;

/**
 * The UpdateInterface provides methods for updating various components of the game's view from the server.
 * Implementing classes should define how these updates are handled.
 */
public interface UpdateInterface {
    /**
     * Updates the view of the common table.
     *
     * @param simplifiedCommonTable the simplified representation of the common table.
     */
    void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable);

    /**
     * Updates the view of a player.
     *
     * @param simplifiedPlayer the simplified representation of a player.
     */
    void updateViewPlayer(SimplifiedPlayer simplifiedPlayer);

    /**
     * Updates the view of the hand.
     *
     * @param simplifiedHand the simplified representation of the hand.
     */
    void updateViewHand(SimplifiedHand simplifiedHand);

    /**
     * Updates the view of the secret hand.
     *
     * @param simplifiedSecretHand the simplified representation of the secret hand.
     */
    void updateViewSecretHand(SimplifiedHand simplifiedSecretHand);

    /**
     * Updates the view of the personal board.
     *
     * @param personalBoard the simplified representation of the personal board.
     */
    void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard);

    /**
     * Updates the view of another player's personal board.
     *
     * @param otherPersonalBoard the simplified representation of the other player's personal board.
     */
    void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard);

    /**
     * Updates the view of the chat.
     *
     * @param simplifiedChat the simplified representation of the chat.
     */
    void updateViewSimplifiedChat(SimplifiedChat simplifiedChat);

    /**
     * Updates the game view with the simplified game state.
     *
     * @param simplifiedGame the simplified representation of the game state.
     */
    void updateGame(SimplifiedGame simplifiedGame);

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed.
     */
    void showMessage(String message);

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to be displayed.
     */
    void showError(String message);

    /**
     * Closes the error popup, if one is displayed.
     */
    void closeErrorPopup();

    /**
     * Prints another player's personal board
     *
     * @param nickname other player's nickname
     */
    void showOtherPersonalBoard(String nickname);
}
