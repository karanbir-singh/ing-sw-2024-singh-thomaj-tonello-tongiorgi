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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StarterCardChoiceController extends SceneController implements Initializable {
    //CommonTable
    @FXML
    private ImageView resourceCard0;
    @FXML
    private ImageView resourceCard1;
    @FXML
    private ImageView resourceDeck;

    @FXML
    private ImageView goldCard0;
    @FXML
    private ImageView goldCard1;
    @FXML
    private ImageView goldDeck;

    @FXML
    private VBox commonTableBox;

    //hand
    @FXML
    private ImageView handCard0;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;

    //layout
    CommonLayout layout = new CommonLayout();
    @FXML
    private HBox HBoxLeftPanel;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private BorderPane rootBorder;
    @FXML
    private ScrollPane rootScrollPane;

    private ArrayList<ImageView> cards = new ArrayList<>();
    @FXML
    VBox choosingBox;

    @FXML
    ImageView image;
    @FXML
    Label status;


    String path = "images/";

    public void onClickGoToNextStep(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        status.setText("Wow you are so fast, now wait other players!");
    }


    public void onImageClick(MouseEvent event){
        if(event.getClickCount() == 2){
            try {
                this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if(simplifiedHand.getSelectedSide() != null){
            this.image.setImage(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getSelectedSide().getImagePath()))));
            this.image.setOnMouseClicked(this::onImageClick);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards.add(handCard0);
        cards.add(handCard1);
        cards.add(handCard2);
        cards.add(resourceCard0);
        cards.add(resourceCard1);
        cards.add(resourceDeck);
        cards.add(goldCard0);
        cards.add(goldCard1);
        cards.add(goldDeck);

        layout.setGameBackground(rootBorder);
        layout.pageBindings(rootScrollPane, rootBorder, HBoxLeftPanel, rightVBox, centerVBox);

    }
}
