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
    private RadioButton secondRadioButton;

    @FXML
    private RadioButton thirdRadioButton;

    @FXML
    private RadioButton fourthRadioButton;

    @FXML
    private Label status;

    @FXML
    private Button playButton;

    private int numMaxPlayer = 2;
    private final CommonLayout layout = new CommonLayout();
    private double initialImageWidth;
    private double initialImageHeight;

    @FXML
    public void onSecondRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(secondRadioButton.getText());
        this.thirdRadioButton.setSelected(false);
        this.fourthRadioButton.setSelected(false);
    }
    @FXML
    public void onThirdRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(thirdRadioButton.getText());
        this.secondRadioButton.setSelected(false);
        this.fourthRadioButton.setSelected(false);
    }
    @FXML
    public void onFourthRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(fourthRadioButton.getText());
        this.thirdRadioButton.setSelected(false);
        this.secondRadioButton.setSelected(false);
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
        secondRadioButton.setSelected(true);

        initialImageWidth = background.getImage().getWidth();
        initialImageHeight = background.getImage().getHeight();

        background.fitHeightProperty().bind(rootPane.heightProperty());
        background.fitWidthProperty().bind(rootPane.widthProperty());

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            layout.updateViewport(rootPane, background, initialImageWidth, initialImageHeight);
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            layout.updateViewport(rootPane, background, initialImageWidth, initialImageHeight);
        });

    }

    public void setStageListeners(Stage stage){
        rootPane.prefWidthProperty().bind(stage.widthProperty());
        rootPane.prefHeightProperty().bind(stage.heightProperty());
        background.fitHeightProperty().bind(stage.heightProperty());

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            rootPane.prefWidthProperty().bind(stage.widthProperty());
        });
    }
}
