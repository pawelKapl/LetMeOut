package data.equipment.armors;

public class LeatherArmor extends Armor {

    public LeatherArmor() {
        setName("Leather Armor (+1def)");
        setDescription("Wooden Armor");
    }

    @Override
    public int getBonusDefense() {
        return 1;
    }
}
