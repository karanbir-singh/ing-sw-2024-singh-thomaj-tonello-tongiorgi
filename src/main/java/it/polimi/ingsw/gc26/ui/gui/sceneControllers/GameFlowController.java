package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.ui.gui.PawnsCoords;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController implements Initializable{

    @FXML
    public TitledPane chat;
    @FXML
    public ImageView scoreBoard;
    public Button scoreBoardButton;
    public AnchorPane anchorPaneScoreBoard;
    public HBox HBoxLeftPanel;
    public AnchorPane anchorPaneChat;
    public TabPane chatTabPane;
    @FXML
    private Button chatButton;
    public GridPane scoreBoardGrid;

    //hand
    @FXML
    private AnchorPane handPane;
    //end hand

    //CommonTable
    @FXML
    private HBox resourceCardBox;
    @FXML
    private HBox goldCardBox;
    @FXML
    private VBox commonMissionsBox;
    @FXML
    private VBox secretMissionBox;
    @FXML
    private ImageView commonMissionLabel;
    @FXML
    private ImageView secretMissionLabel;

    //end CommonTable


    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane personalBoardPane;

    @FXML
    private TabPane personalBoardTabPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;

    private HashMap<String,VBox> chats = new HashMap<>();

    private boolean scoreBoardIsVisible = false;
    private boolean chatIsVisible = false;
    private ImageView scoreIconVisible = new ImageView(new Image(getClass().getResource("/images/icons/sparkle-icon-white.png").toExternalForm()));
    private ImageView scoreIconClose = new ImageView(new Image(getClass().getResource("/images/icons/sparkle-icon-white.png").toExternalForm()));
    private ImageView chatIconVisible = new ImageView(new Image(getClass().getResource("/images/icons/chat-icon-white.png").toExternalForm()));
    private ImageView chatIconClose = new ImageView(new Image(getClass().getResource("/images/icons/chat-icon-white.png").toExternalForm()));

    //layout
    CommonLayout layout = new CommonLayout();
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox leftVBox;
    @FXML
    private BorderPane rootBorder;
    @FXML
    private ScrollPane rootScrollPane;
    @FXML
    private ImageView background;
    @FXML
    private AnchorPane rootAnchor;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private ArrayList<ImageView> playablePrositions = new ArrayList<>();
    private ArrayList<ImageView> handCards = new ArrayList<>();

    private boolean chatHasBeenCreate = false;
    private String path = "/images/";
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);


    //forDraggability
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialX;
    private double initialY;

    //azioni per la mano
    @FXML
    public void onClickMouseHandCard(MouseEvent mouseEvent){
        try {
            int index = Integer.valueOf(((ImageView)mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectCardFromHand(index,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    //fine azioni per la mano

    //azioni carte commonBoard
    public void onClickCommonTableCard(MouseEvent mouseEvent){
        try {
            int index = Integer.valueOf(((ImageView)mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectCardFromCommonTable(index,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onSelectedClickCommonTableCard(MouseEvent mouseEvent){
        try {
            this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    //fine azioni per la commonTable

    private static int toIndex(Integer value) {
        return value == null ? 0 : value;
    }


    //azione delle carte opache
    public void onClickPlayablePosition(MouseEvent mouseEvent){
        try {
            Node node = (Node) mouseEvent.getSource();
            Parent p = node.getParent();

            while (p != this.gridPane) {
                node = p;
                p = p.getParent();
            }

            int row = toIndex(GridPane.getRowIndex(node));
            int column = toIndex(GridPane.getColumnIndex(node));
            System.out.println("image clicked at " + row + " " + column);
            //da inserire

            this.mainClient.getVirtualGameController().selectPositionOnBoard(column-xPositionStarterCard,yPositionStarterCard-row,this.mainClient.getClientID());
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
           // throw new RuntimeException(e);
       }
    }
    //fine azioni carte opache


    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        ArrayList<ImageView> resources = new ArrayList<>();
        ArrayList<ImageView> goldens = new ArrayList<>();
        ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();
        System.out.println("selected index: " + simplifiedCommonTable.getSelectedIndex());

        int index = 0;
        for(Card card: simplifiedCommonTable.getResourceCards()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView,String.valueOf(index));
            resources.add(imageView);
            if(index == simplifiedCommonTable.getSelectedIndex()){
                layout.makeGlow(imageView);
                imageView.setOnMouseClicked(this::onSelectedClickCommonTableCard);
            } else {
                imageView.setOnMouseClicked(this::onClickCommonTableCard);
            }
            index++;
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setParameters(resourceDeck, String.valueOf(index));
        resources.add(resourceDeck);
        if(index == simplifiedCommonTable.getSelectedIndex()){
            layout.makeGlow(resourceDeck);
            resourceDeck.setOnMouseClicked(this::onSelectedClickCommonTableCard);
        } else {
            resourceDeck.setOnMouseClicked(this::onClickCommonTableCard);
        }
        index++;
        for(Card card: simplifiedCommonTable.getGoldCards()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            goldens.add(imageView);
            if(index == simplifiedCommonTable.getSelectedIndex()){
                layout.makeGlow(imageView);
                imageView.setOnMouseClicked(this::onSelectedClickCommonTableCard);
            } else {
                imageView.setOnMouseClicked(this::onClickCommonTableCard);
            }
            index++;
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setParameters(goldDeck,String.valueOf(index));
        goldens.add(goldDeck);
        if(index == simplifiedCommonTable.getSelectedIndex()){
            layout.makeGlow(goldDeck);
            goldDeck.setOnMouseClicked(this::onSelectedClickCommonTableCard);
        } else {
            goldDeck.setOnMouseClicked(this::onClickCommonTableCard);
        }
        index++;

        for(Card card: simplifiedCommonTable.getCommonMissions()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageViewsCommonMissions.add(imageView);
            index++;
        }

        Platform.runLater(()->{
            this.commonMissionsBox.getChildren().setAll(imageViewsCommonMissions);
            this.resourceCardBox.getChildren().setAll(resources);
            this.goldCardBox.getChildren().setAll(goldens);
            layout.cardsLayout(rootBorder, resources);
            layout.cardsLayout(rootBorder, goldens);
            layout.cardsLayout(rootBorder, imageViewsCommonMissions);
        });

    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        handCards = new ArrayList<>();

        int index = 0;
        for(Card card: simplifiedHand.getCards()){
            ImageView imageView;
            if(card.equals(simplifiedHand.getSelectedCard())){
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getSelectedSide().getImagePath()))));
                makeDraggable(imageView, playablePrositions);
                layout.makeGlow(imageView);
            }else{
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
                imageView.setOnMouseClicked(this::onClickMouseHandCard);
            }
            this.setParameters(imageView,String.valueOf(index));
            handCards.add(imageView);
            index++;
        }

        Platform.runLater(()->{
            this.handPane.getChildren().setAll(handCards);
            layout.handLayout(rootBorder, handCards, handPane);
        });

    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        playablePrositions = new ArrayList<>();

        if(personalBoard.getSecretMission() != null){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getSecretMission().getFront().getImagePath()))));
            this.setParameters(imageView,"0");
            Platform.runLater(()->{
                this.secretMissionBox.getChildren().setAll(imageView);
            });
        }
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);
        for(Point point : personalBoard.getPlayablePositions()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
            //il path di prima è solo per prova
            imageView.setOpacity(0.3);
            imageView.setVisible(false);
            imageView.setOnMouseClicked(this::onClickPlayablePosition);
            addImage(imageView,this.xPositionStarterCard + point.getX(),
                    this.yPositionStarterCard - point.getY(), this.gridPane);

            playablePrositions.add(imageView);
        }
    }

    @Override
    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard){
        boolean exist = false;
        Tab consideredTab = null;
        ScrollPane otherScrollPane = null;
        GridPane otherGridPane = null;
        //controlla che se esiste gia un tab con lo stesso nickname, e se esiste prendere i riferimenti allo scrollPane e griPane
        for(Tab tab : personalBoardTabPane.getTabs()){
            if(tab.getText().equals(otherPersonalBoard.getNickname())){
                exist = true;
                consideredTab = tab;
                otherScrollPane = (ScrollPane)tab.getContent();
                otherGridPane = (GridPane)otherScrollPane.getContent();
            }
        }

        //se invece non esiste un tab, con quel nickname, crea un nuovo tab e crea un nuovo scrollPane e GridPane
        if(!otherPersonalBoard.getNickname().equals(this.mainClient.getClientID()) && !exist){
            consideredTab = new Tab();
            consideredTab.setText(otherPersonalBoard.getNickname());
            personalBoardTabPane.getTabs().add(consideredTab);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.buttonSetup(scoreIconClose, scoreIconVisible, scoreBoardButton);
        scoreBoardButton.setOnAction(this::toggleScoreBoard);
        layout.buttonSetup(chatIconClose, chatIconVisible, chatButton);
        chatButton.setOnAction(this::toggleChat);


        //page layout and dimensions bindings
        layout.pageBindings(rootScrollPane, rootBorder, personalBoardTabPane, leftVBox, rightVBox, scoreBoard, handCards, handPane);
        layout.handLayout(rootBorder, handCards, handPane);

        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);

    }

    private void creationAndSettingGridContraints(GridPane gridPane){
        gridPane.setPrefWidth(8000);
        gridPane.setPrefHeight(4000);
        gridPane.setMaxWidth(8000);
        gridPane.setMaxHeight(4000);
        for(int row = 0; row < 81; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for(int column = 0; column < 81; column++){
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    private void setParameters(ImageView imageView, String accessibleText){
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
        imageView.setAccessibleText(accessibleText);

    }

    private void addImage(ImageView imageView,int x, int y, GridPane gridPane){
        setParameters(imageView,"0");
        Platform.runLater(()->{
            gridPane.add(imageView,x,y);
        });
    }

    public void makeDraggable(ImageView imageView, ArrayList<ImageView> targets) {
        imageView.setOnMousePressed(event -> {
            if(event.getClickCount() == 2){
                try {
                    this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else {
                initialX = imageView.getLayoutX();
                initialY = imageView.getLayoutY();
                mouseAnchorX = event.getSceneX() - initialX;
                mouseAnchorY = event.getSceneY() - initialY;
            }

        });

        imageView.setOnMouseDragged(event -> {
            imageView.setLayoutX(event.getSceneX() - mouseAnchorX);
            imageView.setLayoutY(event.getSceneY() - mouseAnchorY);

            for (ImageView target: targets) {
                target.setVisible(true);
            }
        });

        imageView.setOnMouseReleased(event -> {
            for (ImageView target: targets) {
                if (isInTargetSpot(imageView, target)) {

                    try {
                        //TODO da controllare se G minuscola
                        int row = GridPane.getRowIndex(target);
                        int column = GridPane.getColumnIndex(target);
                        //da inserire

                        this.mainClient.getVirtualGameController().selectPositionOnBoard(column-xPositionStarterCard,yPositionStarterCard-row,this.mainClient.getClientID());
                        this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                    } catch (RemoteException e) {
                        // throw new RuntimeException(e);
                    }
                }
            }
            imageView.setLayoutX(initialX);
            imageView.setLayoutY(initialY);
            for (ImageView target: targets) {
                target.setVisible(false);
            }
        });
    }

    private boolean isInTargetSpot(ImageView imageView, ImageView target) {
        Bounds imageViewBounds = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds targetBounds = target.localToScene(target.getBoundsInLocal());

        return targetBounds.contains(
                imageViewBounds.getMinX() + imageViewBounds.getWidth() / 2,
                imageViewBounds.getMinY() + imageViewBounds.getHeight() / 2
        );
    }

    private void createChatTab(String nickname) {
        Tab newTab = new Tab();
        newTab.setText(nickname);
        newTab.setStyle("-fx-border-radius: 0px 0px 5px 5px;");
        AnchorPane newAnchorPane = new AnchorPane();
        newTab.setContent(newAnchorPane);
        newAnchorPane.setStyle("-fx-background-color: #e8f4f8");
        TextField newTextField = new TextField();
        newTextField.setPrefWidth(208);
        newTextField.setPrefHeight(26);
        newTextField.setLayoutX(9);
        newTextField.setLayoutY(489);
        newTextField.setPromptText("Type Message");
        newTextField.setStyle("-fx-border-radius: 5px;");
        newAnchorPane.getChildren().add(newTextField);
        Button newButton = new Button();
        newButton.setText("Send");
        newButton.setLayoutX(231);
        newButton.setLayoutY(489);
        newAnchorPane.getChildren().add(newButton);
        ScrollPane newScrollPane = new ScrollPane();
        newScrollPane.setMinHeight(440);
        newScrollPane.setPrefHeight(440);
        newScrollPane.setMinWidth(262);
        newScrollPane.setMaxWidth(262);
        newScrollPane.setLayoutX(9);
        newScrollPane.setLayoutY(14);
        newAnchorPane.getChildren().add(newScrollPane);
        VBox newVBox = new VBox();
        newScrollPane.setContent(newVBox);
        newVBox.maxWidth(229);
        newVBox.maxHeight(440);
        newVBox.prefWidth(229);
        newScrollPane.setContent(newVBox);
        newScrollPane.setPadding(new Insets(5,0,5,5));
        chatTabPane.getTabs().add(newTab);
        newTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            keyEvent.consume();
        });
        newButton.setOnMouseClicked(event -> {
            if (!newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            event.consume();
        });
        chats.put(nickname, newVBox);
    }

    private void sendMessage(TextField newTextField, VBox newVBox, ScrollPane newScrollPane, Tab newTab) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 30));
        Text text = new Text(newTextField.getText());
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.setStyle("-fx-background-color: rgb(15,125,242);" + "-fx-color: rgb(239, 242,255);" + "-fx-background-radius: 7px;");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(2, 5, 2, 5));
        text.setStyle("-fx-font-smoothing-type: gray;" + "-fx-text-fill: white;");
        text.setFill(Color.color(1, 1, 1));
        hBox.getChildren().add(textFlow);
        newVBox.getChildren().add(hBox);

        newScrollPane.setVvalue(newScrollPane.getVmax());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.err.println("RemoteException while sending message!");
        }
        newTextField.clear();
        //                Platform.runLater(() -> {
//                    newVBox.layout();
//                    newScrollPane.layout(); // Ensure the layout is updated
//                    newScrollPane.setVvalue(newScrollPane.getVmax()); // Scroll to bottom
//                });


    }

    @Override
    public void createChats(SimplifiedGame simplifiedGame, String nickname) {
        this.nickname = nickname;
        for(String playerNickname : simplifiedGame.getPlayersNicknames()) {
            if (!playerNickname.equals(nickname)) {
                createChatTab(playerNickname);
            }
        }
        createChatTab("Group Chat");
        fullScoreBoard();
    }

    @Override
    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
        if (newMessage.getReceiver() == null) {
            if (!newMessage.getSender().getNickname().equals(nickname)) {
                if (simplifiedChat.getMessages().size() == 1 || (simplifiedChat.getMessages().size() > 1 &&
                        !newMessage.getSender().getNickname().equals(simplifiedChat.getMessages().get(simplifiedChat.getMessages().size()-2).getSender().getNickname()))) {
                    addMessageInChat(newMessage.getText(), "Group Chat", newMessage.getSender().getNickname() );
                } else {
                    addMessageInChat(newMessage.getText(), "Group Chat", null);
                }
            }
        } else {
            addMessageInChat(newMessage.getText(), newMessage.getSender().getNickname(), null);
        }
    }

    private void addMessageInChat(String message, String sender, String labelMessage) {
        HBox labelBox = new HBox();
        Text labelText;
        TextFlow labelTextFlow;
        if (labelMessage != null) {
            labelBox.setAlignment(Pos.BASELINE_LEFT);
            labelBox.setPadding(new Insets(0, 30, 0, 5));
            labelText = new Text(labelMessage);
            labelText.setStyle("-fx-font-size: 10px;");
            labelTextFlow = new TextFlow(labelText);
            labelBox.setMinWidth(150);
            labelBox.setMaxWidth(150);
            labelText.setFill(Color.color(0.25, 0.25, 0.25));
            labelBox.getChildren().add(labelTextFlow);
        }
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(5, 30, 5, 5));
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 7px;");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(2, 5, 2, 5));
        text.setFill(Color.color(0.0, 0.0, 0.0));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (labelMessage != null) {
                        chats.get(sender).getChildren().add(labelBox);
                    }
                    chats.get(sender).getChildren().add(hBox);
                } catch (NullPointerException e) {}
            }
        });


        //newScrollPane.setVvalue(newScrollPane.getVmax());
    }

    public void toggleScoreBoard(ActionEvent actionEvent) {
        if(chatIsVisible) {
            chatButton.setGraphic(chatIconClose);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonClose");
            anchorPaneChat.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            chatIsVisible = false;
        }
        if (scoreBoardIsVisible) {
            scoreBoardButton.setGraphic(scoreIconClose);
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonClose");
            anchorPaneScoreBoard.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            scoreBoardIsVisible = false;
        } else {
            scoreBoardButton.setGraphic(scoreIconVisible);
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonVisible");
            anchorPaneScoreBoard.setTranslateX(0);
            HBoxLeftPanel.setMinWidth(340);
            HBoxLeftPanel.setMaxWidth(340);
            scoreBoardIsVisible = true;
        }
    }

    public void toggleChat(ActionEvent actionEvent) {
        if (scoreBoardIsVisible) {
            scoreBoardButton.setGraphic(scoreIconClose);
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonClose");
            anchorPaneScoreBoard.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            scoreBoardIsVisible = false;
        }
        if (chatIsVisible) {
            chatButton.setGraphic(chatIconClose);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonClose");
            anchorPaneChat.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            chatIsVisible = false;
        } else {
            chatButton.setGraphic(chatIconVisible);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonVisible");
            anchorPaneChat.setTranslateX(-270);
            HBoxLeftPanel.setMinWidth(340);
            HBoxLeftPanel.setMaxWidth(340);
            chatIsVisible = true;
        }
    }

    private void fullScoreBoard() {
        for (PawnsCoords pawn : PawnsCoords.values()) {

            Point pawnPoint = pawn.getCoords();
            Node cell = getNodeByRowColumnIndex(pawnPoint.getY(), pawnPoint.getX(), scoreBoardGrid);
            if (cell != null) {
                GridPane miniGrid = ((GridPane) cell);
                miniGrid.add(new Circle(7), 0, 0);
                miniGrid.add(new Circle(7), 0, 1);
                miniGrid.add(new Circle(7), 1, 0);
                miniGrid.add(new Circle(7), 1, 1);
            }

        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }
}