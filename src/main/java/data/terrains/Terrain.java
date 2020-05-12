package data.terrains;

import data.movables.Coords;

public interface Terrain {

    TerrainType[][] getMap();
    Coords getEntrance();
    String getName();
}
