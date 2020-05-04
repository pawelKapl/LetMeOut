package data.moveables;

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

        int[] ints = setStartingPoint();

        switch (profession) {
            case (1):
                return new Solider(ints[0], ints[1], name);
            default:
                log.warning("Wrong profession");
                return new Solider(ints[0], ints[1], "DefaultName");
        }
    }

    private int[] setStartingPoint() {

        log.info("Getting Starting Point for new Player...");

        int[] coords = new int[2];
        Map<Integer, Integer> entrances = terrain.getEntrances();
        int y = entrances.keySet().iterator().next();
        int x = entrances.get(y);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nbx = x + i;
                int nby = y + j;
                if (i == 0 && j == 0) {
                } else if (nbx >= 0 && nby >= 0
                        && nby < terrain.getMap().length
                        && nbx < terrain.getMap()[0].length
                        && terrain.getMap()[nby][nbx] != '#') {
                    coords[0] = nby;
                    coords[1] = nbx;
                    return coords;

                }
            }
        }
        throw new RuntimeException("Error during calculation of player starting point");
    }
}
