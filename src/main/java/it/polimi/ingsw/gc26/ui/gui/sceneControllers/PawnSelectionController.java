package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.player.Pawn;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class PawnSelectionController extends GenericController implements Initializable {

    @FXML
    BorderPane rootBorder;

    @FXML
    Label status;

    @FXML
    Button redButton;
    @FXML
    Button greenButton;
    @FXML
    Button blueButton;
    @FXML
    Button yellowButton;

    private Image gameBackground = new Image(getClass().getResource("/images/game-background.png").toExternalForm());

    public void onClickRedButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().choosePawnColor("RED",this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        status.setText("WOW, SO FAST, NOW WAIT!");


    }
    public void onClickGreenButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().choosePawnColor("GREEN",this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        status.setText("WOW, SO FAST, NOW WAIT!");
    }
    public void onClickBlueButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().choosePawnColor("BLUE",this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        status.setText("WOW, SO FAST, NOW WAIT!");
    }
    public void onClickYellowButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().choosePawnColor("YELLOW",this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        status.setText("WOW, SO FAST, NOW WAIT!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonLayout layout = new CommonLayout();
        layout.setGameBackground(rootBorder);
    }

}
