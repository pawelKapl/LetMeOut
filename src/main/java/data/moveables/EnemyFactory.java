package data.moveables;

import data.moveables.enemies.Enemy;
import data.moveables.enemies.Lizard;
import data.terrains.Terrain;

import java.util.Random;
import java.util.logging.Logger;

public class EnemyFactory {

    private Terrain terrain;
    private Logger log = Logger.getLogger(this.toString());
    private Random gen = new Random();

    public EnemyFactory(Terrain terrain) {
        this.terrain = terrain;
    }

    public Enemy buildEnemy(String name, int race) {

        log.info("Building new Enemy...");

        switch (race) {
            case (1):
                return new Lizard(setStartingPoint());
            default:
                log.warning("Wrong enemy type");
                return new Lizard(setStartingPoint());
        }
    }

    private Coords setStartingPoint() {
        log.info("Getting Starting Point for a new Enemy...");

        Coords coords = new Coords(0,0);

        char[][] map = terrain.getMap();

        return coords;

        //throw new RuntimeException("Error during enemy starting point calculation");
    }
}
