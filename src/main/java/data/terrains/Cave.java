package data.terrains;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.StrictMath.random;

public final class Cave implements Terrain {

    private final String name;
    private final Map<Integer, Integer> entrances;
    private final TerrainType[][] mapFinal;

    private final Logger log = Logger.getLogger(this.getClass().toString());


    public Cave(String name, int height, int width) {
        if (height < 15 || height > 27) {
            height = 25;
        }
        if (width < 20 || width > 102) {
            width = 100;
        }
        this.name = name;
        this.entrances = genEntrances(height,width);
        this.mapFinal = createMap(generatePattern(height, width));
    }

    private final TerrainType[][] createMap(boolean[][] mapPattern) {
        log.info("Composing map and converting to char table");
        TerrainType[][] enumMap = new TerrainType[mapPattern.length][mapPattern[0].length];

        for (int i = 0; i < mapPattern.length; i++) {
            for (int j = 0; j < mapPattern[0].length; j++) {
                if (mapPattern[i][j]) {
                    enumMap[i][j] = TerrainType.WALL;
                } else {
                    enumMap[i][j] = TerrainType.GROUND;
                }
            }
        }
        int entrance = entrances.keySet().iterator().next();
        enumMap[entrance][entrances.get(entrance)] = TerrainType.DOOR;

        growForests(enumMap);
        addTreasures(enumMap, mapPattern);

        return enumMap;
    }

    private final boolean[][] generatePattern(int height, int width) {
        log.info("Map shape calculation process started");
        boolean[][] newMap;

        while (true) {
            int floodCount = 0;
            newMap = initializeMap(height, width);

            //tweakAble
            for (int i = 0; i < 2; i++) {
                newMap = simulationStep(newMap, 4, 3);
            }

            boolean[][] toFlood = copyArray(newMap);

            int entrance = entrances.keySet().iterator().next();

            floodCount = flood(entrances.get(entrance), entrance, toFlood, floodCount);

            if (floodCount > (width*height*55/100)) {
                break;
            }
        }
        log.info("Map shape calculation process successfully ended");
        return newMap;
    }

    private boolean[][] initializeMap(int height, int width) {

        float isAliveChance = 0.40f; //coverage lvl of map
        boolean[][] map = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(random() < isAliveChance) {
                    map[i][j] = true;
                }
            }
        }
        return map;
    }

    private boolean[][] simulationStep(boolean[][] map, int birthLimit, int deathLimit) {
        boolean[][] newMap = new boolean[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int nbs = countAliveNeighbours(map, j, i);

                if (map[i][j]) {
                    newMap[i][j] = nbs >= deathLimit;
                } else {
                    newMap[i][j] = nbs > birthLimit;
                }
            }
        }
        return newMap;
    }

    private int countAliveNeighbours(boolean[][] map, int x, int y) {

        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nbx = x + j;
                int nby = y + i;
                if (i == 0 && j == 0) {
                    //just nothing
                } else if (isOffMap(map, nbx, nby)) {
                    count++;
                } else if (map[nby][nbx]) {
                    count++;
                }
            }
        }
        return count;
    }

    private int flood(int x, int y, boolean[][] toFlood, int count) {

        if (isOffMap(toFlood, x, y) || toFlood[y][x]) {
            return count;
        } else {
            toFlood[y][x] = true;
            count++;
        }

        count = flood(x +1, y, toFlood, count);
        count = flood(x -1, y, toFlood, count);
        count = flood(x, y +1, toFlood, count);
        count = flood(x, y -1, toFlood, count);

        return count;
    }

    private Map<Integer, Integer> genEntrances(int height, int width) {
        log.info("Generating entrances/exits");
        Random gen = new Random();
        Map<Integer, Integer> entrance = new HashMap<>();

        while (entrance.size() < 1) {
            if (gen.nextBoolean()) {
                if (gen.nextBoolean()) {
                    entrance.putIfAbsent(0, gen.nextInt(width - 10) + 5);
                } else {
                    entrance.putIfAbsent(height - 1, gen.nextInt(width - 10) + 5);
                }
            } else {
                if (gen.nextBoolean()) {
                    entrance.putIfAbsent(gen.nextInt(height-10) + 5, width - 1);
                } else {
                    entrance.putIfAbsent(gen.nextInt(height-10) + 5, 0);
                }
            }
        }
        return entrance;
    }

    private boolean[][] copyArray(boolean[][] newMap) {
        return Arrays.stream(newMap)
                .map(boolean[]::clone)
                .toArray(boolean[][]::new);
    }

    private void growForests(TerrainType[][] map) {
        log.info("Growing forests...");
        for (int i = 0; i < map.length-1; i++) {
            for (int j = 0; j < map[0].length-1; j++) {
                if (map[i][j] == TerrainType.GROUND && random() < 0.03) {
                    map[i][j] = TerrainType.FOREST;
                    forestExpansion(map, i, j);
                }
            }
        }
    }

    private void forestExpansion(TerrainType[][] map, int i, int j) {
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                int nby = i + k;
                int nbx = j + l;
                if (nbx >= 0 && nby >= 0 && nby < map.length &&
                        nbx < map[0].length && map[nby][nbx] == TerrainType.GROUND) {
                    if (random() < 0.75) {
                        map[nby][nbx] = TerrainType.FOREST;
                    }
                }
            }
        }
    }

    private void addTreasures(TerrainType[][] map, boolean[][] mapPattern) {
        log.info("Hiding treasures...");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != TerrainType.WALL && map[i][j] != TerrainType.DOOR) {
                    if (countAliveNeighbours(mapPattern, j, i) == 6 && random() > 0.1) {
                        map[i][j] = TerrainType.UNIQUE_ITEM;
                    } else if (countAliveNeighbours(mapPattern, j, i) == 5 && random() > 0.3) {
                        map[i][j] = TerrainType.ITEM;
                    }
                }
            }
        }
    }

    private boolean isOffMap(boolean[][] map, int x, int y) {
        return x < 0 || y < 0 || x >= map[0].length || y >= map.length;
    }

    public Map<Integer, Integer> getEntrances() {
        return new HashMap<>(entrances);
    }

    public TerrainType[][] getMap() {
        return mapFinal;
    }

    public String getName() {
        return name;
    }
}
