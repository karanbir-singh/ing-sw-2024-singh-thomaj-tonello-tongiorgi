package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.awt.*;

public class WinnerController extends SceneController {
    @FXML
    Label title;

    @FXML
    Label status;

    @FXML
    Label rank;


    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame){
        if(simplifiedGame.getWinners() == null){
            return;
        }else{
            title.setText("GAME ENDED, HERE THE RESULTS");
            for(String winnerNickname : simplifiedGame.getWinners()){
            }
        }
    }



}
