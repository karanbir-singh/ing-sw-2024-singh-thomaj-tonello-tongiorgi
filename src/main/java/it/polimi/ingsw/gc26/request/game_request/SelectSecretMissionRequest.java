package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to select a secret mission.
 */
public class SelectSecretMissionRequest implements GameRequest, Serializable {
    /**
     * ID client that has created the request
     */
    final private String playerID;
    /**
     * Index in the array of secret mission. Zero or one are the only accepted values.
     */
    final private int position;

    /**
     * Crates the new request
     *
     * @param position Index in the array of secret mission. Zero or one are the only accepted values.
     * @param playerID ID client that has created the request
     */
    public SelectSecretMissionRequest(int position, String playerID) {
        this.playerID = playerID;
        this.position = position;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.selectSecretMission(this.position, this.playerID);
    }
}
