package data.gameEngine;

import data.terrains.TerrainType;

import java.util.logging.Logger;

public class EffectsLayer {


    private TerrainType[][] effectsLayer;

    private static final Logger log = Logger.getLogger(EffectsLayer.class.toString());

    public EffectsLayer(int width, int height) {
        log.info("Creating Effects Layer");
        effectsLayer = new TerrainType[height][width];
        reset();
    }

    public void reset() {
        for (int i = 0; i < effectsLayer.length; i++) {
            for (int j = 0; j < effectsLayer[0].length; j++) {
                if (effectsLayer[i][j] != TerrainType.DEAD_MARK) {
                    effectsLayer[i][j] = TerrainType.EMPTY;
                }
            }
        }
    }

    public void mark(int x, int y, TerrainType terrainType) {
        if (isInsideBoard(x, y)) {
            effectsLayer[y][x] = terrainType;
        }
    }

    private boolean isInsideBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < effectsLayer[0].length && y < effectsLayer.length;
    }

    public TerrainType[][] getEffectsLayer() {
        return effectsLayer;
    }
}
