package data.gameEngine.pathfinding;

import data.movables.Movable;

public interface TileBasedMap {

    int getWidthInTiles();
    int getHeightInTiles();
    boolean blocked(Movable movable, int x, int y);
    float getCost(Movable movable, int sx, int sy, int tx, int ty);
    void pathFinderVisited(int x, int y);
}
