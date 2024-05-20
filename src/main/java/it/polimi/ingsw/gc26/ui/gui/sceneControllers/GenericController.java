package it.polimi.ingsw.gc26.ui.gui.sceneControllers;

import it.polimi.ingsw.gc26.MainClient;

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


}
