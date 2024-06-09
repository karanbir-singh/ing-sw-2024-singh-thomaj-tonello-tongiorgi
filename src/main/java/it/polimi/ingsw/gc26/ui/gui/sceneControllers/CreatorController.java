package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class CreatorController extends GenericController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView background;

    @FXML
    private Button twoButton;
    @FXML
    private Button threeButton;
    @FXML
    private Button fourButton;

    @FXML
    private Label status;

    @FXML
    private Button playButton;

    @FXML
    private ImageView selector;
    @FXML
    private ImageView selector1;
    @FXML
    private ImageView selector2;

    private int numMaxPlayer = 2;
    private final CommonLayout layout = new CommonLayout();

    @FXML
    public void onSecondButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = 2;
        selector.setVisible(true);
        selector1.setVisible(false);
        selector2.setVisible(false);
    }
    @FXML
    public void onThirdButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = 3;
        selector1.setVisible(true);
        selector.setVisible(false);
        selector2.setVisible(false);
    }
    @FXML
    public void onFourthButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = 4;
        selector2.setVisible(true);
        selector1.setVisible(false);
        selector.setVisible(false);
    }

    @FXML
    public void onClickButton(ActionEvent actionEvent) {
        try { //TODO da cambiare il nickname
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(),this.nickname,this.numMaxPlayer);
            this.status.setText("Waiting for other players...");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setOnAction(this::onClickButton);

        twoButton.setOnAction(this::onSecondButtonClick);
        twoButton.getStyleClass().add("number-button");

        threeButton.setOnAction(this::onThirdButtonClick);
        threeButton.getStyleClass().add("number-button");

        fourButton.setOnAction(this::onFourthButtonClick);
        fourButton.getStyleClass().add("number-button");

        layout.setBackground(rootPane, background);
    }

}
