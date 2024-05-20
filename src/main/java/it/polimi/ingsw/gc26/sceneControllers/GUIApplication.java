package it.polimi.ingsw.gc26.sceneControllers;

import it.polimi.ingsw.gc26.client.ClientState;
import it.polimi.ingsw.gc26.client.MainClient;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.lang.management.LockInfo;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;


public class GUIApplication extends Application {
    MainClient mainClient;

    private ArrayList<SceneInfo> scenes; //scene in order
    private Stage primaryStage;


    public void init(MainClient.NetworkType networkType, String ip, int socketPort, int rmiPort, String remoteObjectName) {
        launch(networkType.toString(), ip, String.valueOf(socketPort), String.valueOf(rmiPort), remoteObjectName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //lanchaure prima startSocketClient e startRMiCLient
        if (getParameters().getUnnamed().get(0) == "rmi") {
            this.startRMIClient(getParameters().getUnnamed().get(1), Integer.parseInt(getParameters().getUnnamed().get(3)), getParameters().getUnnamed().get(4));
        } else {
            this.startSocketClient(getParameters().getUnnamed().get(1), Integer.parseInt(getParameters().getUnnamed().get(2)));
        }

        this.loadScenes();
        this.setMainClientToSceneControllers();
        //settare i mainClient nei generalController
        this.primaryStage = primaryStage;
       new Thread(() -> {
            try {
                this.runConnectionGUI();
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


    public void runConnectionGUI() throws RemoteException {
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
            Platform.runLater(()->{
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
            Platform.runLater(()->{
                this.primaryStage.setScene(this.getSceneInfo(SceneEnum.LOGIN).getScene());
                ((LoginController)this.getSceneController(SceneEnum.LOGIN)).setStatus("NICKNAME INVALIDO, REINSERISCI");
            });
            Platform.runLater(()->this.primaryStage.show());
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
        Platform.runLater(()->{
            this.primaryStage.setScene(this.getSceneInfo(SceneEnum.WAITING).getScene());
        });
        Platform.runLater(()->this.primaryStage.show());
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
            Platform.runLater(()->this.primaryStage.setScene(this.getSceneInfo(SceneEnum.STARTERCARDCHOICE).getScene()));
            Platform.runLater(()->this.primaryStage.show());
            this.mainClient.setVirtualGameController(this.mainClient.getVirtualMainController().getVirtualGameController(this.mainClient.getViewController().getGameID()));
            while (this.mainClient.getVirtualGameController()== null) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game begin");
    }




    private void startSocketClient(String socketServerAddress, int socketServerPort) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(socketServerAddress, socketServerPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        BufferedWriter socketOut = new BufferedWriter(socketTx);

        // Create socket client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        this.mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        this.mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

    }

    private void startRMIClient(String RMIServerAddress, int RMIServerPort, String remoteObjectName) throws RemoteException, NotBoundException {
        // Finding the registry and getting the stub of virtualMainController in the registry
        Registry registry = LocateRegistry.getRegistry(RMIServerAddress, RMIServerPort);

        // Create RMI Client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        Remote remoteObject = (Remote) registry.lookup(remoteObjectName);
        this.mainClient.setVirtualMainController((VirtualMainController) remoteObject);
        this.mainClient.setVirtualView(new VirtualRMIView(mainClient.getViewController()));
    }

}
