package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecretMissionChoiceController extends GenericController implements Initializable {
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

    //commonMissions
    @FXML
    private ImageView commonMission0;
    @FXML
    private ImageView commonMission1;

    //hand
    @FXML
    private ImageView handCard0;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;

    @FXML
    Label status;

    @FXML
    HBox cardHBox;

    @FXML
    HBox secretMissionHBox;

    @FXML
    ImageView secretMission0;

    @FXML
    ImageView secretMission1;
    @FXML
    TabPane personalBoardTabPane;

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
    private ImageView image1 = new ImageView(new Image(getClass().getResource("/images/game-background.png").toExternalForm()));
    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

    String path = "/images/";

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
            this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getCards().get(0).getFront().getImagePath()))));
            this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getCards().get(1).getFront().getImagePath()))));
            this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getCards().get(2).getFront().getImagePath()))));



        }
    }


    @Override
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
        if(simplifiedSecretHand.getCards().size() >= 2) {
            this.secretMission0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedSecretHand.getCards().get(0).getFront().getImagePath()))));
            this.secretMission1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedSecretHand.getCards().get(1).getFront().getImagePath()))));


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO mettere le carte giuste
        cards.add(handCard0);
        cards.add(handCard1);
        cards.add(handCard2);
        cards.add(resourceCard0);
        cards.add(resourceCard1);
        cards.add(resourceDeck);
        cards.add(goldCard0);
        cards.add(goldCard1);
        cards.add(goldDeck);
        cards.add(commonMission0);
        cards.add(commonMission1);
        cards.add(secretMission0);
        cards.add(secretMission1);


        layout.setGameBackground(rootBorder);
        //layout.pageBindings(rootScrollPane, rootAnchor, centerVBox, leftVBox, rightVBox);
        layout.pageBindings(rootScrollPane, rootBorder, HBoxLeftPanel, rightVBox, centerVBox);
        layout.setPersonalBoardRatio(rootBorder, personalBoardTabPane, 0.3, 0.4);

    }

}
