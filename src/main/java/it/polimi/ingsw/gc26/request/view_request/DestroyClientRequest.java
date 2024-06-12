package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.ViewController;

public class DestroyClientRequest implements ViewRequest{

    @Override
    public void execute(ViewController viewController) {
        viewController.killProcess();
    }
}
