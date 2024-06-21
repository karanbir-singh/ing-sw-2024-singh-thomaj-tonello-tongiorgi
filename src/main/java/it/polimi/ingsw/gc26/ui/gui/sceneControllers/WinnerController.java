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


public class WinnerController extends SceneController implements Initializable {
    @FXML
    AnchorPane rootPane;
    @FXML
    ImageView background;
    @FXML
    private Label title;

    @FXML
    private Label status;

    private final int rankTextDimension = 16;
    @FXML
    private VBox winner;


    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame){
        ArrayList<Label> rank = new ArrayList<>();
        title.setText("GAME ENDED, HERE THE RESULTS");
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
            status.setText("ERRRR PRIMOOOO");
        }else{
            status.setText("YOU LOSEEEEE");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonLayout layout = new CommonLayout();
        layout.setBackground(rootPane, background);
    }
}
