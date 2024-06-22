package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.ui.gui.GUIApplication;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

abstract public class SceneController {
    public MainClient mainClient;
    public String nickname;
    private Image gameBackground = new Image(getClass().getResource("images/game-background.png").toExternalForm());

    private HashMap<String, ScrollPane> chats = new HashMap<>();
    @FXML
    public TabPane chatTabPane;
    @FXML
    public HBox HBoxLeftPanel;
    @FXML
    public AnchorPane anchorPaneChat;
    @FXML
    public Button chatButton;
    protected boolean chatIsVisible = false;
    private boolean chatHasBeenCreated = false;

    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public String getNickName() {
        return this.nickname;
    }

    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
    }

    public void changeGUIPlayer(SimplifiedPlayer simplifiedPlayer) {
    }

    public void changeGUIHand(SimplifiedHand simplifiedHand) {
    }

    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
    }

    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
    }

    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
    }

    public void changeGUIGame(SimplifiedGame simplifiedGame) {
    }

    public void addMessageServerDisplayer(String messageFromServer, boolean isErrorMessage) {
    }

    public void updatePointScoreBoard(HashMap<String, Integer> scores, HashMap<String, Pawn> pawnsSelected) {
    }

    // common layout
    public void pageBindings(AnchorPane rootPane, BorderPane rootBorder, ImageView background) {
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            rootBorder.setPrefHeight(newVal.doubleValue());
        });

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            rootBorder.setPrefWidth(newVal.doubleValue());
        });

        background.setImage(gameBackground);
        setBackground(rootPane, background);
    }


    public void cardsLayout(BorderPane rootBorder, ArrayList<ImageView> cards) {
        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (ImageView card : cards) {
                card.setFitWidth(rootBorder.getWidth() * 0.13);
            }
        });
    }

    public void handLayout(BorderPane rootBorder, ArrayList<ImageView> handCards, AnchorPane handPane) {
        double spacing = 20.0;
        handPane.setPrefWidth(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * 3);

        for (int i = 0; i < handCards.size(); i++) {
            ImageView card = handCards.get(i);
            card.setFitWidth(rootBorder.getWidth() * 0.13);
            card.setLayoutX(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * i);
        }

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            handPane.setPrefWidth(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * 3);

            for (int i = 0; i < handCards.size(); i++) {
                ImageView card = handCards.get(i);
                card.setFitWidth(rootBorder.getWidth() * 0.13);
                card.setLayoutX(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * i);
            }
        });
    }

    public void makeGlow(ImageView card) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.CORNSILK);
        glow.setOffsetX(0f);
        glow.setOffsetY(0f);
        glow.setWidth(50);
        glow.setHeight(50);
        card.setEffect(glow);
    }

    public void buttonSetup(ImageView icon, Button button) {
        double iconDimension = 30;
        double buttonDim = 50;

        icon.setFitWidth(iconDimension);
        icon.setFitHeight(iconDimension);
        button.setGraphic(icon);
        button.setPrefWidth(buttonDim);
        button.setPrefHeight(buttonDim);

        button.getStyleClass().clear();
        button.getStyleClass().add("buttonClose");
    }

    public void setBackground(AnchorPane rootPane, ImageView background) {
        double initialImageWidth = background.getImage().getWidth();
        double initialImageHeight = background.getImage().getHeight();

        background.fitHeightProperty().bind(rootPane.heightProperty());
        background.fitWidthProperty().bind(rootPane.widthProperty());

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateViewport(rootPane.getWidth(), rootPane.getHeight(), background, initialImageWidth, initialImageHeight);
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateViewport(rootPane.getWidth(), rootPane.getHeight(), background, initialImageWidth, initialImageHeight);
        });
    }

    public void updateViewport(double paneWidth, double paneHeight, ImageView background, double initialImageWidth, double initialImageHeight) {
        double viewportWidth = Math.min(initialImageWidth, paneWidth);
        double viewportHeight = Math.min(initialImageHeight, paneHeight);

        double x = (initialImageWidth - viewportWidth) / 2;
        double y = (initialImageHeight - viewportHeight) / 2;

        background.setViewport(new Rectangle2D(x, y, viewportWidth, viewportHeight));
    }

    // chat methods

    public void addMessageFromSender(String message, String sender) {
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

    public void createChatTab(String nickname) {
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
        newButton.setLayoutX(230);
        newButton.setLayoutY(489);
        newAnchorPane.getChildren().add(newButton);
        javafx.scene.control.ScrollPane newScrollPane = new javafx.scene.control.ScrollPane();
        newScrollPane.setMinHeight(440);
        newScrollPane.setPrefHeight(440);
        newScrollPane.setMinWidth(262);
        newScrollPane.setMaxWidth(262);
        newScrollPane.setLayoutX(9);
        newScrollPane.setLayoutY(14);
        newScrollPane.setPannable(true);
        newScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        newScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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


    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
        if (!newMessage.getSender().getNickname().equals(this.nickname)) {
            // message from another player
            if (isReceiverUnknown(newMessage)) {
                addMessageInChat(newMessage.getText(), "Group Chat", isLabeled(simplifiedChat) ? newMessage.getSender().getNickname() : null);
            } else {
                addMessageInChat(newMessage.getText(), newMessage.getSender().getNickname(), null);
            }
        } else {
            // message from myself
            addMessageFromSender(newMessage.getText(), isReceiverUnknown(newMessage) ? "Group Chat" : newMessage.getReceiver().getNickname());
        }
    }

    private boolean isReceiverUnknown(Message message) {
        return message.getReceiver() == null || message.getReceiver().getNickname().isEmpty();
    }

    private boolean isLabeled(SimplifiedChat simplifiedChat) {
        return simplifiedChat.getMessages().stream().filter(m -> m.getReceiver() == null || m.getReceiver().getNickname().isEmpty()).count() == 1
                || (simplifiedChat.getMessages().size() > 1 &&
                !simplifiedChat.getMessages().getLast().getSender().getNickname().equals(simplifiedChat.getMessages().get(simplifiedChat.getMessages().size() - 2).getSender().getNickname()));
    }

    protected void addMessageInChat(String message, String sender, String labelMessage) {
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

    protected void sendMessage(javafx.scene.control.TextField newTextField, Tab newTab) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.err.println("RemoteException while sending message!");
        }
        newTextField.clear();

    }

    protected void toggleChat(ActionEvent actionEvent) {
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
            anchorPaneChat.setTranslateX(30);
            HBoxLeftPanel.setMinWidth(380);
            HBoxLeftPanel.setMaxWidth(380);
            chatIsVisible = true;
        }
    }

    public void openRulebook(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                GUIApplication.openRulebook(getClass().getResourceAsStream("CODEX_Rulebook_EN.pdf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
