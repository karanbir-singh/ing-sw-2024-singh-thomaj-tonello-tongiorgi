package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecretMissionChoiceController extends SceneController implements Initializable {
    //CommonTable
    @FXML
    private VBox commonTableBox;

    @FXML
    private Label status;

    @FXML
    private HBox cardHBox;
    @FXML
    private HBox secretMissionHBox;


    @FXML
    private HBox resourceHbox;
    @FXML
    private HBox goldHbox;


    //layout
    @FXML
    TabPane personalBoardTabPane;

    //layout
    CommonLayout layout = new CommonLayout();
    @FXML
    private HBox HBoxLeftPanel;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private BorderPane rootBorder;
    @FXML
    private ScrollPane rootScrollPane;

    private ArrayList<ImageView> cards = new ArrayList<>();
    private ImageView image1 = new ImageView(new Image(getClass().getResource("images/game-background.png").toExternalForm()));
    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);


    @FXML
    GridPane gridPane;
    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    String path = "images/";

    public void onClickSecretMission(MouseEvent mouseEvent) {
        try {
            int index = Integer.parseInt(((ImageView) mouseEvent.getSource()).getAccessibleText());
            this.mainClient.getVirtualGameController().selectSecretMission(index, this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void onClickConfirmButton() {
        try {
            this.mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        ArrayList<ImageView> resources = new ArrayList<>();
        ArrayList<ImageView> goldens = new ArrayList<>();
        ArrayList<ImageView> imageViewsCommonMissions = new ArrayList<>();

        for (Card card : simplifiedCommonTable.getResourceCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            resources.add(imageView);
        }
        ImageView resourceDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getResourceDeck().getBack().getImagePath()))));
        this.setParameters(resourceDeck, String.valueOf(0));
        resources.add(resourceDeck);
        for (Card card : simplifiedCommonTable.getGoldCards()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            goldens.add(imageView);
        }
        ImageView goldDeck = new ImageView(new Image(String.valueOf(getClass().getResource(path + simplifiedCommonTable.getGoldDeck().getBack().getImagePath()))));
        this.setParameters(goldDeck, String.valueOf(0));
        goldens.add(goldDeck);
        for (Card card : simplifiedCommonTable.getCommonMissions()) {
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            imageViewsCommonMissions.add(imageView);
        }

        Platform.runLater(() -> {
            this.rightVBox.getChildren().setAll(imageViewsCommonMissions);
            this.resourceHbox.getChildren().setAll(resources);
            this.goldHbox.getChildren().setAll(goldens);

        });

        //da controllare se vanno messi in platform.runlater
    }

    @Override
    public void changeGUIHand(SimplifiedHand simplifiedHand) {
        ArrayList<ImageView> handCards = new ArrayList<>();
        for (Card card : simplifiedHand.getCards()) {
            ImageView imageView;
            imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(0));
            handCards.add(imageView);
        }

        Platform.runLater(() -> {
            this.cardHBox.getChildren().setAll(handCards);
        });

    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
        ImageView imageCardToPlay = new ImageView(new Image(String.valueOf(
                getClass().getResource(path + personalBoard.getOccupiedPositions().getLast().getSide().getImagePath()))));
        this.addImage(imageCardToPlay,
                this.xPositionStarterCard + personalBoard.getOccupiedPositions().getLast().getX(),
                this.yPositionStarterCard - personalBoard.getOccupiedPositions().getLast().getY(), this.gridPane);

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
        if (!otherPersonalBoard.getNickname().equals(this.nickname) && !exist) {
            consideredTab = new Tab();
            consideredTab.setText(otherPersonalBoard.getNickname());
            this.personalBoardTabPane.getTabs().add(consideredTab);
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
    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
        ArrayList<ImageView> secretHand = new ArrayList<>();
        int index = 0;
        for (Card card : simplifiedSecretHand.getCards()) {
            ImageView imageView;
            imageView = new ImageView(new Image(String.valueOf(getClass().getResource(path + card.getFront().getImagePath()))));
            this.setParameters(imageView, String.valueOf(index));
            imageView.setOnMouseClicked(this::onClickSecretMission);
            secretHand.add(imageView);
            index++;
        }

        Platform.runLater(() -> {
            this.secretMissionHBox.getChildren().setAll(secretHand);
        });
    }

    private void setParameters(ImageView imageView, String accessibleText) {
        imageView.setFitWidth(150);
        imageView.setFitHeight(98);
        imageView.setPreserveRatio(true);
        imageView.setAccessibleText(accessibleText);

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

    private void addImage(ImageView imageView, int x, int y, GridPane gridPane) {
        setParameters(imageView, "0");
        Platform.runLater(() -> {
            gridPane.add(imageView, x, y);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnConstraints.setHalignment(HPos.CENTER);
        rowConstraints.setValignment(VPos.CENTER);


        this.creationAndSettingGridContraints(this.gridPane);


        layout.setGameBackground(rootBorder);
        //layout.pageBindings(rootScrollPane, rootAnchor, centerVBox, leftVBox, rightVBox);
        layout.pageBindings(rootScrollPane, rootBorder, HBoxLeftPanel, rightVBox, centerVBox);
        layout.setPersonalBoardRatio(rootBorder, personalBoardTabPane, 0.3, 0.4);

    }

}
