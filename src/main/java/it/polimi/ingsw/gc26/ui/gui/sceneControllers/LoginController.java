package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController extends SceneController implements Initializable{

    @FXML
    private Label status;
    @FXML
    private TextField nicknameTXT;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView background;
    @FXML
    private ImageView logo;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane logoPane;
    @FXML
    private HBox logoBox;
    @FXML
    private VBox loginVBox;


    private CommonLayout layout = new CommonLayout();
    private double initialImageHeight;
    private double initialImageWidth;


    public void setStatus(String message){
        this.status.setText(message);
        this.status.setVisible(true);
    }

    public void onLoginButtonClick(ActionEvent event){
        //chiedere se il thread viene creato in modo automatico o devo crearlo io
        if(nicknameTXT.getText().isEmpty()){
            status.setText("Insert again, not valid nickname");
            status.setVisible(true);
        } else {
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

        layout.setBackground(rootPane, background);

        loginButton.setOnAction(this::onLoginButtonClick);
        nicknameTXT.setOnAction(event -> {
            loginButton.fire();
        });

        logo.fitWidthProperty().bind(rootPane.heightProperty());
        logoPane.setPrefHeight(rootPane.getHeight());

        AnchorPane.setTopAnchor(logoPane, 20.0);
        AnchorPane.setRightAnchor(logoPane, rootPane.getWidth()*0.05);
        AnchorPane.setRightAnchor(loginVBox, (rootPane.getWidth()-loginVBox.getWidth())/2);

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setRightAnchor(loginVBox, (rootPane.getWidth()-loginVBox.getWidth())/2);

            if(rootPane.getWidth() < 800){
                AnchorPane.setRightAnchor(logoPane, (rootPane.getWidth()-logoPane.getWidth())/2);
                AnchorPane.setBottomAnchor(logoBox, 70.0);
            } else {
                AnchorPane.setRightAnchor(logoPane, rootPane.getWidth()*0.05);
                AnchorPane.setBottomAnchor(logoBox, 0.0);
            }
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            logo.setFitHeight(rootPane.getHeight()*0.5);
            //background.fitHeightProperty().bind(rootPane.heightProperty());

            if(rootPane.getHeight() < 650 && rootPane.getWidth()<800){
                AnchorPane.setBottomAnchor(logoBox, 0.0);
            }
        });
    }
}
