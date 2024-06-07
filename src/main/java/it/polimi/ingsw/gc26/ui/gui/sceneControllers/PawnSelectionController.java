package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.rmi.RemoteException;


public class PawnSelectionController extends SceneController {

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

}
