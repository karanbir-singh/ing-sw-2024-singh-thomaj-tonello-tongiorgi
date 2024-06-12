package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorController extends SceneController {

    @FXML
    Label message;


    public void setMessage(String message){
        this.message.setText("Server is down, now wait a bit");
    }
}
