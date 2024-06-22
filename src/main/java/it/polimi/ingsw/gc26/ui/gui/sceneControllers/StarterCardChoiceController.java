package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StarterCardChoiceController extends SceneController implements Initializable {
    @FXML
    private AnchorPane anchorPaneChat;
    @FXML
    private TabPane chatTabPane;
    @FXML
    public Button chatButton;
    @FXML
    private HBox handHbox;
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

    @FXML
    private HBox HBoxLeftPanel;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private BorderPane rootBorder;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView background;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    private boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;
    private ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

    @FXML
    private Button rulesButton;
    private ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));

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
            makeGlow(this.image);
        }

    }

    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable){
        if(simplifiedCommonTable.getResourceCards().size() >= 2){
            resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            resourceCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(1).getFront().getImagePath()))));
            resourceDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        }

        if(simplifiedCommonTable.getGoldCards().size() >= 2){
            goldCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(0).getFront().getImagePath()))));
            goldCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(1).getFront().getImagePath()))));
            goldDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pageBindings(rootPane, rootBorder, background);
        //buttons setup
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
    }

}
