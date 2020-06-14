package data.movables.player;

import data.gameEngine.SpecialAttacks;
import data.gameEngine.SpecialSkills;

import java.util.List;

public class Recon extends Player {

    public Recon(String name) {
        super(name);
        setDefense(3);
        setAttack(10);
        setSpecialAttacks(List.of(SpecialAttacks.ARMOR_BREAKER, SpecialAttacks.WEAKEN));
        setSpecialSkills(List.of(SpecialSkills.ARMOR_BOOST, SpecialSkills.ATTACK_BOOST));
    }
}
