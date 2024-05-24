package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController implements Initializable{

    @FXML
    ImageView imageview;

    //DraggableMaker draggableMaker = new DraggableMaker();

    public void onClickButton(){
        //draggableMaker.makeDraggable(imageview);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //draggableMaker.makeDraggable(imageview);
    }
}
