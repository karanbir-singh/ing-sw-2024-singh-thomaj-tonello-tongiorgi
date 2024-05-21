package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.ui.UIInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TUIApplication implements UIInterface {
    MainClient mainClient;

    @Override
    public void init(MainClient.NetworkType networkType) throws IOException, NotBoundException {
        this.start(networkType.toString());
    }

    public void start(String... args) throws NotBoundException, IOException {
        String networkType = args[0];

        if (MainClient.NetworkType.valueOf(networkType) == MainClient.NetworkType.rmi) {
            this.mainClient = MainClient.startRMIClient(MainClient.GraphicType.tui);
        } else {
            this.mainClient = MainClient.startSocketClient(MainClient.GraphicType.tui);
        }

        this.mainClient
                .getViewController()
                .getSimplifiedModel()
                .setViewUpdater(
                        new TUIUpdate()
                );

        new Thread(() -> {
            try {
                this.runConnection();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public void runConnection() throws RemoteException {
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert your nickname: ");
        String nickname = scanner.nextLine();
        this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

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
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            String input = scanner.nextLine();
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

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
                System.err.println("Invalid number of players!");
                System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                input = scanner.nextLine();
                this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

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
            while (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!");
                System.err.flush();
                System.out.println("Insert new nickname: ");
                nickname = scanner.nextLine();

                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

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
        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.WAITING) {
                System.out.flush();
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
        System.out.println("Game begin");
    }

    @Override
    public void runGame() throws RemoteException {
        // Declare scanner
        Scanner scan = new Scanner(System.in);
        // Infinite loop
        while (true) {
            int cardIndex;
            Integer option = this.printOptions();
            switch (option) {
                case 1:
                    System.out.println("Select the card position: (0/1/2)");
                    String xPosition = scan.nextLine();
                    mainClient.getVirtualGameController().selectCardFromHand(Integer.parseInt(xPosition), this.mainClient.getClientID());
                    break;
                case 2:
                    mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                    break;
                case 3:
                    mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                    break;
                case 4:
                    System.out.println("Insert the X coordinate: ");
                    String XPosition = scan.nextLine();
                    System.out.println("Insert the Y coordinate: ");
                    String YPosition = scan.nextLine();
                    mainClient.getVirtualGameController().selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), this.mainClient.getClientID());
                    break;
                case 5:
                    //TODO use only one number
                    System.out.println("Insert the card index (0/1/2/3/4/5): ");
                    cardIndex = Integer.parseInt(scan.nextLine());
                    mainClient.getVirtualGameController().selectCardFromCommonTable(cardIndex, this.mainClient.getClientID());
                    break;
                case 6:
                    mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
                    break;
                case 7:
                    System.out.println("Insert the pawn color: ");
                    String color = scan.nextLine();
                    mainClient.getVirtualGameController().choosePawnColor(color, this.mainClient.getClientID());
                    break;
                case 8:
                    System.out.println("Insert the card index: (0/1) ");
                    cardIndex = Integer.parseInt(scan.nextLine());
                    mainClient.getVirtualGameController().selectSecretMission(cardIndex, this.mainClient.getClientID());
                    break;
                case 9:
                    mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
                    break;
                case 10:
                    System.out.println("Insert the player's nickname owner of the board: ");
                    String playerNickname = scan.nextLine();
                    mainClient.getVirtualGameController().printPersonalBoard(playerNickname, this.mainClient.getClientID());
                    break;
                case 11:
                    System.out.println("Insert the receiver's nickname: (Press enter for a broadcast message)");
                    String receiverNickname = scan.nextLine();
                    String message;
                    do {
                        System.out.println("Insert message: ");
                        message = scan.nextLine();
                    } while (message == "");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    mainClient.getVirtualGameController().addMessage(message, receiverNickname, this.mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
                    break;
                case 12:
                    System.exit(0);
                    break;
            }
        }
    }

    private int printOptions() {
        System.out.println("Select your option:");
        System.out.println("" +
                "1) Select a card.\n" +
                "2) Turn selected card side.\n" +
                "3) Play card from hand.\n" +
                "4) Select position on board.\n" +
                "5) Select card from common table.\n" +
                "6) Draw selected card.\n" +
                "7) Choose pawn color.\n" +
                "8) Select secret mission.\n" +
                "9) Set secret mission.\n" +
                "10) Print player's personal board.\n" +
                "11) Open chat.\n" +
                "12) Exit game\n");

        Scanner scan = new Scanner(System.in);
        int response = scan.nextInt();
        return response;
    }
}
