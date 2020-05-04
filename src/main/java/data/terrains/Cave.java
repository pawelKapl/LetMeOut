package data.terrains;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.StrictMath.random;

public final class Cave implements Terrain {

    private final String name;
    private int floodCount;
    private final Map<Integer, Integer> entrances;
    private final boolean[][] mapPattern;
    private final char[][] mapFull;

    private final Logger log = Logger.getLogger(this.getClass().toString());

    public Cave(String name, int height, int width) {
        if (height < 15) {
            height = 15;
        }
        if (width < 20) {
            width = 20;
        }
        this.name = name;
        this.entrances = genEntrances(height,width);
        this.mapPattern = generateMap(height,width);
        this.mapFull = decorateMap();
    }

    private boolean[][] generateMap(int height, int width) {
        log.info("Map shape calculation process started");
        boolean[][] newMap;

        while (true) {
            floodCount = 0;
            newMap = initializeMap(height, width);

            //tweakAble
            for (int i = 0; i < 2; i++) {
                newMap = simulationStep(newMap, 4, 3);
            }

            boolean[][] toFlood = copyArray(newMap);

            int entrance = entrances.keySet().iterator().next();

            flood(entrances.get(entrance), entrance, toFlood);

            if (floodCount > (width*height*6/10)) {
                break;
            }
        }
        log.info("Map shape calculation process successfully ended");
        return newMap;
    }

    private boolean[][] initializeMap(int height, int width) {

        float isAliveChance = 0.35f; //coverage lvl of map
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
                } else if (nbx < 0 || nby < 0 || nbx >= map[0].length || nby >= map.length) {
                    count++;
                } else if (map[nby][nbx]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void flood(int x, int y, boolean[][] toFlood) {

        if (x < 0 || y < 0 || y >= toFlood.length || x >= toFlood[0].length || toFlood[y][x]) {
            return;
        } else {
            toFlood[y][x] = true;
            floodCount++;
        }

        flood(x +1, y, toFlood);
        flood(x -1, y, toFlood);
        flood(x, y +1, toFlood);
        flood(x, y -1, toFlood);
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

    private void growForests(char[][] map) {
        log.info("Growing forests...");
        for (int i = 0; i < map.length-1; i++) {
            for (int j = 0; j < map[0].length-1; j++) {
                if (map[i][j] == '.' && random() < 0.03) {
                    map[i][j] = 'f';
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            int nby = i + k;
                            int nbx = j + l;
                            if (nbx >= 0 && nby >= 0 && nby < map.length && nbx < map[0].length
                                    && map[nby][nbx] == '.') {
                                if (random() < 0.75) {
                                    map[nby][nbx] = 'f';
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addTreasures(char[][] map) {
        log.info("Hiding treasures...");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (!mapPattern[i][j]) {
                    if (countAliveNeighbours(mapPattern, j, i) == 6 && random() > 0.1) {
                        map[i][j] = 'u';
                    } else if (countAliveNeighbours(mapPattern, j, i) == 5 && random() > 0.3) {
                        map[i][j] = 'o';
                    }
                }
            }
        }
    }

    private final char[][] decorateMap() {
        log.info("Composing map and converting to char table");
        char[][] charMap = new char[mapPattern.length][mapPattern[0].length];

        for (int i = 0; i < mapPattern.length; i++) {
            for (int j = 0; j < mapPattern[0].length; j++) {
                if (mapPattern[i][j]) {
                    charMap[i][j] = '#';
                } else {
                    charMap[i][j] = '.';
                }
            }
        }
        int entrance = entrances.keySet().iterator().next();
        charMap[entrance][entrances.get(entrance)] = 'd';

        growForests(charMap);
        addTreasures(charMap);

        return charMap;
    }

    public Map<Integer, Integer> getEntrances() {
        return new HashMap<>(entrances);
    }

    public char[][] getMap() {
        return mapFull;
    }

    public String getName() {
        return name;
    }
}
