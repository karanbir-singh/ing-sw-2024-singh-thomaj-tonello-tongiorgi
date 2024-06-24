package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * This controller manages the game's creator scene.
 * It allows the user to select the number of players and start the game.
 */
public class CreatorController extends SceneController implements Initializable {
    /**
     * Root object containing all other panes and objects as children.
     */
    @FXML
    private AnchorPane rootPane;
    /**
     * Scene's background
     */
    @FXML
    private ImageView background;
    /**
     * Button to be selected to create a game with two players
     */
    @FXML
    private Button twoButton;
    /**
     * Button to be selected to create a game with three players
     */
    @FXML
    private Button threeButton;
    /**
     * Button to be selected to create a game with four players
     */
    @FXML
    private Button fourButton;
    /**
     * Message displayed when the server makes an update.
     */
    @FXML
    private Label status;
    /**
     * Button that triggers the request to the server
     */
    @FXML
    private Button playButton;
    /**
     * Styles to let user know an option was selected
     */
    @FXML
    private ImageView selector;
    /**
     * Styles to let user know an option was selected
     */
    @FXML
    private ImageView selector1;
    /**
     * Styles to let user know an option was selected
     */
    @FXML
    private ImageView selector2;
    /**
     * Number of player that will be on the new game
     */
    private int numMaxPlayer = 2;

    /**
     * Handles the event when the button for 2 players is clicked.
     * Sets the maximum number of players to 2 and updates the selector visibility.
     *
     * @param actionEvent the event triggered by clicking the button
     */
    @FXML
    public void onSecondButtonClick(ActionEvent actionEvent) {
        this.numMaxPlayer = 2;
        selector.setVisible(true);
        selector1.setVisible(false);
        selector2.setVisible(false);
    }

    /**
     * Handles the event when the button for 3 players is clicked.
     * Sets the maximum number of players to 3 and updates the selector visibility.
     *
     * @param actionEvent the event triggered by clicking the button
     */
    @FXML
    public void onThirdButtonClick(ActionEvent actionEvent) {
        this.numMaxPlayer = 3;
        selector1.setVisible(true);
        selector.setVisible(false);
        selector2.setVisible(false);
    }

    /**
     * Handles the event when the button for 4 players is clicked.
     * Sets the maximum number of players to 4 and updates the selector visibility.
     *
     * @param actionEvent the event triggered by clicking the button
     */
    @FXML
    public void onFourthButtonClick(ActionEvent actionEvent) {
        this.numMaxPlayer = 4;
        selector2.setVisible(true);
        selector1.setVisible(false);
        selector.setVisible(false);
    }

    /**
     * Handles the event when the play button is clicked.
     * Initiates the creation of a waiting list for players.
     *
     * @param actionEvent the event triggered by clicking the play button
     */
    @FXML
    public void onClickButton(ActionEvent actionEvent) {
        try {
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), this.nickname, this.numMaxPlayer);
            this.status.setText("Waiting for other players...");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the controller.
     * Sets up event handlers for buttons and initializes the background.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(this::onClickButton);

        twoButton.setOnAction(this::onSecondButtonClick);
        twoButton.getStyleClass().add("number-button");

        threeButton.setOnAction(this::onThirdButtonClick);
        threeButton.getStyleClass().add("number-button");

        fourButton.setOnAction(this::onFourthButtonClick);
        fourButton.getStyleClass().add("number-button");

        setBackground(rootPane, background);
    }
}
