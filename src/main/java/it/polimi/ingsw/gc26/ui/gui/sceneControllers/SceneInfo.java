package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.ui.gui.SceneEnum;
import javafx.scene.Scene;

/**
 * This class represents the information about a specific scene in the application.
 * It contains references to the scene controller, the scene itself, and the scene enumeration type.
 */
public class SceneInfo {
    /**
     * Controller responsible for managing the scene
     */
    private final SceneController sceneController;
    /**
     * JavaFX Scene object
     */
    private final Scene scene;
    /**
     * Enumeration representing the scene
     */
    private final SceneEnum sceneEnum;

    /**
     * Constructs a SceneInfo object with the specified scene controller, scene, and scene enumeration type
     *
     * @param generalController Controller responsible for managing the scene
     * @param scene avaFX Scene object
     * @param sceneEnum Enumeration representing the scene
     */
    public SceneInfo(SceneController generalController, Scene scene, SceneEnum sceneEnum){
        this.sceneController = generalController;
        this.sceneEnum = sceneEnum;
        this.scene = scene;
        //this.css = Objects.requireNonNull(this.getClass().getResource("/it/polimi/ingsw/gc26/css/" + sceneEnum.name() + ".css")).toExternalForm();
    }

    /**
     * Returns scene controller
     *
     * @return controller
     */
    public SceneController getSceneController(){
        return this.sceneController;
    }

    /**
     * Returns the controller's scene
     *
     * @return scene
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Returns the scene enumeration type
     *
     * @return enumeration
     */
    public SceneEnum getSceneEnum(){
        return this.sceneEnum;
    }

}
