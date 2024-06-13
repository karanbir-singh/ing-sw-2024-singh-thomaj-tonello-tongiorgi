package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.ui.gui.GUIApplication;

public enum SceneEnum {
    LOGIN(GUIApplication.scenesPath + "/login.fxml"),
    CREATOR(GUIApplication.scenesPath + "/creator.fxml"),
    WAITING(GUIApplication.scenesPath + "/waiting.fxml"),
    STARTERCARDCHOICE(GUIApplication.scenesPath + "/starterCardChoice.fxml"),
    PAWNSELECTION(GUIApplication.scenesPath + "/pawnSelection.fxml"),
    SECRETMISSIONCHOICE(GUIApplication.scenesPath + "/secretMissionChoice.fxml"),
    GAMEFLOW(GUIApplication.scenesPath + "/gameFlow.fxml"),
    INFO(GUIApplication.scenesPath + "/info.fxml"),
    ERROR(GUIApplication.scenesPath + "/error.fxml"),
    WINNER(GUIApplication.scenesPath + "/winner.fxml");
    //altre

    //all the scenes
    private final String value;

    SceneEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
