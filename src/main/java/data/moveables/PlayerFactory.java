package data.moveables;

import data.moveables.playerClass.Player;
import data.moveables.playerClass.Solider;
import data.terrains.Terrain;

import java.util.Map;
import java.util.logging.Logger;

public class PlayerFactory {

    private Terrain terrain;
    private Logger log = Logger.getLogger(this.toString());

    public PlayerFactory(Terrain terrain) {
        this.terrain = terrain;
    }

    public Player buildPlayer(String name, int profession) {

        log.info("Building new Player...");

        switch (profession) {
            case (1):
                return new Solider(setStartingPoint(), name);
            default:
                log.warning("Wrong profession");
                return new Solider(setStartingPoint(), "DefaultName");
        }
    }

    private Coords setStartingPoint() {
        log.info("Getting Starting Point for a new Player...");

        Coords coords = new Coords(0,0);
        Map<Integer, Integer> entrances = terrain.getEntrances();
        int y = entrances.keySet().iterator().next();
        int x = entrances.get(y);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nbx = x + j;
                int nby = y + i;
                if (i == 0 && j == 0) {
                } else if (nbx >= 0 && nby >= 0
                        && nby < terrain.getMap().length
                        && nbx < terrain.getMap()[0].length
                        && terrain.getMap()[nby][nbx] != '#') {
                    coords.setY(nby);
                    coords.setX(nbx);
                    return coords;
                }
            }
        }
        throw new RuntimeException("Error during calculation of player starting point");
    }
}
