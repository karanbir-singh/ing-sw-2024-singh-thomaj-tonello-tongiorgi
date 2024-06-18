package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
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
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StarterCardChoiceController extends SceneController implements Initializable {
    public AnchorPane anchorPaneChat;
    public TabPane chatTabPane;
    public Button chatButton;
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
    private AnchorPane rootPane;
    @FXML
    private ImageView background;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    private boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;
    private ImageView chatIconVisible = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));
    private ImageView chatIconClose = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

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
