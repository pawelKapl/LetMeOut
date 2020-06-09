package data.equipment.armors;

import data.equipment.Item;

import java.util.Objects;

public abstract class Armor implements Item {

    private final String name;
    private final String description;
    private final int defence;

    protected Armor(String name, String description, int defence) {
        this.name = name;
        this.description = description;
        this.defence = defence;
    }

    public int getDefense() {
        return defence;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Armor armor = (Armor) o;
        return Objects.equals(name, armor.name) &&
                Objects.equals(description, armor.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
