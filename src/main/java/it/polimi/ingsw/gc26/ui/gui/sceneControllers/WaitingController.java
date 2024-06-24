package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller manages the waiting scene.
 * It doesn't allow the user to do anything, it just informs the users that is in a waiting state.
 */
public class WaitingController extends SceneController implements Initializable {
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
