package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

/**
 * The GUIUpdate class implements the UpdateInterface to provide updates to the GUI components
 * in the GUIApplication. It handles the logic for updating the game's view based on the changes
 * in the model.
 */
public class GUIUpdate implements UpdateInterface {
    /**
     * The GUIApplication instance that this class updates.
     */
    GUIApplication guiApplication;

    /**
     * Constructs a new GUIUpdate instance with the specified GUIApplication.
     *
     * @param guiApplication the GUIApplication instance to be updated.
     */
    public GUIUpdate(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

    /**
     * Updates the view of the common table.
     *
     * @param simplifiedCommonTable the simplified representation of the common table.
     */
    @Override
    public void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUICommonTable(simplifiedCommonTable);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUICommonTable(simplifiedCommonTable);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUICommonTable(simplifiedCommonTable);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUICommonTable(simplifiedCommonTable);
        }
    }

    /**
     * Updates the view of a player.
     *
     * @param simplifiedPlayer the simplified representation of a player.
     */
    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIPlayer(simplifiedPlayer);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIPlayer(simplifiedPlayer);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIPlayer(simplifiedPlayer);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIPlayer(simplifiedPlayer);

        }
    }

    /**
     * Updates the view of the hand.
     *
     * @param simplifiedHand the simplified representation of the hand.
     */
    @Override
    public void updateViewHand(SimplifiedHand simplifiedHand) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIHand(simplifiedHand);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIHand(simplifiedHand);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIHand(simplifiedHand);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIHand(simplifiedHand);
        }
    }

    /**
     * Updates the view of the secret hand.
     *
     * @param simplifiedSecretHand the simplified representation of the secret hand.
     */
    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUISecretHand(simplifiedSecretHand);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUISecretHand(simplifiedSecretHand);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUISecretHand(simplifiedSecretHand);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUISecretHand(simplifiedSecretHand);

        }
    }

    /**
     * Updates the view of the personal board.
     *
     * @param personalBoard the simplified representation of the personal board.
     */
    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIPersonalBoard(personalBoard);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIPersonalBoard(personalBoard);
        }
    }

    /**
     * Updates the view of another player's personal board.
     *
     * @param otherPersonalBoard the simplified representation of the other player's personal board.
     */
    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
        }
    }

    /**
     * Updates the view of the chat.
     *
     * @param simplifiedChat the simplified representation of the chat.
     */
    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW) ||
                this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.SECRETMISSIONCHOICE) ||
                this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.PAWNSELECTION) ||
                this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.WAITING) ||
                this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.STARTERCARDCHOICE)) {
            this.guiApplication.getSceneInfo(SceneEnum.PAWNSELECTION).getSceneController().changeGUIChat(simplifiedChat);
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIChat(simplifiedChat);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIChat(simplifiedChat);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIChat(simplifiedChat);
        }
    }

    /**
     * Updates the current showed scene
     *
     * @param simplifiedGame game
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame) {
        switch (simplifiedGame.getGameState()) {
            case WAITING_STARTER_CARD_PLACEMENT:
                this.guiApplication.setCurrentScene(SceneEnum.STARTERCARDCHOICE);
                this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().createChats(simplifiedGame);
                this.guiApplication.getSceneInfo(SceneEnum.PAWNSELECTION).getSceneController().createChats(simplifiedGame);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().createChats(simplifiedGame);
                this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().createChats(simplifiedGame);
                break;
            case WAITING_PAWNS_SELECTION:
                this.guiApplication.setCurrentScene(SceneEnum.PAWNSELECTION);
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                this.guiApplication.setCurrentScene(SceneEnum.SECRETMISSIONCHOICE);
                break;
            case GAME_STARTED:
                this.guiApplication.setCurrentScene(SceneEnum.GAMEFLOW);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().updatePointScoreBoard(simplifiedGame.getScores(), simplifiedGame.getPawnsSelected());
                break;
            case END_STAGE:
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().createChats(simplifiedGame);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().updatePointScoreBoard(simplifiedGame.getScores(), simplifiedGame.getPawnsSelected());
                break;
            case WINNER:
                this.guiApplication.setCurrentScene(SceneEnum.WINNER);
                break;
        }

        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW) || this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.WINNER)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.WINNER).getSceneController().changeGUIGame(simplifiedGame);
            ; //cosi aggiorno solo questa
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.PAWNSELECTION).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIGame(simplifiedGame);
        }
    }

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed.
     */
    @Override
    public void showMessage(String message) {
        try {
            Platform.runLater(() -> this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().addMessageServerDisplayer(message, false));
        } catch (Exception e) {
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to be displayed.
     */
    @Override
    public void showError(String message) {
        if (message.equals("Server is down, wait for reconnection...")) {
            Platform.runLater(() -> this.guiApplication.openErrorPopup(message));
        }
        try {
            Platform.runLater(() -> this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().addMessageServerDisplayer(message, true));
        } catch (Exception e) {
        }

    }

    /**
     * Closes the error popup, if one is displayed.
     */
    @Override
    public void closeErrorPopup() { //this is called in the client ping thread
        Platform.runLater(() -> {
            //so that it can be closed
            this.guiApplication.getSceneInfo(SceneEnum.ERROR).getScene().getWindow().setOnCloseRequest((WindowEvent event) -> {
            });
            //close the error scene
            this.guiApplication.getSceneInfo(SceneEnum.ERROR).getScene().getWindow().hide();
            this.guiApplication.getCurrentScene().getScene().getRoot().setDisable(false);
        });
    }
}
