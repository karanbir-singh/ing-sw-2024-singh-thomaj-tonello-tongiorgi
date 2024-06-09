package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class CommonLayout {
    private Image gameBackground = new Image(getClass().getResource("/images/game-background.png").toExternalForm());
    public void pageBindings(ScrollPane rootScrollPane, BorderPane rootBorder, HBox leftHBox, VBox rightVBox, VBox centerVBox){

        rootBorder.heightProperty().addListener((obs, oldVal, newVal) -> {
            setGameBackground(rootBorder);
            try {
                leftHBox.setPrefHeight(rootBorder.getPrefHeight());
            } catch (NullPointerException e) {}
            rightVBox.setPrefHeight(rootBorder.getPrefHeight());
        });

        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            setGameBackground(rootBorder);
        });
    }

    public void setPersonalBoardRatio(BorderPane rootBorder, TabPane personalBoardTabPane, double vRatio, double hRatio) {
        rootBorder.heightProperty().addListener((obs, oldVal, newVal) -> {
            personalBoardTabPane.prefHeightProperty().bind(rootBorder.heightProperty().multiply(vRatio));
        });
        rootBorder.widthProperty().addListener((obs, oldVal, newVal) -> {
            personalBoardTabPane.prefWidthProperty().bind(rootBorder.widthProperty().multiply(hRatio));
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

    public void setBackground(AnchorPane rootPane, ImageView background) {
        double initialImageWidth = background.getImage().getWidth();
        double initialImageHeight = background.getImage().getHeight();

        background.fitHeightProperty().bind(rootPane.heightProperty());
        background.fitWidthProperty().bind(rootPane.widthProperty());

        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateViewport(rootPane.getWidth(), rootPane.getHeight(), background, initialImageWidth, initialImageHeight);
        });

        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateViewport(rootPane.getWidth(), rootPane.getHeight(), background, initialImageWidth, initialImageHeight);
        });
    }

    public void updateViewport(double paneWidth, double paneHeight, ImageView background, double initialImageWidth, double initialImageHeight) {
        double viewportWidth = Math.min(initialImageWidth, paneWidth);
        double viewportHeight = Math.min(initialImageHeight, paneHeight);

        double x = (initialImageWidth - viewportWidth) / 2;
        double y = (initialImageHeight - viewportHeight) / 2;

        background.setViewport(new Rectangle2D(x, y, viewportWidth, viewportHeight));
    }

    public void setGameBackground(BorderPane rootBorder) {
        rootBorder.setBackground(new Background(new BackgroundImage(gameBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
    }
}