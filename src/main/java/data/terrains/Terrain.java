package data.terrains;

import java.util.Map;

public interface Terrain {

    TerrainType[][] getMap();
    Map<Integer, Integer> getEntrances();
    String getName();
}
