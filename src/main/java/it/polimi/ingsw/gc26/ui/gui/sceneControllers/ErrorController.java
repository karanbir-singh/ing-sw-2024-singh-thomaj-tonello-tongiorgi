package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This controller manages the error scene.
 * It allows the server to display an error message.
 */
public class ErrorController extends SceneController {
    /**
     * Message to be displayed
     */
    @FXML
    Label message;

    /**
     * The message is set and the layout updated
     * @param message
     */
    public void setMessage(String message) {
        this.message.setText("Server is down, please wait");
    }

}
