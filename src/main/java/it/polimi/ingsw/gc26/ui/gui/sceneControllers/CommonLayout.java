package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CommonLayout {
    public void pageBindings(ScrollPane rootScrollPane, BorderPane rootBorder, TabPane personalBoardTabPane, VBox leftVBox, VBox rightVBox, ImageView scoreBoard, ArrayList<ImageView> cards){

        rootBorder.heightProperty().addListener((obs, oldVal, newVal) -> {
            leftVBox.setPrefHeight(rootBorder.getPrefHeight());
            rightVBox.setPrefHeight(rootBorder.getPrefHeight());
            personalBoardTabPane.prefHeightProperty().bind(rootScrollPane.heightProperty().multiply(0.45));
        });

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            for(ImageView card: cards){
                card.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.13));
            }

            scoreBoard.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.2));
            personalBoardTabPane.prefWidthProperty().bind(rootBorder.widthProperty().multiply(0.5));

        });
    }

    public void cardsLayout(ArrayList<ImageView> cards){

    }
}
