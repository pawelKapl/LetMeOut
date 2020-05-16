package data.movables;

import data.movables.enemies.Enemy;
import data.movables.enemies.Lizard;
import data.movables.enemies.Predator;
import data.movables.enemies.Wherewolf;
import data.movables.player.Player;
import data.movables.player.Solider;

import java.util.logging.Logger;

public class MovableFactory {

    private static final Logger log = Logger.getLogger(MovableFactory.class.toString());

    public Enemy buildEnemy(int race) {

        switch (race) {
            case (1):
                return new Lizard(new Coords(0,0));
            case (2):
                return new Predator(new Coords(0,0));
            case (3):
                return new Wherewolf(new Coords(0,0));
            default:
                log.warning("Wrong enemy type");
                return new Lizard(new Coords(0,0));
        }
    }

    public Player buildPlayer(String name, int profession) {

        log.info("Building new Player...");

        switch (profession) {
            case (1):
                return new Solider(name);
            default:
                log.warning("Wrong profession");
                return new Solider("DefaultName");
        }
    }
}
