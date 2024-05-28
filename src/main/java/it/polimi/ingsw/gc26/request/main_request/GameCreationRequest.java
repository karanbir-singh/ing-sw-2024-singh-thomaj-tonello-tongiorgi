package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualView;

public class GameCreationRequest implements MainRequest {
    private final VirtualView client;
    private final String nickname;
    private final int numberOfPlayers;
    private final int priority;

    public GameCreationRequest(VirtualView client, String nickname, int numberOfPlayers, int priority) {
        this.client = client;
        this.nickname = nickname;
        this.numberOfPlayers = numberOfPlayers;
        this.priority = priority;
    }

    @Override
    public void execute(MainController mainController) {
        mainController.createWaitingList(client, nickname, numberOfPlayers);
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}
