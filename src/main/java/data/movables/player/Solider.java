package data.movables.player;

import data.gameEngine.SpecialAttacks;

import java.util.List;

public final class Solider extends Player {

    public Solider(String name) {
        super(name);
        setDefense(6);
        setAttack(8);
        setMaxHp(100);
        setSpecialAttacks(List.of(SpecialAttacks.GRENADE, SpecialAttacks.ARMOR_BREAKER, SpecialAttacks.WEAKEN));
    }
}
