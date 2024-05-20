package it.polimi.ingsw.gc26.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.rmi.RemoteException;

public class CreatorController extends GenericController{

    @FXML
    private RadioButton secondRadioButton;

    @FXML
    private RadioButton thirdRadioButton;

    @FXML
    private RadioButton fourthRadioButton;

    @FXML
    private Label status;

    private int numMaxPlayer = 0;

    @FXML
    public void onSecondRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(secondRadioButton.getText());
    }
    @FXML
    public void onThirdRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(thirdRadioButton.getText());
    }
    @FXML
    public void onFourthRadioButtonClick(ActionEvent actionEvent){
        this.numMaxPlayer = Integer.parseInt(thirdRadioButton.getText());
    }

    @FXML
    public void onClickButton(ActionEvent actionEvent) {
        try { //TODO da cambiare il nickname
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(),this.nickname,this.numMaxPlayer);
            this.status.setText("Adesso aspetta che altri client entrino");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }




}
