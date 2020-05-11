package data.equipment.weapons;

public class BasicKnife extends Weapon {


    public BasicKnife() {
        setDescription("Item Description: Weapon. Starting item. Just a simple rusty knife. (+3dmg)");
        setName("Basic Rusty Knife");
    }

    @Override
    public int getBonusAttack() {
        return 2;
    }
}
