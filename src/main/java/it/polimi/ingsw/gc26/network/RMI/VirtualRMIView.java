package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.view_request.*;
import it.polimi.ingsw.gc26.view_model.*;
import it.polimi.ingsw.gc26.view_model.ViewController;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIView implements VirtualView {
    /**
     * This attribute represents the client controller on which execute the called actions
     */
    private final ViewController viewController;

    public VirtualRMIView(ViewController viewController) throws RemoteException {
        this.viewController = viewController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {
        this.viewController.setClientID(clientID);
    }

    @Override
    public void updateClientState(ClientState clientState) throws RemoteException {
        this.viewController.updateClientState(clientState);
    }

    @Override
    public void setGameController() throws RemoteException {
    }

    @Override
    public void showMessage(String message) throws RemoteException {
        this.viewController.showMessage(message);
    }

    @Override
    public void showError(String message) throws RemoteException {
        this.viewController.showError(message);
    }

    @Override
    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException {
        this.viewController.addRequest(new CommonTableUpdateRequest(simplifiedCommonTable, message));
    }

    @Override
    public void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException {
        this.viewController.addRequest(new HandUpdateRequest(simplifiedHand, message));
    }

    @Override
    public void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException {
        this.viewController.addRequest(new SecretHandUpdateRequest(simplifiedSecretHand, message));
    }

    @Override
    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException {
        this.viewController.addRequest(new PersonalBoardUpdateRequest(personalBoard, message));
    }

    @Override
    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException {
        this.viewController.addRequest(new OtherPersonalBoardUpdateRequest(otherPersonalBoard, message));
    }

    @Override
    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException {
        this.viewController.addRequest(new PlayerUpdateRequest(simplifiedPlayer, message));
    }

//    @Override
//    public void updateOptionsMenu(OptionsMenu optionsMenu, String message) {
//        this.viewController.updateOptionsMenu(optionsMenu, message);
//    }

    @Override
    public void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException {
        this.viewController.addRequest(new ChatUpdateRequest(simplifiedChat, message));
    }


    @Override
    public void updateIDGame(int gameID){
        this.viewController.setGameID(gameID);
    }

    @Override
    public void updateGame(SimplifiedGame simplifiedGame, String message) throws RemoteException {
        this.viewController.addRequest(new GameUpdateRequest(simplifiedGame, message));
    }

    @Override
    public void isClientAlive(){
        //TODO FORSE INSERIRE QUALCOSA
    }

    /**
     * @throws RemoteException
     */
    @Override
    public void killProcess() throws RemoteException {
        this.viewController.killProcess();
    }
}
