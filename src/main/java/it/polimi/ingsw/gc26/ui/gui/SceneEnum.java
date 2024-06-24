package it.polimi.ingsw.gc26.ui.gui;

/**
 * The SceneEnum enumeration defines the different scenes used in the application,
 * each associated with a corresponding FXML file.
 */
public enum SceneEnum {
    /**
     * Scene for the login screen.
     */
    LOGIN("login.fxml"),
    /**
     * Scene for the creator screen.
     */
    CREATOR("creator.fxml"),
    /**
     * Scene for the waiting screen.
     */
    WAITING("waiting.fxml"),
    /**
     * Scene for the starter card choice screen.
     */
    STARTERCARDCHOICE("starterCardChoice.fxml"),
    /**
     * Scene for the pawn selection screen.
     */
    PAWNSELECTION("pawnSelection.fxml"),
    /**
     * Scene for the secret mission choice screen.
     */
    SECRETMISSIONCHOICE("secretMissionChoice.fxml"),
    /**
     * Scene for the game flow screen.
     */
    GAMEFLOW("gameFlow.fxml"),
    /**
     * Scene for the error screen.
     */
    ERROR("error.fxml"),
    /**
     * Scene for the winner screen.
     */
    WINNER("winner.fxml");

    /**
     * The FXML file associated with the scene.
     */
    private final String value;
    /**
     * Constructs a SceneEnum with the specified FXML file.
     *
     * @param value the FXML file associated with the scene.
     */
    SceneEnum(final String value) {
        this.value = value;
    }

    /**
     * Returns the FXML file associated with the scene.
     *
     * @return the FXML file associated with the scene.
     */
    public String value() {
        return value;
    }
}