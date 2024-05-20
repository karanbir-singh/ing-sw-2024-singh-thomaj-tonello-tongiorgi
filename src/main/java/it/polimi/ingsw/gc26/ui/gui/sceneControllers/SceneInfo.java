package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.Scene;

public class SceneInfo {

    private final GenericController genericController;
    private final Scene scene;
    private final SceneEnum sceneEnum;

    public SceneInfo(GenericController generalController, Scene scene,  SceneEnum sceneEnum){
        this.genericController = generalController;
        this.sceneEnum = sceneEnum;
        this.scene = scene;
    }
    public GenericController getSceneController(){
        return this.genericController;
    }

    public Scene getScene() {
        return this.scene;
    }

    public SceneEnum getSceneEnum(){
        return this.sceneEnum;
    }
}
