package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CommonLayout {
    public void pageBindings(ScrollPane rootScrollPane, BorderPane rootBorder, TabPane personalBoardTabPane, VBox leftVBox, VBox rightVBox,
                             ImageView scoreBoard, ArrayList<ImageView> handCards, AnchorPane handPane){

        rootBorder.heightProperty().addListener((obs, oldVal, newVal) -> {
            try {
                leftVBox.setPrefHeight(rootBorder.getPrefHeight());
            } catch (NullPointerException e) {}
            rightVBox.setPrefHeight(rootBorder.getPrefHeight());
            personalBoardTabPane.prefHeightProperty().bind(rootScrollPane.heightProperty().multiply(0.50));
        });

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            try {
                scoreBoard.fitWidthProperty().bind(rootBorder.widthProperty().multiply(0.15));
            } catch (NullPointerException e) {

            }
            personalBoardTabPane.prefWidthProperty().bind(rootBorder.widthProperty().multiply(0.55));

        });
    }

    public void cardsLayout(BorderPane rootBorder, ArrayList<ImageView> cards){
        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            for(ImageView card: cards){
                card.setFitWidth(rootBorder.getWidth()*0.13);
            }
        });
    }

    public void handLayout(BorderPane rootBorder, ArrayList<ImageView> handCards, AnchorPane handPane) {
        double spacing = 20.0;
        handPane.setPrefWidth(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * 3);

        for (int i = 0; i < handCards.size(); i++) {
            ImageView card = handCards.get(i);
            card.setFitWidth(rootBorder.getWidth() * 0.13);
            card.setLayoutX(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * i);
        }

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            handPane.setPrefWidth(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * 3);

            for (int i = 0; i < handCards.size(); i++) {
                ImageView card = handCards.get(i);
                card.setFitWidth(rootBorder.getWidth() * 0.13);
                card.setLayoutX(spacing * 2 + (rootBorder.getWidth() * 0.13 + spacing) * i);
            }
        });
    }

    public void makeGlow(ImageView card){
        DropShadow glow = new DropShadow();
        glow.setColor(Color.CORNSILK);
        glow.setOffsetX(0f);
        glow.setOffsetY(0f);
        glow.setWidth(30);
        glow.setHeight(30);
        card.setEffect(glow);
    }

    public void buttonSetup(ImageView closeIcon, ImageView visibleIcon, Button button) {
        double iconDimension = 30;
        double buttonDim = 50;

        closeIcon.setFitWidth(iconDimension);
        closeIcon.setFitHeight(iconDimension);
        visibleIcon.setFitWidth(iconDimension);
        visibleIcon.setFitHeight(iconDimension);
        button.setGraphic(closeIcon);
        button.setPrefWidth(buttonDim);
        button.setPrefHeight(buttonDim);

        button.getStyleClass().clear();
        button.getStyleClass().add("buttonClose");
    }

    public void updateViewport(AnchorPane rootPane, ImageView background, double initialImageWidth, double initialImageHeight) {
        double viewportWidth = Math.min(initialImageWidth, rootPane.getWidth());
        double viewportHeight = Math.min(initialImageHeight, rootPane.getHeight());

        double x = (initialImageWidth - viewportWidth) / 2;
        double y = (initialImageHeight - viewportHeight) / 2;

        background.setViewport(new Rectangle2D(x, y, viewportWidth, viewportHeight));
    }
}
