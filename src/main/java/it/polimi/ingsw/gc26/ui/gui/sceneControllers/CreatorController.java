package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.rmi.RemoteException;

public class CreatorController extends SceneController {

    @FXML
    private RadioButton secondRadioButton;

    @FXML
    private RadioButton thirdRadioButton;

    @FXML
    private RadioButton fourthRadioButton;
    private int numMaxPlayer = 0;

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
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }




}
