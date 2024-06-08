package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class PawnSelectionController extends GenericController{

    @FXML
    private Label status;

    @FXML
    private HBox buttonHBox;

    public void onClickButton(ActionEvent event){
        String pawnColor = ((Button)event.getSource()).getAccessibleText();
        try {
            this.mainClient.getVirtualGameController().choosePawnColor(pawnColor, this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDimensionAndColor(Button button, String color) {
        button.setPrefWidth(100);
        button.setPrefHeight(100);
        button.setStyle("-fx-background-color: " + color);
    }

    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame){
        ArrayList<Button> buttons = new ArrayList<>();
        for(Pawn pawn : simplifiedGame.getAvailablePawns()){
            Button button = new Button();
            button.setAccessibleText(pawn.toString());
            this.setDimensionAndColor(button,pawn.toString());
            button.setOnAction((this::onClickButton));
            buttons.add(button);
        }
        Platform.runLater(()->{
            buttonHBox.getChildren().setAll(buttons);
        });


    }




}
