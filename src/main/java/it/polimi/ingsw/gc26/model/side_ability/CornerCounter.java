package it.polimi.ingsw.gc26.model.side_ability;

public class CornerCounter extends Ability {
    public static final Type TYPE = Type.CORNER_COUNTER;
    @Override
    public int useAbility(int numCorners) {
        return numCorners * 2;
    }
}
