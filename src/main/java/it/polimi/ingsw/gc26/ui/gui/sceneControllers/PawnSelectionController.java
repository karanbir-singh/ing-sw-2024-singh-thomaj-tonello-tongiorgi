package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.ui.gui.GUIApplication;
import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class PawnSelectionController extends SceneController implements Initializable {

    public HBox HBoxLeftPanel;
    public AnchorPane anchorPaneChat;
    public TabPane chatTabPane;
    public Button chatButton;
    @FXML
    private Label status;
    CommonLayout layout = new CommonLayout();

    @FXML
    private HBox buttonHBox;
    private HashMap<String, ScrollPane> chats = new HashMap<>();

    private boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;
    private javafx.scene.image.ImageView chatIconVisible = new javafx.scene.image.ImageView(new javafx.scene.image.Image(getClass().getResource("/images/icons/chat-icon-white.png").toExternalForm()));
    private javafx.scene.image.ImageView chatIconClose = new ImageView(new Image(getClass().getResource("/images/icons/chat-icon-white.png").toExternalForm()));

    public void onClickButton(ActionEvent event){
        String pawnColor = ((Button)event.getSource()).getAccessibleText();
        try {
            this.mainClient.getVirtualGameController().choosePawnColor(pawnColor, this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDimensionAndColor(Button button, String color) {
        button.setPrefWidth(100);
        button.setPrefHeight(100);
        button.setStyle("-fx-background-color: " + color);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //buttons setup
        layout.buttonSetup(chatIconClose, chatIconVisible, chatButton);
        chatButton.setOnAction(this::toggleChat);

    }

    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame){
        ArrayList<Button> buttons = new ArrayList<>();
        for(Pawn pawn : simplifiedGame.getAvailablePawns()){
            Button button = new Button();
            button.setAccessibleText(pawn.toString());
            this.setDimensionAndColor(button,pawn.toString());
            button.setOnAction((this::onClickButton));
            buttons.add(button);
        }
        Platform.runLater(()->{
            buttonHBox.getChildren().setAll(buttons);
        });


    }


    public void openRulebook(ActionEvent actionEvent) {
        Platform.runLater(() -> GUIApplication.openRulebook());
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
            buttonHBox.setTranslateX(0);
        } else {
            chatButton.setGraphic(chatIconVisible);
            chatButton.getStyleClass().clear();
            chatButton.getStyleClass().add("buttonVisible");
            anchorPaneChat.setTranslateX(30);
            HBoxLeftPanel.setMinWidth(380);
            HBoxLeftPanel.setMaxWidth(380);
            chatIsVisible = true;
            buttonHBox.setTranslateX(500);
        }
    }

    private void addMessageInChat(String message, String sender, String labelMessage) {
        HBox labelBox = new HBox();
        Text labelText;
        TextFlow labelTextFlow;
        if (labelMessage != null) {
            labelBox.setAlignment(Pos.BASELINE_LEFT);
            labelBox.setPadding(new javafx.geometry.Insets(0, 30, 0, 5));
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
        hBox.setPadding(new javafx.geometry.Insets(5, 30, 5, 5));
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

    @Override
    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
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
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            Platform.runLater(() -> newScrollPane.setVvalue(1.0));
            keyEvent.consume();
        });
        newButton.setOnMouseClicked(event -> {
            if (!newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            Platform.runLater(() -> newScrollPane.setVvalue(1.0));
            event.consume();
        });
        Platform.runLater(() -> newScrollPane.setVvalue(1.0));
        chats.put(nickname, newScrollPane);
    }

    private void sendMessage(javafx.scene.control.TextField newTextField, VBox newVBox, ScrollPane newScrollPane, Tab newTab) {
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
        text.setStyle("-fx-font-smoothing-type: #a8a8a8 ;" + "-fx-text-fill: white;");
        text.setFill(Color.color(1, 1, 1));
        hBox.getChildren().add(textFlow);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.err.println("RemoteException while sending message!");
        }
        newTextField.clear();
        Platform.runLater(() -> {
            newVBox.getChildren().add(hBox);
            newScrollPane.setVvalue(1.0);
        });

    }
}
