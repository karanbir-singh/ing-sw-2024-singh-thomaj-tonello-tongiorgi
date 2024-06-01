package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CommonLayout {
    Rectangle clipper;

    public void pageBindings(ScrollPane rootScrollPane, BorderPane rootBorder, TabPane personalBoardTabPane, VBox leftVBox, VBox rightVBox,
                             ImageView scoreBoard, AnchorPane handPane){

        rootBorder.heightProperty().addListener((obs, oldVal, newVal) -> {
            leftVBox.setPrefHeight(rootBorder.getPrefHeight());
            rightVBox.setPrefHeight(rootBorder.getPrefHeight());
            personalBoardTabPane.prefHeightProperty().bind(rootScrollPane.heightProperty().multiply(0.45));
        });

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            scoreBoard.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.15));
            personalBoardTabPane.prefWidthProperty().bind(rootBorder.widthProperty().multiply(0.5));

        });
    }


    public void cardsLayout(BorderPane rootBorder, ArrayList<ImageView> cards){
        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            for(ImageView card: cards){
                card.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.13));
            }
        });
    }

    public void handLayout(BorderPane rootBorder, ArrayList<ImageView> handCards){
        for(ImageView card: handCards){
            card.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.13));
            card.setLayoutX(25.0 + rootBorder.getWidth()*0.13 * handCards.indexOf(card));

            rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
                card.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.13));
                card.setLayoutX(20.0 + rootBorder.getWidth()*0.13 * handCards.indexOf(card));
            });
        }
    }
}
