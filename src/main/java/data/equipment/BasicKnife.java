package data.equipment;

import data.movables.playerClass.Player;

public class BasicKnife implements Weapon {

    private final String name = "Basic Knife (+2 dmg)";
    private final String description = "Basic Knife. Weapon. +2dmg bonus.";


    @Override
    public void use(Player player) {
        //todo
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getBonusAttack() {
        //todo
        return 0;
    }
}
