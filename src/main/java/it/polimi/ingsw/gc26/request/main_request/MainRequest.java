package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;

public interface MainRequest {
    void execute(MainController mainController);

    int getPriority();
}
