package it.polimi.ingsw.gc26.client;

import javafx.application.Application;
import javafx.stage.Stage;


public class GUIApplication extends Application {
    MainClient mainClient;

    public void init(MainClient.NetworkType networkType , String ip, int socketPort, int rmiPort, String remoteObjectName){
        launch(networkType.toString(), ip, String.valueOf(socketPort), String.valueOf(rmiPort), remoteObjectName);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
