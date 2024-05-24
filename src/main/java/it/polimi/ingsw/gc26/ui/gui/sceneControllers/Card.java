package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    final double width = 150;
    final double height = 98;

    public ImageView getImageView(Image image, boolean isDraggable) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        return imageView;
    }




}
