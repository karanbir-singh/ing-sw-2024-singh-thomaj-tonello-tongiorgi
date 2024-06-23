package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.ui.gui.PawnsCoords;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This controller manages the game flow scene where the user can play tge game.
 * It allows the user to chat with other player, play cards in its personal board, see the point is the scoreboard
 * see common mission and common table.
 */
public class GameFlowController extends SceneController implements Initializable {
    /**
     * Pane with chat tabs as children
     */
    @FXML
    private AnchorPane anchorPaneChat;
    /**
     * When clicked, it toggles the chat
     */
    @FXML
    private Button chatButton;
    /**
     * Icon for chat button
     */
    private final ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));
    /**
     * When clicked, it toggles the score board
     */
    @FXML
    private Button scoreBoardButton;
    /**
     * Pane with grid pane and score board image as children
     */
    @FXML
    private AnchorPane anchorPaneScoreBoard;
    /**
     * Grid to map every point as a cell
     */
    @FXML
    private GridPane scoreBoardGrid;
    /**
     * Flag used to toggle scoreboard
     */
    private boolean scoreBoardIsVisible = false;
    /**
     * Icon for score board
     */
    private final ImageView scoreIcon = new ImageView(new Image(getClass().getResource("images/icons/sparkle-icon-white.png").toExternalForm()));
    /**
     * Pane with card in hand as children
     */
    @FXML
    private AnchorPane handPane;
    /**
     * Box with resource card as children
     */
    @FXML
    private HBox resourceCardBox;
    /**
     * bod with gold cards as children
     */
    @FXML
    private HBox goldCardBox;
    /**
     * Box with two common mission as children
     */
    @FXML
    private VBox commonMissionsBox;
    /**
     * Box with secret mission as children
     */
    @FXML
    private VBox secretMissionBox;
    /**
     * Resources in the personal board
     */
    private ArrayList<ImageView> resources = new ArrayList<>();
    /**
     * Gold cards' list
     */
    private ArrayList<ImageView> goldens = new ArrayList<>();
    /**
     * Common missions. Two images as children.
     */
    private ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();
    /**
     * Grid where to place the played cards
     */
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane personalBoardPane;

    @FXML
    private TabPane personalBoardTabPane;
    /**
     * Coordinate 0 in the grid where the cards are placed.
     */
    private final int xPositionStarterCard = 40;
    /**
     * Coordinate 0 in the grid where the cards are placed.
     */
    private final int yPositionStarterCard = 40;
    /**
     * Default dimensions for each column
     */
    private final ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    /**
     * Default dimensions for each row
     */
    private final RowConstraints rowConstraints = new RowConstraints(60, 60, 60);
    /**
     * Root object containing all other panes and objects as children.
     */
    @FXML
    private AnchorPane rootPane;
    /**
     * Box used to give space to chat and scoreboard. Contains the scroll pane.
     */
    @FXML
    private HBox HBoxLeftPanel;
    /**
     * Pane containing the main structure of the scene.
     */
    @FXML
    private BorderPane rootBorder;
    /**
     * Scene's background
     */
    @FXML
    private ImageView background;
    /**
     * Positions in the grid where a card can be played
     */
    private final ArrayList<ImageView> playablePositions = new ArrayList<>();
    /**
     * Path to images resources
     */
    private String path = "images/";

    /**
     * Positions needed to drag the cards in the personal board
     */
    private double mouseAnchorX, mouseAnchorY, initialX, initialY;
    /**
     * Box where info messages from the server are displayed
     */
    @FXML
    public VBox serverMessagesDisplayer;
    /**
     * Scroll pane containing the VBox serverMessagesDisplayer object
     */
    @FXML
    private ScrollPane serverMessagesScrollPane;

    /**
     * List of images to use during the game
     */
    private ArrayList<ImageView> handImages, resourceCommonTableImages, goldCommonTableImages, commonMissionsCommonTableImages;
    /**
     * The button to show the rules.
     */
    @FXML
    private Button rulesButton;
    /**
     * The image view for the rules icon.
     */
    private final ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));


    /**
     * Selects the position and draws the card that has been double-clicked
     *
     * @param mouseEvent the event triggered by clicking the card
     */
    @FXML
    public void onClickCommonTableCard(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            try {
                int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
                this.mainClient.getVirtualGameController().selectCardFromCommonTable(index, this.mainClient.getClientID());
                this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
            } catch (RemoteException e) {
                System.out.println("Connection problem, please wait!");
            }
        }
    }


    /**
     * Updates images in common table with the new values in simplified common table
     *
     * @param simplifiedCommonTable new common table
     */
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        int index = 0;
        for (Card card : simplifiedCommonTable.getResourceCards()) {
            resourceCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())), 831, 556, true, true, false));
            index++;
        }
        resourceCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath())), 831, 556, true, true, false));

        index = 0;
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            goldCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())), 831, 556, true, true, false));

            index++;
        }
        goldCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath())), 831, 556, true, true, false));

        index = 0;
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            commonMissionsCommonTableImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())), 831, 556, true, true, false));
            index++;
        }

    }

    /**
     * Updates hans images with the new value in simplified hand
     *
     * @param simplifiedHand new player's hand
     */
    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        // Update hand
        int index = 0;
        for (Card card : simplifiedHand.getCards()) {
            if (card.equals(simplifiedHand.getSelectedCard())) {
                handImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath())), 831, 556, true, true, false));
                makeGlow(handImages.get(index));
            } else {
                handImages.get(index).setImage(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath())), 831, 556, true, true, false));
                handImages.get(index).setEffect(null);
            }
            index++;
        }
        if (simplifiedHand.getCards().size() == 2) {
            handImages.get(2).setImage(null);
        }

    }

    /**
     * Updates cards drawn in the personal board with the new cards.
     *
     * @param personalBoard new player's personal board
     */
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

    /**
     * Updates the personal board of another player
     *
     * @param otherPersonalBoard other player's personal board
     */
    @Override
    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        boolean exist = false;
        Tab consideredTab = null;
        ScrollPane otherScrollPane = null;
        GridPane otherGridPane = null;
        // if already exists a tab for the personal board's owner, gets its scroll pane and grid pane
        for (Tab tab : personalBoardTabPane.getTabs()) {
            if (tab.getText().equals(otherPersonalBoard.getNickname())) {
                exist = true;
                consideredTab = tab;
                otherScrollPane = (ScrollPane) tab.getContent();
                otherGridPane = (GridPane) otherScrollPane.getContent();
                otherScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.getStyleClass().add("tabScrollPane");
                break;
            }
        }

        // if there's no tab for the personal board's owner, creates a new tab pane for its personal board
        if (!otherPersonalBoard.getNickname().equals(this.nickname) && !exist) {
            consideredTab = new Tab();
            consideredTab.setText(otherPersonalBoard.getNickname());
            //consideredTab.setText("1");
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

    /**
     * Modifies the color of each tab for every player that has selected its pawn
     *
     * @param simplifiedGame new simplified game
     */
    @Override
    public void changeGUIGame(SimplifiedGame simplifiedGame) {
        Pawn pawn;
        for (Tab tab : personalBoardTabPane.getTabs()) {
            pawn = simplifiedGame.getPawnsSelected().get(tab.getText());
            if(pawn != null) {
                tab.setId(pawn.name());
            }
        }
    }

    @Override
    public void changeGUIPlayer (SimplifiedPlayer simplifiedPlayer) {
        if(simplifiedPlayer.getPawnColor() != null) {
            personalBoardTabPane.getTabs().getFirst().setId(simplifiedPlayer.getPawnColor().name());
        }
    }

    /**
     * Initializes the controller.
     * Sets up event handlers for buttons and initializes the background, cards, chats and scoreboard.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.handImages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView, i);
            imageView.getStyleClass().add("cardHover");
            makeDraggable(imageView, playablePositions);
            imageView.setOnMouseClicked(this::onHandCardClicked);
            handImages.add(imageView);
        }

        Platform.runLater(() -> {
            this.handPane.getChildren().setAll(handImages);
            handLayout(rootBorder, handImages, handPane);
        });

        this.resourceCommonTableImages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView, i);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageView.getStyleClass().add("cardHover");
            this.resourceCommonTableImages.add(imageView);
        }

        this.goldCommonTableImages = new ArrayList<>();
        for (int i = 3; i < 6; i++) {
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView, i);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageView.getStyleClass().add("cardHover");
            this.goldCommonTableImages.add(imageView);
        }

        this.commonMissionsCommonTableImages = new ArrayList<>();
        for (int i = 6; i < 8; i++) {
            ImageView imageView = new ImageView();
            this.setCardImageParameters(imageView, i);
            this.commonMissionsCommonTableImages.add(imageView);
        }
        Platform.runLater(() -> {
            this.commonMissionsBox.getChildren().setAll(commonMissionsCommonTableImages);
            this.resourceCardBox.getChildren().setAll(resourceCommonTableImages);
            this.goldCardBox.getChildren().setAll(goldCommonTableImages);
            cardsLayout(rootBorder, resourceCommonTableImages);
            cardsLayout(rootBorder, goldCommonTableImages);
            cardsLayout(rootBorder, commonMissionsCommonTableImages);
        });

        //buttons setup
        buttonSetup(scoreIcon, scoreBoardButton);
        scoreBoardButton.setOnAction(this::toggleScoreBoard);
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
        buttonSetup(rulesIcon, rulesButton);


        //page layout and dimensions bindings
        pageBindings(rootPane, rootBorder, background);

        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);
    }

    /**
     * Populates the grid pane representing the personal board
     *
     * @param gridPane pane inside players tab
     */
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

    /**
     * Standards card images parameters
     *
     * @param imageView image to be standard
     * @param index     card's index in the hand
     */
    private void setCardImageParameters(ImageView imageView, int index) {
        imageView.setId(String.valueOf(index));
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
    }

    /**
     * Adds an image in the personal board using the run later function
     *
     * @param imageView new image to be added in the grid
     * @param x         x coordinate
     * @param y         y coordinate
     * @param gridPane  destination grid pane
     */
    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setCardImageParameters(imageView, 0);
        //TODO capire perché a volte è nullo
        if(gridPane != null) {
            Platform.runLater(() -> {
                gridPane.add(imageView, x, y);
            });
        }
    }

    /**
     * Sets properties on image to make it draggable
     *
     * @param imageView image to make draggable
     * @param targets   positions where a card can be placed on the personal board
     */
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
                if (isInTargetSpot(imageView, target) && this.personalBoardTabPane.getSelectionModel().getSelectedItem().equals(
                        this.personalBoardTabPane.getTabs().getFirst())) {

                    try {
                        int row = GridPane.getRowIndex(target);
                        int column = GridPane.getColumnIndex(target);

                        for (ImageView p : playablePositions) {
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

    /**
     * Handles the event when the card is clicked. Selects a card from hand in the server
     *
     * @param mouseEvent the event triggered by clicking the card
     */
    public void onHandCardClicked(MouseEvent mouseEvent) {
        try {
            int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
            this.mainClient.getVirtualGameController().selectCardFromHand(index, this.mainClient.getClientID());
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait!");
        }
    }

    /**
     * Checks if a card has been released in a playable position
     *
     * @param imageView image released by player
     * @param target    playable position
     * @return true if the imageView covers a playable position
     */
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

    /**
     * Toggles scoreboard. If the chat is opened, it is closed.
     *
     * @param actionEvent the event triggered by clicking the score board button
     */
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

    /**
     * Toggles chat. If the scoreboard is opened, it is closed.
     *
     * @param actionEvent the event triggered by clicking the chat button
     */
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

    /**
     * Returns the element in a specific position in a grid pane
     *
     * @param row      row index. int > 0
     * @param column   column index. int > 0
     * @param gridPane grid pane containing the cell to be obtained
     * @return Node object is the position is valid and there's an object in the cell, null otherwise
     */
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row
                    && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    /**
     * Adds a message in the box where the messages from the server are displayed.
     *
     * @param messageFromServer new info message
     * @param isErrorMessage    if it is an error message, it will be displayed with red font
     */
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

    /**
     * Clears all nodes in scoreboard
     */
    private void clearScoreBoard() {
        for (Point pawnPoint : PawnsCoords.getCoords()) {
            Node cell = getNodeByRowColumnIndex(pawnPoint.getY(), pawnPoint.getX(), scoreBoardGrid);
            if (cell != null) {
                GridPane miniGrid = ((GridPane) cell);
                miniGrid.getChildren().clear();
            }
        }
    }

    /**
     * Updates points in the score board
     *
     * @param scores        map containing the updated scores
     * @param pawnsSelected pawns that have been selected by players
     */
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

    /**
     * Manages the scroll event on the personal board
     *
     * @param scrollEvent the event triggered by scrolling on personal board
     */
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

    /**
     * Manages the zoom event on the personal board
     *
     * @param zoomEvent the event triggered by zooming on the personal board
     */
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