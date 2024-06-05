package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController extends GenericController implements Initializable{

    @FXML
    Label status;
    @FXML
    TextField nicknameTXT;
    @FXML
    Button loginButton;
    @FXML
    ImageView background;
    @FXML
    AnchorPane rootPane;

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

    public String getText(){
        return this.nicknameTXT.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background.fitWidthProperty().bind(rootPane.widthProperty());
        background.fitHeightProperty().bind(rootPane.heightProperty());

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            background.fitWidthProperty().bind(rootPane.widthProperty());

        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            background.fitHeightProperty().bind(rootPane.heightProperty());

        });
    }
}
