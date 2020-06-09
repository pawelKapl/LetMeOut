package data.equipment.weapons;

import data.equipment.Item;

import java.util.Objects;

public abstract class Weapon implements Item {

    private final String name;
    private final String description;
    private final int attack;

    protected Weapon(String name, String description, int attack) {
        this.name = name;
        this.description = description;
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Weapon weapon = (Weapon) o;
        return Objects.equals(name, weapon.name) &&
                Objects.equals(description, weapon.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
