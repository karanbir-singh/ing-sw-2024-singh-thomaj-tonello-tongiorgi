package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialX;
    private double initialY;

    public void makeDraggable(ArrayList<ImageView> array, ArrayList<ImageView> targets) {
        for(ImageView imageView: array){
            imageView.setOnMousePressed(event -> {
                initialX = imageView.getLayoutX();
                initialY = imageView.getLayoutY();
                mouseAnchorX = event.getSceneX() - initialX;
                mouseAnchorY = event.getSceneY() - initialY;
            });

            imageView.setOnMouseDragged(event -> {
                imageView.setLayoutX(event.getSceneX() - mouseAnchorX);
                imageView.setLayoutY(event.getSceneY() - mouseAnchorY);
            });

            imageView.setOnMouseReleased(event -> {
                for (ImageView target: targets) {
                    if (isInTargetSpot(imageView, target)) {
                    }
                }
                imageView.setLayoutX(initialX);
                imageView.setLayoutY(initialY);
                /*
                if
                    //selectPositionOnCommonBoard();

                    //playCardFrom()
                } else {
                    imageView.setLayoutX(initialX);
                    imageView.setLayoutY(initialY);
                }*/
            });
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
