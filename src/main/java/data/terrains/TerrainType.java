package data.terrains;

import java.io.Serializable;

public enum TerrainType implements Serializable {

    EMPTY(" "),
    WALL("#"),
    GROUND("."),
    FOREST("f"),
    DOOR("d"),
    TRAP("ꭙ"),
    WATER("="),
    ITEM("o"),
    UNIQUE_ITEM("Ϯ"),
    LIZARD("k"),
    PREDATOR("Ψ"),
    WHEREWOLF("w"),
    PLAYER("@");


    private final String stamp;

    TerrainType(String stamp) {
        this.stamp = stamp;
    }

    public String getStamp() {
        return stamp;
    }


}
