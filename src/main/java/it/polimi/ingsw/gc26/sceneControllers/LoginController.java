package it.polimi.ingsw.gc26.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.rmi.RemoteException;

public class LoginController extends GenericController{

    @FXML
    Label status;

    @FXML
    TextField nicknameTXT;

    public void onClickButton(ActionEvent event){
        //chiedere se il thread viene creato in modo automatico o devo crearlo io
        if(nicknameTXT.getText().equals("")){
            status.setText("Insert again, not valid nickname");
            status.setVisible(true); //setto visibile il label
        }else{
            try {
                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(),nicknameTXT.getText(),this.mainClient.getClientState());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
