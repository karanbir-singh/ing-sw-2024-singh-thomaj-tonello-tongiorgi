package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SecretMissionChoiceController extends SceneController implements Initializable {
    @FXML
    public TabPane chatTabPane;
    @FXML
    public Button chatButton;
    @FXML
    public AnchorPane anchorPaneChat;
    //CommonTable
    @FXML
    private VBox commonTableBox;

    @FXML
    private Label status;

    @FXML
    private HBox cardHBox;
    @FXML
    private HBox secretMissionHBox;


    @FXML
    private HBox resourceHbox;
    @FXML
    private HBox goldHbox;


    //layout
    @FXML
    TabPane personalBoardTabPane;


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
    @FXML
    private Button confirmButton;
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    private boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;
    private final ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

    @FXML
    private Button rulesButton;
    private final ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));


    private ArrayList<ImageView> cards = new ArrayList<>();
    private ImageView image1 = new ImageView(new Image(getClass().getResource("images/game-background.png").toExternalForm()));
    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);


    @FXML
    GridPane gridPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    String path = "images/";

    public void onClickSecretMission(MouseEvent mouseEvent) {
        try {
            int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectSecretMission(index, this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void onClickConfirmButton() {
        try {
            this.mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        ArrayList<ImageView> resources = new ArrayList<>();
        ArrayList<ImageView> goldens = new ArrayList<>();
        ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();

        int index = 0;
        for (Card card : simplifiedCommonTable.getResourceCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            resources.add(imageView);
            index++;
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setParameters(resourceDeck, String.valueOf(index));
        index++;
        resources.add(resourceDeck);
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            goldens.add(imageView);
            index++;
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setParameters(goldDeck, String.valueOf(index));
        goldens.add(goldDeck);
        index++;
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageViewsCommonMissions.add(imageView);
            index++;
        }

        Platform.runLater(()->{
            this.rightVBox.getChildren().setAll(imageViewsCommonMissions);
            this.resourceHbox.getChildren().setAll(resources);
            this.goldHbox.getChildren().setAll(goldens);

        });

        //da controllare se vanno messi in platform.runlater
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        ArrayList<ImageView> handCards = new ArrayList<>();
        for (Card card : simplifiedHand.getCards()) {
            ImageView imageView;
            imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            handCards.add(imageView);
        }

        Platform.runLater(() -> {
            this.cardHBox.getChildren().setAll(handCards);
        });

    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);

    }

    @Override
    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        boolean exist = false;
        Tab consideredTab = null;
        ScrollPane otherScrollPane = null;
        GridPane otherGridPane = null;
        //controlla che se esiste gia un tab con lo stesso nickname, e se esiste prendere i riferimenti allo scrollPane e griPane
        for (Tab tab : personalBoardTabPane.getTabs()) {
            if (tab.getText().equals(otherPersonalBoard.getNickname())) {
                exist = true;
                consideredTab = tab;
                otherScrollPane = (ScrollPane) tab.getContent();
                otherGridPane = (GridPane) otherScrollPane.getContent();
                otherScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.getStyleClass().add("tabScrollPane");
            }
        }

        //se invece non esiste un tab, con quel nickname, crea un nuovo tab e crea un nuovo scrollPane e GridPane
        if (!otherPersonalBoard.getNickname().equals(this.nickname) && !exist) {
            consideredTab = new Tab();
            consideredTab.setText(otherPersonalBoard.getNickname());
            this.personalBoardTabPane.getTabs().add(consideredTab);
            otherScrollPane = new ScrollPane();
            otherScrollPane.setHvalue(0.5);
            otherScrollPane.setVvalue(0.5);

            otherGridPane = new GridPane();
            otherScrollPane.setContent(otherGridPane);
            this.creationAndSettingGridContraints(otherGridPane);
            consideredTab.setContent(otherScrollPane);
        }

        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + otherPersonalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + otherPersonalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - otherPersonalBoard.getOccupiedPositions().getLast().getY(), otherGridPane);
    }


    @Override
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
        ArrayList<ImageView> secretHand = new ArrayList<>();
        int index = 0;
        for (Card card : simplifiedSecretHand.getCards()) {
            ImageView imageView;
            imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickSecretMission);
            secretHand.add(imageView);
            if(card == simplifiedSecretHand.getSelectedCard()) {
                makeGlow(imageView);
            }
            index++;
        }

        Platform.runLater(() -> {
            this.secretMissionHBox.getChildren().setAll(secretHand);
            this.secretMissionHBox.getChildren().add(confirmButton);
        });
    }

    private void setParameters(ImageView imageView, String accessibleText) {
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
        imageView.setAccessibleText(accessibleText);

    }


    private void creationAndSettingGridContraints(GridPane gridPane) {
        gridPane.setPrefWidth(8000);
        gridPane.setPrefHeight(4000);
        gridPane.setMaxWidth(8000);
        gridPane.setMaxHeight(4000);
        for (int row = 0; row < 81; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int column = 0; column < 81; column++) {
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setParameters(imageView, "0");
        Platform.runLater(() -> {
            gridPane.add(imageView, x, y);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);

        pageBindings(rootPane, rootBorder, background);
        //buttons setup
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
    }
}
