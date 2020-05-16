package locations;

import java.util.Map;

public class Location {

    private final String name;
    private final int width;
    private final int height;
    private final int enemies;
    private final int difficulty;
    private final String terrainType;
    private final String locationType;
    private final Map<Integer, String> exits;

    public Location(String name,
            String width,
            String height,
            String enemies,
            String difficulty, String terrainType,
            String locationType,
            Map<Integer, String> exits) {
        this.name = name;
        this.width = Integer.parseInt(width);
        this.height = Integer.parseInt(height);
        this.enemies = Integer.parseInt(enemies);
        this.difficulty = Integer.parseInt(difficulty);
        this.terrainType = terrainType;
        this.locationType = locationType;
        this.exits = exits;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEnemies() {
        return enemies;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getLocationType() {
        return locationType;
    }

    public Map<Integer, String> getExits() {
        return exits;
    }

    public String getTerrainType() {
        return terrainType;
    }
}
