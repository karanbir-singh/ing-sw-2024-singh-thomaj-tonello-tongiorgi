package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller manages the secret mission selection scene.
 * It allows the user to select the secret mission and chat with other players.
 */
public class SecretMissionChoiceController extends SceneController implements Initializable {
    /**
     * Tab pane for displaying chat tabs.
     */
    @FXML
    public TabPane chatTabPane;
    /**
     * Button to toggle chat visibility.
     */
    @FXML
    public Button chatButton;
    /**
     * Anchor pane to hold chat related components.
     */
    @FXML
    public AnchorPane anchorPaneChat;
    //CommonTable
    /**
     * The label to display the status of the game or player.
     */
    @FXML
    private Label status;
    /**
     * HBox that contains card elements.
     */
    @FXML
    private HBox cardHBox;
    /**
     * The HBox to hold secret mission cards.
     */
    @FXML
    private HBox secretMissionHBox;
    /**
     * The HBox to hold resource cards.
     */
    @FXML
    private HBox resourceHbox;
    /**
     * The HBox to hold gold cards.
     */
    @FXML
    private HBox goldHbox;
    //layout
    /**
     * Pane for displaying personal board tabs.
     */
    @FXML
    TabPane personalBoardTabPane;
    /**
     * The HBox for the left panel layout.
     */
    @FXML
    private HBox HBoxLeftPanel;
    /**
     * VBox for the right panel layout.
     */
    @FXML
    private VBox rightVBox;
    /**
     * The root border pane for the main layout.
     */
    @FXML
    private BorderPane rootBorder;
    /**
     * The root anchor pane for the main layout.
     */
    @FXML
    private AnchorPane rootPane;
    /**
     * The background image view for the scene.
     */
    @FXML
    private ImageView background;
    /**
     * The button to confirm selected mission.
     */
    @FXML
    private Button confirmButton;
    /**
     * The image view for the chat icon.
     */
    private final ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));
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
     * The grid pane for cards.
     */
    @FXML
    GridPane gridPane;
    /**
     * The starting x-position for cards in the grid pane.
     */
    private final int xPositionStarterCard = 40;
    /**
     * The starting y-position for cards in the grid pane.
     */

    private final int yPositionStarterCard = 40;
    /**
     * The column constraints for the grid pane.
     */
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    /**
     * The row constraints for the grid pane.
     */
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);
    /**
     * Saves the mission chosen by the user to display it while waiting for the other players.
     */
    private ImageView chosenMission;

    private final Label waitMessage = new Label("Great choice!\nNow please wait for the other players...");

    /**
     * Path to locate images
     */
    String path = "images/";

    /**
     * Handles the event when the button is clicked. Creates the select secret mission in the server
     *
     * @param mouseEvent event that triggers the function
     */
    public void onClickSecretMission(MouseEvent mouseEvent) {
        try {
            chosenMission = (ImageView) mouseEvent.getSource();
            int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectSecretMission(index, this.mainClient.getClientID());

            this.confirmButton.setVisible(true);
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait");
        }
    }

    /**
     * Handles the event when the button is clicked. Creates the set secret mission in the server
     */
    public void onClickConfirmButton() {
        try {
            this.mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
        } catch (RemoteException e) {
            System.out.println("Connection problem, please wait");
        }
        secretMissionHBox.getChildren().clear();
        secretMissionHBox.getChildren().add(chosenMission);
        waitMessage.wrapTextProperty().set(true);
        secretMissionHBox.getChildren().add(waitMessage);
    }

    /**
     * Updates images in common table with the new values in simplified common table
     *
     * @param simplifiedCommonTable new common table
     */
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

        Platform.runLater(() -> {
            this.rightVBox.getChildren().setAll(imageViewsCommonMissions);
            this.resourceHbox.getChildren().setAll(resources);
            this.goldHbox.getChildren().setAll(goldens);

        });
    }

    /**
     * Updates hans images with the new value in simplified hand
     *
     * @param simplifiedHand new player's hand
     */
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

    /**
     * Updates cards drawn in the personal board with the new cards.
     *
     * @param personalBoard new player's personal board
     */
    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);

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
                otherScrollPane = (ScrollPane) tab.getContent();
                otherGridPane = (GridPane) otherScrollPane.getContent();
                otherScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                otherScrollPane.getStyleClass().add("tabScrollPane");
            }
        }

        //se invece non esiste un tab, con quel nickname, crea un nuovo tab e crea un nuovo scrollPane e GridPane
        if (!otherPersonalBoard.getNickname().equals(this.mainClient.getNickname()) && !exist) {
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

            otherScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            otherScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            otherScrollPane.getStyleClass().add("tabScrollPane");
        }

        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + otherPersonalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + otherPersonalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - otherPersonalBoard.getOccupiedPositions().getLast().getY(), otherGridPane);
    }


    /**
     * Updates the secret hand with the new mission cards
     *
     * @param simplifiedSecretHand new mission cards
     */
    @Override
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
        if(simplifiedSecretHand.getCards().size() > 1) {
            ArrayList<ImageView> secretHand = new ArrayList<>();
            int index = 0;
            for (Card card : simplifiedSecretHand.getCards()) {
                ImageView imageView;
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
                this.setParameters(imageView, String.valueOf(index));
                imageView.setOnMouseClicked(this::onClickSecretMission);
                secretHand.add(imageView);
                if (card == simplifiedSecretHand.getSelectedCard()) {
                    makeGlow(imageView);
                }
                index++;
            }

            Platform.runLater(() -> {
                this.secretMissionHBox.getChildren().setAll(secretHand);
                this.secretMissionHBox.getChildren().add(confirmButton);
            });
        }
    }

    /**
     * Set styles for generic image view
     *
     * @param imageView
     * @param accessibleText
     */
    private void setParameters(ImageView imageView, String accessibleText) {
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
        imageView.setAccessibleText(accessibleText);

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
     * Adds an image in the grid pane. Used to set the starter card
     *
     * @param imageView card to be placed
     * @param x         x coordinate
     * @param y         y coordinate
     * @param gridPane  grid pane representing the personal board
     */
    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setParameters(imageView, "0");
        if(gridPane != null){
            Platform.runLater(() -> {
                gridPane.add(imageView, x, y);
            });
        }
    }

    @Override
    public void changeGUIGame (SimplifiedGame simplifiedGame) {
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
     * Sets up event handlers for buttons, cards and initializes the background styles.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);

        this.confirmButton.setVisible(false);

        pageBindings(rootPane, rootBorder, background);
        //buttons setup
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
        buttonSetup(rulesIcon, rulesButton);
    }
}
