package data.equipment.weapons;

public final class CasualWeapon extends Weapon {

    private CasualWeapon(String name, String description, int attack) {
        super(name, description, attack);
    }

    public static Weapon getInstance(String name, String description, int attack) {
        return new CasualWeapon(name, description, attack);
    }

}
