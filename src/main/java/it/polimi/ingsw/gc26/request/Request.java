package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public interface Request {
    void execute(GameController gameController);
}
