package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.Scene;

public class SceneInfo {

    private final SceneController sceneController;
    private final Scene scene;
    private final SceneEnum sceneEnum;

    public SceneInfo(SceneController generalController, Scene scene, SceneEnum sceneEnum){
        this.sceneController = generalController;
        this.sceneEnum = sceneEnum;
        this.scene = scene;
    }
    public SceneController getSceneController(){
        return this.sceneController;
    }

    public Scene getScene() {
        return this.scene;
    }

    public SceneEnum getSceneEnum(){
        return this.sceneEnum;
    }
}
