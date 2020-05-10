package data.equipment.weapons;

import data.equipment.Item;

import java.util.Objects;

public abstract class Weapon implements Item {

    private String name;
    private String description;

    public abstract int getBonusAttack();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
