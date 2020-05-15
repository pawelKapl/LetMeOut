package data.gameEngine;

import data.movables.Coords;

import java.io.Serializable;
import java.util.logging.Logger;

public class FogOfWar implements Serializable {

    private static final long serialVersionUID = 5L;

    private boolean[][] fog;
    private static final Logger log = Logger.getLogger(FogOfWar.class.toString());

    public FogOfWar(int height, int width) {
        log.info("Creating Fog of War");
        fog = new boolean[height][width];
    }

    public void uncover(Coords coords) {
        int count = 0;
        int size = 3;
        if (!isInsideBoard(coords.getX(), coords.getY())) {
            return;
        }
        for (int i = coords.getY() + size; i >= coords.getY() - size; i--) {
            for (int j = coords.getX() - count; j <= coords.getX() + count; j++) {
                if (isInsideBoard(j, i)) {
                    fog[i][j] = true;
                }
            }
            if (i > coords.getY()) {
                count++;
            } else {
                count--;
            }
        }
    }

    private boolean isInsideBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < fog[0].length && y < fog.length;
    }

    public boolean[][] getFog() {
        return fog;
    }
}
