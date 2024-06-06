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
    ImageView logo;
    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane logoPane;
    @FXML
    double initialLogoHeight;
    @FXML
    HBox logoBox;
    @FXML
    VBox loginVBox;


    private double initialImageWidth;
    private double initialImageHeight;


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
        initialImageWidth = background.getImage().getWidth();
        initialImageHeight = background.getImage().getHeight();
        initialLogoHeight = logo.getFitHeight();


        background.fitHeightProperty().bind(rootPane.heightProperty());
        background.fitWidthProperty().bind(rootPane.widthProperty());
        logo.fitWidthProperty().bind(rootPane.heightProperty());
        logoPane.setPrefHeight(rootPane.getHeight());

        AnchorPane.setTopAnchor(logoPane, 20.0);
        AnchorPane.setRightAnchor(logoPane, rootPane.getWidth()*0.05);
        AnchorPane.setRightAnchor(loginVBox, (rootPane.getWidth()-loginVBox.getWidth())/2);

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateViewport();
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
            background.fitHeightProperty().bind(rootPane.heightProperty());

            if(rootPane.getHeight() < 650 && rootPane.getWidth()<800){
                AnchorPane.setBottomAnchor(logoBox, 0.0);
            }
            updateViewport();
        });
    }

    private void updateViewport() {
        double viewportWidth = Math.min(initialImageWidth, rootPane.getWidth());
        double viewportHeight = Math.min(initialImageHeight, rootPane.getHeight());

        double x = (initialImageWidth - viewportWidth) / 2;
        double y = (initialImageHeight - viewportHeight) / 2;

        background.setViewport(new Rectangle2D(x, y, viewportWidth, viewportHeight));
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
