package data.equipment.armors;

public final class CasualArmor extends Armor {

    private CasualArmor(String name, String description, int defence) {
        super(name, description, defence);
    }

    public static Armor getInstance(String name, String description, int defence) {
        return new CasualArmor(name, description, defence);
    }
}
