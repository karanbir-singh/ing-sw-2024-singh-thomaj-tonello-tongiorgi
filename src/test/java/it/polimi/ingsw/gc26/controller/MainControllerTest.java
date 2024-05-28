package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.request.main_request.ConnectionRequest;
import it.polimi.ingsw.gc26.ui.tui.TUIUpdate;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainControllerTest {

    static ArrayList<MainClient> clients;

    static MainController mainController;

    static void beforeAll() throws RemoteException {
        // Create main controller
        mainController = new MainController();

        clients = new ArrayList<>();

        // Add 3 RMI clients (this is for testing, if it works with RMI, it's same for socket
        for (int i = 0; i < 3; i++) {
            MainClient client = new MainClient(new TUIUpdate());
            client.setVirtualMainController(new VirtualRMIMainController(mainController));
            client.setVirtualView(new VirtualRMIView(client.getViewController()));

            clients.add(client);
        }

    }

    @Test
    void addRequest() throws RemoteException {
        beforeAll();

        // new ConnectionRequest is only an example, it works with all type of main requests
        mainController.addRequest(new ConnectionRequest(clients.get(0).getVirtualView(), "User1", 0));

        assertEquals(1, mainController.getMainRequests().size());
    }

    @Test
    void connect() throws RemoteException {
        beforeAll();

    }

    @Test
    void createWaitingList() {
    }

    @Test
    void getGameController() {
    }

    @Test
    void recreateGames() {
    }

    @Test
    void amAlive() {
    }
}