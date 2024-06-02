package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.SceneEnum;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.StarterCardChoiceController;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import it.polimi.ingsw.gc26.ui.UpdateInterface;

public class GUIUpdate implements UpdateInterface {
    GUIApplication guiApplication;

    public GUIUpdate(GUIApplication guiApplication) {
        this.guiApplication = guiApplication;
    }

    @Override
    public void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            Platform.runLater(()->this.guiApplication.getCurrentScene().getSceneController().changeGUICommonTable(simplifiedCommonTable)); //cosi aggiorno solo questa
        }else{
            Platform.runLater(()->{
                this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUICommonTable(simplifiedCommonTable);
                this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUICommonTable(simplifiedCommonTable);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUICommonTable(simplifiedCommonTable);
            });

        }
    }

    @Override
    public void updateViewPlayer(SimplifiedPlayer simplifiedPlayer) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            this.guiApplication.getCurrentScene().getSceneController().changeGUIPlayer(simplifiedPlayer); //cosi aggiorno solo questa
        }else{
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIPlayer(simplifiedPlayer);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIPlayer(simplifiedPlayer);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIPlayer(simplifiedPlayer);

        }
    }

    @Override
    public void updateViewHand(SimplifiedHand simplifiedHand) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            Platform.runLater(()->this.guiApplication.getCurrentScene().getSceneController().changeGUIHand(simplifiedHand)); //cosi aggiorno solo questa
        }else{
            Platform.runLater(()->{
                this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIHand(simplifiedHand);
                this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIHand(simplifiedHand);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIHand(simplifiedHand);
            });
        }
    }

    @Override
    public void updateViewSecretHand(SimplifiedHand simplifiedSecretHand) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            Platform.runLater(()->this.guiApplication.getCurrentScene().getSceneController().changeGUISecretHand(simplifiedSecretHand)); //cosi aggiorno solo questa
        }else{
            Platform.runLater(()->{
                this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUISecretHand(simplifiedSecretHand);
                this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUISecretHand(simplifiedSecretHand);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUISecretHand(simplifiedSecretHand);
            });

        }
    }

    @Override
    public void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            Platform.runLater(()->this.guiApplication.getCurrentScene().getSceneController().changeGUIPersonalBoard(personalBoard)); //cosi aggiorno solo questa
        }else{
            Platform.runLater(()->{
                this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
                this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIPersonalBoard(personalBoard);
                this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIPersonalBoard(personalBoard);

            });

        }
    }

    @Override
    public void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            this.guiApplication.getCurrentScene().getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard); //cosi aggiorno solo questa
        }else{
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIotherPersonalBoard(otherPersonalBoard);

        }
    }

    @Override
    public void updateViewSimplifiedChat(SimplifiedChat simplifiedChat) {
        if(this.guiApplication.getCurrentScene().getSceneEnum().equals(SceneEnum.GAMEFLOW)){
            this.guiApplication.getCurrentScene().getSceneController().changeGUIChat(simplifiedChat); //cosi aggiorno solo questa
        }else{
            this.guiApplication.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getSceneController().changeGUIChat(simplifiedChat);
            this.guiApplication.getSceneInfo(SceneEnum.SECRETMISSIONCHOICE).getSceneController().changeGUIChat(simplifiedChat);
            this.guiApplication.getSceneInfo(SceneEnum.GAMEFLOW).getSceneController().changeGUIChat(simplifiedChat);

        }
    }

    /**
     * @param simplifiedGame
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame) {
        switch(simplifiedGame.getGameState()){
            case WAITING_STARTER_CARD_PLACEMENT:
                Platform.runLater(()->{
                    guiApplication.setCurrentSceneAndShow(SceneEnum.STARTERCARDCHOICE);
                });
                break;
            case WAITING_PAWNS_SELECTION:
                Platform.runLater(()->{
                    guiApplication.setCurrentSceneAndShow(SceneEnum.PAWNSELECTION);
                });
                break;
            case WAITING_SECRET_MISSION_CHOICE:
                Platform.runLater(()->{
                    guiApplication.setCurrentSceneAndShow(SceneEnum.SECRETMISSIONCHOICE);
                });
                break;
            case GAME_STARTED:
                Platform.runLater(()->{
                    guiApplication.setCurrentSceneAndShow(SceneEnum.GAMEFLOW);
                });
                break;
        }

    }

    @Override
    public void showMessage(String message) {
        return;
        //Platform.runLater(()->this.guiApplication.openInfoPopup(message)); //TODO kevin
    }

    @Override
    public void showError(String message) {
        Platform.runLater(()->this.guiApplication.openErrorPopup(message));
    }
}
