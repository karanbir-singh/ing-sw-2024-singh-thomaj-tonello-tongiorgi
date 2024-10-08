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

/**
 * This controller manages the pawn selection scene.
 * It allows the user to select a pawn color and chat with other players.
 */
public class PawnSelectionController extends SceneController implements Initializable {
    /**
     * Objects containing available pawn
     */
    @FXML
    private TilePane pawnsTile;
    /**
     * Scene background
     */
    @FXML
    private ImageView background;
    /**
     * Root object containing all other panes and objects as children.
     */
    @FXML
    private AnchorPane rootPane;
    /**
     * Pane containing the border panes to structure the scene
     */
    @FXML
    private BorderPane rootBorder;
    /**
     * Box used to re-dimension pawn depending on the screen view port
     */
    @FXML
    public VBox centerVBox, rightVBox;
    /**
     * Button to trigger rules pdf
     */
    @FXML
    private Button rulesButton;

    /**
     * Flag to know whether keep displaying the available colors or not
     */
    private boolean hasChosen = false;
    /**
     * Icon for rules button
     */
    private final ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));

    /**
     * Handles the click on a pawn image. It performs the choose pawn color request to the server
     *
     * @param event the event triggered by clicking the pawn button
     */
    public void onClickButton(ActionEvent event) {
        String pawnColor = ((Button) event.getSource()).getAccessibleText();
        try {
            this.mainClient.getVirtualGameController().choosePawnColor(pawnColor, this.mainClient.getClientID());
            hasChosen = true;
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait");
        }
        pawnsTile.getChildren().clear();
        pawnsTile.getChildren().add((Button) event.getSource());
        pawnsTile.getChildren().add(new Label("Nice color!\nNow wait for the other players..."));
    }

    /**
     * Sets default styles for button representing a pawn
     *
     * @param button button to be styled
     * @param color  pawn color
     */
    private void setDimensionAndColor(Button button, String color) {
        button.setPrefWidth(100);
        button.setPrefHeight(100);
        ImageView image = new ImageView(new Image(getClass().getResource("images/pawns/" + color.toLowerCase() + ".png").toExternalForm()));
        image.setFitWidth(100);
        image.setFitHeight(100);
        button.setGraphic(image);
        button.getStyleClass().add("pawn");
    }

    /**
     * Initializes the controller.
     * Sets up event handlers for buttons, initializes the background styles and the chat.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageBindings(rootPane, rootBorder, background);

        //buttons setup
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
        buttonSetup(rulesIcon, rulesButton);
    }

    /**
     * Modifies the color of each tab for every player that has selected its pawn
     *
     * @param simplifiedGame new simplified game
     */
    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame) {
        if(!hasChosen) {
            ArrayList<Button> buttons = new ArrayList<>();
            for (Pawn pawn : simplifiedGame.getAvailablePawns()) {
                Button button = new Button();
                button.setAccessibleText(pawn.toString());
                this.setDimensionAndColor(button, pawn.toString());
                button.setOnAction((this::onClickButton));
                buttons.add(button);
            }
            Platform.runLater(() -> {
                pawnsTile.getChildren().setAll(buttons);
            });
        }
    }
}
