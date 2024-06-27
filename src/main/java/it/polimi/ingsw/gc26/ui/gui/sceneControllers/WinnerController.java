package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
     * Image of player final result
     */
    @FXML
    ImageView winnerOrLoser;
    /**
     * Box containing the final result
     */
    @FXML
    private VBox results;

    /**
     * Modifies the color of each tab for every player that has selected its pawn
     *
     * @param simplifiedGame new simplified game
     */
    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame) {
        ArrayList<Label> rank = new ArrayList<>();
        Label title = new Label();
        Label status = new Label();
        boolean areYouWinner = false;

        if(simplifiedGame.getWinners().size() > 1) {
            title.setText("Winners:");
        } else {
            title.setText("Winner:");
        }
        title.getStyleClass().add("winnerTitle");
        rank.add(title);

        for(String winnerNickname : simplifiedGame.getWinners()){
            Label label = new Label();
            Label score = new Label();
            label.setText(winnerNickname);
            label.getStyleClass().add("winnerName");
            score.setText("score: " + simplifiedGame.getScores().get(winnerNickname) + " points");
            score.getStyleClass().add("winnerLabel");
            rank.add(label);
            rank.add(score);
            if(this.mainClient.getNickname().equals(winnerNickname)){
                areYouWinner = true;
            }
        }

        if(areYouWinner){
            winnerOrLoser.setImage(new Image(getClass().getResource("images/labels/WinnerLabel.png").toExternalForm()));
            status.setText("\n\nCongratulations!");
            status.getStyleClass().add("winnerLabel");
            rank.add(status);
        }else{
            winnerOrLoser.setImage(new Image(getClass().getResource("images/labels/LoserLabel.png").toExternalForm()));
            status.setText("\n\nNext time you will do better!\nYour score: " + simplifiedGame.getScores().get(this.mainClient.getNickname()) + " points");
            status.getStyleClass().add("winnerLabel");
            rank.add(status);
        }

        winnerOrLoser.getStyleClass().add("winnerOrLoser");

        Platform.runLater(()->{
            results.getChildren().setAll(rank);
            results.getStyleClass().add("winnerBox");
        });

    }

    /**
     * Initializes the controller.
     * Initializes the background.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground(rootPane, background);
    }
}
