package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.ClientResetTimerToServer;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.*;
import it.polimi.ingsw.gc26.ui.UIInterface;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;


public class GUIApplication extends Application implements UIInterface {

    public static final String scenesPath = "/it/polimi/ingsw/gc26";

    private MainClient mainClient;
    private ArrayList<SceneInfo> scenes; //scene in order
    private Stage primaryStage;
    private SceneInfo currentSceneInfo;
    private Stage popupStage;


    @Override
    public void init(MainClient.NetworkType networkType) {
        launch(networkType.toString());
    }

    @Override
    public void start(Stage primaryStage) {
        // Get value from args
        String networkType = getParameters().getUnnamed().get(0);

        //To try with a network
        //System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");

        //lanchaure prima startSocketClient e startRMiCLient
        if (MainClient.NetworkType.valueOf(networkType) == MainClient.NetworkType.rmi) {
            try {
                this.mainClient = MainClient.startRMIClient(MainClient.GraphicType.gui);
            } catch (RemoteException e) {
                ConsoleColors.printError(e.getMessage());
                System.exit(-1);
            }
        } else {
            try {
                this.mainClient = MainClient.startSocketClient(MainClient.GraphicType.gui);
            } catch (IOException e) {
                ConsoleColors.printError(e.getMessage());
                System.exit(-1);
            }
        }

        mainClient.getViewController()
                .getSimplifiedModel()
                .setViewUpdater(
                        new GUIUpdate(this)
                );

        // Load all scenes
        this.loadScenes();

        // Setup starting stage
        this.primaryStage = primaryStage;
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.setTitle(" Codex Naturalis");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("sceneControllers/images/icon.png"))));

        // Launch thread for managing connection
        new Thread(() -> {
            try {
                // Run client connection
                this.runConnection();

                // Launch thread for managing server ping
                new Thread(this.mainClient.getPingManager()).start();
                new Thread(new ClientResetTimerToServer(this.mainClient)).start();
            } catch (RemoteException e) {
                // TODO manage on GUI
            }
        }).start();
    }

    /**
     * Load scenes into the list
     */
    private void loadScenes() {
        scenes = new ArrayList<>();

        for(SceneEnum sceneEnum : SceneEnum.values()){
            FXMLLoader loader = null;

            loader = new FXMLLoader(this.getClass().getResource(sceneEnum.value()));


            Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ConsoleColors.printError("[ERROR]: cannot load " + sceneEnum.name());
                        System.exit(-1);
                    }
                    SceneController sceneController = loader.getController();
                    sceneController.setMainClient(mainClient);

            // Add scene
            scenes.add(new SceneInfo(sceneController, new Scene(root), sceneEnum));
        }

    }

    /**
     * Returns the scene info of the given scene enum
     *
     * @param sceneEnum scene enum of the researched scene info
     * @return SceneInfo
     */
    public SceneInfo getSceneInfo(SceneEnum sceneEnum) {
        return scenes.stream()
                .filter(scene -> scene.getSceneEnum().equals(sceneEnum))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the scene controller of the given scene enum
     *
     * @param sceneEnum scene enum of the researched scene controller
     * @return SceneController
     */
    public SceneController getSceneController(SceneEnum sceneEnum) {
        return scenes.stream()
                .filter(scene -> scene.getSceneEnum().equals(sceneEnum))
                .map(SceneInfo::getSceneController)
                .findFirst()
                .orElse(null);
    }
    /**
     * Changes the showing scene
     *
     * @param sceneEnum selected scene
     */
    public void setCurrentScene(SceneEnum sceneEnum) {
        // Update current scene info
        this.currentSceneInfo = this.getSceneInfo(sceneEnum);
        Scene scene = getSceneInfo(sceneEnum).getScene();
        Platform.runLater(() -> {
            // Update stage
            scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/GeneralStyle.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/LOGIN.css")).toExternalForm());
            this.primaryStage.setOnCloseRequest((WindowEvent windowEvent) ->{
                this.mainClient.killProcesses();
            });
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        });

    }

    /**
     * @return current showing scene info
     */
    public SceneInfo getCurrentScene() {
        return this.currentSceneInfo;
    }


    @Override
    public void runConnection() throws RemoteException {
        // Set login scene
        this.getSceneInfo(SceneEnum.LOGIN).getScene().getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/GeneralStyle.css")).toExternalForm());
        this.getSceneInfo(SceneEnum.LOGIN).getScene().getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/LOGIN.css")).toExternalForm());
        this.setCurrentScene(SceneEnum.LOGIN);


        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                try {
                    mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //mainClient.setNickname(this.getSceneController(SceneEnum.LOGIN).getNickName());
        if (this.mainClient.getClientState() == ClientState.CREATOR) {
            Platform.runLater(() -> {
                this.getSceneInfo(SceneEnum.CREATOR).getScene().getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/GeneralStyle.css")).toExternalForm());
            });

            // Set creator scene
            this.mainClient.setNickname(((LoginController) this.getSceneController(SceneEnum.LOGIN)).getNickname());
            this.setCurrentScene(SceneEnum.CREATOR);

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
            // Set login scene again
            this.setCurrentScene(SceneEnum.LOGIN);
            Platform.runLater(() -> {
                ((LoginController) this.getSceneController(SceneEnum.LOGIN)).setStatus("Invalid Nickname, please try again!");
            });

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

        this.mainClient.setNickname(((LoginController) this.getSceneController(SceneEnum.LOGIN)).getNickname());

        // Set waiting scene
        this.setCurrentScene(SceneEnum.WAITING);

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
            this.mainClient.setVirtualGameController(this.mainClient.getVirtualMainController().getVirtualGameController(this.mainClient.getViewController().getGameID()));
            while (this.mainClient.getVirtualGameController() == null) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void runGame() {
    }

    public void openErrorPopup(String message) {
        this.getCurrentScene().getScene().getRoot().setDisable(true);
        this.popupStage = new Stage();
        SceneInfo sceneInfo = this.getSceneInfo(SceneEnum.ERROR);
        sceneInfo.getScene().getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/Styles/GeneralStyle.css")).toExternalForm());
        this.popupStage.setScene(sceneInfo.getScene());
        ((ErrorController) sceneInfo.getSceneController()).setMessage(message);
        this.popupStage.setOnCloseRequest(Event::consume);
        this.popupStage.alwaysOnTopProperty();
        this.popupStage.show();

    }

    public static void openRulebook(InputStream inputStream) {
        try {
            if (inputStream != null) {
                File tempFile = File.createTempFile("CODEX_Rulebook_EN", ".pdf");
                tempFile.deleteOnExit();

                // Copy the input stream to a temporary file
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Open the temporary file
                Desktop.getDesktop().open(tempFile);
            } else {
                System.err.println("Resource not found: " );
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getNickname(){
        return this.mainClient.getNickname();
    }


}
