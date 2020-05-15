package data.terrains;

import java.awt.Color;

import static data.other.Colors.GROUND_CANYON;
import static data.other.Colors.GROUND_DCAVE;
import static data.other.Colors.GROUND_PLUTO;
import static data.other.Colors.GROUND_PURPLE_HAZE;
import static data.other.Colors.WALL_CANYON;
import static data.other.Colors.WALL_DCAVE;
import static data.other.Colors.WALL_PLUTO;
import static data.other.Colors.WALL_PURPLE_HAZE;

public enum TerrainTypeColors {

    PLUTO(WALL_PLUTO, GROUND_PLUTO),
    DCAVE(WALL_DCAVE, GROUND_DCAVE),
    CANYON(WALL_CANYON, GROUND_CANYON),
    PURPLE_HAZE(WALL_PURPLE_HAZE, GROUND_PURPLE_HAZE);

    private final Color wall;
    private final Color ground;

    TerrainTypeColors(Color wall, Color ground) {
        this.wall = wall;
        this.ground = ground;
    }

    public Color getWallPaint() {
        return wall;
    }

    public Color getGroundPaint() {
        return ground;
    }
}
