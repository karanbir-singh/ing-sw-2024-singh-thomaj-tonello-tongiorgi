module it.polimi.ingsw.gc26 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires jdk.jfr;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires java.management;


    exports it.polimi.ingsw.gc26.model.game;
    opens it.polimi.ingsw.gc26.model.game to javafx.fxml;
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
    exports it.polimi.ingsw.gc26.network;
    opens it.polimi.ingsw.gc26.network to javafx.fxml;
    exports it.polimi.ingsw.gc26.request.game_request;
    opens it.polimi.ingsw.gc26.request.game_request to javafx.fxml;
    exports it.polimi.ingsw.gc26.request.main_request;
    opens it.polimi.ingsw.gc26.request.main_request to javafx.fxml;
    exports it.polimi.ingsw.gc26.view_model;
    opens it.polimi.ingsw.gc26.view_model to javafx.fxml;
    exports it.polimi.ingsw.gc26.ui.gui.sceneControllers;
    opens it.polimi.ingsw.gc26.ui.gui.sceneControllers to javafx.fxml;
    exports it.polimi.ingsw.gc26.ui.tui;
    opens it.polimi.ingsw.gc26.ui.tui to javafx.fxml;
    exports it.polimi.ingsw.gc26.ui.gui;
    opens it.polimi.ingsw.gc26.ui.gui to javafx.fxml;
    exports it.polimi.ingsw.gc26.ui;
    opens it.polimi.ingsw.gc26.ui to javafx.fxml;
}