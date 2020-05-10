package data.equipment.weapons;

public class BasicKnife extends Weapon {


    public BasicKnife() {
        setDescription("Basic Knife");
        setName("Basic Knife (+2dmg)");
    }

    @Override
    public int getBonusAttack() {
        return 2;
    }
}
