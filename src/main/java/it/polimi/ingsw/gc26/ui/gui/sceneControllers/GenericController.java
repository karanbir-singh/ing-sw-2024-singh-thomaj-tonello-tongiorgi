package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.view_model.*;

public class GenericController {
    public MainClient mainClient;
    public String nickname;

    public void setMainClient(MainClient mainClient){
        this.mainClient = mainClient;
    }

    public void setNickName(String nickname){
        this.nickname = nickname;
    }

    public String getNickName(){
        return this.nickname;
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

    public void createChats(SimplifiedGame simplifiedGame, String nickname) {

    }
    public void changeGUIGame(SimplifiedGame simplifiedGame){

    }

}
