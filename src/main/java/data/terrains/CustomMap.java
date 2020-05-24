package data.terrains;

import data.movables.Coords;
import locations.Location;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomMap implements Terrain, Serializable {

    private final String name;
    private Coords entrance;
    private final String terrainType;
    private final TerrainType[][] mapFinal;
    private Map<Coords, String> inOuts = new HashMap<>();

    private static final Logger log = Logger.getLogger(CustomMap.class.toString());


    public CustomMap(Location location) {
        this.name = location.getName();
        this.terrainType = location.getTerrainType();
        this.mapFinal = createMap(location.getLocationType(), location.getExits());
    }

    private final TerrainType[][] createMap(String fileName, Map<Integer, String> exits) {
        Path path = Paths.get("src", "resources", "customMaps", fileName + ".txt");
        List<String> map = null;

        try {
            map = Files.readAllLines(path);
        } catch (IOException e)
        {
            log.log(Level.WARNING,"Troubles During reading map form file", e);
        }
        return printTerrain(exits, map);
    }

    private TerrainType[][] printTerrain(Map<Integer, String> exits, List<String> map) {
        int exitNumber = 0;
        int width = map.get(0).length();
        int height = map.size();
        TerrainType[][] finalMap = new TerrainType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String stamp = Character.toString(map.get(i).charAt(j));
                switch (stamp) {
                    case "-":
                        finalMap[i][j] = TerrainType.EMPTY;
                        break;
                    case "#":
                        finalMap[i][j] = TerrainType.WALL;
                        break;
                    case ".":
                        finalMap[i][j] = TerrainType.GROUND;
                        break;
                    case "o":
                        finalMap[i][j] = TerrainType.ITEM;
                        break;
                    case "Ϯ":
                        finalMap[i][j] = TerrainType.UNIQUE_ITEM;
                        break;
                    case "ꭙ":
                        finalMap[i][j] = TerrainType.TRAP;
                        break;
                    case "=":
                        finalMap[i][j] = TerrainType.WATER;
                        break;
                    case "f":
                        finalMap[i][j] = TerrainType.FOREST;
                        break;
                    case "d":
                        inOuts.put(new Coords(j, i), exits.get(exitNumber));
                        if (exitNumber == 0) {
                            entrance = new Coords(j, i);
                        }
                        exitNumber++;
                        finalMap[i][j] = TerrainType.DOOR;
                        break;
                }
            }
        }
        return finalMap;
    }


    @Override
    public TerrainType[][] getMap() {
        return mapFinal;
    }

    @Override
    public Coords getEntrance() {
        return entrance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTerrainType() {
        return terrainType;
    }

    @Override
    public Map<Coords, String> getInOuts() {
        return inOuts;
    }
}
