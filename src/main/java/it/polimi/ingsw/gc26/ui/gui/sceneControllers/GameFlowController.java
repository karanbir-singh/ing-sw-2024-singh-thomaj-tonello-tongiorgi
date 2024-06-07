package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.application.Platform;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameFlowController extends SceneController implements Initializable {

    //hand
    @FXML
    private AnchorPane handPane;
    //end hand


    //CommonTable
    @FXML
    private HBox resourceCardBox;
    @FXML
    private HBox goldCardBox;
    @FXML
    private VBox commonMissionsBox;
    @FXML
    private VBox secretMissionBox;

    //end CommonTable


    @FXML
    private GridPane gridPane;
    @FXML
    private TabPane personalBoardTabPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;

    @FXML
    private Button turnSideButton;
    @FXML
    private Button drawCardButton;

    //layout
    CommonLayout layout = new CommonLayout();
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

    private ArrayList<ImageView> playablePositions = new ArrayList<>();

    private final String path = "/images/";
    private final ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private final RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    //forDraggability
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialX;
    private double initialY;

    //TODO da spostare in css
    private final Glow glowEffect = new Glow(0.3);

    @FXML
    public void onClickTurnSideButton(ActionEvent actionEvent) {
        try {
            this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickDrawCardButton(ActionEvent actionEvent) {
        try {
            this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //this.drawCardButton.setVisible(false);
    }

    //azioni carte commonBoard
    @FXML
    public void onClickCommonTableCard(MouseEvent mouseEvent) {
        try {
            int index = Integer.valueOf(((ImageView) mouseEvent.getSource()).getId());
            this.mainClient.getVirtualGameController().selectCardFromCommonTable(index, this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //fine azioni per la mano

    private static int toIndex(Integer value) {
        return value == null ? 0 : value;
    }

    //azione delle carte opache
    public void onClickPlayablePosition(MouseEvent mouseEvent) {
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

            this.mainClient.getVirtualGameController().selectPositionOnBoard(column - xPositionStarterCard, yPositionStarterCard - row, this.mainClient.getClientID());
            this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
        } catch (RemoteException e) {
            // throw new RuntimeException(e);
        }
    }
    //fine azioni carte opache


    // String.valueOf(getClass().getResource
    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        ArrayList<ImageView> resources = new ArrayList<>();
        ArrayList<ImageView> goldens = new ArrayList<>();
        ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();

        int index = 0;
        for (Card card : simplifiedCommonTable.getResourceCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setCardImageParameters(imageView, index);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            resources.add(imageView);
            index++;
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setCardImageParameters(resourceDeck, index);
        resourceDeck.setOnMouseClicked(this::onClickCommonTableCard);
        resources.add(resourceDeck);
        index++;
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setCardImageParameters(imageView, index);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            goldens.add(imageView);
            index++;
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setCardImageParameters(goldDeck, index);
        goldDeck.setOnMouseClicked(this::onClickCommonTableCard);
        goldens.add(goldDeck);
        index++;
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setCardImageParameters(imageView, index);
            imageView.setOnMouseClicked(this::onClickCommonTableCard);
            imageViewsCommonMissions.add(imageView);
            index++;
        }

        Platform.runLater(() -> {
            this.commonMissionsBox.getChildren().setAll(imageViewsCommonMissions);
            this.resourceCardBox.getChildren().setAll(resources);
            this.goldCardBox.getChildren().setAll(goldens);
            layout.cardsLayout(rootBorder, resources);
            layout.cardsLayout(rootBorder, goldens);
            layout.cardsLayout(rootBorder, imageViewsCommonMissions);
        });

        //da controllare se vanno messi in platform.runlater
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        // List of card images
        ArrayList<ImageView> handCards = new ArrayList<>();

        // Hand cards
        ArrayList<Card> hand = simplifiedHand.getCards();

        // Update hand
        int index = 0;
        for (Card card : hand) {
            ImageView imageView;
            if (card.equals(simplifiedHand.getSelectedCard())) {
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedHand.getSelectedSide().getImagePath()))));
                imageView.setEffect(glowEffect);
            } else {
                imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            }
            this.setCardImageParameters(imageView, index);
            makeDraggable(imageView, playablePositions);
            imageView.setOnMouseClicked(this::onHandCardClicked);

            handCards.add(imageView);
            index++;
        }

        Platform.runLater(() -> {
            this.handPane.getChildren().setAll(handCards);
            layout.handLayout(rootBorder, handCards);
        });

        //controllare se va messo in run later
    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        playablePositions = new ArrayList<>();

        if (personalBoard.getSecretMission() != null) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + personalBoard.getSecretMission().getFront().getImagePath()))));
            this.setCardImageParameters(imageView, 0);
            Platform.runLater(() -> {
                this.secretMissionBox.getChildren().setAll(imageView);
            });
        }
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);
        for (Point point : personalBoard.getPlayablePositions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + "backSide/img_1.jpeg"))));
            //il path di prima è solo per prova
            imageView.setOpacity(0.3);
            imageView.setVisible(false);
            imageView.setOnMouseClicked(this::onClickPlayablePosition);
            addImage(imageView, this.xPositionStarterCard + point.getX(),
                    this.yPositionStarterCard - point.getY(), this.gridPane);

            playablePositions.add(imageView);
        }
    }

    @Override
    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
        boolean exist = false;
        Tab consideredTab = null;
        ScrollPane otherScrollPane = null;
        GridPane otherGridPane = null;
        //controlla che se esiste gia un tab con lo stesso nickname, e se esiste prendere i riferimenti allo scrollPane e griPane
        for (Tab tab : personalBoardTabPane.getTabs()) {
            if (tab.getText().equals(otherPersonalBoard.getNickname())) {
                exist = true;
                consideredTab = tab;
                otherScrollPane = (ScrollPane) tab.getContent();
                otherGridPane = (GridPane) otherScrollPane.getContent();
            }
        }

        //se invece non esiste un tab, con quel nickname, crea un nuovo tab e crea un nuovo scrollPane e GridPane
        if (!otherPersonalBoard.getNickname().equals(this.mainClient.getClientID()) && !exist) {
            consideredTab = new Tab();
            consideredTab.setText(otherPersonalBoard.getNickname());
            personalBoardTabPane.getTabs().add(consideredTab);
            otherScrollPane = new ScrollPane();
            otherScrollPane.setHvalue(0.5);
            otherScrollPane.setVvalue(0.5);

            otherGridPane = new GridPane();
            otherScrollPane.setContent(otherGridPane);
            this.creationAndSettingGridContraints(otherGridPane);
            consideredTab.setContent(otherScrollPane);
        }

        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + otherPersonalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + otherPersonalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - otherPersonalBoard.getOccupiedPositions().getLast().getY(), otherGridPane);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //page layout and dimensions bindings
        layout.pageBindings(rootScrollPane, rootBorder, personalBoardTabPane, leftVBox, rightVBox, scoreBoard, handPane);

        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);

        this.creationAndSettingGridContraints(this.gridPane);
    }

    private void creationAndSettingGridContraints(GridPane gridPane) {
        gridPane.setPrefWidth(8000);
        gridPane.setPrefHeight(4000);
        gridPane.setMaxWidth(8000);
        gridPane.setMaxHeight(4000);
        for (int row = 0; row < 81; row++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int column = 0; column < 81; column++) {
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

    private void setCardImageParameters(ImageView imageView, int index) {
        imageView.setId(String.valueOf(index));
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
    }

    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setCardImageParameters(imageView, 0);
        Platform.runLater(() -> {
            gridPane.add(imageView, x, y);
        });
    }

    public void makeDraggable(ImageView imageView, ArrayList<ImageView> targets) {
        // Set on card pressed
        imageView.setOnMousePressed(event -> {
            initialX = imageView.getLayoutX();
            initialY = imageView.getLayoutY();
            mouseAnchorX = event.getSceneX() - initialX;
            mouseAnchorY = event.getSceneY() - initialY;
        });

        // Set on card dragging
        imageView.setOnMouseDragged(event -> {
            imageView.setLayoutX(event.getSceneX() - mouseAnchorX);
            imageView.setLayoutY(event.getSceneY() - mouseAnchorY);

            for (ImageView target : targets) {
                target.setVisible(true);
            }
        });

        // Set on card released
        imageView.setOnMouseReleased(event -> {
            for (ImageView target : targets) {
                if (isInTargetSpot(imageView, target)) {

                    try {
                        //TODO da controllare se G minuscola
                        int row = GridPane.getRowIndex(target);
                        int column = GridPane.getColumnIndex(target);

                        this.mainClient.getVirtualGameController().selectCardFromHand(Integer.parseInt(imageView.getId()),this.mainClient.getClientID());
                        this.mainClient.getVirtualGameController().selectPositionOnBoard(column - xPositionStarterCard, yPositionStarterCard - row, this.mainClient.getClientID());
                        this.mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                    } catch (RemoteException e) {
                        // throw new RuntimeException(e);
                    }
                }
            }
            imageView.setLayoutX(initialX);
            imageView.setLayoutY(initialY);
            for (ImageView target : targets) {
                target.setVisible(false);
            }
        });
    }

    public void onHandCardClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            try {
                this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId());
                this.mainClient.getVirtualGameController().selectCardFromHand(index, this.mainClient.getClientID());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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