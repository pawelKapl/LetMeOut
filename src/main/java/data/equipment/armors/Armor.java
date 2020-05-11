package data.equipment.armors;

import data.equipment.Item;

import java.util.Objects;

public abstract class Armor implements Item {

    private String name;
    private String description;
    private int defence;

    public int getDefense() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
