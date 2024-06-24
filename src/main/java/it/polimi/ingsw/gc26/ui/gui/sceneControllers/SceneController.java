package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.ui.gui.GUIApplication;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

abstract public class SceneController {
    public MainClient mainClient;

    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }


    public void changeGUICommonTable(SimplifiedCommonTable simplifiedCommonTable) {
    }

    public void changeGUIPlayer(SimplifiedPlayer simplifiedPlayer) {
    }

    public void changeGUIHand(SimplifiedHand simplifiedHand) {
    }

    public void changeGUISecretHand(SimplifiedHand simplifiedSecretHand) {
    }

    public void changeGUIPersonalBoard(SimplifiedPersonalBoard personalBoard) {
    }

    public void changeGUIotherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard) {
    }

    public void changeGUIChat(SimplifiedChat simplifiedChat) {
    }

    public void createChats(SimplifiedGame simplifiedGame) {
    }

    public void changeGUIGame(SimplifiedGame simplifiedGame) {
    }

    public void addMessageServerDisplayer(String messageFromServer, boolean isErrorMessage) {
    }

    public void updatePointScoreBoard(HashMap<String, Integer> scores, HashMap<String, Pawn> pawnsSelected) {
    }

    public void openRulebook(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                GUIApplication.openRulebook(getClass().getResourceAsStream("CODEX_Rulebook_EN.pdf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
