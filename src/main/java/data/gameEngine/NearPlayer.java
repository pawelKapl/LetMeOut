package data.gameEngine;

import data.movables.enemies.Enemy;
import data.movables.player.Player;

public interface NearPlayer {

    static boolean nearPlayer(Enemy enemy, Player player, int radius) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int dx = player.getX() + j;
                int dy = player.getY() + i;
                if (enemy.getX() == dx && enemy.getY() == dy) {
                    return true;
                }
            }
        }
        return false;
    }
}
