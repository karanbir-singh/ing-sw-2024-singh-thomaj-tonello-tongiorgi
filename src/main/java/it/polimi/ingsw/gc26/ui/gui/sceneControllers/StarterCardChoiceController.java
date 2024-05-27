package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class StarterCardChoiceController extends GenericController implements Initializable {

    @FXML
    ScrollPane RootPane;
    @FXML
    AnchorPane LayoutAnchorPane;
    @FXML
    VBox VLayoutBox;
    @FXML
    HBox HCardBox;
    @FXML
    ImageView image;
    @FXML
    Label status;


    String path = "/images/";
    public void onClickFlipButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickGoToNextStep(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        status.setText("Wow you are so fast, now wait other players!");
    }


    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if(simplifiedHand.getSelectedSide() != null){
            this.image.setImage(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getSelectedSide().getImagePath()))));
            //System.out.println("HA CAMBIATO LA GUI");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LayoutAnchorPane.prefWidthProperty().bind(RootPane.widthProperty());
        LayoutAnchorPane.prefHeightProperty().bind(RootPane.heightProperty());

        VLayoutBox.prefHeightProperty().bind(LayoutAnchorPane.heightProperty());
        AnchorPane.setTopAnchor(VLayoutBox, 0.0);
        AnchorPane.setLeftAnchor(VLayoutBox, (LayoutAnchorPane.getWidth() - VLayoutBox.getWidth()) / 2);

        LayoutAnchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(VLayoutBox, 0.0);
        });

        LayoutAnchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(VLayoutBox, (newVal.doubleValue() - VLayoutBox.getWidth()) / 2);
        });
    }
}
