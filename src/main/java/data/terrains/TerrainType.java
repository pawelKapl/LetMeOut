package data.terrains;

public enum TerrainType {
    WALL("#"),
    GROUND("."),
    FOREST("f"),
    DOOR("d"),
    ITEM("o"),
    UNIQUE_ITEM("Ϯ"),
    LIZARD("k"),
    PLAYER("@");

    private final String stamp;

    TerrainType(String stamp) {
        this.stamp = stamp;
    }

    public String getStamp() {
        return stamp;
    }


}
