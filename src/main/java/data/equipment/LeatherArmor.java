package data.equipment;

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
