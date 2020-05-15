package data.terrains;

import data.movables.Coords;

import java.util.Map;

public interface Terrain {

    TerrainType[][] getMap();
    Coords getEntrance();
    String getName();
    String getTerrainType();
    Map<Coords, String> getInOuts();
}
