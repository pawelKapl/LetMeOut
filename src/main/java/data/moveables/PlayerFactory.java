package data.moveables;

import data.moveables.playerClass.Player;
import data.moveables.playerClass.Solider;

import java.util.logging.Logger;

public class PlayerFactory {

    private Logger log = Logger.getLogger(this.toString());

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
