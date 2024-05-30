package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

public enum SceneEnum {
    LOGIN("/it/polimi/ingsw/gc26/login.fxml"),
    CREATOR("/it/polimi/ingsw/gc26/creator.fxml"),
    WAITING("/it/polimi/ingsw/gc26/waiting.fxml"),
    STARTERCARDCHOICE("/it/polimi/ingsw/gc26/starterCardChoice.fxml"),
    PAWNSELECTION("/it/polimi/ingsw/gc26/pawnSelection.fxml"),
    SECRETMISSIONCHOICE("/it/polimi/ingsw/gc26/secretMissionChoice.fxml"),
    GAMEFLOW("/it/polimi/ingsw/gc26/gameFlow.fxml"),
    INFO("/it/polimi/ingsw/gc26/info.fxml"),
    ERROR("/it/polimi/ingsw/gc26/error.fxml");
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
