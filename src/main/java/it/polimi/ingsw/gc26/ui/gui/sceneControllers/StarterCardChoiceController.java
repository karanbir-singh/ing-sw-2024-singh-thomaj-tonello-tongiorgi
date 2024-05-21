package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class StarterCardChoiceController extends GenericController{
    @FXML
    ImageView front1;
    @FXML
    ImageView back1;


    public void onClickButton(ActionEvent event){
        if(this.front1.isVisible()){
            this.front1.setVisible(false);
            this.back1.setVisible(true);
        }else{
            this.front1.setVisible(true);
            this.back1.setVisible(false);
        }
    }
}
