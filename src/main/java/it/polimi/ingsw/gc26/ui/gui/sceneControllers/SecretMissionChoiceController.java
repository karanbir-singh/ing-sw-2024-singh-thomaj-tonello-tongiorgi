package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class SecretMissionChoiceController extends GenericController implements Initializable {

    @FXML
    Label status;

    @FXML
    ImageView handCard0;

    @FXML
    ImageView handCard1;

    @FXML
    ImageView handCard2;

    @FXML
    HBox cardHBox;

    @FXML
    HBox secretMissionHBox;

    @FXML
    ImageView secretMission0;

    @FXML
    ImageView secretMission1;


    public void onClickSecretMission0(){
        try {
            this.mainClient.getVirtualGameController().selectSecretMission(0,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void onClickSecretMission1(){
        try {
            this.mainClient.getVirtualGameController().selectSecretMission(1,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void onClickButtonConferm(){
        try {
            this.mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        this.status.setText("you chose, now wait");


    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if(simplifiedHand.getCards().size() >= 3){
            this.handCard0.setImage(new Image(String.valueOf(getClass().getResource("/images/"+ simplifiedHand.getCards().get(0).getFront().getImagePath()))));
            this.handCard1.setImage(new Image(String.valueOf(getClass().getResource("/images/"+ simplifiedHand.getCards().get(1).getFront().getImagePath()))));
            this.handCard2.setImage(new Image(String.valueOf(getClass().getResource("/images/"+ simplifiedHand.getCards().get(2).getFront().getImagePath()))));



        }
    }


    @Override
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
        if(simplifiedSecretHand.getCards().size() >= 2) {
            this.secretMission0.setImage(new Image(String.valueOf(getClass().getResource("/images/" + simplifiedSecretHand.getCards().get(0).getFront().getImagePath()))));
            this.secretMission1.setImage(new Image(String.valueOf(getClass().getResource("/images/" + simplifiedSecretHand.getCards().get(1).getFront().getImagePath()))));


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
