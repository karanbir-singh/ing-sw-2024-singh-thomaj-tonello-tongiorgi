package it.polimi.ingsw.gc26.model.side_ability;

public class ManuscriptCounter extends Ability {
    public static final Type TYPE = Type.MANUSCRIPT_COUNTER;
    @Override
    public int useAbility(int numManuscripts) {
        return numManuscripts + 1;
    }
}
