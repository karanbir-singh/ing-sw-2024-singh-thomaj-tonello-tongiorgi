package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CommonLayout {
    public void pageBindings(ScrollPane rootScrollPane, AnchorPane rootAnchor, VBox centerVBox, VBox leftVBox, VBox rightVBox){
        rootAnchor.prefWidthProperty().bind(rootScrollPane.widthProperty());
        rootAnchor.prefHeightProperty().bind(rootScrollPane.heightProperty());

        centerVBox.prefHeightProperty().bind(rootAnchor.heightProperty());
        AnchorPane.setTopAnchor(centerVBox, 0.0);
        AnchorPane.setLeftAnchor(centerVBox, (rootAnchor.getWidth() - centerVBox.getWidth()) / 2);

        rootAnchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(centerVBox, 0.0);
        });

        rootAnchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(centerVBox, (newVal.doubleValue() - centerVBox.getWidth()) / 2);
        });
    }

    public void cardsLayout(ArrayList<ImageView> cards){

    }
}
