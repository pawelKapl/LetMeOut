package data.gameEngine;

import data.movables.enemies.Enemy;
import data.movables.player.Player;

import static java.lang.StrictMath.random;

public enum SpecialAttacks {

    GRENADE {
        @Override
        String attack(Enemy enemy, Player player) {
            player.setCooldown(15);
            if (IsNear.isNear(enemy.getCoords(), player.getCoords(), 2)) {
                int attack = player.getAttack() * 2 / 3 + player.getLevel() - enemy.getDefense() / 3;
                enemy.getHit(attack);
                return "[ATTACK]: " + enemy.getClass().getSimpleName() + " got " + attack + "dmg form grenade explosion";
            }
            return null;
        }
    },

    WEAKEN {
        @Override
        String attack(Enemy enemy, Player player) {
            player.setCooldown(10);
            if (IsNear.isNear(enemy.getCoords(), player.getCoords(), 1) && random() > 0.2) {
                enemy.setAttack(enemy.getAttack() * 2 / 3);
                return "[WEAKNESS]: " + enemy.getClass().getSimpleName() + " attack reduced to " + enemy.getAttack();
            }
            return null;
        }
    },

    ARMOR_BREAKER {
        @Override
        String attack(Enemy enemy, Player player) {
            player.setCooldown(10);
            if (IsNear.isNear(enemy.getCoords(), player.getCoords(), 1) && random() > 0.2) {
                enemy.setDefense(enemy.getDefense()/2);
                return "[WEAKNESS]: " + enemy.getClass().getSimpleName() + " armor reduced to " + enemy.getDefense();
            }
            return null;
        }
    };

    abstract String attack(Enemy enemies, Player player);
}
