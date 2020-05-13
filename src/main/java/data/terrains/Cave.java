package data.terrains;

import data.gameEngine.pathfinding.AStarPathFinder;
import data.gameEngine.pathfinding.FPath;
import data.gameEngine.pathfinding.TileMapImpl;
import data.movables.Coords;
import locations.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.StrictMath.random;

public final class Cave implements Terrain, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Coords entrance;
    private final TerrainType[][] mapFinal;
    private Map<Coords, String> inOuts = new HashMap<>();

    private static final Logger log = Logger.getLogger(Cave.class.toString());


    public Cave(Location location) {
        this.name = location.getName();
        this.entrance = genEntrance(location.getHeight(),location.getWidth());
        inOuts.put(entrance, location.getExits().get(0));
        this.mapFinal = createMap(generatePattern(location.getHeight(),location.getWidth()), location.getExits());
    }

    private final TerrainType[][] createMap(boolean[][] mapPattern, Map<Integer, String> exits) {
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

        enumMap[entrance.getY()][entrance.getX()] = TerrainType.DOOR;

        growForests(enumMap);
        addTreasures(enumMap, mapPattern);

        for (int i = 1; i < exits.size(); i++) {
            Coords exit = genSingleDoor(enumMap , mapPattern);
            inOuts.put(exit, exits.get(i));
        }
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

            floodCount = flood(entrance.getX(), entrance.getY(), toFlood, floodCount);

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

    private int flood(int x, int y, boolean[][] mapToFlood, int count) {

        if (isOffMap(mapToFlood, x, y) || mapToFlood[y][x]) {
            return count;
        } else {
            mapToFlood[y][x] = true;
            count++;
        }

        count = flood(x +1, y, mapToFlood, count);
        count = flood(x -1, y, mapToFlood, count);
        count = flood(x, y +1, mapToFlood, count);
        count = flood(x, y -1, mapToFlood, count);

        return count;
    }

    private Coords genEntrance(int height, int width) {
        log.info("Generating entrance");
        Random gen = new Random();
        Coords entrance;

        if (gen.nextBoolean()) {
            if (gen.nextBoolean()) {
                entrance = new Coords(gen.nextInt(width - 10) + 5, 0);
            } else {
                entrance = new Coords(gen.nextInt(width - 10) + 5, height - 1);
            }
        } else {
            if (gen.nextBoolean()) {
                entrance = new Coords(width - 1, gen.nextInt(height-10) + 5);
            } else {
                entrance = new Coords(0, gen.nextInt(height-10) + 5);
            }
        }
        return entrance;
    }

    private Coords genSingleDoor(TerrainType[][] map, boolean[][] mapPattern) {
        AStarPathFinder asp = new AStarPathFinder(new TileMapImpl(map, new ArrayList<>()),
                (int) 1.2*map[0].length, false);
        Random random = new Random();
        while (true) {
            int x = random.nextInt(map[0].length - 6) + 5;
            int y = random.nextInt(map.length - 6) + 5;
            if (map[y][x] == TerrainType.GROUND & countAliveNeighbours(mapPattern, x, y) > 1) {
                FPath pathToExit = asp.findPath(null, entrance.getX(), entrance.getY(), x, y);
                if (pathToExit != null && pathToExit.size() > map[0].length/2) {
                    map[y][x] = TerrainType.DOOR;
                    return new Coords(x, y);
                }
            }
        }
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
                if (isFreeToPopulate(map, nby, nbx)) {
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

    private boolean isFreeToPopulate(TerrainType[][] map, int nby, int nbx) {
        return nbx >= 0 && nby >= 0 && nby < map.length &&
                nbx < map[0].length && map[nby][nbx] == TerrainType.GROUND;
    }

    public Map<Coords, String> getInOuts() {
        return inOuts;
    }

    public TerrainType[][] getMap() {
        return mapFinal;
    }

    @Override
    public Coords getEntrance() {
        return entrance;
    }

    public String getName() {
        return name;
    }
}
