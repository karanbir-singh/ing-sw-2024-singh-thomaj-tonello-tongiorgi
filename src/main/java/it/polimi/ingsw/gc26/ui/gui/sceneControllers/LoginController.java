package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.rmi.RemoteException;

public class LoginController extends GenericController{

    @FXML
    Label status;

    @FXML
    TextField nicknameTXT;

    public void setStatus(String message){
        this.status.setText(message);
        this.status.setVisible(true);
    }

    public void onClickButton(ActionEvent actionEvent){
        //chiedere se il thread viene creato in modo automatico o devo crearlo io
        if(nicknameTXT.getText().equals("")){
            status.setText("Insert again, not valid nickname");
            status.setVisible(true); //setto visibile il label
        }else{
            try {
                this.setNickName(nicknameTXT.getText());
                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(),this.nickname,this.mainClient.getClientState());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
