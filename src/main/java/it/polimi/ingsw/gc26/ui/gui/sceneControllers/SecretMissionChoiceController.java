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
    private AnchorPane rootPane;
    @FXML
    private ImageView background;
    @FXML
    private Button confirmButton;
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    private boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;
    private ImageView chatIconVisible = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));
    private ImageView chatIconClose = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

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

        for (Card card : simplifiedCommonTable.getResourceCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            resources.add(imageView);
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setParameters(resourceDeck, String.valueOf(0));
        resources.add(resourceDeck);
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            goldens.add(imageView);
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setParameters(goldDeck, String.valueOf(0));
        goldens.add(goldDeck);
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            imageViewsCommonMissions.add(imageView);
        }

        Platform.runLater(()->{
            this.rightVBox.getChildren().addAll(imageViewsCommonMissions);
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

        layout.pageBindings(rootPane, rootBorder, background);
        //buttons setup
        layout.buttonSetup(chatIconClose, chatIconVisible, chatButton);
        chatButton.setOnAction(this::toggleChat);
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

    private void createChatTab(String nickname) {
        Tab newTab = new Tab();
        newTab.setText(nickname);
        newTab.setStyle("-fx-border-radius: 0px 0px 5px 5px;");
        AnchorPane newAnchorPane = new AnchorPane();
        newTab.setContent(newAnchorPane);
        newAnchorPane.setStyle("-fx-background-color: #e8f4f8");
        javafx.scene.control.TextField newTextField = new javafx.scene.control.TextField();
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
        javafx.scene.control.ScrollPane newScrollPane = new javafx.scene.control.ScrollPane();
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

    private void sendMessage(javafx.scene.control.TextField newTextField, Tab newTab) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.err.println("RemoteException while sending message!");
        }
        newTextField.clear();

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

    public void toggleChat(ActionEvent actionEvent) {

        if (chatIsVisible) {
            chatButton.setGraphic(chatIconClose);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonClose");
            anchorPaneChat.setTranslateX(-2000);
            HBoxLeftPanel.setMinWidth(40);
            HBoxLeftPanel.setMaxWidth(40);
            chatIsVisible = false;
            //buttonHBox.setTranslateX(0);
        } else {
            chatButton.setGraphic(chatIconVisible);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonVisible");
            anchorPaneChat.setTranslateX(30);
            HBoxLeftPanel.setMinWidth(380);
            HBoxLeftPanel.setMaxWidth(380);
            chatIsVisible = true;
            //buttonHBox.setTranslateX(500);
        }
    }


}
