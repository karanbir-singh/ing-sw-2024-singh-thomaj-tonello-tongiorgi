package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualView;

public class ConnectionRequest implements MainRequest {
    private VirtualView client;
    private String nickname;
    private int priority;

    public ConnectionRequest(VirtualView client, String nickname, int priority) {
        this.client = client;
        this.nickname = nickname;
        this.priority = priority;
    }

    @Override
    public void execute(MainController mainController) {
        mainController.connect(client, nickname);
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}
