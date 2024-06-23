package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.ui.UpdateInterface;

import java.util.HashMap;

/**
 * This class represents a simplified version of the model.
 * It provides methods to access the attributes and put together the simplified version.
 */
public class SimplifiedModel {
    /**
     * Simplified representation of the game
     */
    private SimplifiedGame simplifiedGame;
    /**
     * Simplified representation of the common table
     */
    private SimplifiedCommonTable simplifiedCommonTable;
    /**
     * Simplified representation of the current player
     */
    private SimplifiedPlayer simplifiedPlayer;
    /**
     * Simplified representation of the player's hand
     */
    private SimplifiedHand simplifiedHand;
    /**
     * Simplified representation of the player's secret hand
     */
    private SimplifiedHand simplifiedSecretHand;
    /**
     * Simplified representation of the player's personal board
     */
    private SimplifiedPersonalBoard personalBoard;
    /**
     * Simplified representations of other players' personal boards
     */
    private HashMap<String, SimplifiedPersonalBoard> otherPersonalBoards = new HashMap<>();
    /**
     * Simplified representation of the chat
     */
    private SimplifiedChat simplifiedChat;
    /**
     * Interface for updating the user interface
     */
    UpdateInterface view;

    /**
     * Sets the view updater for this simplified model.
     *
     * @param view The view updater implementing the UpdateInterface.
     */
    public void setViewUpdater(UpdateInterface view) {
        this.view = view;
    }

    /**
     * Sets the simplified game and notifies the view about the update.
     *
     * @param simplifiedGame The simplified game object to set.
     * @param message        The message to display to the user.
     */
    public void setSimplifiedGame(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedGame = simplifiedGame;
        view.updateGame(simplifiedGame);
        view.showMessage(message);
    }

    /**
     * Sets the simplified common table and notifies the view about the update.
     *
     * @param simplifiedCommonTable The simplified common table object to set.
     * @param message               The message to display to the user.
     */
    public void setSimplifiedCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedCommonTable = simplifiedCommonTable;
        view.updateViewCommonTable(simplifiedCommonTable);
        view.showMessage(message);
    }

    /**
     * Sets the simplified player and notifies the view about the update.
     *
     * @param simplifiedPlayer The simplified player object to set.
     * @param message          The message to display to the user.
     */
    public void setSimplifiedPlayer(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedPlayer = simplifiedPlayer;
        view.updateViewPlayer(simplifiedPlayer);
        view.showMessage(message);
    }

    /**
     * Sets the simplified hand and notifies the view about the update.
     *
     * @param simplifiedHand The simplified hand object to set.
     * @param message        The message to display to the user.
     */
    public void setSimplifiedHand(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedHand = simplifiedHand;
        view.updateViewHand(simplifiedHand);
        view.showMessage(message);
    }

    /**
     * Sets the simplified secret hand and notifies the view about the update.
     *
     * @param simplifiedSecretHand The simplified secret hand object to set.
     * @param message              The message to display to the user.
     */
    public void setSimplifiedSecretHand(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedSecretHand = simplifiedSecretHand;
        view.updateViewSecretHand(simplifiedSecretHand);
        view.showMessage(message);
    }

    /**
     * Sets the simplified personal board and notifies the view about the update.
     *
     * @param personalBoard The simplified personal board object to set.
     * @param message       The message to display to the user.
     */
    public void setPersonalBoard(SimplifiedPersonalBoard personalBoard, String message) {
        this.personalBoard = personalBoard;
        view.updateViewPersonalBoard(personalBoard);
        view.showMessage(message);
    }

    /**
     * Sets the simplified other personal board and stores it in the map.
     * Does not notify the view about the update.
     *
     * @param otherPersonalBoard The simplified other personal board object to set.
     */
    public void setOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.otherPersonalBoards.put(otherPersonalBoard.getNickname(), otherPersonalBoard);
        // view.updateViewOtherPersonalBoard(otherPersonalBoard); // Uncomment if view update is desired
        // view.showMessage(message); // Uncomment if message display is desired
    }

    /**
     * Sets the simplified chat and notifies the view about the update.
     *
     * @param simplifiedChat The simplified chat object to set.
     * @param message        The message to display to the user.
     */
    public void setSimplifiedChat(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedChat = simplifiedChat;
        view.updateViewSimplifiedChat(simplifiedChat);
        view.showMessage(message);
    }

    /**
     * Returns the simplified common table.
     *
     * @return The simplified common table.
     */
    public SimplifiedCommonTable getSimplifiedCommonTable() {
        return simplifiedCommonTable;
    }

    /**
     * Returns the simplified game.
     *
     * @return The simplified game.
     */
    public SimplifiedGame getSimplifiedGame() {
        return simplifiedGame;
    }

    /**
     * Returns the simplified player.
     *
     * @return The simplified player.
     */
    public SimplifiedPlayer getSimplifiedPlayer() {
        return simplifiedPlayer;
    }

    /**
     * Returns the simplified hand.
     *
     * @return The simplified hand.
     */
    public SimplifiedHand getSimplifiedHand() {
        return simplifiedHand;
    }

    /**
     * Returns the simplified secret hand.
     *
     * @return The simplified secret hand.
     */
    public SimplifiedHand getSimplifiedSecretHand() {
        return simplifiedSecretHand;
    }

    /**
     * Returns the simplified personal board.
     *
     * @return The simplified personal board.
     */
    public SimplifiedPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Returns the map of other simplified personal boards.
     *
     * @return The map of other simplified personal boards.
     */
    public HashMap<String, SimplifiedPersonalBoard> getOthersPersonalBoards() {
        return otherPersonalBoards;
    }

    /**
     * Returns the simplified chat.
     *
     * @return The simplified chat.
     */
    public SimplifiedChat getSimplifiedChat() {
        return simplifiedChat;
    }

    /**
     * Returns the view updater associated with this simplified model.
     *
     * @return The view updater.
     */
    public UpdateInterface getView() {
        return view;
    }
}
