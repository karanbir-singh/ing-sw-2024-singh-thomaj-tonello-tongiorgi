package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.ui.gui.PawnsCoords;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.event.ActionEvent;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameFlowController extends SceneController implements Initializable {

    //Chat
    @FXML
    private TitledPane chat;
    @FXML
    private AnchorPane anchorPaneChat;
    @FXML
    private TabPane chatTabPane;
    @FXML
    private Button chatButton;
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    private boolean chatHasBeenCreated = false;
    private boolean chatIsVisible = false;
    private final ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

    //ScoreBoard
    @FXML
    private ImageView scoreBoard;
    @FXML
    private Button scoreBoardButton;
    @FXML
    private AnchorPane anchorPaneScoreBoard;
    @FXML
    private GridPane scoreBoardGrid;
    private boolean scoreBoardIsVisible = false;
    private final ImageView scoreIcon = new ImageView(new Image(getClass().getResource("images/icons/sparkle-icon-white.png").toExternalForm()));


    @FXML
    private Button rulesButton;
    private final ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));

    //Hand
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

    private ArrayList<ImageView> resources = new ArrayList<>();
    private ArrayList<ImageView> goldens = new ArrayList<>();
    private ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();

    //end CommonTable


    //PersonalBoard
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane personalBoardPane;

    @FXML
    private TabPane personalBoardTabPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;
    private final ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private final RowConstraints rowConstraints = new RowConstraints(60, 60, 60);


    //layout
    CommonLayout layout = new CommonLayout();
    @FXML
    private AnchorPane rootPane;
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
    @FXML
    private ImageView background;

    private final ArrayList<ImageView> playablePositions = new ArrayList<>();
    private final ArrayList<ImageView> handCards = new ArrayList<>();

    private String path = "images/";

    //forDraggability
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialX;
    private double initialY;

    @FXML
    public VBox serverMessagesDisplayer;
    @FXML
    private ScrollPane serverMessagesScrollPane;


    private ArrayList<ImageView> handImages;
    private ArrayList<ImageView> resourceCommonTableImages;
    private ArrayList<ImageView> goldCommonTableImages;
    private ArrayList<ImageView> commonMissionsCommonTableImages;
    private ImageView secretMission = new ImageView();

    //azioni carte commonBoard
    @FXML
    public void onClickCommonTableCard(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            try {
                int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
                this.mainClient.getVirtualGameController().selectCardFromCommonTable(index, this.mainClient.getClientID());
                this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
            } catch (RemoteException e) {
                System.out.println("Connection problem, please wait!");
            }
        }
    }
    //fine azioni per la commonTable

    private static int toIndex(Integer value) {
        return value == null ? 0 : value;
    }


    // String.valueOf(getClass().getResource
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        int index = 0;
        for (Card card : simplifiedCommonTable.getResourceCards()) {
            resourceCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())),831,556,true,true,false));
            index++;
        }
        resourceCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath())),831,556,true,true,false));

        index = 0;
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            goldCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())),831,556,true,true,false));

            index++;
        }
        goldCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath())),831,556,true,true,false));

        index = 0;
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            commonMissionsCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())),831,556,true,true,false));
            index++;
        }

    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {


        // Update hand
        int index = 0;
        for (Card card : simplifiedHand.getCards()) {
            if (card.equals(simplifiedHand.getSelectedCard())) {
                handImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath())),831,556,true,true,false));
                layout.makeGlow(handImages.get(index));
            } else {
                handImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())),831,556,true,true,false));
                handImages.get(index).setEffect(null);
            }
            index++;
        }
        if(simplifiedHand.getCards().size() == 2){
            handImages.get(2).setImage(null);
        }

    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        playablePositions.clear();

        if (personalBoard.getSecretMission() != null) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getSecretMission().getFront().getImagePath())), 415, 278, true, false, false));
            this.setCardImageParameters(imageView, 0);
            Platform.runLater(() -> {
                this.secretMissionBox.getChildren().setAll(imageView);
            });
        }
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath())), 415, 278, true, false, false));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);
        for (Point point : personalBoard.getPlayablePositions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "playable-position.png")), 415, 278, true, false, false));
            imageView.setOpacity(0.3);
            imageView.setVisible(false);
            addImage(imageView, this.xPositionStarterCard + point.getX(),
                    this.yPositionStarterCard - point.getY(), this.gridPane);

            playablePositions.add(imageView);
        }
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
            consideredTab.setId("1");
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
                getClass().getResource(path + otherPersonalBoard.getOccupiedPositions().getLast().getSide().getImagePath())), 415, 278, true, false, false));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + otherPersonalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - otherPersonalBoard.getOccupiedPositions().getLast().getY(), otherGridPane);

    }

    @Override
    public void changeGUIGame (SimplifiedGame simplifiedGame) {
        Pawn pawn;
        for (Tab tab : personalBoardTabPane.getTabs()) {
            pawn = simplifiedGame.getPawnsSelected().get(tab.getText());
            if(pawn != null) {
                tab.getStyleClass().add(pawn.toString());
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.personalBoardTabPane.getTabs().getFirst().setId("0");

        this.handImages = new ArrayList<>();
        for(int i = 0; i< 3; i++){
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView, i);
            makeDraggable(imageView, playablePositions);
            imageView.setOnMouseClicked(this::onHandCardClicked);
            handImages.add(imageView);
        }

        Platform.runLater(() -> {
            this.handPane.getChildren().setAll(handImages);
            layout.handLayout(rootBorder, handImages, handPane);
        });

        this.resourceCommonTableImages = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView,i);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            this.resourceCommonTableImages.add(imageView);
        }

        this.goldCommonTableImages = new ArrayList<>();
        for(int i = 3; i < 6; i++){
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView,i);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            this.goldCommonTableImages.add(imageView);
        }

        this.commonMissionsCommonTableImages = new ArrayList<>();
        for(int i = 6; i < 8; i++){
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView,i);
            this.commonMissionsCommonTableImages.add(imageView);
        }
        Platform.runLater(() -> {
            this.commonMissionsBox.getChildren().setAll(commonMissionsCommonTableImages);
            this.resourceCardBox.getChildren().setAll(resourceCommonTableImages);
            this.goldCardBox.getChildren().setAll(goldCommonTableImages);
            layout.cardsLayout(rootBorder, resourceCommonTableImages);
            layout.cardsLayout(rootBorder, goldCommonTableImages);
            layout.cardsLayout(rootBorder, commonMissionsCommonTableImages);
        });

        //buttons setup
        layout.buttonSetup(scoreIcon, scoreBoardButton);
        scoreBoardButton.setOnAction(this::toggleScoreBoard);
        layout.buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);


        //page layout and dimensions bindings
        layout.pageBindings(rootPane, rootBorder, background);

        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);
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

    private void setCardImageParameters(ImageView imageView, int index) {
        imageView.setId(String.valueOf(index));
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
    }

    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setCardImageParameters(imageView, 0);
        if(gridPane != null){
            Platform.runLater(() -> {
                gridPane.add(imageView, x, y);
            });
        }
    }

    public void makeDraggable(ImageView imageView, ArrayList<ImageView> targets) {
        // Set on card pressed
        imageView.setOnMousePressed(event -> {
            if (event.getClickCount() == 2) {
                try {
                    int index = Integer.parseInt(((ImageView) event.getSource()).getId());
                    this.mainClient.getVirtualGameController().selectCardFromHand(index, this.mainClient.getClientID());
                    this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            initialX = imageView.getLayoutX();
            initialY = imageView.getLayoutY();
            mouseAnchorX = event.getSceneX() - initialX;
            mouseAnchorY = event.getSceneY() - initialY;
        });

        // Set on card dragging
        imageView.setOnMouseDragged(event -> {
            imageView.setLayoutX(event.getSceneX() - mouseAnchorX);
            imageView.setLayoutY(event.getSceneY() - mouseAnchorY);

            for (ImageView target : targets) {
                target.setVisible(true);
            }
        });

        // Set on card released
        imageView.setOnMouseReleased(event -> {
            for (ImageView target : targets) {
                //control if is a target and if the tab is the right one
                if (isInTargetSpot(imageView, target) && this.personalBoardTabPane.getSelectionModel().getSelectedItem().getId().equals("0")) {

                    try {
                        int row = GridPane.getRowIndex(target);
                        int column = GridPane.getColumnIndex(target);

                        for(ImageView p: playablePositions) {
                            this.gridPane.getChildren().remove(p);
                        }

                        this.mainClient.getVirtualGameController().selectCardFromHand(Integer.parseInt(imageView.getId()), this.mainClient.getClientID());
                        this.mainClient.getVirtualGameController().selectPositionOnBoard(column - xPositionStarterCard, yPositionStarterCard - row, this.mainClient.getClientID());
                        this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                    } catch (RemoteException e) {
                        // throw new RuntimeException(e);
                    }

                    break;
                }
            }
            imageView.setLayoutX(initialX);
            imageView.setLayoutY(initialY);
            for (ImageView target : targets) {
                target.setVisible(false);
            }
        });
    }

    public void onHandCardClicked(MouseEvent mouseEvent) {
        try {
            int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
            this.mainClient.getVirtualGameController().selectCardFromHand(index, this.mainClient.getClientID());
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait!");
        }
    }

    private boolean isInTargetSpot(ImageView imageView, ImageView target) {
        Bounds imageViewBounds = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds targetBounds = target.localToScene(target.getBoundsInLocal());

        if (!personalBoardTabPane.getChildrenUnmodifiable().getFirst().localToScene(personalBoardTabPane.getChildrenUnmodifiable().getFirst().getBoundsInLocal()).contains(imageViewBounds.getMinX() + imageViewBounds.getWidth() / 2,
                imageViewBounds.getMinY() + imageViewBounds.getHeight() / 2)) {
            return false;
        }

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
        newScrollPane.setPadding(new Insets(5, 0, 5, 5));
        chatTabPane.getTabs().add(newTab);
        newTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newTab);
            }
            Platform.runLater(() -> newScrollPane.setVvalue(1.0));
            keyEvent.consume();
        });
        newButton.setOnMouseClicked(event -> {
            if (!newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newTab);
            }
            Platform.runLater(() -> newScrollPane.setVvalue(1.0));
            event.consume();
        });
        Platform.runLater(() -> newScrollPane.setVvalue(1.0));
        chats.put(nickname, newScrollPane);
    }

    private void sendMessage(TextField newTextField, Tab newTab) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait!");
        }
        newTextField.clear();

    }

    @Override
    public void createChats(SimplifiedGame simplifiedGame, String nickname) {
        if (!chatHasBeenCreated) {
            this.nickname = nickname;
            for (String playerNickname : simplifiedGame.getPlayersNicknames()) {
                if (!playerNickname.equals(nickname)) {
                    createChatTab(playerNickname);
                }
            }
            createChatTab("Group Chat");
            chatHasBeenCreated = true;
        }

    }

    @Override
    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
        if (!newMessage.getSender().getNickname().equals(this.nickname)) {
            if (newMessage.getReceiver() == null || newMessage.getReceiver().getNickname().isEmpty()) {
                if (!newMessage.getSender().getNickname().equals(nickname)) {
                    if (simplifiedChat.getMessages().size() == 1 || (simplifiedChat.getMessages().size() > 1 &&
                            !newMessage.getSender().getNickname().equals(simplifiedChat.getMessages().get(simplifiedChat.getMessages().size() - 2).getSender().getNickname()))) {
                        addMessageInChat(newMessage.getText(), "Group Chat", newMessage.getSender().getNickname());
                    } else {
                        addMessageInChat(newMessage.getText(), "Group Chat", null);
                    }
                }
            } else {
                addMessageInChat(newMessage.getText(), newMessage.getSender().getNickname(), null);
            }
        } else {
            if (newMessage.getReceiver() == null || newMessage.getReceiver().getNickname().isEmpty()) {
                addMessageFromSender(newMessage.getText(), "Group Chat");
            } else {
                addMessageFromSender(newMessage.getText(), newMessage.getReceiver().getNickname());
            }
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


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    hBox.getChildren().add(textFlow);
                    if (labelMessage != null) {
                        ((VBox) chats.get(sender).getContent()).getChildren().add(labelBox);
                    }
                    ((VBox) chats.get(sender).getContent()).getChildren().add(hBox);
                    chats.get(sender).setVvalue(1.0);
                } catch (NullPointerException e) {

                }
            }
        });
    }

    public void toggleScoreBoard(ActionEvent actionEvent) {
        if (chatIsVisible) {
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonClose");
            anchorPaneChat.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            chatIsVisible = false;
        }
        if (scoreBoardIsVisible) {
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonClose");
            anchorPaneScoreBoard.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            scoreBoardIsVisible = false;
        } else {
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonVisible");
            anchorPaneScoreBoard.setTranslateX(0);
            HBoxLeftPanel.setMinWidth(380);
            HBoxLeftPanel.setMaxWidth(380);
            scoreBoardIsVisible = true;
        }
    }

    public void toggleChat(ActionEvent actionEvent) {
        if (scoreBoardIsVisible) {
            scoreBoardButton.getStyleClass().clear();
            scoreBoardButton.getStyleClass().add("buttonClose");
            anchorPaneScoreBoard.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            scoreBoardIsVisible = false;
        }
        if (chatIsVisible) {
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonClose");
            anchorPaneChat.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            chatIsVisible = false;
        } else {
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonVisible");
            anchorPaneChat.setTranslateX(-270);
            HBoxLeftPanel.setMinWidth(380);
            HBoxLeftPanel.setMaxWidth(380);
            chatIsVisible = true;
        }
    }

    private void clearScoreBoard() {
        for (Point pawnPoint : PawnsCoords.getCoords()) {
            Node cell = getNodeByRowColumnIndex(pawnPoint.getY(), pawnPoint.getX(), scoreBoardGrid);
            if (cell != null) {
                GridPane miniGrid = ((GridPane) cell);
                miniGrid.getChildren().clear();
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

    public void addMessageServerDisplayer(String messageFromServer, boolean isErrorMessage) {
        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setMinWidth(180);
        textFlow.setMaxWidth(180);
        if (isErrorMessage) {
            text.setFill(Color.color(1, 0.0, 0.0));
        } else {
            text.setFill(Color.color(0.0, 0.0, 0.0));
        }

        try {
            Platform.runLater(() -> {
                if (serverMessagesDisplayer.getChildren().size() > 10) {
                    serverMessagesDisplayer.getChildren().remove(0);
                }
                serverMessagesDisplayer.getChildren().add(textFlow);
                serverMessagesDisplayer.layout();
                serverMessagesScrollPane.layout();
                serverMessagesScrollPane.setVvalue(1.0);
            });

        } catch (NullPointerException e) {
        }

    }

    public void updatePointScoreBoard(HashMap<String, Integer> scores, HashMap<String, Pawn> pawnsSelected) {
        Platform.runLater(() -> {
            clearScoreBoard();

            for (Map.Entry<String, Integer> playerScore : scores.entrySet()) {
                if (pawnsSelected.containsKey(playerScore.getKey())) {
                    Point pawnPoint = PawnsCoords.getCoords(playerScore.getValue());
                    Node cell = getNodeByRowColumnIndex(pawnPoint.getY(), pawnPoint.getX(), scoreBoardGrid);
                    if (cell != null) {
                        GridPane miniGrid = ((GridPane) cell);
                        ImageView image = new ImageView(new Image(getClass().getResource("images/pawns/" + pawnsSelected.get(playerScore.getKey()).toString().toLowerCase() + ".png").toExternalForm()));
                        image.setFitHeight(20);
                        image.setFitWidth(20);
                        switch (miniGrid.getChildren().size()) {
                            case 0:
                                miniGrid.add(image, 0, 0);
                                break;
                            case 1:
                                miniGrid.add(image, 0, 1);
                                break;
                            case 2:
                                miniGrid.add(image, 1, 0);
                                break;
                            case 3:
                                miniGrid.add(image, 1, 1);
                                break;
                        }
                    }
                }
            }
        });
    }

    private void addMessageFromSender(String message, String sender) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 30));
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.setStyle("-fx-background-color: rgb(15,125,242);" + "-fx-color: rgb(239, 242,255);" + "-fx-background-radius: 7px;");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(2, 5, 2, 5));
        text.setStyle("-fx-text-fill: white;");
        text.setFill(Color.color(1, 1, 1));
        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> {
            ((VBox) chats.get(sender).getContent()).getChildren().add(hBox);
            chats.get(sender).setVvalue(1.0);
        });
    }

    @FXML
    public void onPersonalBoardScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            scrollEvent.consume();

            final double zoomFactor = scrollEvent.getDeltaY() > 0 ? 1.05 : 1 / 1.05;

            Scale newScale = new Scale();
            newScale.setPivotX(scrollEvent.getX());
            newScale.setPivotY(scrollEvent.getY());
            newScale.setX(zoomFactor);
            newScale.setY(zoomFactor);

            gridPane.getTransforms().add(newScale);
        }
    }

    @FXML
    public void onPersonalBoardZoom(ZoomEvent zoomEvent) {
        zoomEvent.consume();

        final double zoomFactor = zoomEvent.getZoomFactor() > 1 ? 1.1 : 1 / 1.1;

        Scale newScale = new Scale();
        newScale.setPivotX(zoomEvent.getX());
        newScale.setPivotY(zoomEvent.getY());
        newScale.setX(zoomFactor);
        newScale.setY(zoomFactor);

        gridPane.getTransforms().add(newScale);
    }
}