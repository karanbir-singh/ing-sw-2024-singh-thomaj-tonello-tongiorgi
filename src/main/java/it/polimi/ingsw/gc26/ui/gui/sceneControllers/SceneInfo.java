package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import javafx.scene.Scene;

import java.util.Objects;

public class    SceneInfo {

    private final SceneController sceneController;
    private final Scene scene;
    private final SceneEnum sceneEnum;
    //private final String css;

    public SceneInfo(SceneController generalController, Scene scene, SceneEnum sceneEnum){
        this.sceneController = generalController;
        this.sceneEnum = sceneEnum;
        this.scene = scene;
        //this.css = Objects.requireNonNull(this.getClass().getResource("/it/polimi/ingsw/gc26/css/" + sceneEnum.name() + ".css")).toExternalForm();
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

    //public String getCss() { return this.css; }
}
