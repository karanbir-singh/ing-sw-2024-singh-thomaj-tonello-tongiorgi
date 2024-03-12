package it.polimi.ingsw.gc26.model.side_ability;

abstract public class Ability {
    public enum Type {
        CORNER_COUNTER,
        FEATHER_COUNTER,
        FLASK_COUNTER,
        MANUSCRIPT_COUNTER
    }
    abstract public int useAbility(int num);
}
