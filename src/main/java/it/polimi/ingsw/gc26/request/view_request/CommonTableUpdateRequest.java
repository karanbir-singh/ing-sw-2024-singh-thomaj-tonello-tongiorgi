package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class CommonTableUpdateRequest implements ViewRequest {

    private final SimplifiedCommonTable simplifiedCommonTable;

    private final String message;

    public CommonTableUpdateRequest(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedCommonTable = simplifiedCommonTable;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateCommonTable(this.simplifiedCommonTable, this.message);
    }
}
