package data.terrains;

import data.movables.Coords;

import java.util.Map;

public interface Terrain {

    TerrainType[][] getMap();
    Coords getEntrance();
    String getName();
    Map<Coords, String> getInOuts();
}
