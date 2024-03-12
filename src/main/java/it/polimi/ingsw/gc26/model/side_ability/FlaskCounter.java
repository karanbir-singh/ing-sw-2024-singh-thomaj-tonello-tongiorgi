package it.polimi.ingsw.gc26.model.side_ability;

public class FlaskCounter extends Ability {
    public static final Type TYPE = Type.FLASK_COUNTER;
    @Override
    public int useAbility(int numFlasks) {
        return numFlasks + 1;
    }
}
