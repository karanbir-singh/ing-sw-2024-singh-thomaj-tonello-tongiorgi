package it.polimi.ingsw.gc26.view_model;

import java.io.Serializable;

public class OptionsMenu implements Serializable {
    private final String options;

    public OptionsMenu(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }
}
