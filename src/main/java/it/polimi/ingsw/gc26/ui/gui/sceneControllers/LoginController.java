package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * This controller manages the login scene.
 * It allows the user to insert his nickname.
 */
public class LoginController extends SceneController implements Initializable {

    @FXML
    private Label status;
    /**
     * Text field where the player is writes its name
     */
    @FXML
    private TextField nicknameTXT;
    /**
     * Button that triggers connection request
     */
    @FXML
    private Button loginButton;
    /**
     * Scene's background
     */
    @FXML
    private ImageView background;
    /**
     * Codex naturalis logo
     */
    @FXML
    private ImageView logo;
    /**
     * Root object containing all other panes and objects as children.
     */
    @FXML
    private AnchorPane rootPane;
    /**
     * Pane where the logo is contained
     */
    @FXML
    private AnchorPane logoPane;
    /**
     * Logo box
     */
    @FXML
    private HBox logoBox;
    /**
     * Vbox containing the login button and the text field
     */
    @FXML
    private VBox loginVBox;

    /**
     * Displayes message from the server
     * @param message string to be displayed
     */
    public void setStatus(String message) {
        this.status.setText(message);
        this.status.setVisible(true);
    }

    /**
     * Manages the click event and performs the connection request to the sever.
     *
     * @param event the event triggered by clicking the login button
     */
    public void onLoginButtonClick(ActionEvent event) {
        if (nicknameTXT.getText().isEmpty()) {
            status.setText("Insert again, not valid nickname");
            status.setVisible(true);
        } else {
            try {
                this.setNickName(nicknameTXT.getText());
                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), this.nickname, this.mainClient.getClientState());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Returns player's nickname
     *
     * @return
     */
    public String getText() {
        return this.nicknameTXT.getText();
    }

    /**
     * Initializes the controller.
     * Sets up event handlers for nickname text field and styles.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the resources are not specified
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground(rootPane, background);

        loginButton.setOnAction(this::onLoginButtonClick);
        nicknameTXT.setOnAction(event -> {
            loginButton.fire();
        });

        logo.fitWidthProperty().bind(rootPane.heightProperty());
        logoPane.setPrefHeight(rootPane.getHeight());

        AnchorPane.setTopAnchor(logoPane, 20.0);
        AnchorPane.setRightAnchor(logoPane, rootPane.getWidth() * 0.05);
        AnchorPane.setRightAnchor(loginVBox, (rootPane.getWidth() - loginVBox.getWidth()) / 2);

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setRightAnchor(loginVBox, (rootPane.getWidth() - loginVBox.getWidth()) / 2);

            if (rootPane.getWidth() < 800) {
                AnchorPane.setRightAnchor(logoPane, (rootPane.getWidth() - logoPane.getWidth()) / 2);
                AnchorPane.setBottomAnchor(logoBox, 70.0);
            } else {
                AnchorPane.setRightAnchor(logoPane, rootPane.getWidth() * 0.05);
                AnchorPane.setBottomAnchor(logoBox, 0.0);
            }
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            logo.setFitHeight(rootPane.getHeight() * 0.5);

            if (rootPane.getHeight() < 650 && rootPane.getWidth() < 800) {
                AnchorPane.setBottomAnchor(logoBox, 0.0);
            }
        });
    }
}
