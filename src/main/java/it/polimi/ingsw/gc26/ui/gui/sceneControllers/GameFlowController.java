package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.rmi.RemoteException;
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
    @FXML
    private TilePane handPane;
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

    @FXML
    private VBox commonTableBox;
    @FXML
    private VBox commonMissionsBox;
    @FXML
    private VBox secretMissionBox;

    //end CommonTable


    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane personalBoardPane;
    @FXML
    private ScrollPane personalBoardScrollPane;
    @FXML
    private TabPane personalBoardTabPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;

    @FXML
    private Button turnSideButton;
    @FXML
    private Button drawCardButton;

    //layout
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox leftVBox;
    @FXML
    private BorderPane rootBorder;
    @FXML
    private ScrollPane rootScrollPane;
    @FXML
    private ImageView scoreBoard;
    private ArrayList<ImageView> cards = new ArrayList<>();
    private ArrayList<ImageView> playablePrositions = new ArrayList<>();
    private ArrayList<ImageView> handCards = new ArrayList<>();

    private String path = "/images/";
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    //forDraggability
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialX;
    private double initialY;

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
    public void onClickMouseHandCard(javafx.scene.input.MouseEvent mouseEvent){
        try {
            int index = Integer.valueOf(((ImageView)mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectCardFromHand(index,this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //azioni carte commonBoard
    public void onClickCommonTableCard(MouseEvent mouseEvent){
        try {
            int index =  Integer.valueOf(((ImageView)mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectCardFromCommonTable(index,this.mainClient.getClientID());
            System.out.println("hai cliccato su resourceCard");
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













    // String.valueOf(getClass().getResource
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        int index = 0;
        for(Card card: simplifiedCommonTable.getResourceCards()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView,String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageViews.add(imageView);
            index++;
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setParameters(resourceDeck, String.valueOf(index));
        resourceDeck.setOnMouseClicked(this::onClickCommonTableCard);
        imageViews.add(resourceDeck);
        index++;
        for(Card card: simplifiedCommonTable.getGoldCards()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageViews.add(imageView);
            index++;
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setParameters(goldDeck,String.valueOf(index));
        goldDeck.setOnMouseClicked(this::onClickCommonTableCard);
        imageViews.add(goldDeck);
        index++;
        ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();
        for(Card card: simplifiedCommonTable.getCommonMissions()){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageViewsCommonMissions.add(imageView);
            index++;
        }

        this.commonMissionsBox.getChildren().setAll(imageViewsCommonMissions);

        this.commonTablePane.getChildren().setAll(imageViews);

    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        handCards = new ArrayList<>();

        int index = 0;
        for(Card card: simplifiedHand.getCards()){
            ImageView imageView;
            if(card.equals(simplifiedHand.getSelectedCard())){
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ simplifiedHand.getSelectedSide().getImagePath()))));
            }else{
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path+ card.getFront().getImagePath()))));
            }
            this.setParameters(imageView,String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickMouseHandCard);
            handCards.add(imageView);
            index++;
        }
        this.handPane.getChildren().setAll(handCards);

        makeDraggable(handCards, playablePrositions);
    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        playablePrositions = new ArrayList<>();

        if(personalBoard.getSecretMission() != null){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getSecretMission().getFront().getImagePath()))));
            this.setParameters(imageView,"0");
            this.secretMissionBox.getChildren().setAll(imageView);
        }
        if (personalBoard.getOccupiedPositions().size() == 1) {
            ImageView starterCardImage = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
            this.addImage(starterCardImage,this.xPositionStarterCard,this.yPositionStarterCard);

            for(Point point : personalBoard.getPlayablePositions()){
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
                imageView.setOnMouseClicked(this::onClickPlayablePosition);
                imageView.setOpacity(0.3);
                imageView.setVisible(false);
                addImage(imageView,this.xPositionStarterCard + point.getX(),
                        this.yPositionStarterCard - point.getY());

                playablePrositions.add(imageView);
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
                imageView.setOpacity(0.3);
                imageView.setVisible(false);
                imageView.setOnMouseClicked(this::onClickPlayablePosition);
                addImage(imageView,this.xPositionStarterCard + point.getX(),
                        this.yPositionStarterCard - point.getY());

                playablePrositions.add(imageView);
            }
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards.add(handCard0);
        cards.add(handCard1);
        cards.add(handCard2);
        cards.add(resourceCard0);
        cards.add(resourceCard1);
        cards.add(resourceDeck);
        cards.add(goldCard0);
        cards.add(goldCard1);
        cards.add(goldDeck);
        cards.add(commonMission0);
        cards.add(commonMission1);
        cards.add(secretMission);

        //page layout and dimensions bindings
        CommonLayout layout = new CommonLayout();
        layout.pageBindings(rootScrollPane, rootBorder, personalBoardTabPane, leftVBox, rightVBox, scoreBoard, cards);

        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.gridPane.setPrefWidth(8000);
        this.gridPane.setPrefHeight(4000);
        this.gridPane.setMaxWidth(8000);
        this.gridPane.setMaxHeight(4000);
        for(int row = 0; row < 81; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for(int column = 0; column < 81; column++){
            gridPane.getColumnConstraints().add(columnConstraints);
        }


    }

    private void setParameters(ImageView imageView, String accessibleText){
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
        imageView.setAccessibleText(accessibleText);

    }

    private void addImage(ImageView imageView,int x, int y){
        setParameters(imageView,"0");
        gridPane.add(imageView,x,y);
    }

    public void makeDraggable(ArrayList<ImageView> array, ArrayList<ImageView> targets) {
        for(ImageView imageView: array){
            imageView.setOnMousePressed(event -> {
                initialX = imageView.getLayoutX();
                initialY = imageView.getLayoutY();
                mouseAnchorX = event.getSceneX() - initialX;
                mouseAnchorY = event.getSceneY() - initialY;
                for (ImageView target: targets) {
                    target.setVisible(true);
                }
            });

            imageView.setOnMouseDragged(event -> {
                imageView.setLayoutX(event.getSceneX() - mouseAnchorX);
                imageView.setLayoutY(event.getSceneY() - mouseAnchorY);
            });

            imageView.setOnMouseReleased(event -> {
                for (ImageView target: targets) {
                    if (isInTargetSpot(imageView, target)) {

                        try {
                            int row = gridPane.getRowIndex(target);
                            int column = gridPane.getColumnIndex(target);
                            //da inserire

                            this.mainClient.getVirtualGameController().selectPositionOnBoard(column-xPositionStarterCard,yPositionStarterCard-row,this.mainClient.getClientID());
                            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                        } catch (RemoteException e) {
                            // throw new RuntimeException(e);
                        }
                    }
                }
                imageView.setLayoutX(initialX);
                imageView.setLayoutY(initialY);
                for (ImageView target: targets) {
                    target.setVisible(false);
                }
            });
        }
    }

    private boolean isInTargetSpot(ImageView imageView, ImageView target) {
        Bounds imageViewBounds = imageView.localToScene(imageView.getBoundsInLocal());
        Bounds targetBounds = target.localToScene(target.getBoundsInLocal());

        return targetBounds.contains(
                imageViewBounds.getMinX() + imageViewBounds.getWidth() / 2,
                imageViewBounds.getMinY() + imageViewBounds.getHeight() / 2
        );
    }
}