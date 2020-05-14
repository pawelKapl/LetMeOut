package data.gameEngine;

import data.movables.Coords;
import data.movables.enemies.Enemy;
import data.movables.enemies.Lizard;
import data.movables.enemies.Predator;
import data.movables.player.Player;
import data.movables.player.Solider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FightUtilTest {


    Player player;
    List<Enemy> enemies;
    Enemy enemy1;
    Enemy enemy2;
    FightUtil fightUtil;

    @BeforeEach
    void setUp() {
        //given
        player = new Solider("test");
        player.setCoords(new Coords(1,1));
        enemy1 = new Lizard(new Coords(1, 1));
        enemy2 = new Predator(new Coords(2,2));
        enemies = new ArrayList<>();
        enemies.add(enemy1);
        enemies.add(enemy2);
        fightUtil = new FightUtil(player, enemies);
    }
    @DisplayName("Testing player locking after attack")
    @ParameterizedTest(name = "For given x = {0} and y = {1} player.isLocked() should return {2}")
    @CsvSource({
            "1, 1, true",
            "2, 2, true",
            "2, 1, false",
            "1, 2, false",
            "0, 0, false"
    })
    void attackEnemy(int x, int y, boolean result) {
        fightUtil.attackEnemy(x, y);
        assertEquals(result, player.isLocked());
    }

    @DisplayName("Testing enemy attack against Player, if eney was close enough to attack," +
            " and player get locked after attack")
    @ParameterizedTest(name= "For given enemy coords x = {0}, y = {1}, method should return {2}," +
                                " player.isLocked() should return {3} ")
    @CsvSource({
            "0, 0, true, true",
            "2, 2, true, true",
            "2, 0, true, true",
            "2, 1, true, true",
            "0, 1, true, true",
            "0, 3, false, false",
            "0, -1, false, false"
    })
    void tryAttackPlayer(int x, int y, boolean result, boolean playerLock) {
        Enemy enemy = new Lizard(new Coords(x,y));
        boolean b = fightUtil.tryAttackPlayer(enemy);
        assertEquals(result, b);
        assertEquals(playerLock, player.isLocked());
    }

    @Test
    void checkIfEnemyKilledTrue() {
        enemy1.setHP(0);
        fightUtil.attackEnemy(1, 1);
        assertEquals(1, enemies.size());
    }

    @Test
    void checkIfEnemyKilledFalse() {
        enemy1.setHP(0);
        enemy2.setHP(1000);
        fightUtil.attackEnemy(2, 2);
        assertEquals(2, enemies.size());
    }

    @Test
    void getMessagesDuringPlayerAttack() {
        fightUtil.attackEnemy(2, 2);
        String fightMessage = fightUtil.getMessages().peek().split(" ")[0];
        assertEquals("[FIGHT]:", fightMessage);
    }

    @Test
    void getMessagesDuringEnemyAttack() {
        player.setCoords(new Coords(1,2));
        fightUtil.tryAttackPlayer(enemy2);
        String fightMessage = fightUtil.getMessages().peek().split(" ")[0];
        assertEquals("[ATTACK]:", fightMessage);
    }
}