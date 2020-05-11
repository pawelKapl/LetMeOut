package data.equipment.armors;

public class LeatherArmor extends Armor {

    public LeatherArmor() {
        setName("Old Leather Armor");
        setDescription("Item Description: Armor. Starting item. Just a dirty, old, leather mantle. (+1def)");
    }

    @Override
    public int getBonusDefense() {
        return 1;
    }
}
