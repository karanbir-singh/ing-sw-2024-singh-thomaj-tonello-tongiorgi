package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * This controller manages the starter card selection scene.
 * It allows the user to select the starter card.
 */
public class StarterCardChoiceController extends SceneController implements Initializable {
    /**
     * Pane to hold chat-related components.
     */
    @FXML
    private AnchorPane anchorPaneChat;
    /**
     * The tab pane for displaying chat tabs.
     */
    @FXML
    private TabPane chatTabPane;
    /**
     * Button to toggle chat visibility.
     */
    @FXML
    public Button chatButton;
    /**
     * The HBox to hold hand elements.
     */
    @FXML
    private HBox handHbox;

    // CommonTable elements
    /**
     * The image view for the first resource card.
     */
    @FXML
    private ImageView resourceCard0;
    /**
     * The image view for the second resource card.
     */
    @FXML
    private ImageView resourceCard1;
    /**
     * The image view for the resource deck.
     */
    @FXML
    private ImageView resourceDeck;
    /**
     * The image view for the first gold card.
     */
    @FXML
    private ImageView goldCard0;
    /**
     * The image view for the second gold card.
     */
    @FXML
    private ImageView goldCard1;
    /**
     * The image view for the gold deck.
     */
    @FXML
    private ImageView goldDeck;
    /**
     * VBox to hold common table elements.
     */
    @FXML
    private VBox commonTableBox;

    // Layout elements
    /**
     * The HBox for the left panel layout.
     */
    @FXML
    private HBox HBoxLeftPanel;
    /**
     * The VBox for the center panel layout.
     */
    @FXML
    private VBox centerVBox;
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
     * Background image view for the scene.
     */
    @FXML
    private ImageView background;
    /**
     * Image view for the chat icon.
     */
    private ImageView chatIcon = new ImageView(new Image(getClass().getResource("images/icons/chat-icon-white.png").toExternalForm()));
    /**
     * The button to show the rules.
     */
    @FXML
    private Button rulesButton;
    /**
     * The image view for the rules icon.
     */
    private ImageView rulesIcon = new ImageView(new Image(getClass().getResource("images/icons/rules-icon.png").toExternalForm()));
    /**
     * VBox for card and play button.
     */
    @FXML
    private VBox choosingBox;
    /**
     * The image view for the image.
     */
    @FXML
    private ImageView image;
    /**
     * The label to display the status of the game or player.
     */
    @FXML
    private Label status;
    /**
     * Button to choose the starter card's side to play.
     */
    @FXML
    private Button playButton;
    /**
     * Path for images used in the scene.
     */
    private String path = "images/";

    /**
     * Handles the confirm button. Creates a request on the server to play the selected card.
     *
     * @param event event that triggers the function
     */
    public void onClickGoToNextStep(ActionEvent event) {
        try {
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        playButton.setVisible(false);
        status.setText("Card selected! Please, wait for other players to select their side!");
    }

    /**
     * Handles the click on the cards. Crates a request to turn the selected side on the server.
     *
     * @param event event that triggers the function
     */
    public void onImageClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Updates hans images with the new value in simplified hand
     *
     * @param simplifiedHand new player's hand
     */
    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if (simplifiedHand.getSelectedSide() != null) {
            this.image.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath()))));
            this.image.setOnMouseClicked(this::onImageClick);
            makeGlow(this.image);
        }

    }

    /**
     * Updates images in common table with the new values in simplified common table
     *
     * @param simplifiedCommonTable new common table
     */
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        if (simplifiedCommonTable.getResourceCards().size() >= 2) {
            resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            resourceCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(1).getFront().getImagePath()))));
            resourceDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        }

        if (simplifiedCommonTable.getGoldCards().size() >= 2) {
            goldCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(0).getFront().getImagePath()))));
            goldCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(1).getFront().getImagePath()))));
            goldDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        }


    }

    /**
     * Initializes the controller.
     * Sets up event handlers for button, starter card and initializes the background.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pageBindings(rootPane, rootBorder, background);
        //buttons setup
        buttonSetup(chatIcon, chatButton);
        chatButton.setOnAction(this::toggleChat);
        buttonSetup(rulesIcon, rulesButton);
    }

}
