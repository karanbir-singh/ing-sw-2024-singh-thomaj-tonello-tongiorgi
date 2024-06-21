package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController extends SceneController {
    @FXML
    Label message;

    public void setMessage(String message){
        this.message.setText("Server is down, please wait");
    }
}
