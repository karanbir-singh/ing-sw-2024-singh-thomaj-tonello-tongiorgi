package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoController extends SceneController {

    @FXML
    Label message;


    public void setMessage(String message){
        this.message.setText(message);
    }
}
