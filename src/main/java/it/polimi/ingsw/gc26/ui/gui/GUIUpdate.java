package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.ui.gui.sceneControllers.SceneEnum;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUIUpdate implements UpdateInterface {
    GUIApplication guiApplication;

    public GUIUpdate(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

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

    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIPersonalBoard(personalBoard);
            System.out.println("your points: " +personalBoard.getScore());
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIPersonalBoard(personalBoard);
        }
    }

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

    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIChat(simplifiedChat);
        } else {
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
                break;
            case WAITING_PAWNS_SELECTION:
                this.guiApplication.setCurrentScene(SceneEnum.PAWNSELECTION);
                this.guiApplication.getSceneInfo(SceneEnum.PAWNSELECTION).getSceneController().createChats(simplifiedGame, guiApplication.getNickname());
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                this.guiApplication.setCurrentScene(SceneEnum.SECRETMISSIONCHOICE);
                break;
            case GAME_STARTED:
                this.guiApplication.setCurrentScene(SceneEnum.GAMEFLOW);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().createChats(simplifiedGame, guiApplication.getNickname());
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().updatePointScoreBoard(simplifiedGame.getScores(), simplifiedGame.getPawnsSelected());
                break;
        }

        if (this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)) {
            this.guiApplication.getCurrentScene().getSceneController().changeGUIGame(simplifiedGame);
            ; //cosi aggiorno solo questa
        } else {
            this.guiApplication.getSceneInfo(SceneEnum.PAWNSELECTION).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIGame(simplifiedGame);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIGame(simplifiedGame);

        }


    }

    @Override
    public void showMessage(String message) {
        try {
            Platform.runLater(()-> this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().addMessageServerDisplayer(message, false));
        } catch (Exception e) {
        }
        //Platform.runLater(()->this.guiApplication.openInfoPopup(message)); //TODO kevin
    }

    @Override
    public void showError(String message) {
        if(message.equals("Server is down, wait for reconnection...")){
            Platform.runLater(()->this.guiApplication.openErrorPopup(message));
        }
        try {
            Platform.runLater(()->this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().addMessageServerDisplayer(message, true));
        } catch (Exception e) {
        }

    }

    @Override
    public void closeErrorPopup(){ //this is called in the client ping thread
        Platform.runLater(()->{
            //so that it can be closed
            this.guiApplication.getSceneInfo(SceneEnum.ERROR).getScene().getWindow().setOnCloseRequest((WindowEvent event)->{});
            //close the error scene
            this.guiApplication.getSceneInfo(SceneEnum.ERROR).getScene().getWindow().hide();
        });
    }
}
