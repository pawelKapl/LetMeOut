package data.terrains;

import data.movables.Coords;

import java.io.Serializable;
import java.util.Map;

public interface Terrain extends Serializable {

    TerrainType[][] getMap();
    Coords getEntrance();
    String getName();
    String getTerrainType();
    Map<Coords, String> getInOuts();
}
