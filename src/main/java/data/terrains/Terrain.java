package data.terrains;

import java.util.Map;

public interface Terrain {

    char[][] getMap();
    Map<Integer, Integer> getEntrances();
    String getName();
}
