package it.polimi.ingsw.gc26.model.side_ability;

public class FeatherCounter extends Ability{
    public static final Type TYPE = Type.FEATHER_COUNTER;
    @Override
    public int useAbility(int numFeathers) {
        return numFeathers + 1;
    }
}
