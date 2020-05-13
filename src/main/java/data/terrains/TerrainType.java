package data.terrains;

import java.io.Serializable;

public enum TerrainType implements Serializable {


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
