package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PawnSelectionController extends SceneController implements Initializable {
    @FXML
    private Label status;

    CommonLayout layout = new CommonLayout();

    @FXML
    private TilePane pawnsTile;
    @FXML
    private ImageView background;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private BorderPane rootBorder;
    @FXML
    public VBox centerVBox;
    @FXML
    public VBox rightVBox;

    private ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

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
        ImageView image = new ImageView(new Image(getClass().getResource("images/pawns/" + color.toLowerCase() + ".png").toExternalForm()));
        image.setFitWidth(100);
        image.setFitHeight(100);
        button.setGraphic(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.pageBindings(rootPane, rootBorder, background);

        //buttons setup
        layout.buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
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
            pawnsTile.getChildren().setAll(buttons);
        });
    }
}
