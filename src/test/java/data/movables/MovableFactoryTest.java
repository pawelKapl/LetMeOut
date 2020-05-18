package data.movables;

import data.movables.enemies.Enemy;
import data.movables.enemies.Lizard;
import data.movables.enemies.Predator;
import data.movables.player.Player;
import data.movables.player.Solider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovableFactoryTest {

    MovableFactory movableFactory;

    @BeforeEach
    void setUp() {
        movableFactory = new MovableFactory();
    }

    @Test
    void buildEnemySuccess() {
        Enemy enemy = movableFactory.buildEnemy(2);
        assertTrue(enemy instanceof Predator);
    }

    @Test
    void buildEnemyWrongInputDefaultBehaviour() {
        Enemy enemy = movableFactory.buildEnemy(0);
        assertTrue(enemy instanceof Lizard);
        enemy = movableFactory.buildEnemy(9999);
        assertTrue(enemy instanceof Lizard);
    }

    @Test
    void buildPlayerSuccess() {
        Player player = movableFactory.buildPlayer("Test", 1);
        assertTrue(player instanceof Solider);
        assertEquals("Test", player.getName());
    }

    @Test
    void buildPlayerWrongInputDefaultBehaviour() {
        Player player = movableFactory.buildPlayer("Test", 9999);
        assertTrue(player instanceof Solider);
        assertEquals("DefaultName", player.getName());
    }
}