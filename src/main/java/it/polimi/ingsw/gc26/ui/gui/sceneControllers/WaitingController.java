package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingController extends GenericController implements Initializable {
    @FXML
    AnchorPane rootPane;
    @FXML
    ImageView background;

    private CommonLayout layout = new CommonLayout();
    private double initialImageHeight;
    private double initialImageWidth;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialImageWidth = background.getImage().getWidth();
        initialImageHeight = background.getImage().getHeight();

        background.fitHeightProperty().bind(rootPane.heightProperty());
        background.fitWidthProperty().bind(rootPane.widthProperty());

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            layout.updateViewport(rootPane, background, initialImageWidth, initialImageHeight);
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            layout.updateViewport(rootPane, background, initialImageWidth, initialImageHeight);
        });
    }
}
