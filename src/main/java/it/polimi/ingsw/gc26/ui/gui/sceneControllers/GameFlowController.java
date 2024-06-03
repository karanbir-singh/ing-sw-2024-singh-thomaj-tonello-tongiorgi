package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameFlowController extends GenericController implements Initializable{

    @FXML
    public TitledPane chat;
    @FXML
    public ImageView scoreBoard;

    @FXML
    private VBox commonMissionsBox;

    @FXML
    private VBox secretMissionBox;

    //hand
    @FXML
    private TilePane handPane;

    //CommonTable
    @FXML
    private TilePane commonTablePane;

    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane personalBoardPane;

    private final int xPositionStarterCard = 40;
    private final int yPositionStarterCard = 40;

    private HashMap<String,VBox> chats = new HashMap<>();

    @FXML
    private Button turnSideButton;
    @FXML
    private Button drawCardButton;

    private boolean chatHasBeenCreate = false;
    private String path = "/images/";
    private ColumnConstraints columnConstraints = new ColumnConstraints(115, 115, 115);
    private RowConstraints rowConstraints = new RowConstraints(60, 60, 60);

    @FXML
    public void onClickTurnSideButton(ActionEvent actionEvent){
        try {
            this.mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickDrawCardButton(ActionEvent actionEvent){
        try {
            this.mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        //this.drawCardButton.setVisible(false);
    }



    //azioni per la mano
    @FXML
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
        ArrayList<ImageView> imageViews = new ArrayList<>();
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
            imageViews.add(imageView);
            index++;
        }
        this.handPane.getChildren().setAll(imageViews);


    }

    @Override
    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
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


    private void createChatTab(String nickname, TitledPane chatBox) {
        Tab newTab = new Tab();
        newTab.setText(nickname);
        newTab.setStyle("-fx-border-radius: 0px 0px 5px 5px;");
        AnchorPane newAnchorPane = new AnchorPane();
        newTab.setContent(newAnchorPane);
        newAnchorPane.setStyle("-fx-background-color: #e8f4f8");
        TextField newTextField = new TextField();
        newTextField.setPrefWidth(208);
        newTextField.setPrefHeight(26);
        newTextField.setLayoutX(9);
        newTextField.setLayoutY(489);
        newTextField.setPromptText("Type Message");
        newTextField.setStyle("-fx-border-radius: 5px;");
        newAnchorPane.getChildren().add(newTextField);
        Button newButton = new Button();
        newButton.setText("Send");
        newButton.setLayoutX(231);
        newButton.setLayoutY(489);
        newAnchorPane.getChildren().add(newButton);
        ScrollPane newScrollPane = new ScrollPane();
        newScrollPane.setMinHeight(440);
        newScrollPane.setPrefHeight(440);
        newScrollPane.setMinWidth(262);
        newScrollPane.setMaxWidth(262);
        newScrollPane.setLayoutX(9);
        newScrollPane.setLayoutY(14);
        newAnchorPane.getChildren().add(newScrollPane);
        VBox newVBox = new VBox();
        newScrollPane.setContent(newVBox);
        newVBox.maxWidth(229);
        newVBox.maxHeight(440);
        newVBox.prefWidth(229);
        newScrollPane.setContent(newVBox);
        newScrollPane.setPadding(new Insets(5,0,5,5));
        ((TabPane)chatBox.getContent()).getTabs().add(newTab);
        newTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            keyEvent.consume();
        });
        newButton.setOnMouseClicked(event -> {
            if (!newTextField.getText().isEmpty()) {
                sendMessage(newTextField, newVBox, newScrollPane, newTab);
            }
            event.consume();
        });
        chats.put(nickname, newVBox);
    }

    private void sendMessage(TextField newTextField, VBox newVBox, ScrollPane newScrollPane, Tab newTab) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 30));
        Text text = new Text(newTextField.getText());
        chat.setSnapToPixel(true);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.setStyle("-fx-background-color: rgb(15,125,242);" + "-fx-color: rgb(239, 242,255);" + "-fx-background-radius: 7px;");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(2, 5, 2, 5));
        text.setStyle("-fx-font-smoothing-type: gray;" + "-fx-text-fill: white;");
        text.setFill(Color.color(1, 1, 1));
        hBox.getChildren().add(textFlow);
        newVBox.getChildren().add(hBox);

        newScrollPane.setVvalue(newScrollPane.getVmax());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.mainClient.getVirtualGameController().addMessage(newTextField.getText(), newTab.getText(), mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        } catch (RemoteException e) {
            System.err.println("RemoteException while sending message!");
        }
        newTextField.clear();
        //                Platform.runLater(() -> {
//                    newVBox.layout();
//                    newScrollPane.layout(); // Ensure the layout is updated
//                    newScrollPane.setVvalue(newScrollPane.getVmax()); // Scroll to bottom
//                });


    }

    @Override
    public void createChats(SimplifiedGame simplifiedGame, String nickname) {
        this.nickname = nickname;
        for(String playerNickname : simplifiedGame.getPlayersNicknames()) {
            if (!playerNickname.equals(nickname)) {
                createChatTab(playerNickname, chat);
            }
        }
        createChatTab("Group Chat", chat);
    }

    @Override
    public void changeGUIChat(SimplifiedChat simplifiedChat) {
        Message newMessage = simplifiedChat.getMessages().getLast();
        if (newMessage.getReceiver() == null) {
            if (!newMessage.getSender().getNickname().equals(nickname)) {
                if (simplifiedChat.getMessages().size() == 1 || (simplifiedChat.getMessages().size() > 1 &&
                        !newMessage.getSender().getNickname().equals(simplifiedChat.getMessages().get(simplifiedChat.getMessages().size()-2).getSender().getNickname()))) {
                    addMessageInChat(newMessage.getText(), "Group Chat", newMessage.getSender().getNickname() );
                } else {
                    addMessageInChat(newMessage.getText(), "Group Chat", null);
                }
            }
        } else {
            addMessageInChat(newMessage.getText(), newMessage.getSender().getNickname(), null);
        }
    }

    private void addMessageInChat(String message, String sender, String labelMessage) {
        HBox labelBox = new HBox();
        Text labelText;
        TextFlow labelTextFlow;
        if (labelMessage != null) {
            labelBox.setAlignment(Pos.BASELINE_LEFT);
            labelBox.setPadding(new Insets(0, 30, 0, 5));
            labelText = new Text(labelMessage);
            labelText.setStyle("-fx-font-size: 10px;");
            labelTextFlow = new TextFlow(labelText);
            labelBox.setMinWidth(150);
            labelBox.setMaxWidth(150);
            labelText.setFill(Color.color(0.25, 0.25, 0.25));
            labelBox.getChildren().add(labelTextFlow);
        }
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.setPadding(new Insets(5, 30, 5, 5));
        Text text = new Text(message);
        chat.setSnapToPixel(true);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setSnapToPixel(true);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 7px;");
        hBox.setMinWidth(240);
        hBox.setMaxWidth(240);
        textFlow.setPadding(new Insets(2, 5, 2, 5));
        text.setFill(Color.color(0.0, 0.0, 0.0));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (labelMessage != null) {
                        chats.get(sender).getChildren().add(labelBox);
                    }
                    chats.get(sender).getChildren().add(hBox);
                } catch (NullPointerException e) {}
            }
        });


        //newScrollPane.setVvalue(newScrollPane.getVmax());
    }
}