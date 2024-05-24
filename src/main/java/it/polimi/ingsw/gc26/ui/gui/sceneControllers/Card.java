package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    ImageView imageView;
    final double width = 150;
    final double height = 98;


    private void makeDraggable(){

    }
    private void clip(){

    }

    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(Image image){
        this.imageView.setImage(image);
    }


}
