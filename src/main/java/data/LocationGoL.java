package data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.StrictMath.random;

public final class LocationGoL implements Location {

    private final String name;
    private final boolean[][] mapPattern;
    private int floodCount;
    private final Map<Integer, Integer> entrances;

    public LocationGoL(String name, int width, int height) {
        if (height < 20) {
            height = 20;
        }
        if (width < 15) {
            width = 15;
        }
        this.name = name;
        this.entrances = genEntrances(width, height);
        this.mapPattern = generateMap(width,height);
    }

    private boolean[][] generateMap(int width, int height) {

        boolean[][] newMap;
        int simulationStepsNumber = 2;

        while (true) {
            floodCount = 0;
            newMap = initializeMap(width, height);

            for (int i = 0; i < simulationStepsNumber; i++) {
                newMap = simulationStep(newMap, 4, 3);
            }

            boolean[][] toFlood = copyArray(newMap);

            int entrance = entrances.keySet().iterator().next();

            flood(entrance, entrances.get(entrance), toFlood);

            if (floodCount > (width*height*6/10)) {
                break;
            }
        }
        return newMap;
    }

    private boolean[][] initializeMap(int width, int height) {

        float isAliveChance = 0.35f; //coverage lvl of map
        boolean[][] map = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(random() < isAliveChance) {
                    map[x][y] = true;
                }
            }
        }
        return map;
    }

    private boolean[][] simulationStep(boolean[][] map, int birthLimit, int deathLimit) {
        boolean[][] newMap = new boolean[map.length][map[0].length];

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                int nbs = countAliveNeighbours(map, x, y);

                if (map[x][y]) {
                    newMap[x][y] = nbs >= deathLimit;
                } else {
                    newMap[x][y] = nbs > birthLimit;
                }
            }
        }
        return newMap;
    }

    private int countAliveNeighbours(boolean[][] map, int x, int y) {

        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nbx = x + i;
                int nby = y + j;
                if (i == 0 && j == 0) {
                } else if (nbx < 0 || nby < 0 || nbx >= map.length || nby >= map[0].length) {
                    count++;
                } else if (map[nbx][nby]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void flood(int x, int y, boolean[][] toFlood) {

        if (x < 0 || y < 0 || x >= toFlood.length || y >= toFlood[0].length || toFlood[x][y]) {
            return;
        } else {
            toFlood[x][y] = true;
            floodCount++;
        }

        flood(x +1, y, toFlood);
        flood(x -1, y, toFlood);
        flood(x, y +1, toFlood);
        flood(x, y -1, toFlood);
    }

    private Map<Integer, Integer> genEntrances(int width, int height) {

        Random gen = new Random();
        Map<Integer, Integer> entrance = new HashMap<>();

        while (entrance.size() < 1) {
            if (gen.nextBoolean()) {
                if (gen.nextBoolean()) {
                    entrance.putIfAbsent(0, gen.nextInt(height - 10) + 5);
                } else {
                    entrance.putIfAbsent(width - 1, gen.nextInt(height - 10) + 5);
                }
            } else {
                if (gen.nextBoolean()) {
                    entrance.putIfAbsent(gen.nextInt(width-10) + 5, height - 1);
                } else {
                    entrance.putIfAbsent(gen.nextInt(width-10) + 5, 0);
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

    private void growForests(String[][] map) {
        for (int i = 0; i < map.length-1; i++) {
            for (int j = 0; j < map[0].length-1; j++) {
                if (map[i][j].equals(".") && random() < 0.03) {
                    map[i][j] = "f";

                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            int nbx = i + k;
                            int nby = j + l;
                            if (nbx >= 0 && nby >= 0 && nbx < map.length && nby < map[0].length
                                    && map[nbx][nby].equals(".")) {
                                if (random() < 0.75) {
                                    map[nbx][nby] = "f";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String[][] getMap() {

        String[][] stringMap = new String[mapPattern.length][mapPattern[0].length];

        for (int i = 0; i < mapPattern.length; i++) {
            for (int j = 0; j < mapPattern[0].length; j++) {
                if (mapPattern[i][j]) {
                    stringMap[i][j] = "#";
                } else {
                    stringMap[i][j] = ".";
                }
            }
        }
        int entrance = entrances.keySet().iterator().next();
        stringMap[entrance][entrances.get(entrance)] = "+";

        growForests(stringMap);

        return stringMap;
    }
}
