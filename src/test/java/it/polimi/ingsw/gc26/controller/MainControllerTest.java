package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.RMI.RMIPingManager;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.request.main_request.ConnectionRequest;
import it.polimi.ingsw.gc26.ui.tui.TUIUpdate;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    static ArrayList<MainClient> clients;

    static MainController mainController;

    static void beforeAll() throws RemoteException, InterruptedException {
        // Create main controller
        mainController = new MainController();

        synchronized (mainController.lock) {
            while (!mainController.threadStarted) {
                mainController.lock.wait();
            }

            clients = new ArrayList<>();

            // Add 3 RMI clients (this is for testing, if it works with RMI, it's same for socket)
            for (int i = 0; i < 3; i++) {
                MainClient client = new MainClient();
                client.setVirtualMainController(new VirtualRMIMainController(mainController));
                client.setVirtualView(new VirtualRMIView(client.getViewController()));
                client.getViewController().getSimplifiedModel().setViewUpdater(new TUIUpdate(new SimplifiedModel()));
                client.setPingManager(new RMIPingManager(client));

                clients.add(client);
            }
        }
    }

    @Test
    void addRequest() throws RemoteException, InterruptedException {
        beforeAll();

        // new ConnectionRequest is only an example, it works with all type of main requests
        mainController.addRequest(new ConnectionRequest(clients.get(0).getVirtualView(), "User1", 0));

        assertEquals(1, mainController.getMainRequests().size());
    }

    @Test
    void connect() throws RemoteException, InterruptedException {
        beforeAll();

        mainController.connect(clients.get(0).getVirtualView(), "User1");

        assertEquals(ClientState.CREATOR, clients.get(0).getClientState());
    }

    @Test
    void invalidNickname() throws RemoteException, InterruptedException {
        beforeAll();

        mainController.connect(clients.get(0).getVirtualView(), "User1");
        mainController.createWaitingList(clients.get(0).getVirtualView(), "User1", 2);
        mainController.connect(clients.get(1).getVirtualView(), "User1");

        assertEquals(ClientState.INVALID_NICKNAME, clients.get(1).getClientState());
    }

    @Test
    void createWaitingList() throws RemoteException, InterruptedException {
        beforeAll();

        mainController.createWaitingList(clients.get(0).getVirtualView(), "User1", 2);

        assertEquals(ClientState.WAITING, clients.get(0).getClientState());
        assertEquals(1, mainController.getWaitingClients().size());
    }

    @Test
    void joinWaitingList() throws RemoteException, InterruptedException {
        beforeAll();

        mainController.createWaitingList(clients.get(0).getVirtualView(), "User1", 3);
        mainController.joinWaitingList(clients.get(1).getVirtualView(), "User2");

        assertEquals(ClientState.WAITING, clients.get(1).getClientState());
    }

    @Test
    void recreateGame() throws IOException, InterruptedException, ClassNotFoundException {
        beforeAll();

        mainController.connect(clients.get(0).getVirtualView(), "User1");
        mainController.createWaitingList(clients.get(0).getVirtualView(), "User1", 2);
        mainController.connect(clients.get(1).getVirtualView(), "User2");
        mainController.connect(clients.get(2).getVirtualView(), "User3");

        mainController.recreateGames();

        assertNotNull(mainController.getGameController(1));
    }

    @Test
    void getGameController() throws RemoteException, InterruptedException {
        beforeAll();

        mainController.connect(clients.get(0).getVirtualView(), "User1");
        mainController.createWaitingList(clients.get(0).getVirtualView(), "User1", 2);
        mainController.connect(clients.get(1).getVirtualView(), "User2");
        mainController.connect(clients.get(2).getVirtualView(), "User3");

        assertNotNull(mainController.getGameController(1));
    }
}