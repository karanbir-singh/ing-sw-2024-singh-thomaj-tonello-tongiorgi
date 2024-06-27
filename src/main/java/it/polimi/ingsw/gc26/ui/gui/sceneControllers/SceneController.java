package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.ui.gui.GUIApplication;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
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

/**
 * This abstract controller contains methods and attributes used by all the controllers.
 * It contains the methods to update the chat and set the common layout.
 */
abstract public class SceneController {
    /**
     * The main client associated with this controller
     */
    public MainClient mainClient;
    /**
     * The background image for the game scene.
     */
    private Image gameBackground = new Image(getClass().getResource("images/game-background.png").toExternalForm());
    /**
     * A map to hold chat windows identified by their names
     */
    private HashMap<String, ScrollPane> chats = new HashMap<>();
    /**
     * Tab pane to display different chat tabs
     */
    @FXML
    public TabPane chatTabPane;
    /**
     * The left panel HBox containing the chat and scoreboard
     */
    @FXML
    public HBox HBoxLeftPanel;
    /**
     * The anchor pane to hold chat related components
     */
    @FXML
    public AnchorPane anchorPaneChat;
    /**
     * Button to toggle chat visibility
     */
    @FXML
    public Button chatButton;
    /**
     * Flag to track the visibility state of the chat
     */
    protected boolean chatIsVisible = false;
    /**
     * Flag to track the creation of the chat tabs
     */
    private boolean chatHasBeenCreated = false;
    /**
     * Chat icon
     */
    public ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));

    /**
     * Sets main client reference
     *
     * @param mainClient
     */
    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    /**
     * Updates images in common table with the new values in simplified common table
     *
     * @param simplifiedCommonTable new common table
     */
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
    }

    /**
     * Updates player information
     *
     * @param simplifiedPlayer new simplified player
     */
    public void changeGUIPlayer(SimplifiedPlayer simplifiedPlayer) {
    }

    /**
     * Updates hand images with the new value in simplified hand
     *
     * @param simplifiedHand new player's hand
     */
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
    }

    /**
     * Updates secret hand images with the new ones in the update hand
     *
     * @param simplifiedSecretHand new hand
     */
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
    }

    /**
     * Updates cards drawn in the personal board with the new cards.
     *
     * @param personalBoard new player's personal board
     */
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
    }

    /**
     * Updates the personal board of another player
     *
     * @param otherPersonalBoard other player's personal board
     */
    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
    }

    /**
     * Modifies the color of each tab for every player that has selected its pawn
     *
     * @param simplifiedGame new simplified game
     */
    public void changeGUIGame(SimplifiedGame simplifiedGame) {
    }

    /**
     * Adds a message in the box where the messages from the server are displayed.
     *
     * @param messageFromServer new info message
     * @param isErrorMessage    if it is an error message, it will be displayed with red font
     */
    public void addMessageServerDisplayer(String messageFromServer, boolean isErrorMessage) {
    }

    /**
     * Updates points in the score board
     *
     * @param scores        map containing the updated scores
     * @param pawnsSelected pawns that have been selected by players
     */
    public void updatePointScoreBoard(HashMap<String, Integer> scores, HashMap<String, Pawn> pawnsSelected) {
    }

    /**
     * Sets styles and binding to base panes
     *
     * @param rootPane
     * @param rootBorder
     * @param background
     */
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

    /**
     * Sets styles for generic card
     *
     * @param rootBorder
     * @param cards
     */
    public void cardsLayout(BorderPane rootBorder, ArrayList<ImageView> cards) {
        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (ImageView card : cards) {
                card.setFitWidth(rootBorder.getWidth() * 0.13);
            }
        });
    }

    /**
     * Sets styles for generic hand
     *
     * @param rootBorder
     * @param handCards
     * @param handPane
     */
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

    /**
     * Adds the effect of glow to a generic card
     *
     * @param card card to be set the effect
     */
    public void makeGlow(ImageView card) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.CORNSILK);
        glow.setOffsetX(0f);
        glow.setOffsetY(0f);
        glow.setWidth(50);
        glow.setHeight(50);
        card.setEffect(glow);
    }

    /**
     * Sets styles for generic button
     *
     * @param icon   button's icon
     * @param button button to set styles
     */
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

    /**
     * Sets a new background to the root pane
     *
     * @param rootPane   scene's root pane
     * @param background new background
     */
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

    /**
     * Updates viewport when the dimension of the application changes.
     *
     * @param paneWidth
     * @param paneHeight
     * @param background
     * @param initialImageWidth
     * @param initialImageHeight
     */
    public void updateViewport(double paneWidth, double paneHeight, ImageView background, double initialImageWidth, double initialImageHeight) {
        double viewportWidth = Math.min(initialImageWidth, paneWidth);
        double viewportHeight = Math.min(initialImageHeight, paneHeight);

        double x = (initialImageWidth - viewportWidth) / 2;
        double y = (initialImageHeight - viewportHeight) / 2;

        background.setViewport(new Rectangle2D(x, y, viewportWidth, viewportHeight));
    }


    /**
     * Adds a message in chat from the player itself
     *
     * @param message new message
     * @param sender  nickname sender
     */
    public void addMessageFromSender(String message, String sender) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 30));
        Text text = new Text(message);
        text.getStyleClass().add("senderMessageText");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.getStyleClass().add("senderMessageBox");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setStyle("-fx-text-fill: white;");
        text.setFill(Color.color(1, 1, 1));
        hBox.getChildren().add(textFlow);
        AnchorPane.setLeftAnchor(hBox, 0.0);

        Platform.runLater(() -> {
            ((VBox) chats.get(sender).getContent()).getChildren().add(hBox);
            chats.get(sender).setVvalue(1.0);
        });
    }

    /**
     * Creates one chat with each player and the group chat
     *
     * @param simplifiedGame updated game
     */
    public void createChats(SimplifiedGame simplifiedGame) {
        if (!chatHasBeenCreated) {
            for (String playerNickname : simplifiedGame.getPlayersNicknames()) {
                if (!playerNickname.equals(this.mainClient.getNickname())) {
                    createChatTab(playerNickname);
                }
            }
            createChatTab("Group Chat");
            chatHasBeenCreated = true;
        }
    }

    /**
     * Creates a new chat tab for a player
     *
     * @param nickname nickname player new chat
     */
    public void createChatTab(String nickname) {
        Tab newTab = new Tab();
        newTab.setText(nickname);
        newTab.getStyleClass().add("chatTab");
        AnchorPane newAnchorPane = new AnchorPane();
        newTab.setContent(newAnchorPane);
        newAnchorPane.getStyleClass().add("chatPane");
        TextField newTextField = new TextField();
        newTextField.setPrefWidth(208);
        newTextField.setPrefHeight(26);
        newTextField.setPromptText("Type Message");
        newAnchorPane.getChildren().add(newTextField);
        AnchorPane.setBottomAnchor(newTextField, 10.0);
        AnchorPane.setLeftAnchor(newTextField, 10.0);
        Button newButton = new Button();
        newButton.setText("Send");
        newAnchorPane.getChildren().add(newButton);
        AnchorPane.setBottomAnchor(newButton, 10.0);
        AnchorPane.setRightAnchor(newButton, 10.0);
        ScrollPane newScrollPane = new ScrollPane();
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

    /**
     * Updates chat adding the last message received
     * If the last messages comes from another player, it adds the new message on the left side, but if it is a message
     * from the player itself, the new message is added on the right side
     *
     * @param simplifiedChat updated chat
     */
    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
        if (!newMessage.getSender().getNickname().equals(this.mainClient.getNickname())) {
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

    /**
     * Return true if the receiver nickname it has been explicit
     *
     * @param message new message in chat
     * @return true if the nickname is present
     */
    private boolean isReceiverUnknown(Message message) {
        return message.getReceiver() == null || message.getReceiver().getNickname().isEmpty();
    }

    /**
     * Return true if a message needs a label with the sender's nickname in the group chat
     *
     * @param simplifiedChat new chat
     * @return true if label is needed
     */
    private boolean isLabeled(SimplifiedChat simplifiedChat) {
        return simplifiedChat.getMessages().stream().filter(m -> m.getReceiver() == null || m.getReceiver().getNickname().isEmpty()).count() == 1
                || (simplifiedChat.getMessages().size() > 1 &&
                !simplifiedChat.getMessages().getLast().getSender().getNickname().equals(simplifiedChat.getMessages().get(simplifiedChat.getMessages().size() - 2).getSender().getNickname()));
    }

    /**
     * Adds message received from the server when the sender is not the player itself
     *
     * @param message      message to display
     * @param sender       nickname sender
     * @param labelMessage name to be shown in group chat
     */
    protected void addMessageInChat(String message, String sender, String labelMessage) {
        HBox labelBox = new HBox();
        Text labelText;
        TextFlow labelTextFlow;
        if (labelMessage != null) {
            labelBox.setAlignment(Pos.BASELINE_LEFT);
            labelBox.setPadding(new Insets(0, 30, 0, 5));
            labelText = new Text(labelMessage);
            labelText.getStyleClass().add("chatLabelText");
            labelTextFlow = new TextFlow(labelText);
            labelBox.setMinWidth(150);
            labelBox.setMaxWidth(150);
            labelBox.getChildren().add(labelTextFlow);
        }
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(5, 30, 5, 5));
        Text text = new Text(message);
        text.getStyleClass().add("messageText");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.getStyleClass().add("messageBox");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(5, 10, 5, 10));


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

    /**
     * Sent a new message request to the server
     *
     * @param newTextField filed containing the message in the chat tab
     * @param newTab       tab where the send button has been clicked
     */
    protected void sendMessage(javafx.scene.control.TextField newTextField, Tab newTab) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            ConsoleColors.printError("RemoteException while sending message!");
        }
        newTextField.clear();

    }

    /**
     * Toggles chat. If the scoreboard is opened, it is closed.
     *
     * @param actionEvent the event triggered by clicking the chat button
     */
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

    /**
     * Opens the PDF containing the game's rule
     * @param actionEvent event that triggers the action
     */
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
