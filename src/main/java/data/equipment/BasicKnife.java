package data.equipment;

public class BasicKnife extends Weapon {


    public BasicKnife() {
        setDescription("Basic Knife");
        setName("Basic Knife (+3dmg)");
    }

    @Override
    public int getBonusAttack() {
        return 3;
    }
}
