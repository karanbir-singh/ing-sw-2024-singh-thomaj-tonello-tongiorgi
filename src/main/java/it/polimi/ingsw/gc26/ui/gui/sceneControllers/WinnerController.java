package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.swing.text.PlainDocument;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller manages the winners' scene.
 * It allows the user to see the game's winners.
 */
public class WinnerController extends SceneController implements Initializable {
    /**
     * Root object containing all other panes and objects as children.
     */
    @FXML
    AnchorPane rootPane;
    /**
     * Scene's background
     */
    @FXML
    ImageView background;
    /**
     * Scene's title
     */
    @FXML
    private Label title;
    /**
     * The label to display the status of the game or player.
     */
    @FXML
    private Label status;
    /**
     * Font style
     */
    private final int rankTextDimension = 16;
    /**
     * Box containing the game's winners
     */
    @FXML
    private VBox winner;

    /**
     * Modifies the color of each tab for every player that has selected its pawn
     *
     * @param simplifiedGame new simplified game
     */
    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame){
        ArrayList<Label> rank = new ArrayList<>();
        title.setText("Game ended, here are the results: ");
        boolean areYouWinner = false;
        for(String winnerNickname : simplifiedGame.getWinners()){
            Label label = new Label();
            label.setFont(new Font(rankTextDimension));
            label.setText(winnerNickname);
            rank.add(label);
            if(this.nickname.equals(winnerNickname)){
                areYouWinner = true;
            }
        }
        Platform.runLater(()->{
            winner.getChildren().setAll(rank);
        });

        if(areYouWinner){
            status.setText("Congratulations! You won!");
        }else{
            status.setText("You can do better next time!");
        }
    }

    /**
     * Initializes the controller.
     * Initializes the background.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground(rootPane, background);
    }
}
