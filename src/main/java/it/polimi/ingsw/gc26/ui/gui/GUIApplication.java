package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.GenericController;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.LoginController;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.SceneEnum;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.SceneInfo;
import it.polimi.ingsw.gc26.ui.UIInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class GUIApplication extends Application implements UIInterface {
    private MainClient mainClient;

    private ArrayList<SceneInfo> scenes; //scene in order

    private Stage primaryStage;


    @Override
    public void init(MainClient.NetworkType networkType) throws IOException, NotBoundException {
        launch(networkType.toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Get value from args


        String networkType = getParameters().getUnnamed().get(0);

        //lanchaure prima startSocketClient e startRMiCLient
        if (MainClient.NetworkType.valueOf(networkType) == MainClient.NetworkType.rmi) {
            this.mainClient = MainClient.startRMIClient(MainClient.GraphicType.gui);
        } else {
            this.mainClient = MainClient.startSocketClient(MainClient.GraphicType.gui);
        }

        mainClient.getViewController()
                .getSimplifiedModel()
                .setViewUpdater(
                        new GUIUpdate(this)
                );

        this.loadScenes();
        this.setMainClientToSceneControllers();
        //settare i mainClient nei generalController
        this.primaryStage = primaryStage;

        new Thread(() -> {
            try {
                this.runConnection();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    private void loadScenes() {
        //Loads all the scenes available to be showed during the game

        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController genericController;
        for (int i = 0; i < SceneEnum.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].value()));
            try {
                root = loader.load();
                genericController = loader.getController(); //sarà di tipo dinamico corretto
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new SceneInfo(genericController, new Scene(root), SceneEnum.values()[i]));
        }
    }

    private void setMainClientToSceneControllers() {
        for (SceneInfo scene : scenes) {
            scene.getSceneController().setMainClient(this.mainClient);
        }
    }

    public SceneInfo getSceneInfo(SceneEnum sceneEnum) {
        for (int i = 0; i < SceneEnum.values().length; i++) {
            if (scenes.get(i).getSceneEnum().equals(sceneEnum)) {
                return scenes.get(i);
            }
        }
        return null; //se non l ho trovato
    }

    public GenericController getSceneController(SceneEnum sceneEnum) {
        for (int i = 0; i < SceneEnum.values().length; i++) {
            if (scenes.get(i).getSceneEnum().equals(sceneEnum)) {
                return scenes.get(i).getSceneController();
            }
        }
        return null; //se non l ho trovato
    }


    @Override
    public void runConnection() throws RemoteException {
        //Initial state in CONNECTION

        Platform.runLater(() -> this.primaryStage.setScene(this.getSceneInfo(SceneEnum.LOGIN).getScene()));
        Platform.runLater(() -> this.primaryStage.show());

        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                try {
                    mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.mainClient.getClientState() == ClientState.CREATOR) {
            Platform.runLater(() -> {
                this.getSceneController(SceneEnum.CREATOR).setNickName(this.getSceneController(SceneEnum.LOGIN).getNickName());
            });
            Platform.runLater(() -> this.primaryStage.setScene(this.getSceneInfo(SceneEnum.CREATOR).getScene()));
            Platform.runLater(() -> this.primaryStage.show());
            synchronized (this.mainClient.getLock()) {
                while (this.mainClient.getClientState() == ClientState.CREATOR) {
                    try {
                        mainClient.getLock().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            while (this.mainClient.getClientState() == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.mainClient.setClientState(ClientState.CREATOR);
                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CREATOR) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
            Platform.runLater(() -> {
                this.primaryStage.setScene(this.getSceneInfo(SceneEnum.LOGIN).getScene());
                ((LoginController) this.getSceneController(SceneEnum.LOGIN)).setStatus("NICKNAME INVALIDO, REINSERISCI");
            });
            Platform.runLater(() -> this.primaryStage.show());
            while (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        System.out.println("Waiting for other players ...");
        Platform.runLater(() -> {
            this.primaryStage.setScene(this.getSceneInfo(SceneEnum.WAITING).getScene());
        });
        Platform.runLater(() -> this.primaryStage.show());
        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.WAITING) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        synchronized (this.mainClient.getLock()) {
            Platform.runLater(() -> this.primaryStage.setScene(this.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getScene()));
            Platform.runLater(() -> this.primaryStage.show());
            this.mainClient.setVirtualGameController(this.mainClient.getVirtualMainController().getVirtualGameController(this.mainClient.getViewController().getGameID()));
            while (this.mainClient.getVirtualGameController() == null) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game begin");
    }

    @Override
    public void runGame() throws RemoteException {

    }
}
