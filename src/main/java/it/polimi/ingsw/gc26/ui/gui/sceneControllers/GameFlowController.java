package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController{

    //commonMissions
    @FXML
    private ImageView commonMission0;
    @FXML
    private ImageView commonMission1;
    //end CommonMissions

    // secretMission
    @FXML
    private ImageView secretMission;
    //secretMission


    //hand
    @FXML
    private ImageView handCard0;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;
    //end hand


    //CommonTable
    @FXML
    private ImageView resourceCard0;
    @FXML
    private ImageView resourceCard1;
    @FXML
    private ImageView resourceDeck;

    @FXML
    private ImageView goldCard0;
    @FXML
    private ImageView goldCard1;
    @FXML
    private ImageView goldDeck;

    //end CommonTable


    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView starterCard;
    int xPositionStarterCard = 0;
    int yPositionStarterCard = 0;


    String path = "/images/";

   // String.valueOf(getClass().getResource
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        if(simplifiedCommonTable.getGoldCards().size() >= 2 && simplifiedCommonTable.getResourceCards().size() >= 2
                && simplifiedCommonTable.getCommonMissions().size() >= 2){
            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            this.resourceCard1.setImage(new Image(String.valueOf(getClass().getResource(path +simplifiedCommonTable.getResourceCards().get(1).getFront().getImagePath()))));
            this.resourceDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getFront().getImagePath()))));

            this.goldCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(0).getFront().getImagePath()))));
            this.goldCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(1).getFront().getImagePath()))));
            this.goldDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getFront().getImagePath()))));

            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));

            this.commonMission0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(0).getFront().getImagePath()))));
            this.commonMission1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(1).getFront().getImagePath()))));

        }
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if(simplifiedHand.getCards().size() >= 3){
            this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
            this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
            this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(2).getFront().getImagePath()))));
        }
    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if(personalBoard.getOccupiedPositions().size() == 1){
            starterCard.setImage(new Image(String.valueOf(getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        }else{
            //se aggiungo una colonna prima della starterCard, xPositionStarterCard aumenta di 1
            //se aggiungo una colonna dopo la starterCard, xPositionStarterCard rimane invariato
            //se aggiungo una riga sopra la starterCard, yPositionStarterCard aumenta di 1
            //se aggiungo una riga sotto la starterCard, yPositionStarterCard rimane inviariato
        }


    }
}
