package it.polimi.ingsw.gc26.ui.gui;

public enum SceneEnum {
    LOGIN("login.fxml"),
    CREATOR("creator.fxml"),
    WAITING("waiting.fxml"),
    STARTERCARDCHOICE("starterCardChoice.fxml"),
    PAWNSELECTION("pawnSelection.fxml"),
    SECRETMISSIONCHOICE("secretMissionChoice.fxml"),
    GAMEFLOW("gameFlow.fxml"),
    ERROR("error.fxml"),
    WINNER("winner.fxml");

    private final String value;

    SceneEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
