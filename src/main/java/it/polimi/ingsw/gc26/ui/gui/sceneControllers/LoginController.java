package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class LoginController extends SceneController {

    @FXML
    private Label status;

    @FXML
    private TextField nicknameTXT;

    public void setStatus(String message){
        this.status.setText(message);
        this.status.setVisible(true);
    }

    @FXML
    public void onClickButton(ActionEvent actionEvent){
        if(nicknameTXT.getText().equals("")){
            status.setText("Nickname not valid, insert again");
            status.setVisible(true);
        }else{
            try {
                this.setNickName(nicknameTXT.getText());
                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(),this.nickname,this.mainClient.getClientState());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String getText(){
        return this.nicknameTXT.getText();
    }
}
