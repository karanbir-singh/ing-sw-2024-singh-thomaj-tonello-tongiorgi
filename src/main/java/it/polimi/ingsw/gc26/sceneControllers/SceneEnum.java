package it.polimi.ingsw.gc26.sceneControllers;

public enum SceneEnum {
    LOGIN("/it/polimi/ingsw/gc26/login.fxml"),
    CREATOR("/it/polimi/ingsw/gc26/creator.fxml"),
    STARTERCARDCHOICE("/it/polimi/ingsw/gc26/starterCardChoice.fxml"); //ricorda di togliere
                                                                            //il punto virgola
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
