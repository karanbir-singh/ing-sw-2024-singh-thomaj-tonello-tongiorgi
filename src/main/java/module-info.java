module it.polimi.ingsw.gc26 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires jdk.jfr;
    requires java.sql;
    requires java.desktop;


    exports it.polimi.ingsw.gc26.model.card_side;
    opens it.polimi.ingsw.gc26.model.card_side to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card;
    opens it.polimi.ingsw.gc26.model.card to javafx.fxml;
    exports it.polimi.ingsw.gc26;
    opens it.polimi.ingsw.gc26 to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card_side.mission;
    opens it.polimi.ingsw.gc26.model.card_side.mission to javafx.fxml;
    exports it.polimi.ingsw.gc26.model.card_side.ability;
    opens it.polimi.ingsw.gc26.model.card_side.ability to javafx.fxml;
    exports it.polimi.ingsw.gc26.network.RMI;
    opens it.polimi.ingsw.gc26.network.RMI to javafx.fxml;
    exports it.polimi.ingsw.gc26.controller;
    opens it.polimi.ingsw.gc26.controller to javafx.fxml;

}