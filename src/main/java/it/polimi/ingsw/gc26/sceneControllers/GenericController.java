package it.polimi.ingsw.gc26.sceneControllers;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.gc26.client.MainClient;

public class GenericController {
    protected MainClient mainClient;
    protected String nickname;

    protected void setMainClient(MainClient mainClient){
        this.mainClient = mainClient;
    }

    protected void setNickName(String nickname){
        this.nickname = nickname;
    }

    protected String getNickName(){
        return this.nickname;
    }


}
