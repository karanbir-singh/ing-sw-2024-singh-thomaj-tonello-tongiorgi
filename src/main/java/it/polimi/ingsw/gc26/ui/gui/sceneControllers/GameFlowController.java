package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController implements Initializable{

    @FXML
    public TilePane handPane;
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
    private final int xPositionStarterCard = 10;
    private final int yPositionStarterCard = 10;

    @FXML
    private Button turnSideButton;
    @FXML
    private Button drawCardButton;


    private String path = "/images/";
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    public void onClickTurnSideButton(ActionEvent actionEvent){
        try {
            this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickDrawCardButton(ActionEvent actionEvent){
        try {
            this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //this.drawCardButton.setVisible(false);
    }


    //azioni per la mano
    public void onClickMouseFirstHandCard(javafx.scene.input.MouseEvent mouseEvent){
        try {
            this.mainClient.getVirtualGameController().selectCardFromHand(0,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickMouseSecondHandCard(javafx.scene.input.MouseEvent mouseEvent){
        try {
            this.mainClient.getVirtualGameController().selectCardFromHand(1,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void onClickMouseThirdHandCard(javafx.scene.input.MouseEvent mouseEvent){
        try {
            this.mainClient.getVirtualGameController().selectCardFromHand(2,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    //fine azioni per la mano

    private static int toIndex(Integer value) {
        return value == null ? 0 : value;
    }
    //azione delle carte opache
    public void onClickPlayablePosition(javafx.scene.input.MouseEvent mouseEvent){
        try {
            Node node = (Node) mouseEvent.getSource();
            Parent p = node.getParent();

            while (p != this.gridPane) {
                node = p;
                p = p.getParent();
            }

            int row = toIndex(GridPane.getRowIndex(node));
            int column = toIndex(GridPane.getColumnIndex(node));
            System.out.println("image clicked at " + row + " " + column);
            //da inserire

            this.mainClient.getVirtualGameController().selectPositionOnBoard(column-xPositionStarterCard,yPositionStarterCard-row,this.mainClient.getClientID());
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
           // throw new RuntimeException(e);
       }



    }
    //fine azioni carte opache

    //azioni carte commonBoard
    public void onClickCommonTableResourceCard0(MouseEvent mouseEvent){
        try {
            this.mainClient.getVirtualGameController().selectCardFromCommonTable(0,this.mainClient.getClientID());
            System.out.println("hai cliccato su resourceCard");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }













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

            this.commonMission0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(0).getFront().getImagePath()))));
            this.commonMission1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getCommonMissions().get(1).getFront().getImagePath()))));

            this.resourceCard0.setVisible(true);
            this.resourceCard1.setVisible(true);
            this.resourceDeck.setVisible(true);
            this.goldCard0.setVisible(true);
            this.goldCard1.setVisible(true);
            this.goldDeck.setVisible(true);
        }
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        if (simplifiedHand.getCards().size() >= 3) {
            int index = 0;
            for(index = 0; index< 3; index++){
                if(simplifiedHand.getCards().get(index).equals(simplifiedHand.getSelectedCard())){
                    break;
                }
            }
            if(index == 0){
                this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath()))));
                this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
                this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(2).getFront().getImagePath()))));
            }
            if(index == 1){
                this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
                this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath()))));
                this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(2).getFront().getImagePath()))));
            }
            if(index == 2){
                this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
                this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
                this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath()))));
            }
            if(index == 3){ // la selected card è null
                this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
                this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
                this.handCard2.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(2).getFront().getImagePath()))));
            }

            this.handCard0.setVisible(true);
            this.handCard1.setVisible(true);
            this.handCard2.setVisible(true);
        }
        //le righe sotto servono perchè quando un player gioca una carta dalla mano non si deve più vedere quella carta


        if(simplifiedHand.getCards().size() == 2){
            this.handCard0.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(0).getFront().getImagePath()))));
            this.handCard1.setImage(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getCards().get(1).getFront().getImagePath()))));
            this.handCard2.setVisible(false);
        }
    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        if(personalBoard.getSecretMission() != null){
            this.secretMission.setImage(new Image(String.valueOf(getClass().getResource(path + personalBoard.getSecretMission().getFront().getImagePath()))));
        }
        if (personalBoard.getOccupiedPositions().size() == 1) {
            ImageView starterCardImage = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
            this.addImage(starterCardImage,this.xPositionStarterCard,this.yPositionStarterCard);

            for(Point point : personalBoard.getPlayablePositions()){
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
                imageView.setOnMouseClicked(this::onClickPlayablePosition);
                imageView.setOpacity(0);
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
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
                //il path di prima è solo per prova
                imageView.setOpacity(0);
                imageView.setOnMouseClicked(this::onClickPlayablePosition);
                addImage(imageView,this.xPositionStarterCard + point.getX(),
                        this.yPositionStarterCard - point.getY());

            }
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);
        this.personalBoardPane.setPrefWidth(2500);
        this.personalBoardPane.setPrefHeight(1400);
        this.personalBoardPane.setMaxWidth(2500);
        this.personalBoardPane.setMaxHeight(1400);

        this.gridPane.setPrefWidth(1200);
        this.gridPane.setPrefHeight(784);
        this.gridPane.setMaxWidth(1200);
        this.gridPane.setMaxHeight(784);
        for(int row = 0; row < 21; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for(int column = 0; column < 21; column++){
            gridPane.getColumnConstraints().add(columnConstraints);
        }


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