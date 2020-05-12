package locations;

import java.util.Map;

public class Location {

    private final String name;
    private final int width;
    private final int height;
    private final int enemies;
    private final LocationType locationType;
    private final Map<Integer, String> exits;

    public Location(String name,
            int width,
            int height,
            int enemies,
            LocationType locationType,
            Map<Integer, String> exits) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.enemies = enemies;
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

    public LocationType getLocationType() {
        return locationType;
    }

    public Map<Integer, String> getExits() {
        return exits;
    }
}
