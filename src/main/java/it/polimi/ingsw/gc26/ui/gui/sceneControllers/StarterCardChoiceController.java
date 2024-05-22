package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;

public class StarterCardChoiceController extends GenericController{
    @FXML
    ImageView image;

    @FXML
    Label status;


    public void onClickFlipButton(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickGoToNextStep(ActionEvent event){
        try {
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        status.setText("Wow you are so fast, now wait other players!");
    }


    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if(simplifiedHand.getSelectedSide() != null){
            this.image.setImage(new Image(String.valueOf(getClass().getResource("/images/"+ simplifiedHand.getSelectedSide().getImagePath()))));
            this.image.setVisible(true);
        }

    }
}
