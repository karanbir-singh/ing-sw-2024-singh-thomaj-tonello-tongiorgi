package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController implements Initializable{

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
    private AnchorPane personalBoardPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;


    String path = "/images/";
    ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    // String.valueOf(getClass().getResource
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        if (simplifiedCommonTable.getGoldCards().size() >= 2 && simplifiedCommonTable.getResourceCards().size() >= 2
                && simplifiedCommonTable.getCommonMissions().size() >= 2) {
            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            this.resourceCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(1).getFront().getImagePath()))));
            this.resourceDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));

            this.goldCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(0).getFront().getImagePath()))));
            this.goldCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldCards().get(1).getFront().getImagePath()))));
            this.goldDeck.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));

            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));
            this.resourceCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceCards().get(0).getFront().getImagePath()))));

            this.commonMission0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(0).getFront().getImagePath()))));
            this.commonMission1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(1).getFront().getImagePath()))));

        }
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if (simplifiedHand.getCards().size() >= 3) {
            this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
            this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
            this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(2).getFront().getImagePath()))));
        }
    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if (personalBoard.getOccupiedPositions().size() == 1) {
            ImageView starterCardImage = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
            this.addImage(starterCardImage,this.xPositionStarterCard,this.yPositionStarterCard);

            for(Point point : personalBoard.getPlayablePositions()){
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
                //il path di prima è solo per prova
                imageView.setOpacity(0.3);
                addImage(imageView,this.xPositionStarterCard + point.getX(),
                        this.yPositionStarterCard - point.getY());
            }

        } else {
            //se aggiungo una colonna prima della starterCard, xPositionStarterCard aumenta di 1
            //se aggiungo una colonna dopo la starterCard, xPositionStarterCard rimane invariato
            //se aggiungo una riga sopra la starterCard, yPositionStarterCard aumenta di 1
            //se aggiungo una riga sotto la starterCard, yPositionStarterCard rimane inviariato
            //gli offset rispetto alla posizione della starterCard sono
            // (x,y) = (xPositionStarterCard + point.getX, yPositionStarterCard - point.getY)
            ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                    getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
            this.addImage(imageCardToPlay,
                    this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                    this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY());

            for(Point point : personalBoard.getPlayablePositions()){
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getOccupiedPositions().getFirst().getSide().getImagePath()))));
                //il path di prima è solo per prova
                imageView.setOpacity(0.5);
                addImage(imageView,this.xPositionStarterCard + point.getX(),
                        this.yPositionStarterCard - point.getY());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);
        for(int row = 0; row < 80; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for(int column = 0; column < 80; column++){
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        this.personalBoardPane.setPrefWidth(12000);
        this.personalBoardPane.setPrefHeight(7840);
        this.personalBoardPane.setMaxWidth(12000);
        this.personalBoardPane.setMaxHeight(7840);
        this.gridPane.setPrefWidth(12000);
        this.gridPane.setPrefHeight(7840);
        this.gridPane.setMaxWidth(12000);
        this.gridPane.setMaxHeight(7840);















    }

    private void setParameters(ImageView imageView){
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);

    }

    private void addImage(ImageView imageView,int x, int y){
        setParameters(imageView);
        gridPane.add(imageView,x,y);
    }

}