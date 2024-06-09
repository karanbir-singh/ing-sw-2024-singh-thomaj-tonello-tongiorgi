package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingController extends SceneController implements Initializable {
    @FXML
    AnchorPane rootPane;
    @FXML
    ImageView background;


    private CommonLayout layout = new CommonLayout();
    private double initialImageHeight;
    private double initialImageWidth;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.setBackground(rootPane, background);
    }
}
