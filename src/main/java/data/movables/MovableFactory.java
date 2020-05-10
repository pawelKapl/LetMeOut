package data.movables;

import data.movables.enemies.Enemy;
import data.movables.enemies.Lizard;
import data.movables.playerClass.Player;
import data.movables.playerClass.Solider;

import java.util.logging.Logger;

public class MovableFactory {

    private Logger log = Logger.getLogger(this.toString());

    public Enemy buildEnemy(int race) {

        switch (race) {
            case (1):
                return new Lizard(new Coords(0,0));
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
